package com.app.reserve.role.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"Role\"")
public class RoleEntity{
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", length = 255, unique = true, updatable = false)
    private String name;
}
