package com.app.reserve.offer.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"Offer\"")
public class OfferEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Column(name = "state")
    private boolean state;

    @Column(name = "title")
    private String title;
}
