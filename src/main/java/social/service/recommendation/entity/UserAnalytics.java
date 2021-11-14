package social.service.recommendation.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import social.service.recommendation.utils.Utils;

@Entity
@Data
public class UserAnalytics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String userName;

	private String topCategory1;

	private String topCategory2;

	private String topCategory3;

	private String bottomCategory1;

	private String bottomCategory2;

	private String bottomCategory3;

	private String friend;

	private String suggestedCategory1;

	private String suggestedCategory2;

	private String suggestedCategory3;

	public UserAnalytics(String userName) {
		this.userName = userName;
	}

	public UserAnalytics() {

	}

	public UserAnalytics(String userName, String topRatings, String bottomRatings) {
		this.userName = userName;

		List<String> ratingsData = Arrays.asList(topRatings.split(Utils.SEMICOLON)).stream()
				.sorted(Comparator.reverseOrder()).limit(3).collect(Collectors.toList());
		if (ratingsData.size() > 0) {
			topCategory1 = ratingsData.get(0).split(Utils.AMPERSAND)[1];
			if (ratingsData.size() > 1) {
				topCategory2 = ratingsData.get(1).split(Utils.AMPERSAND)[1];
				if (ratingsData.size() > 2) {
					topCategory3 = ratingsData.get(2).split(Utils.AMPERSAND)[1];
				}
			}
		}

		List<String> bottomratingsData = Arrays.asList(bottomRatings.split(Utils.SEMICOLON)).stream().sorted().limit(3)
				.collect(Collectors.toList());
		if (bottomratingsData.size() > 0) {
			bottomCategory1 = bottomratingsData.get(0).split(Utils.AMPERSAND)[1];
			if (bottomratingsData.size() > 1) {
				bottomCategory2 = bottomratingsData.get(1).split(Utils.AMPERSAND)[1];
				if (bottomratingsData.size() > 2) {
					bottomCategory3 = bottomratingsData.get(2).split(Utils.AMPERSAND)[1];
				}
			}
		}
	}

	public void populateCategories() {

	}

	public List<String> getTopCategories() {
		return getListFromInput(topCategory1, topCategory2, topCategory3);
	}

	public List<String> getBottomCategories() {
		return getListFromInput(bottomCategory1, bottomCategory2, bottomCategory3);
	}

	public List<String> getSuggestedCategories() {
		return getListFromInput(suggestedCategory1, suggestedCategory2, suggestedCategory3);
	}

	private List<String> getListFromInput(String... args) {
		return Arrays.asList(args).stream().filter(Objects::nonNull).collect(Collectors.toList());
	}
}
