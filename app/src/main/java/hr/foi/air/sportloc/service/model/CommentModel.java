package hr.foi.air.sportloc.service.model;

import java.io.Serializable;

public class CommentModel implements Serializable {
    private int userId;
    private int commentatorId;
    private boolean vote;
    private String comment;
    private String commentator;

    public CommentModel(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(int commentatorId) {
        this.commentatorId = commentatorId;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }
}
