package uk.co.icfuture.mvc.dao;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	@Override
	public User getUser(String username) {
		return getItem("username", username);
	}

}
