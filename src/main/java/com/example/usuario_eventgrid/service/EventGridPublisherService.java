package com.example.usuario_eventgrid.service;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
public class EventGridPublisherService {

    @Value("${azure.eventgrid.topic-endpoint}")
    private String topicEndpoint;

    @Value("${azure.eventgrid.key}")
    private String accessKey;

    private EventGridPublisherClient<EventGridEvent> client;

    @PostConstruct
    private void init() {
        client = new EventGridPublisherClientBuilder()
            .endpoint(topicEndpoint)
            .credential(new AzureKeyCredential(accessKey))
            .buildEventGridEventPublisherClient();
    }

    public void publishEvent(String eventType, String subject, Object data) {
        try {
            EventGridEvent event = new EventGridEvent(
                subject,              // subject, ejemplo: "/usuario/123"
                eventType,            // ejemplo: "UsuarioCreado"
                BinaryData.fromObject(data), // datos serializados
                "1.0"                 // dataVersion
            );
            event.setId(UUID.randomUUID().toString());
            event.setEventTime(OffsetDateTime.now());

            client.sendEvents(Collections.singletonList(event));
        } catch (Exception e) {
            throw new RuntimeException("Error publicando evento en EventGrid", e);
        }
    }
}