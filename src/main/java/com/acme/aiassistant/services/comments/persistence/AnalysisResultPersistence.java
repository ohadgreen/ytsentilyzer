package com.acme.aiassistant.services.comments.persistence;

import com.acme.aiassistant.model.comments.CommentSentimentResult;

import java.util.List;

public interface AnalysisResultPersistence {
    void saveCommentSentimentResult(List<CommentSentimentResult> commentSentimentResults);
    CommentSentimentResult getCommentSentimentResultByCommentIdAndSentimentObject(String commentId, String sentimentObject);
    List<CommentSentimentResult> getCommentSentimentResultsByVideoIdAndSentimentObject(String videoId, String sentimentObject);
    void deleteCommentSentimentResultByVideoId(String videoId);
}
