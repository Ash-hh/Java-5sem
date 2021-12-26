package com.shabunya.carrent.services;


import com.shabunya.carrent.model.Role;
import com.shabunya.carrent.model.User;
import com.shabunya.carrent.model.UserRole;
import com.shabunya.carrent.repository.UserRepository;

import com.shabunya.carrent.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;


    public UserServiceImpl(UserRepository userRepository,UserRoleRepository userRoleRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public User saveUser(User user)
    {
        UserRole userRole = userRoleRepository.findByName(Role.ROLE_USER);
        if(user.getUserRole() == null){
            user.setUserRole(userRole);
        }
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }
    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByLoginAndPassword(String login, String password){
        User user = findByLogin(login);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    public boolean existsUserByLogin(String login)
    {
        return userRepository.existsUserByLogin(login);
    }
    public boolean existsUserByLoginAndPassword(String login, String password){
        return findByLoginAndPassword(login, password) != null;
    }
    @Override
    public User findById(Long id){
        return userRepository.getById(id);
    }

    @Override
    public UserRole getRoleByName(Role role){
        return userRoleRepository.findByName(role);
    }

    @Override
    public void deleteUser(Long Id) {
        userRepository.deleteById(Id);
    }

}
