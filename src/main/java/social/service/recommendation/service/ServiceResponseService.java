package social.service.recommendation.service;

import java.util.List;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.entity.User;

public interface ServiceResponseService {

	void createServiceResponse(ServiceResponse serviceResponse);

	ServiceResponse findById(Long id);

	List<ServiceResponse> findServiceResponsesByUserNameOrderByCreatedDateDesc(User user);

	List<ServiceResponse> findServiceResponsesByServiceRequest(ServiceRequest serviceRequest);
	
	void updateRatingAndCommentOfServiceResponse(long id, int rating,String comment);

}
