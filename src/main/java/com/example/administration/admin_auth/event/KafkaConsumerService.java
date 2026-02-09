package com.example.administration.admin_auth.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.administration.admin_auth.dto.AgencyRequestDTO;
import com.example.administration.admin_auth.dto.AgencyRequestDTOMapper;
import com.example.administration.admin_auth.repository.AgencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final AgencyRepository agencyRepository;
    private final AgencyRequestDTOMapper agencyRequestDTOMapper;
    /**
     * Listens for messages on the "test-events" topic.
     * The consumer group ID is defined in application.properties (e.g., my-application-group).
     * @param message The received message payload.
     */
    @KafkaListener(topics = "Agency-created", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        // ObjectMapper mapper = new ObjectMapper();
        try {
            // JsonNode node = mapper.readTree(message);
            AgencyRequestDTO agencyRequest = objectMapper.readValue(message, AgencyRequestDTO.class); 
            System.out.println("Received event: " + agencyRequest); 
            agencyRepository.save(agencyRequestDTOMapper.toEntity(agencyRequest));
        } catch (Exception e) {
            System.err.println("Error processing event: " + e.getMessage());
        }
    }
}
