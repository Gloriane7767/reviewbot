package com.gloriane.reviewbot.service;

import com.gloriane.reviewbot.dto.ApplicationRequest;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final OpenAiChatModel openAiChatModel;

    public OpenAIServiceImpl(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @Override
    public String reviewApplication(ApplicationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        SystemMessage systemMessage = SystemMessage.builder()
                .text("""
                        ROLE:
                        You are an experienced recruiter reviewing applications
                        for a Fullstack Development course.

                        TASK:
                        Analyze the candidate application carefully.

                        RULES:
                        - Evaluate candidates based on their background, experience, motivation, and skills.
                        - Decide if the candidate should be Accepted or Rejected.
                        - Give short constructive feedback.
                        - Generate a professional email response.
                        - Be encouraging and professional.
                        """)
                .build();

        String userInput = String.format("""
                    Review this application:
                    Name: %s
                    Background: %s
                    Experience: %s
                    Motivation: %s
                    Skills: %s

                    Respond with:
                    1. Decision: Accepted / Rejected
                    2. Professional email to the candidate
                    3. Short feedback (2-3 sentences)
                    """,
                request.name(),
                request.background(),
                request.experience(),
                request.motivation(),
                request.skills()
        );

        Prompt prompt = Prompt.builder()
                .messages(systemMessage, new UserMessage(userInput))
                .chatOptions(ChatOptions.builder()
                        .model("gpt-4o")
                        .maxTokens(1500)
                        .temperature(0.4)
                        .build())
                .build(
        );

        ChatResponse response = openAiChatModel.call(prompt);
        String content = response.getResult() != null
                ? response.getResult()
                .getOutput()
                .getText()
                : "No response from OpenAI model";
        return (content != null) ? content : "No response from OpenAI model";
    }
}
