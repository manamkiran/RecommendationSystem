package social.service.recommendation.service;

import java.util.List;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.User;

public interface RecommendationService {
	
	List<ServiceRequest> getRecommendedServiceRequests(String userName);
	
	List<ServiceRequest> getRecommendedServiceRequests(User user);
}
