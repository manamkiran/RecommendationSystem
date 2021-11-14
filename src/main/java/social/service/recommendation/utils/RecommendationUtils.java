package social.service.recommendation.utils;

public class RecommendationUtils {

	/*
	 * public static Map<String, Double>
	 * getAvgRatingsListFromCategoryLists(List<CategoryRatings> categoryRatings) {
	 * return categoryRatings.parallelStream().collect(Collectors.groupingBy(
	 * CategoryRatings::getCategory,
	 * Collectors.averagingInt(CategoryRatings::getRating))); }
	 * 
	 * public static List<String>
	 * getTopNCategoriesBasedOnRatings(List<CategoryRatings> categoryRatings , int
	 * n) { Map<String, Double> categoryWitAvgRatingMap = RecommendationUtils
	 * .getAvgRatingsListFromCategoryLists(categoryRatings); return
	 * categoryWitAvgRatingMap.entrySet().stream()
	 * .sorted(Map.Entry.comparingByValue()) .filter(entry -> entry.getValue() >
	 * 3.0) .limit(n).map(entry -> entry.getKey()) .collect(Collectors.toList()); }
	 */
}
