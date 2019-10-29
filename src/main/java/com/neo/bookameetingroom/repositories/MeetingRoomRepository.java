package com.neo.bookameetingroom.repositories;

import com.neo.bookameetingroom.model.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("meetingRoomRepository")
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}
