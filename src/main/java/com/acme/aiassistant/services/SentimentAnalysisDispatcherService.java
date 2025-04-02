package com.acme.aiassistant.services;

import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisDispatcherService {

    private final SentimentAnalysisService sentimentAnalysisService;

    public SentimentAnalysisDispatcherService(SentimentAnalysisService sentimentAnalysisService) {
        this.sentimentAnalysisService = sentimentAnalysisService;
    }


}
