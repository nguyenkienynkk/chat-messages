package com.example.chatmessages.repository;

import com.example.chatmessages.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByUserIdAndRoomId(Integer userId, Integer roomId);
}