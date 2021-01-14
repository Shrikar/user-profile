package com.exostar.userprofile.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table (name = "users")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 298243862557482905L;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private Date dateOfBirth;

    private Integer age;

    private String country;
}
