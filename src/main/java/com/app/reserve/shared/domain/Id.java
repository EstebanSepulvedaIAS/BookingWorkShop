package com.app.reserve.shared.domain;

import java.util.UUID;

public class Id {

    private final UUID id;

    public Id(String id) {
        this.id = UUID.fromString(id);
    }

    public UUID value() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Id) && ((Id) obj).id.equals(this.id);
    }
}
