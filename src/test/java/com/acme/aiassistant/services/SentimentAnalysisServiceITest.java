package com.acme.aiassistant.services;

import com.acme.aiassistant.model.comments.*;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("memory")
class SentimentAnalysisServiceITest {

    @Autowired
    SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private ChatModel chatModel;

    @Test
    void testSentimentAnalysisPrompt() {

        SentimentAnalysisRequest sentimentReqTest = getSentimentAnalysisRequest();

        SentimentAnalysisResponse response = sentimentAnalysisService.analyzeCommentsChunk(sentimentReqTest);

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getSentimentAnalysisId(), "Response ID should be generated");
        assertFalse(response.getCommentSentiments().isEmpty(), "Sentiments list should not be empty");

        // Print output for debugging
        for (CommentSentiment commentSentiment : response.getCommentSentiments()) {
            System.out.println("Comment ID: " + commentSentiment.getCommentId() + " - sentiment: " + commentSentiment.getSentiment() + " - reason: " + commentSentiment.getSentimentReason());
            if (commentSentiment.getCommentId().equals("c2")) {
                assertEquals(Sentiment.POSITIVE, commentSentiment.getSentiment(), "Comment c2 should be positive");
            }
        }

    }

    private static SentimentAnalysisRequest getSentimentAnalysisRequest() {
        String analysisObject = "Google";
        String c1 = "Until Veo2 is released to the public, it is just fiction";
        String c2 = "Google is superior!";
        String c3 = "There's no way in hell I'm coming back to Chrome for their AI addons. I hope they come to Firefox.";

        SentimentAnalysisRequest sentimentReqTest = new SentimentAnalysisRequest(
                analysisObject,
                "Google AI DEMOLISHES OpenAI! The Ultimate AI Showdown is OVER?",
                "Google's new video generation model is called Veo 2. SORA is a video generation model created by OpenAI",
                Arrays.asList(new CommentToAnalyze("c1", c1), new CommentToAnalyze("c2", c2), new CommentToAnalyze("c3", c3))
        );
        return sentimentReqTest;
    }
}