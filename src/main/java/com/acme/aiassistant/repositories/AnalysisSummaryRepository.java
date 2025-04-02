package com.acme.aiassistant.repositories;

import com.acme.aiassistant.model.comments.CommentsAnalyzeSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalysisSummaryRepository extends MongoRepository<CommentsAnalyzeSummary, String> {
    CommentsAnalyzeSummary findAnalyzeSummaryByVideoId(String videoId);
}
