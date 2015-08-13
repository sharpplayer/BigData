package uk.co.icfuture.mvc.dao;

import uk.co.icfuture.mvc.model.User;

public interface UserDao {

	public User getUser(String username);
}
