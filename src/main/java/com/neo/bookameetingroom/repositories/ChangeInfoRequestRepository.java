package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.ChangeInfoRequest;
import com.neo.bookameetingroom.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeInfoRequestRepository extends JpaRepository<ChangeInfoRequest, Long> {
    List<ChangeInfoRequest> findAll();
}
