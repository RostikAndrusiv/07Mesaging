package com.rostik.andrusiv.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventEntity {

    @Id
    @GeneratedValue()
    private Long eventId;

    private String title;

    private String place;

    private String speaker;

    private EventType eventType;

    private LocalDateTime dateTime;
}
