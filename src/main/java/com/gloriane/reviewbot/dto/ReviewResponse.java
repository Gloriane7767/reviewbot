package com.gloriane.reviewbot.dto;

public record ReviewResponse(
        String decision,
        String feedback,
        String emailResponse
) {
}
