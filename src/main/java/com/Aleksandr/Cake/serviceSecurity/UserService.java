package com.Aleksandr.Cake.serviceSecurity;

import com.Aleksandr.Cake.model.User;

public interface UserService {
	User findUserByEmail(String email);
	void saveUser(User user);
}
