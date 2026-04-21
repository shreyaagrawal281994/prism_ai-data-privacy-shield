package com.prism.shield.services;

import com.prism.shield.entities.PrivacyResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

@Service
public class PrivacyService {

    private final ChatClient chatClient;

    public PrivacyService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public PrivacyResponse checkPrivacy(String text) {
        // This converter tells the AI exactly what the JSON should look like
        var converter = new BeanOutputConverter<>(PrivacyResponse.class);

        PrivacyResponse detectionResponse = chatClient.prompt()
                .system(s -> s.text("...")
                        .text(converter.getFormat())) // This injects the JSON schema instructions
                .user(text)
                .call()
                .entity(PrivacyResponse.class); // This automatically parses the JSON back to Java!

        String finalRedactedText = text;
        if(detectionResponse != null) {
            for(String s : detectionResponse.foundPII()) {
                finalRedactedText = text.replaceAll("?i" + s, "[REDACTED]");
            }
        }
        return new PrivacyResponse(detectionResponse.foundPII(), detectionResponse.status(), finalRedactedText);
    }

}