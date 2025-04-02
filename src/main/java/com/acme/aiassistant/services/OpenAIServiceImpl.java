package com.acme.aiassistant.services;

import com.acme.aiassistant.model.sfg.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService{

    private final Logger logger = LoggerFactory.getLogger(OpenAIServiceImpl.class);
    private final ChatModel chatModel;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPromptWithInfo;
    @Value("classpath:templates/get-capital-prompt-format.st")
    private Resource getCapitalPromptWithFormat;
    @Value("classpath:templates/get-country-info-prompt.st")
    private Resource getCountryInfoPrompt;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String getAnswer(String question) {
        logger.info("getAnswer for: {}", question);
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);
        String answerText = response.getResult().getOutput().getContent();

        return new Answer(answerText);
    }

    public GetCapitalResponse getCapital(getStateInfoRequest request) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();

//        System.out.println("Prompt Format: \n"+ format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateName", request.stateName(),
                "format", format));

        ChatResponse response = chatModel.call(prompt);

        String answerContent = response.getResult().getOutput().getContent();

        City city = initCity(request.stateName(), answerContent);
        System.out.println("city = " + city);

        System.out.println("answerContent = " + answerContent);

        return converter.convert(answerContent);
    }

    @Override
    public GetCountryInfoResponse getCountryInfo(getStateInfoRequest request) {
        BeanOutputConverter<GetCountryInfoResponse> countryInfoConverter = new BeanOutputConverter<>(GetCountryInfoResponse.class);
        String format = countryInfoConverter.getFormat();

//        System.out.println("Prompt Format: \n"+ format);

        PromptTemplate promptTemplate = new PromptTemplate(getCountryInfoPrompt);
        Prompt prompt = promptTemplate.create(Map.of("countryName", request.stateName(),
                "format", format));

        ChatResponse response = chatModel.call(prompt);

        String answerContent = response.getResult().getOutput().getContent();

        System.out.println("answerContent = " + answerContent);

        return countryInfoConverter.convert(answerContent);
    }


    protected City initCity(String stateName, String aiJsonResponse) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(aiJsonResponse);
            String cityName = jsonNode.get("answer").asText();
            return new City(cityName, stateName, true);
        } catch (JsonProcessingException e) {
            logger.error("cannot read json response {} {}", aiJsonResponse, e.getMessage());
            return null;
        }
    }

}
