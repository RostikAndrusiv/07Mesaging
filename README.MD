docker run -d --hostname my-active -p 61616:61616 -p 8161:8161 rmohr/activemq

docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management

docker-compose up -d

-Dspring.profiles.active=rabbit/activemq/kafka
