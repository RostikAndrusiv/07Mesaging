package com.rostik.andrusiv.eventmessaging.kafka;

import com.rostik.andrusiv.profile.KafkaProfile;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.rostik.andrusiv.common.Constants.*;

@Configuration
@KafkaProfile
class KafkaTopicConfig {

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