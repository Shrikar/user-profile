package com.exostar.userprofile.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.exostar.userprofile.constant.OperatorEnum;
import com.exostar.userprofile.entity.UserEntity;
import com.exostar.userprofile.service.UserService;

@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping (value = "/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<UserEntity> getUserByEmail(@Valid @PathVariable (name = "email") String email) {
        Optional<UserEntity> userEntityOptional = userService.findByUserEmail(email);
        if (userEntityOptional.isPresent()) {
            return ResponseEntity.ok(userEntityOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping (value = "/firstName/{firstName}", method = RequestMethod.GET)
    public ResponseEntity findByFirstName(@Valid @PathVariable (name = "firstName") String firstName) {
        return ResponseEntity.ok().body(userService.findByFirstName(firstName));
    }

    @RequestMapping (value = "/lastName/{lastName}", method = RequestMethod.GET)
    public ResponseEntity findByLastName(@Valid @PathVariable (name = "lastName") String lastName) {
        return ResponseEntity.ok().body(userService.findByLastName(lastName));
    }

    @RequestMapping (value = "/age/{operator}/{age}", method = RequestMethod.GET)
    public ResponseEntity findByAge(@Valid @PathVariable (name = "operator") String operator, @Valid @PathVariable (name = "age") Integer age) {
        return ResponseEntity.ok().body(userService.findByAge(operator, age));
    }

}
