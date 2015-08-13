package uk.co.icfuture.mvc.service;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.icfuture.mvc.dao.UserDao;
import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User getUser(String username) throws ItemNotFoundException {
		User user = userDao.getUser(username);
		if (user == null) {
			throw new ItemNotFoundException("user", username);
		} else {
			Hibernate.initialize(user.getUserRoles());
			return user;
		}
	}

}
