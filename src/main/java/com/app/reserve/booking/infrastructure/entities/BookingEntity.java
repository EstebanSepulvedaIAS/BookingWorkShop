package com.app.reserve.booking.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
public class BookingEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("status")
    private String status;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Nullable
    @Column("cancelled_at")
    private LocalDateTime cancelledAt;

    @Column("offer_id")
    private UUID offerId;

    @Column("user_id")
    private UUID userId;

}
