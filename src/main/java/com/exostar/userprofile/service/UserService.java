package com.exostar.userprofile.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exostar.userprofile.dao.UserDAO;
import com.exostar.userprofile.entity.UserEntity;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public Optional<UserEntity> findByUserEmail(String email) {
        return Optional.ofNullable(userDAO.findByEmail(email));
    }
}
