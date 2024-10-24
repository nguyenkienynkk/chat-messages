package com.example.chatmessages.repository;

import com.example.chatmessages.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, Integer> {
    void deleteAllByRoomId(Integer roomId);
    Page<Message> findByRoomId(Integer roomId, Pageable pageable);
}