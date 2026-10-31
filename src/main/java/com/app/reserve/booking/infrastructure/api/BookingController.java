package com.app.reserve.booking.infrastructure.api;

import com.app.reserve.booking.domain.Booking;
import com.app.reserve.booking.domain.useCases.CreateBooking;
import com.app.reserve.booking.domain.useCases.DeleteBooking;
import com.app.reserve.booking.domain.useCases.RetrieveBooking;
import com.app.reserve.booking.domain.useCases.UpdateBooking;
import com.app.reserve.shared.domain.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final CreateBooking bookingCreator;
    private final RetrieveBooking bookingRetriever;
    private final UpdateBooking bookingUpdater;
    private final DeleteBooking bookingDeleter;

    public BookingController(CreateBooking bookingCreator, RetrieveBooking bookingRetriever, UpdateBooking bookingUpdater, DeleteBooking bookingDeleter) {
        this.bookingCreator = bookingCreator;
        this.bookingRetriever = bookingRetriever;
        this.bookingUpdater = bookingUpdater;
        this.bookingDeleter = bookingDeleter;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createBooking(@RequestBody BookingInput bookingInput){
        return bookingCreator.createBooking(Booking.create(bookingInput.id(), bookingInput.status(), bookingInput.createdAt(),
                bookingInput.cancelledAt(), bookingInput.offerId(), bookingInput.userId()))
                .doOnSuccess(unused -> log.info("Booking created: {}", bookingInput))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @GetMapping
    public Flux<BookingResponse> getBookings(){
        return bookingRetriever.getAllBookings()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getStatus(),
                        booking.getCreatedAt(),
                        booking.getCancelledAt(),
                        booking.getOfferId(),
                        booking.getUserId()
                ))
                .doOnComplete(() -> log.info("Bookings retrieved successfully"));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookingResponse>> getBookingById(@PathVariable("id") String id){
        return bookingRetriever.getBooking(id)
                .map(booking -> {
                    var bookingResponse = new BookingResponse(
                            booking.getId(),
                            booking.getStatus(),
                            booking.getCreatedAt(),
                            booking.getCancelledAt(),
                            booking.getOfferId(),
                            booking.getUserId()
                    );
                    log.info("Booking retrieved: {}", booking.getId());
                    return ResponseEntity.ok().body(bookingResponse);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/cancel/{id}")
    public Mono<ResponseEntity<Void>> cancelledBookingById(@PathVariable("id") String id){
        return bookingUpdater.cancelledBookingId(new Id(id))
                .doOnSuccess(unused -> log.info("Booking cancelled: {}", id))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteBookingById(@PathVariable("id") String id){
        return bookingDeleter.deleteBookingById(id)
                .doOnSuccess(unused -> log.info("Booking deleted: {}", id))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
