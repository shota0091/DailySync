package com.example.demo.controller;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    
    @GetMapping
    public List<UserEntity> getUser() {
        return userService.getUserList();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userdto,BindingResult result) {
    	if(result.hasErrors()) {
    		List<String> errors = result.getFieldErrors().stream()
    	            .map(err -> err.getField() + ": " + err.getDefaultMessage())
    	            .collect(Collectors.toList());
    	        return ResponseEntity.badRequest().body(errors);
    		
    	}
    	UserEntity user = new UserEntity();
    	user.setName(userdto.getName());
    	user.setPassword(userdto.getPassword());
    	
        userService.saveUser(user);
        return ResponseEntity.ok("ユーザー作成成功");
    	
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
    

    @PatchMapping("/{id}")
    public void editPatchUser(@PathVariable Long id, @RequestBody UserEntity user) {
        userService.editUser(id, user);
    }

    @PutMapping("/{id}")
    public void editPutUser(@PathVariable Long id, @RequestBody UserEntity user) {
        user.setId(id);
        userService.saveUser(user);
    }
}