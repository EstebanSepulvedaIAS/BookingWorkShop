package com.app.reserve.offer.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offer")
public class OfferEntity {

    @Id
    @Column("id")
    private UUID id;

    @Column("quantity")
    private int quantity;

    @Column("available_quantity")
    private int availableQuantity;

    @Column("date")
    @CreatedDate
    private LocalDateTime date;

    @Column("state")
    private boolean state;

    @Column("title")
    private String title;
}
