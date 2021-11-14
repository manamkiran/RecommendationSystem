package social.service.recommendation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import social.service.recommendation.entity.User;
import social.service.recommendation.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void createUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findUserByUserNameAndPassword(String userName, String password) {
		List<User> users = userRepository.findByUserNameAndPassword(userName, password);
		return users.size() == 1 ? users.get(0) : null;
	}

	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findById(userName).get();
	}

}
