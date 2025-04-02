package com.acme.aiassistant.model.comments;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment_sentiments")
@CompoundIndexes( {
        @CompoundIndex(name = "comment_object_index", def = "{'commentId': 1, 'sentimentObject': 1}", unique = true)
})
public class CommentSentimentResult {
    @Id
    private String id;
    private String commentId;
    private String videoId;
    private String sentimentObject;
    private Sentiment sentiment;
    private String sentimentReason;

    public CommentSentimentResult(String commentId, String videoId, String sentimentObject, Sentiment sentiment, String sentimentReason) {
        this.commentId = commentId;
        this.videoId = videoId;
        this.sentimentObject = sentimentObject;
        this.sentiment = sentiment;
        this.sentimentReason = sentimentReason;
    }

    public CommentSentimentResult() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getSentimentObject() {
        return sentimentObject;
    }

    public void setSentimentObject(String sentimentObject) {
        this.sentimentObject = sentimentObject;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getSentimentReason() {
        return sentimentReason;
    }

    public void setSentimentReason(String sentimentReason) {
        this.sentimentReason = sentimentReason;
    }
}
