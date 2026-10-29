package com.app.reserve.offer.infrastructure.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @CreatedDate
    private LocalDateTime date;

    @Column(name = "state")
    private boolean state;

    @Column(name = "title")
    private String title;
}
