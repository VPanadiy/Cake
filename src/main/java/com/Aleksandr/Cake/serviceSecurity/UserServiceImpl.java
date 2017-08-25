package com.Aleksandr.Cake.serviceSecurity;

import java.util.Arrays;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Aleksandr.Cake.model.Role;
import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.repository.RoleRepository;
import com.Aleksandr.Cake.repository.UserRepository;


@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        LOGGER.info("Find user by email: " + user);
        return user;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        LOGGER.info("Save user in DB! " + user);
        userRepository.save(user);
    }

}
