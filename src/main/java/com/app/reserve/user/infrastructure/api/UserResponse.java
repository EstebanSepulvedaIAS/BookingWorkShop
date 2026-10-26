package com.app.reserve.user.infrastructure.api;

import java.util.UUID;

public record UserResponse(UUID id, String email, String password, String name) {
}
