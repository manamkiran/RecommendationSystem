package social.service.recommendation.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import social.service.recommendation.entity.ServiceRequest;
import social.service.recommendation.entity.ServiceResponse;
import social.service.recommendation.entity.User;

public interface ServiceResponseRepository extends JpaRepository<ServiceResponse, Long> {

	List<ServiceResponse> findByUser(User user);

	List<ServiceResponse> findByUser(User user, Pageable pageable);
	
	List<ServiceResponse> findByServiceRequest(ServiceRequest serviceRequest);

	List<ServiceResponse> findByUserOrderByCreatedDateDesc(User user);
	
	@Modifying
	@Query("update ServiceResponse sr set sr.rating = ?2,sr.comment = ?3 where sr.id = ?1")
	void updateRatingAndCommentOfServiceResponse(long id, int rating,String comment);
}
