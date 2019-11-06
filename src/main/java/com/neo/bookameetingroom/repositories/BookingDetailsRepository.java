package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
    Page<BookingDetails> findAll(Pageable pageable);
    Page<BookingDetails> findAllByPerson(Person person, Pageable pageable);
}
