package com.acme.aiassistant.services.comments.persistence;

import com.acme.aiassistant.model.comments.ConciseComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("memory")
public class CommentsPersistInMemory implements CommentsPersistence {

    private final Logger logger = LoggerFactory.getLogger(CommentsPersistInMemory.class);
    public Map<String, List<ConciseComment>> conciseCommentsMap = new HashMap<>();

    @Override
    public void saveConciseComments(List<ConciseComment> conciseCommentList) {
        if (conciseCommentList.isEmpty()) {
            return;
        }
        String videoId = conciseCommentList.getFirst().getVideoId();
        if (conciseCommentsMap.containsKey(videoId)) {
            logger.warn("videoId exists in map");
        } else {
            conciseCommentsMap.put(videoId, conciseCommentList);
            logger.info("saved {} comments for videoId {}", conciseCommentList.size(), videoId);
        }
    }

    @Override
    public List<ConciseComment> getCommentsByVideoId(String videoId, int limit) {
        List<ConciseComment> conciseCommentList = conciseCommentsMap.get(videoId);

        return conciseCommentList.stream()
                .sorted(Comparator.comparingInt(ConciseComment::getLikeCount).reversed())
                .limit(limit)
                .toList();
    }

    @Override
    public void removeCommentsByVideoId(String videoId) {
        conciseCommentsMap.remove(videoId);
    }
}
