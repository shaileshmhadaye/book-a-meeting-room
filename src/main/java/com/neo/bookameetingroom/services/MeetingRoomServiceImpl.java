package com.neo.bookameetingroom.services;

import com.neo.bookameetingroom.model.MeetingRoom;
import com.neo.bookameetingroom.repositories.MeetingRoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService{

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomServiceImpl(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }

    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {
        return meetingRoomRepository.save(meetingRoom);
    }

    @Override
    public Optional<MeetingRoom> findById(Long id) {
        return meetingRoomRepository.findById(id);
    }

    @Override
    public List<MeetingRoom> findAll() {
        return meetingRoomRepository.findAll();
    }

    @Override
    public Page<MeetingRoom> getPaginatedMeetingRooms(Pageable pageable) {
        return meetingRoomRepository.findAll(pageable);
    }
}
