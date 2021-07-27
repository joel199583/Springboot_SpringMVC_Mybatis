package com.example.demo.entity;

import org.springframework.lang.NonNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name="id", unique=true, nullable = false, length = 50)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String name;
    @NonNull
    @NotEmpty
    @Column(name = "account", nullable = false, length = 50)
    private String account;
    @NonNull
    @NotEmpty
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Column(name = "create_time")
    private Date createTime;
}
