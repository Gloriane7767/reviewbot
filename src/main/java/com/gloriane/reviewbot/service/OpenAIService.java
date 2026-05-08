package com.gloriane.reviewbot.service;

import com.gloriane.reviewbot.dto.ApplicationRequest;
import com.gloriane.reviewbot.dto.ReviewResponse;

public interface OpenAIService {
    ReviewResponse generateApplicationReview(ApplicationRequest request);
}
