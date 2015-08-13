package uk.co.icfuture.mvc.service;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.model.User;

public interface UserService {
	public User getUser(String username) throws ItemNotFoundException;

}
