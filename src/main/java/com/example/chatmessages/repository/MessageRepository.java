package com.example.chatmessages.repository;

import com.example.chatmessages.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}