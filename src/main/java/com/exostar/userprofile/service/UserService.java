package com.exostar.userprofile.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import com.exostar.userprofile.constant.OperatorEnum;
import com.exostar.userprofile.dao.UserDAO;
import com.exostar.userprofile.entity.UserEntity;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public Optional<UserEntity> findByUserEmail(String email) {
        return Optional.ofNullable(userDAO.findByEmail(email));
    }

    public List<UserEntity> findByFirstName(String firstName) {
        return userDAO.findByFirstName(firstName);
    }

    public List<UserEntity> findByLastName(String lastName) {
        return userDAO.findByLastName(lastName);
    }

    public List<UserEntity> findByAge(String operator, Integer age) {
        switch (OperatorEnum.fromName(operator)) {
            case eq:
                return userDAO.findByAgeEquals(age);
            case ge:
                return userDAO.findByAgeGreaterThanEqual(age);
            case gt:
                userDAO.findByAgeGreaterThan(age);
            case le:
                userDAO.findByAgeLessThanEqual(age);
            case lt:
                return userDAO.findByAgeLessThan(age);
            default:
                return Arrays.asList();
        }
    }
}
