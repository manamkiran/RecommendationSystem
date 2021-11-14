package social.service.recommendation.service;

import java.util.List;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.User;
import social.service.recommendation.types.CategoryAndSubCategories;

public interface ServiceRequestService {
	
	void createServiceRequest(ServiceRequest serviceRequest);
	
	ServiceRequest findById(Long id);

	List<ServiceRequest> findServiceRequestsByCreatorNameOrderByCreatedDateDesc(User user);

	List<ServiceRequest> findRecentlyAddedServiceRequests();

	void updateQuantityAcquiredServiceRequest(long id, long l);

	List<ServiceRequest> findByFilter(CategoryAndSubCategories filter);

}
