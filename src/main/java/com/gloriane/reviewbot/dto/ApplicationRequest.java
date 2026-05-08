package com.gloriane.reviewbot.dto;

import jakarta.validation.constraints.NotBlank;

public record ApplicationRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Background is required")
        String background,

        @NotBlank(message = "Experience is required")
        String experience,

        @NotBlank(message = "Motivation is required")
        String motivation,

        @NotBlank(message = "Skills are required")
        String skills
) {
}
