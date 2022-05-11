# Salesforce Event Listener POC

## Environment

Environment variables should include the following keys:

- SALESFORCE_PASSWORD
- SALESFORCE_SECURITY_TOKEN
- SALESFORCE_URL
- SALESFORCE_USERNAME

## Running kafka

To run Kafka broker and Zookeeper run:

`docker compose up -d` 

To create a topic, run:

`docker exec broker kafka-topics --bootstrap-server broker:9092 --create --topic contacts`

To list topics, run:

`docker exec broker kafka-topics --bootstrap-server broker:9092 --list`


To read messages, run:

`docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic contacts --from-beginning`