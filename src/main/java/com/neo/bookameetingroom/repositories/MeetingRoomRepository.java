package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("meetingRoomRepository")
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    Page<MeetingRoom> findAll(Pageable pageable);
}
