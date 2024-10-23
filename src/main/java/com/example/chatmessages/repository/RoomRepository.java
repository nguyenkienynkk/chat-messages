package com.example.chatmessages.repository;

import com.example.chatmessages.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}