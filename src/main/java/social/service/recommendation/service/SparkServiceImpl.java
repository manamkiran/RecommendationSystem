package social.service.recommendation.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Value;
import scala.Tuple2;
import social.service.recommendation.entity.UserAnalytics;
import social.service.recommendation.repository.UserAnalyticsRepository;
import social.service.recommendation.utils.Utils;

@Service
public class SparkServiceImpl implements SparkService {

	@Autowired
	private UserAnalyticsRepository userAnalyticsRepository;

	@Override
	public void runSparkJob() {

		try (SparkSession spark = SparkSession.builder().appName("RecommendationJob").master("local[*]")
				.getOrCreate()) {
			Dataset<Row> serviceResponseDF = spark.read().format("jdbc")
					.option("url", "jdbc:mysql://localhost:3306/ssrecommendation").option("dbtable", "serviceresponse")
					.option("user", "root").option("password", "root").load();
			serviceResponseDF.createOrReplaceTempView("serviceResponse");

			Dataset<Row> userCategoryRatings = spark
					.sql("select userId,category,rating from serviceResponse where rating != 0");
			userCategoryRatings.createOrReplaceTempView("userCategoryRatings");

			Dataset<Row> userAverageRatings = spark.sql(
					"select userId,category,round(avg(rating),2) as ratings from userCategoryRatings group by userId,category");
			userAverageRatings.createOrReplaceTempView("userAverageRatings");
			userAverageRatings.cache();

			JavaPairRDD<String, String> top3RatingsRDD = populateTop3RatingRDD(spark);
			JavaPairRDD<String, String> bottom3RatingsRDD = populateBottom3RatingRDD(spark);
			List<UserAnalytics> userAnalyticsList = top3RatingsRDD.join(bottom3RatingsRDD)
					.map(tuple -> new UserAnalytics(tuple._1, tuple._2._1, tuple._2._2)).collect();

			for (UserAnalytics currentUserAnalytics : userAnalyticsList) {
				findClosestHaterFriendAndAddHisSuggestions(currentUserAnalytics, userAnalyticsList);
			}

			userAnalyticsRepository.deleteAll();
			userAnalyticsRepository.saveAll(userAnalyticsList);
			

		}
	}

	private JavaPairRDD<String, String> populateBottom3RatingRDD(SparkSession spark) {
		Dataset<Row> userBottom3Ratings = spark.sql(
				"select userId,category,ratings, RANK() OVER (PARTITION BY userId ORDER BY ratings asc) AS rank from userAverageRatings where ratings < 3.0 order by userId,ratings asc")
				.filter("rank<4");

		JavaPairRDD<String, String> bottom3RatingsRDD = userBottom3Ratings.toJavaRDD()
				.mapToPair(row -> new Tuple2<>(row.getString(0), row.getDouble(2) + Utils.AMPERSAND + row.getString(1)))
				.reduceByKey((x1, x2) -> x1 + Utils.SEMICOLON + x2);
		return bottom3RatingsRDD;
	}

	private JavaPairRDD<String, String> populateTop3RatingRDD(SparkSession spark) {
		Dataset<Row> userTop3Ratings = spark.sql(
				"select userId,category,ratings, RANK() OVER (PARTITION BY userId ORDER BY ratings desc) AS rank from userAverageRatings where ratings >= 3.0 order by userId,ratings desc")
				.filter("rank < 4");

		JavaPairRDD<String, String> top3RatingsRDD = userTop3Ratings.toJavaRDD()
				.mapToPair(row -> new Tuple2<>(row.getString(0), row.getDouble(2) + Utils.AMPERSAND + row.getString(1)))
				.reduceByKey((x1, x2) -> x1 + Utils.SEMICOLON + x2);
		return top3RatingsRDD;
	}

	private void findClosestHaterFriendAndAddHisSuggestions(UserAnalytics currentUserAnalytics,
			List<UserAnalytics> userAnalyticsList) {
		List<RatingUser> ratingUserList = userAnalyticsList.stream()
				.filter(userAnalytics -> userAnalytics != currentUserAnalytics)
				.map(userAnalytics -> new RatingUser(getSimilarityRating(userAnalytics, currentUserAnalytics),
						userAnalytics))
				.sorted(Comparator.reverseOrder()).limit(1).collect(Collectors.toList());

		if (ratingUserList.size() == 1) {
			RatingUser friend = ratingUserList.get(0);
			currentUserAnalytics.setFriend(friend.getUserAnalytics().getUserName());
			currentUserAnalytics.setSuggestedCategory1(friend.getUserAnalytics().getTopCategory1());
			currentUserAnalytics.setSuggestedCategory2(friend.getUserAnalytics().getTopCategory2());
			currentUserAnalytics.setSuggestedCategory3(friend.getUserAnalytics().getTopCategory3());
		}

	}

	private int getSimilarityRating(UserAnalytics userAnalytics, UserAnalytics currentUserAnalytics) {
		int similarity = 0;
		List<String> bottomCategories = userAnalytics.getBottomCategories();
		if (bottomCategories.contains(currentUserAnalytics.getBottomCategory1())) {
			similarity += 5;
		}
		if (bottomCategories.contains(currentUserAnalytics.getBottomCategory2())) {
			similarity += 4;
		}
		if (bottomCategories.contains(currentUserAnalytics.getBottomCategory3())) {
			similarity += 2;
		}
		return similarity;
	}

}

@Value
class RatingUser implements Comparable<RatingUser> {

	private int similarity;

	private UserAnalytics userAnalytics;

	@Override
	public int compareTo(RatingUser o) {
		return Integer.compare(similarity, o.similarity);
	}
}

// Dataset<Row> results=spark.sql("select serviceRequestId,avg(rating) as
// avgRating, count(*) as totalRatings from serviceresponse group by
// serviceRequestId having totalRatings > 2 order by avgRating desc");
