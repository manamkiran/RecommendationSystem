package social.service.recommendation.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.entity.User;
import social.service.recommendation.repository.ServiceResponseRepository;

@Service
public class ServiceResponseServiceImpl implements ServiceResponseService {

	@Autowired
	private ServiceResponseRepository serviceResponseRepository;

	@Override
	public void createServiceResponse(ServiceResponse serviceResponse) {
		serviceResponseRepository.save(serviceResponse);
	}

	@Override
	public ServiceResponse findById(Long id) {
		return serviceResponseRepository.findById(id).get();
	}

	@Override
	public List<ServiceResponse> findServiceResponsesByUserNameOrderByCreatedDateDesc(User user) {
		return serviceResponseRepository.findByUserOrderByCreatedDateDesc(user);
	}


	@Override
	public List<ServiceResponse> findServiceResponsesByServiceRequest(ServiceRequest serviceRequest) {
		return serviceResponseRepository.findByServiceRequest(serviceRequest);
	}

	@Override
	@Transactional
	public void updateRatingAndCommentOfServiceResponse(long id, int rating, String comment) {
		serviceResponseRepository.updateRatingAndCommentOfServiceResponse(id, rating, comment);
		
	}

}
