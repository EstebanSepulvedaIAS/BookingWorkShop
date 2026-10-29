package com.app.reserve.role.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Role\"")
public class RoleEntity {
    @Id
    @Column("id")
    private UUID id;

    @Column("name")
    private String name;
}
