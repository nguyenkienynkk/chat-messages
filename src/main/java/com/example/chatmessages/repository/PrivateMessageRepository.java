package com.example.chatmessages.repository;

import com.example.chatmessages.entity.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {
}