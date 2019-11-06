package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.BookingDetails;
import com.neo.bookameetingroom.model.Person;
import com.neo.bookameetingroom.repositories.BookingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingDetailsRepository bookingRepository;

    @Override
    public void save(BookingDetails bookingDetails) {
        bookingRepository.save(bookingDetails);
    }

    @Override
    public List<BookingDetails> findAll() {
        List<BookingDetails> bookingDetails = bookingRepository.findAll();
        return bookingDetails;
    }

    @Override
    public Optional<BookingDetails> findById(Long id) {
        Optional<BookingDetails> bookingDetails = bookingRepository.findById(id);
        return bookingDetails;
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Page<BookingDetails> getPaginatedBookingDetails(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    public Page<BookingDetails> getPaginatedBookingDetails(Person person, Pageable pageable) {
//        List<BookingDetails> bookingDetails = new ArrayList<>();
//        LocalDate today = LocalDate.now();
//        for (BookingDetails bookingDetail: bookingRepository.findAllByPerson(person, pageable)) {
//            if(bookingDetail.getDate().isAfter(today) || bookingDetail.getDate().isEqual(today)){
//                bookingDetails.add(bookingDetail);
//            }
//        }
        return bookingRepository.findAllByPerson(person, pageable);
    }
}
