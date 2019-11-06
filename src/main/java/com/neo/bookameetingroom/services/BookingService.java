package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    void save(BookingDetails bookingDetails);
    List<BookingDetails> findAll();
    Optional<BookingDetails> findById(Long id);
    void deleteById(Long id);
    Page<BookingDetails> getPaginatedBookingDetails(Pageable pageable);
    Page<BookingDetails> getPaginatedBookingDetails(Person person, Pageable pageable);
}
