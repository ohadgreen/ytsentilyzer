package com.acme.aiassistant.repositories;


import com.acme.aiassistant.model.comments.ConciseComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConciseCommentRepository extends MongoRepository<ConciseComment, String> {
    ConciseComment findConciseCommentByCommentId(String id);
    List<ConciseComment> findConciseCommentByVideoId(String videoId);
    List<ConciseComment> findTopRatedCommentsByVideoId(String videoId, Pageable pageable);
    void deleteByVideoId(String videoId);
}
