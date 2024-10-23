package com.example.chatmessages.repository;

import com.example.chatmessages.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    void deleteAllByRoomId(Integer roomId);
    List<Message> findByRoomId(Integer roomId);
}