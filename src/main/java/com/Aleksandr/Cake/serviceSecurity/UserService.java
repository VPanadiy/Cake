package com.Aleksandr.Cake.serviceSecurity;

import com.Aleksandr.Cake.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
