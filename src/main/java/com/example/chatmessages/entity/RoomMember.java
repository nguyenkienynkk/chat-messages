package com.example.chatmessages.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "room_members")
public class RoomMember {
    @EmbeddedId
    private RoomMemberId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Size(max = 50)
    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "joined_at")
    private Instant joinedAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
    @PrePersist
    protected void onCreate() {
        this.joinedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
