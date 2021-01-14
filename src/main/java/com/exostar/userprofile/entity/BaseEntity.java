package com.exostar.userprofile.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedBy
    @Column (updatable = false)
    private String createdBy;

    @CreatedDate
    @Column (updatable = false,insertable = false)
    private Date createAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private Date updateAt;
}
