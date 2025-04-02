package com.acme.aiassistant.controllers;

import com.acme.aiassistant.model.comments.*;
import com.acme.aiassistant.model.rawcomment.CommentThread;
import com.acme.aiassistant.services.SentimentAnalysisService;
import com.acme.aiassistant.services.comments.AnalysisSummaryService;
import com.acme.aiassistant.services.comments.CommentsHandlingService;
import com.acme.aiassistant.services.comments.GetRawCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sentiment")
public class SentimentAnalysisController {

    private final Logger logger = LoggerFactory.getLogger(SentimentAnalysisController.class);

    private final SentimentAnalysisService sentimentAnalysisService;
    private final CommentsHandlingService commentsHandlingService;
    private final AnalysisSummaryService analysisSummaryService;

    public SentimentAnalysisController(SentimentAnalysisService sentimentAnalysisService, CommentsHandlingService commentsHandlingService, AnalysisSummaryService analysisSummaryService) {
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.commentsHandlingService = commentsHandlingService;
        this.analysisSummaryService = analysisSummaryService;
    }

    @PostMapping("/comments")
    public SentimentAnalysisResponse analyzeCommentsSentiment(@RequestBody SentimentAnalysisRequest sentimentAnalysisRequest) {
        return sentimentAnalysisService.analyzeCommentsChunk(sentimentAnalysisRequest);
    }

    @GetMapping("/comments/{videoId}")
    public CommentsAnalyzeSummary getCommentsAnalysisSummary(@PathVariable String videoId) {
        return analysisSummaryService.getCommentsAnalysisSummary(videoId);
    }

    @PostMapping("/videocomments")
    public String getRawComments(@RequestBody VideoCommentsRequest videoCommentsRequest) {
        commentsHandlingService.handleGetCommentList(videoCommentsRequest);
        return "action completed";
    }

    @GetMapping("/comments/{videoId}/{commentsNum}")
    public List<ConciseComment> getConciseComments(@PathVariable String videoId, @PathVariable Integer commentsNum) {
        return commentsHandlingService.getConciseCommentList(videoId, commentsNum);
    }

}
