package com.exostar.userprofile.batch.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.CollectionUtils;
import com.exostar.userprofile.Exception.IllegalCountryCodeException;
import com.exostar.userprofile.Exception.IllegalDateException;
import com.exostar.userprofile.VO.UserView;
import com.exostar.userprofile.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserItemProcessor implements ItemProcessor<UserView, UserEntity> {
    @Override
    public UserEntity process(UserView userView) throws IllegalDateException, ParseException, IllegalCountryCodeException {
        UserEntity userEntity = UserEntity.builder().country(getLocaleCountry(userView.getCountryCode()))
                                          .dateOfBirth(getDate(userView.getDateOfBirth())).age(calculateAge(userView.getDateOfBirth()))
                                          .email(userView.getEmail()).firstName(userView.getFirstName()).lastName(userView.getLastName()).build();
        log.info("Transformed UserView: {} to UserEntity: {}", userView, userEntity);
        return userEntity;
    }

    private String getLocaleCountry(String countryCode) throws IllegalCountryCodeException {
        Locale locale = Arrays.stream(Locale.getISOCountries()).filter(code -> code.equalsIgnoreCase(countryCode)).findAny()
                     .map(code -> new Locale("", code)).orElseThrow(IllegalCountryCodeException::new);
        return locale.getDisplayCountry();
    }

    private Date getDate(String stringDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(stringDate);
    }

    private Integer calculateAge(String stringDate) throws IllegalDateException, ParseException {
        Date date = getDate(stringDate);
        if (date.after(new Date())) {
            throw new IllegalDateException("Illegal Date of Birth: " + date);
        }
        return Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
    }
}
