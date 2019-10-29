package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.MeetingRoom;

import java.util.List;
import java.util.Optional;

public interface MeetingRoomService {
    MeetingRoom save(MeetingRoom meetingRoom);
    Optional<MeetingRoom> findById(Long id);
    List<MeetingRoom> findAll();
}
