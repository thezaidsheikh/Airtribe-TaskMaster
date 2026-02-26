package com.airtribe.task_master.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Entity
@Table(name = "users", schema = "user_schema")
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;


    @Digits(integer = 15, fraction = 0, message = "Phone number must be at most 15 digits")
    @Min(value = 1000000000L, message = "Phone number must be at least 10 digits")
    @Column(nullable = true)
    private Long phoneNum;

    @Column(nullable = false)
    private String password;
}
