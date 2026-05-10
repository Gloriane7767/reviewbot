package com.gloriane.reviewbot.service;

import com.gloriane.reviewbot.dto.ApplicationRequest;

public interface OpenAIService {
    String reviewApplication(ApplicationRequest request);
}
