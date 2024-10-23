
package com.example.chatmessages.repository;

import com.example.chatmessages.entity.RoomExit;
import com.example.chatmessages.entity.RoomExitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomExitRepository extends JpaRepository<RoomExit, RoomExitId> {
}