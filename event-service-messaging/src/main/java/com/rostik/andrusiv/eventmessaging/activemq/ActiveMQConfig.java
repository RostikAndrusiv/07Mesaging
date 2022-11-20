package com.rostik.andrusiv.eventmessaging.activemq;

import com.rostik.andrusiv.eventmessaging.profile.ActiveMQProfile;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@EnableJms
@ActiveMQProfile
public class ActiveMQConfig {

    @Bean
    public Queue createEventQueue() {
        return new ActiveMQQueue("create-event-notification, create-event-request");
    }

    @Bean
    public Queue updateEventQueue() {
        return new ActiveMQQueue("update-event-notification, update-event-request");
    }

    @Bean
    public Queue deleteEventQueue() {
        return new ActiveMQQueue("delete-event-notification, delete-event-request");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory();
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return jmsTemplate;
    }
}
