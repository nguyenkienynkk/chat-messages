package com.example.chatmessages.repository;

import com.example.chatmessages.entity.RoomMember;
import com.example.chatmessages.entity.RoomMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberId> {
    Optional<RoomMember> findByUserIdAndRoomId(Integer userId, Integer roomId);
    void deleteAllByRoomId(Integer roomId);
}