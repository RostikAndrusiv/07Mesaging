package com.rostik.andrusiv.common;

public class Constants {

    public static final String CREATE_EVENT_REQUEST_QUEUE = "create-event-request";
    public static final String UPDATE_EVENT_REQUEST_QUEUE = "create-event-request";
    public static final String DELETE_EVENT_REQUEST_QUEUE = "create-event-request";
    public static final String CREATE_EVENT_NOTIFICATION_QUEUE = "create-event-notification";
    public static final String UPDATE_EVENT_NOTIFICATION_QUEUE = "update-event-notification";
    public static final String DELETE_EVENT_NOTIFICATION_QUEUE = "delete-event-notification";
    public static final String EXCHANGE = "message_exchange";
    public static final String CREATE_EVENT_NOTIFICATION_ROUTING_KEY = "create.event.notification.routing.key";
    public static final String UPDATE_EVENT_NOTIFICATION_ROUTING_KEY = "update.event.notification.routing.key";
    public static final String DELETE_EVENT_NOTIFICATION_ROUTING_KEY = "delete.event.notification.routing.key";
    public static final String CREATE_EVENT_TOPIC = "createEventTopic";
    public static final String UPDATE_EVENT_TOPIC = "updateEventTopic";
    public static final String DELETE_EVENT_TOPIC = "deleteEventTopic";

    private Constants() {
        // util class
    }
}
