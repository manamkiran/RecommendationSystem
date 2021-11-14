package social.service.recommendation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.User;
import social.service.recommendation.entity.UserAnalytics;
import social.service.recommendation.repository.ServiceRequestRepository;
import social.service.recommendation.repository.UserAnalyticsRepository;
import social.service.recommendation.repository.UserRepository;

@Service
public class RecommendationServiceImpl implements RecommendationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	private UserAnalyticsRepository userAnalyticsRepository;

	@Override
	public List<ServiceRequest> getRecommendedServiceRequests(String userName) {
		UserAnalytics userAnalytics = userAnalyticsRepository.findById(userName).get();
		if (userAnalytics != null) {
			List<String> topCategories = userAnalytics.getTopCategories();
			if (!topCategories.isEmpty()) {
				return serviceRequestRepository.findTop10BySubCategoryInOrderByCreatedDateDesc(topCategories);
			}
			User user = userRepository.findById(userName).get();
			List<String> interestedCategories = user.getInterestedCategories();
			if (!interestedCategories.isEmpty()) {
				return serviceRequestRepository.findTop10ByCategoryInOrderByCreatedDateDesc(interestedCategories);
			}

			List<String> suggestedCategories = userAnalytics.getSuggestedCategories();
			if (!suggestedCategories.isEmpty()) {
				return serviceRequestRepository.findTop10BySubCategoryInOrderByCreatedDateDesc(suggestedCategories);
			}
		}
		return serviceRequestRepository.findTop10ByOrderByCreatedDateDesc();
	}

	@Override
	public List<ServiceRequest> getRecommendedServiceRequests(User user) {
		return getRecommendedServiceRequests(user.getUserName());
	}
}
