package kr.co.devst.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentVO {
	private int commentId;
	private int brdId;
	private int memId;
	
	@NotEmpty(message = "댓글을 입력해주세요")
	@Size(max = 150, message = "50자 이내로 작성해주세요")
	private String content;
	private int likeCount;
	private int dislikeCount;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getBrdId() {
		return brdId;
	}
	public void setBrdId(int brdId) {
		this.brdId = brdId;
	}
	public int getMemId() {
		return memId;
	}
	public void setMemId(int memId) {
		this.memId = memId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getDislikeCount() {
		return dislikeCount;
	}
	public void setDislikeCount(int dislikeCount) {
		this.dislikeCount = dislikeCount;
	}
	
	
	
}
