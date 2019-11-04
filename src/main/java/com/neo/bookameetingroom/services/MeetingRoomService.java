package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.MeetingRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MeetingRoomService {
    MeetingRoom save(MeetingRoom meetingRoom);
    Optional<MeetingRoom> findById(Long id);
    List<MeetingRoom> findAll();
    Page<MeetingRoom> getPaginatedMeetingRooms(Pageable pageable);
}
