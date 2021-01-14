package com.exostar.userprofile.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.exostar.userprofile.entity.UserEntity;
import com.exostar.userprofile.service.UserService;

@RestController
@RequestMapping ("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping (value = "/email", method = RequestMethod.GET)
    public ResponseEntity<UserEntity> getUserByEmail(@Valid @RequestParam (name = "email") String email) {
        Optional<UserEntity> userEntityOptional = userService.findByUserEmail(email);
        if (userEntityOptional.isPresent()) {
            return ResponseEntity.ok(userEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping (value = "firstName", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> findByFirstName(@Valid @RequestParam (name = "firstName") String firstName) {
        return ResponseEntity.ok(userService.findByFirstName(firstName));
    }
}
