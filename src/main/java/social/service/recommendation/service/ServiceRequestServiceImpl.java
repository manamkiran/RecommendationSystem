package social.service.recommendation.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.User;
import social.service.recommendation.repository.ServiceRequestRepository;
import social.service.recommendation.types.CategoryAndSubCategories;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {

	@Autowired
	private ServiceRequestRepository serviceRequestRepository;

	@Override
	public void createServiceRequest(ServiceRequest serviceRequest) {
		serviceRequestRepository.save(serviceRequest);
	}

	@Override
	public ServiceRequest findById(Long id) {
		return serviceRequestRepository.findById(id).get();
	}

	@Override
	public List<ServiceRequest> findServiceRequestsByCreatorNameOrderByCreatedDateDesc(User user) {
		return serviceRequestRepository.findByCreatorOrderByCreatedDateDesc(user);
	}

	@Override
	public List<ServiceRequest> findRecentlyAddedServiceRequests() {
		return serviceRequestRepository.findTop10ByOrderByCreatedDateDesc();
	}

	@Override
	@Transactional
	public void updateQuantityAcquiredServiceRequest(long id, long quantityAcquired) {
		serviceRequestRepository.updateQuantityAcquiredServiceRequest(id, quantityAcquired);

	}

	@Override
	public List<ServiceRequest> findByFilter(CategoryAndSubCategories filter) {
		if (!StringUtils.isEmpty(filter.getSubCategory())
				&& !CategoryAndSubCategories.ALL.equals(filter.getSubCategory())) {
			return serviceRequestRepository.findTop100BySubCategoryOrderByCreatedDateDesc(filter.getSubCategory());
		}
		if (!StringUtils.isEmpty(filter.getCategory()) && !CategoryAndSubCategories.ALL.equals(filter.getSubCategory())) {
			return serviceRequestRepository.findTop100ByCategoryOrderByCreatedDateDesc(filter.getCategory());
		}
		return serviceRequestRepository.findTop100ByOrderByCreatedDateDesc();
	}

}
