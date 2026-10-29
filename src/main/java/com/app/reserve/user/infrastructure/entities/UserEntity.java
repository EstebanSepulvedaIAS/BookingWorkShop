package com.app.reserve.user.infrastructure.entities;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"User\"")
public class UserEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

}
