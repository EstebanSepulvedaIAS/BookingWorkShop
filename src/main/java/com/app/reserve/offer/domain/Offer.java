package com.app.reserve.offer.domain;

import com.app.reserve.offer.domain.valueObjects.*;
import com.app.reserve.shared.domain.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public class Offer {
    private final Id id;
    private OfferQuantity quantity;
    private OfferAvailableQuantity availableQuantity;
    private final OfferDate date;
    private OfferState state;
    private final OfferTitle title;

    public Offer(String id, int quantity, int availableQuantity, LocalDateTime date, boolean state, String title) {
        this.id = new Id(id);
        this.quantity = new OfferQuantity(quantity);
        this.availableQuantity = new OfferAvailableQuantity(availableQuantity);
        this.date = new OfferDate(date);
        this.state = new OfferState(state);
        this.title = new OfferTitle(title);
    }

    public static Offer create(String id, int quantity, int availableQuantity, LocalDateTime date, boolean state, String title){
        return new Offer(id, quantity, availableQuantity, date, state, title);
    }

    public UUID getId() {
        return id.value();
    }

    public int getQuantity(){
        return quantity.quantity();
    }

    public int getAvailableQuantity(){
        return availableQuantity.availableQuantity();
    }

    public LocalDateTime getDate(){
        return date.date();
    }

    public boolean getState(){
        return state.state();
    }

    public String getTitle(){
        return title.title();
    }

    public void updateState(boolean newState){
        this.state = new OfferState(newState);
    }

    public void reduceQuantityOffer(){
        this.availableQuantity = this.availableQuantity.reducePersonsForOffer();
    }

    public void incrementQuantityOffer(){this.availableQuantity = this.availableQuantity.incrementPersonsForOffer();}

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", availableQuantity=" + availableQuantity +
                ", date=" + date +
                ", state=" + state +
                ", title=" + title +
                '}';
    }
}
