package com.acme.aiassistant.controllers;

import com.acme.aiassistant.model.sfg.*;
import com.acme.aiassistant.services.OpenAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OpenAiController {

    private final Logger logger = LoggerFactory.getLogger(OpenAiController.class);

    private final OpenAIService openAIService;

    public OpenAiController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/askai")
    public Answer answerForPrompt(@RequestBody Question question) {
        logger.info("Controller q: {}", question.question());
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public GetCapitalResponse getCapital(@RequestBody getStateInfoRequest capitalRequest) {
        logger.info("Controller get capital: {}", capitalRequest.stateName());
        return openAIService.getCapital(capitalRequest);
    }

    @PostMapping("/countryInfo")
    public GetCountryInfoResponse getCountryInfoResponse(@RequestBody getStateInfoRequest stateInfoRequest) {
        return openAIService.getCountryInfo(stateInfoRequest);
    }
//    @GetMapping("ai_prompt")
//    public String aiGreeting() {
//        logger.info("greeting request received");
//        String promptText = "As a modern generative AI model, Generate a 5 liner greeting message in your style for a human in text form. Thanks in advance.";
//        Prompt prompt = new Prompt(promptText);
//
//        ChatResponse res = chatModel.call(prompt);
//
//        return res.getResult().getOutput().getContent();
//    }
}
