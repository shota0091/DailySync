package com.example.demo.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;

@RestController
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO request) {
		UserEntity user = userService.findByName(request.getName());

		if (user != null && user.getPassword().equals(request.getPassword())) {
			String token = jwtUtil.generateToken(user.getName());
			return ResponseEntity.ok(Collections.singletonMap("token", token));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}
}
