package com.acme.aiassistant.services.comments;

import com.acme.aiassistant.model.comments.CommentDto;
import com.acme.aiassistant.model.comments.CommentsAnalyzeSummary;
import com.acme.aiassistant.model.comments.ConciseComment;
import com.acme.aiassistant.services.comments.persistence.AnalysisSummaryPersistence;
import com.acme.aiassistant.services.comments.persistence.CommentsPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisSummaryService {
    private final static int MAX_TOP_COMMENTS = 50;
    private final AnalysisSummaryPersistence analysisSummaryPersistence;
    private final CommentsPersistence commentsPersistence;

    public AnalysisSummaryService(AnalysisSummaryPersistence analysisSummaryPersistence, CommentsPersistence commentsPersistence) {
        this.analysisSummaryPersistence = analysisSummaryPersistence;
        this.commentsPersistence = commentsPersistence;
    }

    public CommentsAnalyzeSummary getCommentsAnalysisSummary(String videoId) {
        CommentsAnalyzeSummary commentsAnalysisSummary = analysisSummaryPersistence.getCommentsAnalysisSummary(videoId);

        List<ConciseComment> topComments = commentsPersistence.getCommentsByVideoId(videoId, MAX_TOP_COMMENTS);
        List<CommentDto> topCommentsDtoList = topComments.stream().map(this::convertConciseCommentToCommentDto).toList();

        commentsAnalysisSummary.setTopRatedComments(topCommentsDtoList);

        return commentsAnalysisSummary;
    }

    private CommentDto convertConciseCommentToCommentDto(ConciseComment conciseComment) {
        return new CommentDto(
                conciseComment.getTextDisplay(),
                conciseComment.getLikeCount(),
                conciseComment.getAuthorDisplayName(),
                conciseComment.getAuthorProfileImageUrl(),
                conciseComment.getPublishedAt());
    }

}
