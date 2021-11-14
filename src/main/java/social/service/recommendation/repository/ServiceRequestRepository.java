package social.service.recommendation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.User;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

	List<ServiceRequest> findByCreator(User creatorName);

	List<ServiceRequest> findByCreatorOrderByCreatedDateDesc(User user);

	List<ServiceRequest> findByCategory(String category);
	
	List<ServiceRequest> findTop10BySubCategoryInOrderByCreatedDateDesc(List<String> subCategory);
	
	List<ServiceRequest> findTop10ByCategoryInOrderByCreatedDateDesc(List<String> category);

	List<ServiceRequest> findTop10ByOrderByCreatedDateDesc();
	
	List<ServiceRequest> findTop100ByOrderByCreatedDateDesc();
	
	List<ServiceRequest> findTop100ByCategoryOrderByCreatedDateDesc(String category);
	
	List<ServiceRequest> findTop100BySubCategoryOrderByCreatedDateDesc(String subCategory);

	@Modifying
	@Query("update ServiceRequest sr set sr.totalQuantityAcquired = ?2 where sr.id = ?1")
	void updateQuantityAcquiredServiceRequest(long id, long quantityAcquired);

}
