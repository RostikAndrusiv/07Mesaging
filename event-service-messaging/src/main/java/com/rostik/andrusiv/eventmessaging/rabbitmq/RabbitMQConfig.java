package com.rostik.andrusiv.eventmessaging.rabbitmq;

import com.rostik.andrusiv.eventmessaging.profile.RabbitMQProfile;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitMQProfile
@EnableRabbit
public class RabbitMQConfig {

    public static final String CREATE_EVENT_NOTIFICATION_QUEUE = "create-event-notification";
    public static final String UPDATE_EVENT_NOTIFICATION_QUEUE = "update-event-notification";
    public static final String DELETE_EVENT_NOTIFICATION_QUEUE = "delete-event-notification";
    public static final String CREATE_EVENT_REQUEST_QUEUE = "create-event-request";
    public static final String UPDATE_EVENT_REQUEST_QUEUE = "update-event-request";
    public static final String DELETE_EVENT_REQUEST_QUEUE = "delete-event-request";

    public static final String EXCHANGE = "message_exchange";

    public static final String CREATE_EVENT_NOTIFICATION_ROUTING_KEY = "create.event.notification.routing.key";
    public static final String UPDATE_EVENT_NOTIFICATION_ROUTING_KEY = "update.event.notification.routing.key";
    public static final String DELETE_EVENT_NOTIFICATION_ROUTING_KEY = "delete.event.notification.routing.key";

    @Bean
    public Queue createNotificationQueue() {
        return new Queue(CREATE_EVENT_NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue updateNotificationQueue() {
        return new org.springframework.amqp.core.Queue(UPDATE_EVENT_NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue deleteNotificationQueue() {
        return new org.springframework.amqp.core.Queue(DELETE_EVENT_NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue createRequestQueue() {
        return new org.springframework.amqp.core.Queue(CREATE_EVENT_REQUEST_QUEUE);
    }

    @Bean
    public Queue updateRequestQueue() {
        return new org.springframework.amqp.core.Queue(UPDATE_EVENT_REQUEST_QUEUE);
    }

    @Bean
    public Queue deleteRequestQueue() {
        return new org.springframework.amqp.core.Queue(DELETE_EVENT_REQUEST_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }


    @Bean
    public Binding bindingCreateNotification(Queue createNotificationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(createNotificationQueue)
                .to(exchange)
                .with("create.event.#");
    }

    @Bean
    public Binding bindingUpdateNotification(Queue updateNotificationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(updateNotificationQueue)
                .to(exchange)
                .with("update.event.#");
    }

    @Bean
    public Binding bindingDeleteNotification(Queue deleteNotificationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(deleteNotificationQueue)
                .to(exchange)
                .with("delete.event.#");
    }

    @Bean
    public Binding bindingCreateRequest(Queue createRequestQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(createRequestQueue)
                .to(exchange)
                .with("create.event.#");
    }

    @Bean
    public Binding bindingUpdateRequest(Queue updateRequestQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(updateRequestQueue)
                .to(exchange)
                .with("update.event.#");
    }

    @Bean
    public Binding bindingDeleteRequest(Queue deleteRequestQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(deleteRequestQueue)
                .to(exchange)
                .with("delete.event.#");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
