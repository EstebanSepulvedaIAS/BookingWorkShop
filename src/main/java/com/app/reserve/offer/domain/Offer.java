package com.app.reserve.offer.domain;

import com.app.reserve.offer.domain.valueObjects.OfferDate;
import com.app.reserve.offer.domain.valueObjects.OfferQuantity;
import com.app.reserve.offer.domain.valueObjects.OfferState;
import com.app.reserve.offer.domain.valueObjects.OfferTitle;
import com.app.reserve.shared.domain.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public class Offer {
    private final Id id;
    private OfferQuantity quantity;
    private final OfferDate date;
    private OfferState state;
    private final OfferTitle title;

    public Offer(String id, int quantity, LocalDateTime date, boolean state, String title) {
        this.id = new Id(id);
        this.date = new OfferDate(date);
        this.state = new OfferState(state);
        this.title = new OfferTitle(title);
        this.quantity = new OfferQuantity(quantity);
    }

    public static Offer create(String id, int quantity, LocalDateTime date, boolean state, String title){
        return new Offer(id, quantity, date, state, title);
    }

    public UUID getId() {
        return id.value();
    }

    public int getQuantity(){
        return quantity.quantity();
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
        this.quantity = this.quantity.reducePersonsForOffer();
    }

    public void incrementQuantityOffer(){
        this.quantity = this.quantity.incrementPersonsForOffer();
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", date=" + date +
                ", state=" + state +
                ", title=" + title +
                '}';
    }
}
