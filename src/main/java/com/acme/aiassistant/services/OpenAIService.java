package com.acme.aiassistant.services;

import com.acme.aiassistant.model.sfg.*;

public interface OpenAIService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
    GetCapitalResponse getCapital(getStateInfoRequest request);
    GetCountryInfoResponse getCountryInfo(getStateInfoRequest request);
}
