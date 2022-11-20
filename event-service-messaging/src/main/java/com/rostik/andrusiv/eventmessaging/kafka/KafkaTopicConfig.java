package com.rostik.andrusiv.eventmessaging.kafka;

import com.rostik.andrusiv.eventmessaging.profile.KafkaProfile;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@KafkaProfile
class KafkaTopicConfig {

    public static final String CREATE_EVENT_TOPIC = "createEventTopic";

    public static final String UPDATE_EVENT_TOPIC = "updateEventTopic";

    public static final String DELETE_EVENT_TOPIC = "deleteEventTopic";

    @Bean
    public NewTopic createEventTopic() {
        return TopicBuilder.name(CREATE_EVENT_TOPIC).build();
    }

    @Bean
    public NewTopic createEventRequest() {
        return TopicBuilder.name(UPDATE_EVENT_TOPIC).build();
    }

    @Bean
    public NewTopic updateEventTopic() {
        return TopicBuilder.name(DELETE_EVENT_TOPIC).build();
    }
}