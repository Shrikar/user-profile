package com.exostar.userprofile.VO;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserView {
    @Size (min = 3)
    private String firstName;

    @Valid
    private String lastName;

    @Email
    private String email;

    @Valid
    private String dateOfBirth;

    @Size (min = 2, max = 3)
    private String countryCode;
}
