package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findById(Long id);
}
