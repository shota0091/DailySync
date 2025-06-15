package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

public class UserService {
	@Autowired
    private UserRepository repository;

    public List<UserEntity> getUserList() {
        return repository.findAll();
    }

    public UserEntity getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveUser(UserEntity user) {
        repository.save(user);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    public void editUser(Long id, UserEntity newUser) {
        UserEntity useritems = repository.findById(id).orElse(null);
        if (useritems == null) return;

        if (newUser.getName() != null) {
            useritems.setName(newUser.getName());
        }
        if (newUser.getPassword() != null) {
            useritems.setPassword(newUser.getPassword());
        }
        repository.save(useritems);
    }
}
