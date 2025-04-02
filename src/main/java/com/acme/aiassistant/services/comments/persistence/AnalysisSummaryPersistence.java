package com.acme.aiassistant.services.comments.persistence;

import com.acme.aiassistant.model.comments.CommentsAnalyzeSummary;

public interface AnalysisSummaryPersistence {
    void saveAnalysisSummary(CommentsAnalyzeSummary commentsAnalyzeSummary);
    CommentsAnalyzeSummary getCommentsAnalysisSummary(String videoId);
    void updateAnalysisSummary(String videoId, CommentsAnalyzeSummary commentsAnalyzeSummary);
    void deleteAnalysisSummary(String videoId);
}
