package social.service.recommendation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import social.service.recommendation.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	List<User> findByUserNameAndPassword(String userName,String password);

}
