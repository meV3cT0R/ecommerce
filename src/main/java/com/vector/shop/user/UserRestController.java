package com.vector.shop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping(path="/api",produces="application/json")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/rest")
    public boolean user(@RequestBody User user) {
        System.out.println(user);
        return true;
    }

    @GetMapping("/users")
    public ResponseEntity<User> show() {
        System.out.println("Inside show user");

        return new ResponseEntity<User>(userService.find("vector"),HttpStatus.OK);
    }
}
