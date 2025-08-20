package com.metoeriver.library.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.metoeriver.library.user.dto.UserRequestDTO;
import com.metoeriver.library.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.createUser(userRequestDTO));
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.updateUser(userRequestDTO));
    }
    @GetMapping()
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping()
    public ResponseEntity<?> getUserById(@RequestParam Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
