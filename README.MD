Local setup:

profiles: -Dspring.profiles.active=rabbit/activemq/kafka

docker commands:

docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
docker run -d --name activemq -p 61616:61616 -p 8161:8161 rmohr/activemq
docker-compose up -d (for kafka)
