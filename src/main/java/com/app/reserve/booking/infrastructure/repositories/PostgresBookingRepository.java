package com.app.reserve.booking.infrastructure.repositories;

import com.app.reserve.booking.domain.Booking;
import com.app.reserve.booking.domain.BookingRepository;
import com.app.reserve.booking.domain.exceptions.BookingException;
import com.app.reserve.booking.infrastructure.entities.BookingEntity;
import com.app.reserve.booking.infrastructure.entities.BookingMapper;
import com.app.reserve.offer.domain.exceptions.OfferNotFoundException;
import com.app.reserve.offer.infrastructure.entities.OfferEntity;
import com.app.reserve.offer.infrastructure.repositories.JpaOfferRepository;
import com.app.reserve.shared.domain.Id;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class PostgresBookingRepository implements BookingRepository {

    private final JpaBookingRepository jpaBookingRepository;
    private final JpaOfferRepository jpaOfferRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public PostgresBookingRepository(JpaBookingRepository jpaBookingRepository, JpaOfferRepository jpaOfferRepository, R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.jpaBookingRepository = jpaBookingRepository;
        this.jpaOfferRepository = jpaOfferRepository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    @Transactional
    public Mono<Void> save(Booking booking) {
        UUID bookingIdUUID = UUID.fromString(booking.getId().toString());
        UUID userIdUUID = UUID.fromString(booking.getUserId().toString());
        UUID offerIdUUID = UUID.fromString(booking.getOfferId().toString());
        return checkIdempotency(bookingIdUUID, offerIdUUID, userIdUUID)
                .switchIfEmpty(Mono.defer(() ->
                        validateUserHasNoActiveBooking(userIdUUID)
                                .then(findAndValidateOffer(offerIdUUID))
                                .then(decrementOfferQuota(offerIdUUID))
                                .then(insertBookingEntity(booking))
                        ))
                .then();
    }

    @Override
    public Mono<Booking> findById(Id id) {
        return jpaBookingRepository.findById(id.value())
                .map(BookingMapper::toDomainBooking);
    }

    @Override
    public Flux<Booking> findAll() {
        return jpaBookingRepository.findAll()
                .map(BookingMapper::toDomainBooking);
    }

    @Override
    public Mono<Void> cancelledBookingById(Id id) {
        UUID bookingIdUUID = UUID.fromString(id.toString());
        LocalDateTime now = LocalDateTime.now();
        return jpaBookingRepository.findById(bookingIdUUID)
                .switchIfEmpty(Mono.error(() -> ))
    }

    @Override
    @Transactional
    public Mono<Void> deleteBookingById(String id) {
        return jpaBookingRepository.findById(UUID.fromString(id))
                .switchIfEmpty(Mono.error(new BookingException("Booking with id: " + id + " not found")))
                .flatMap(jpaBookingRepository::delete);
    }

    private Mono<Boolean> checkIdempotency(UUID bookingId, UUID offerId, UUID userId){
        return jpaBookingRepository.findById(bookingId)
                .flatMap(existing -> {
                    boolean isSamePayLoad = existing.getOfferId().equals(offerId)
                            && existing.getUserId().equals(userId);
                    if(!isSamePayLoad){
                        return Mono.error(new BookingException(
                                "Conflict: The key: " + bookingId + " it´s already use in another data"
                        ));
                    }
                    return Mono.just(true);
                });
    }

    private Mono<Void> validateUserHasNoActiveBooking(UUID userId){
        return jpaBookingRepository.existsByUserIdAndStatus(userId, "BOOKED")
                .flatMap(hasActiveBooking -> Boolean.TRUE.equals(hasActiveBooking)
                        ? Mono.error(new BookingException("The user have already booking active"))
                        : Mono.empty()
                );
    }

    private Mono<Void> findAndValidateOffer(UUID offerId){
        return jpaOfferRepository.findById(offerId)
                .switchIfEmpty(Mono.error(() -> new OfferNotFoundException("Offer not found with id: " + offerId)))
                .flatMap(this::validateOfferRules);
    }

    private Mono<Void> validateOfferRules(OfferEntity offerEntity){
        if(!offerEntity.isState()){
            return Mono.error(new BookingException("The offer doesn't activate"));
        }
        if(offerEntity.getDate() != null && !LocalDateTime.now().isBefore(offerEntity.getDate())){
            return Mono.error(new BookingException(
                    "You cannot reservation because the date limit (" + offerEntity.getDate() + ") it´s started or expired"
            ));
        }
        if(offerEntity.getAvailableQuantity() <= 0){
            return Mono.error(new BookingException("Not exists spots available for this offer"));
        }
        return Mono.empty();
    }

    private Mono<Void> decrementOfferQuota(UUID offerId){
        return jpaOfferRepository.decrementAvailableQuantity(offerId)
                .flatMap(decremented -> Boolean.FALSE.equals(decremented)
                        ? Mono.error(new BookingException("We cannot reduce the spots because the offer it´s sold out"))
                        : Mono.empty()
                );
    }

    private Mono<Boolean> insertBookingEntity(Booking booking){
        var entity = BookingMapper.toBookingEntity(booking);
        return r2dbcEntityTemplate.insert(entity).thenReturn(true);
    }
}
