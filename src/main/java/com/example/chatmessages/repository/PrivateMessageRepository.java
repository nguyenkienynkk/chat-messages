package com.example.chatmessages.repository;

import com.example.chatmessages.entity.PrivateMessage;
import com.example.chatmessages.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {
    @Query("SELECT pm FROM PrivateMessage pm WHERE (pm.sender.id = :senderId AND pm.receiver.id = :receiverId) " +
            "OR (pm.sender.id = :receiverId AND pm.receiver.id = :senderId)")
    Page<PrivateMessage> findMessagesBetween(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId, Pageable pageable);

    @Query("""
            SELECT DISTINCT pm.sender FROM PrivateMessage pm WHERE pm.receiver.id = :userId UNION
                        SELECT DISTINCT pm.receiver FROM PrivateMessage pm WHERE pm.sender.id = :userId
            """)
    List<User> findChatPartnersByUserId(@Param("userId") Integer userId);
}