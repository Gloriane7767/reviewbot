package com.gloriane.reviewbot.service;

import com.gloriane.reviewbot.dto.ApplicationRequest;
import com.gloriane.reviewbot.dto.ReviewResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAIServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public ReviewResponse generateApplicationReview(ApplicationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        SystemMessage systemMessage = SystemMessage.builder()
                .text(
                        """
                                  ROLE:
                                         You are an experienced recruiter reviewing applications
                                         for a Fullstack Development course.
                                
                                         TASK:
                                         Analyze the candidate application carefully.
                                
                                         RULES:
                                         1. Decide if the candidate should be Accepted or Rejected.
                                         2. Give short constructive feedback.
                                         3. Generate a professional email response.
                                         4. Be encouraging and professional.
                                         5. Return ONLY valid JSON.
                                         6. Do not include markdown.
                                         7. Do not explain the JSON.
                                
                                         RESPONSE FORMAT:
                                         {
                                           "decision": "Accepted or Rejected",
                                           "feedback": "Short feedback",
                                           "emailResponse": "Professional email"
                                         }
                                
                                         CANDIDATE APPLICATION:
                                
                                         Name: %s
                                         Background: %s
                                         Experience: %s
                                         Motivation: %s
                                         Skills: %s
                                         """.formatted(
                                                 request.name(),
                                                 request.background(),
                                                 request.experience(),
                                                 request.motivation(),
                                                 request.skills()
                                         )
                )
                .build();
        UserMessage userMessage = UserMessage.builder()
                .text(
                        """
                                Name: %s
                                Background: %s
                                Experience: %s
                                Motivation: %s
                                Skills: %s
                                """.formatted(
                                        request.name(),
                                        request.background(),
                                        request.experience(),
                                        request.motivation(),
                                        request.skills()
                                )
                )
                .build();

        return chatClient.prompt()
                .messages(systemMessage, userMessage)
                .call()
                .entity(ReviewResponse.class);
    }
}
