package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
}
