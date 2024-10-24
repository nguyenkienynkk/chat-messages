package com.example.chatmessages.repository;

import com.example.chatmessages.entity.Room;
import com.example.chatmessages.entity.RoomMember;
import com.example.chatmessages.entity.RoomMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, RoomMemberId> {
    Optional<RoomMember> findByUserIdAndRoomId(Integer userId, Integer roomId);
    void deleteAllByRoomId(Integer roomId);
    @Query("SELECT rm.room FROM RoomMember rm WHERE rm.user.id = :userId")
    List<Room> findRoomsByUserId(@Param("userId") Integer userId);

}