package com.acme.aiassistant.services.comments.persistence;

import com.acme.aiassistant.model.comments.ConciseComment;

import java.util.List;

public interface CommentsPersistence{
    void saveConciseComments(List<ConciseComment> conciseCommentList);
    List<ConciseComment> getCommentsByVideoId(String videoId, int limit);
    void removeCommentsByVideoId(String videoId);
}
