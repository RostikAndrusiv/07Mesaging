package com.rostik.andrusiv.mesaging.repository;

import com.rostik.andrusiv.mesaging.servicedto.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
