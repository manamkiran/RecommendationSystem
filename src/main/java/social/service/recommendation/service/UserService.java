package social.service.recommendation.service;

import social.service.recommendation.entity.User;

public interface UserService {
	
	void createUser(User user);
	
	User findUserByUserNameAndPassword(String userName,String password);

	User findUserByUserName(String userName);

}
