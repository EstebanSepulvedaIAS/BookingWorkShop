package com.app.reserve.user.infrastructure.entities;

import com.app.reserve.user.domain.User;

public class UserMapper {

    private UserMapper(){}

    public static UserEntity toUserEntity(User user){
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public static User toDomainUser(UserEntity userEntity){
        return User.create(
                userEntity.getId().toString(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }
}
