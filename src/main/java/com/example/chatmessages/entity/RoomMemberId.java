package com.example.chatmessages.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RoomMemberId implements Serializable {
    private static final long serialVersionUID = 7806343998787722471L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoomMemberId entity = (RoomMemberId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.roomId, entity.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roomId);
    }

}