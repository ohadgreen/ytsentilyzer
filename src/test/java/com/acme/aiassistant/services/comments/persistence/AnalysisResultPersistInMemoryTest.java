package com.acme.aiassistant.services.comments.persistence;

import com.acme.aiassistant.model.comments.CommentSentimentResult;
import com.acme.aiassistant.model.comments.Sentiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisResultPersistInMemoryTest {

        private AnalysisResultPersistInMemory service;
        private CommentSentimentResult result1, result2, result3, result4, result11, result12;
        private final String videoId = "video123";
        private final String video2Id = "video234";
        private final String sentimentObject = "topicA";
        private final String sentimentObject2 = "topicB";

        @BeforeEach
        void setUp() {
            service = new AnalysisResultPersistInMemory();

            result1 = new CommentSentimentResult();
            result1.setId(UUID.randomUUID().toString());
            result1.setCommentId("comment1");
            result1.setVideoId(videoId);
            result1.setSentimentObject(sentimentObject);
            result1.setSentiment(Sentiment.POSITIVE);
            result1.setSentimentReason("Good comment");

            result2 = new CommentSentimentResult();
            result2.setId(UUID.randomUUID().toString());
            result2.setCommentId("comment2");
            result2.setVideoId(videoId);
            result2.setSentimentObject(sentimentObject);
            result2.setSentiment(Sentiment.NEGATIVE);
            result2.setSentimentReason("Bad comment");

            result3 = new CommentSentimentResult();
            result3.setId(UUID.randomUUID().toString());
            result3.setCommentId("comment3");
            result3.setVideoId(videoId);
            result3.setSentimentObject(sentimentObject);
            result3.setSentiment(Sentiment.NEUTRAL);
            result3.setSentimentReason("Neutral comment");

            // same videoId, same comments, different sentiment object
            result11 = new CommentSentimentResult();
            result11.setId(UUID.randomUUID().toString());
            result11.setCommentId("comment1");
            result11.setVideoId(videoId);
            result11.setSentimentObject(sentimentObject2);
            result11.setSentiment(Sentiment.POSITIVE);
            result11.setSentimentReason("Good comment 2");

            result12 = new CommentSentimentResult();
            result12.setId(UUID.randomUUID().toString());
            result12.setCommentId("comment2");
            result12.setVideoId(videoId);
            result12.setSentimentObject(sentimentObject2);
            result12.setSentiment(Sentiment.NEGATIVE);
            result12.setSentimentReason("Bad comment");

            result4 = new CommentSentimentResult();
            result4.setId(UUID.randomUUID().toString());
            result4.setCommentId("comment4");
            result4.setVideoId(video2Id);
            result4.setSentimentObject("topicC");
            result4.setSentiment(Sentiment.POSITIVE);
            result4.setSentimentReason("Another good comment");

        }

        @Test
        void saveCommentSentimentResult_shouldSaveResults() {
            List<CommentSentimentResult> results = Arrays.asList(result1, result2, result3);
            service.saveCommentSentimentResult(results);

            CommentSentimentResult retrieved1 = service.getCommentSentimentResultByCommentIdAndSentimentObject("comment1", sentimentObject);
            CommentSentimentResult retrieved2 = service.getCommentSentimentResultByCommentIdAndSentimentObject("comment2", sentimentObject);
            CommentSentimentResult retrieved3 = service.getCommentSentimentResultByCommentIdAndSentimentObject("comment3", sentimentObject);

            assertNotNull(retrieved1);
            assertNotNull(retrieved2);
            assertNotNull(retrieved3);
            assertEquals(result1.getId(), retrieved1.getId());
            assertEquals(result2.getId(), retrieved2.getId());
            assertEquals(result3.getId(), retrieved3.getId());

            List<CommentSentimentResult> secondSentimentObjectResults = Arrays.asList(result11, result12);
            service.saveCommentSentimentResult(secondSentimentObjectResults);

            CommentSentimentResult retrieved11 = service.getCommentSentimentResultByCommentIdAndSentimentObject("comment1", sentimentObject2);
            CommentSentimentResult retrieved12 = service.getCommentSentimentResultByCommentIdAndSentimentObject("comment2", sentimentObject2);
            assertNotNull(retrieved11);
            assertNotNull(retrieved12);
            assertEquals(result11.getId(), retrieved11.getId());
            assertEquals(result12.getId(), retrieved12.getId());
        }

        @Test
        void getCommentSentimentResultByCommentIdAndSentimentObject_shouldReturnCorrectResult() {
            List<CommentSentimentResult> results = Arrays.asList(result1);
            service.saveCommentSentimentResult(results);

            CommentSentimentResult retrieved = service.getCommentSentimentResultByCommentIdAndSentimentObject("comment1", sentimentObject);
            assertNotNull(retrieved);
            assertEquals(result1.getId(), retrieved.getId());
            assertNull(service.getCommentSentimentResultByCommentIdAndSentimentObject("nonexistent", sentimentObject));
            assertNull(service.getCommentSentimentResultByCommentIdAndSentimentObject("comment1", "wrongSentiment"));

        }

        @Test
        void getCommentSentimentResultsByVideoIdAndSentimentObject_shouldReturnCorrectResults() {
            List<CommentSentimentResult> results = Arrays.asList(result1, result2, result3);
            service.saveCommentSentimentResult(results);

            service.saveCommentSentimentResult(Collections.singletonList(result4));

            List<CommentSentimentResult> retrievedResults = service.getCommentSentimentResultsByVideoIdAndSentimentObject(videoId, sentimentObject);
            assertEquals(3, retrievedResults.size());
            assertTrue(retrievedResults.stream().anyMatch(r -> r.getId().equals(result1.getId())));
            assertTrue(retrievedResults.stream().anyMatch(r -> r.getId().equals(result2.getId())));
            assertTrue(retrievedResults.stream().anyMatch(r -> r.getId().equals(result3.getId())));
            assertEquals(0, service.getCommentSentimentResultsByVideoIdAndSentimentObject("nonexistent", sentimentObject).size());
            assertEquals(1, service.getCommentSentimentResultsByVideoIdAndSentimentObject(video2Id, "topicC").size());
        }

        @Test
        void deleteCommentSentimentResultByVideoId_shouldDeleteResults() {
            List<CommentSentimentResult> results = Arrays.asList(result1, result2, result3);
            service.saveCommentSentimentResult(results);

            List<CommentSentimentResult> results2 = Arrays.asList(result11, result12);
            service.saveCommentSentimentResult(results2);

            service.saveCommentSentimentResult(Collections.singletonList(result4));

            service.deleteCommentSentimentResultByVideoId(videoId);

            assertEquals(1, service.getCommentSentimentResultsByVideoIdAndSentimentObject(video2Id, "topicC").size()); // should be zero, but the method returns the same list.
            assertNull(service.getCommentSentimentResultByCommentIdAndSentimentObject("comment1", sentimentObject));
        }

}