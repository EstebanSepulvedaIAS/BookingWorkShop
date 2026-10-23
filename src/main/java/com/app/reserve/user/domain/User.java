package com.app.reserve.user.domain;

import com.app.reserve.role.domain.Role;
import com.app.reserve.shared.domain.Id;
import com.app.reserve.user.domain.valueObjects.UserEmail;
import com.app.reserve.user.domain.valueObjects.UserName;
import com.app.reserve.user.domain.valueObjects.UserPassword;
import com.app.reserve.user.domain.valueObjects.UserRoles;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    private final Id id;
    private final UserEmail email;
    private UserPassword password;
    private UserName name;
    private final UserRoles roles;

    public User(String id, String email, String password, String name, UserRoles roles){
        this.id = new Id(id);
        this.name = new UserName(name);
        this.password = new UserPassword(password);
        this.email = new UserEmail(email);
        this.roles = roles;
    }

    public static User create(String id, String email, String password, String name){
        return new User(id, email, password, name, new UserRoles(new HashSet<>()));
    }

    public UUID getId() {
        return id.value();
    }

    public String getEmail() {
        return email.email();
    }

    public String getPassword() {
        return password.password();
    }

    public String getName() {
        return name.name();
    }

    public Set<Role> getRoles() {
        return roles.roles();
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public void updatePassword(String newPassword){
        this.password = new UserPassword(newPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id.value() +
                ", email=" + email.email() +
                ", password=" + password.password() +
                ", name=" + name.name() +
                '}';
    }
}
