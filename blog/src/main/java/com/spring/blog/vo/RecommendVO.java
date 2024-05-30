package com.spring.blog.vo;

import java.time.LocalDateTime;

public class RecommendVO {

	private long id;
	private String userId;
	private long postId;
	private LocalDateTime recommendDate;
	private String deleteYn;
	private LocalDateTime deleteDate;
	
	private int countFlag;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public LocalDateTime getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(LocalDateTime recommendDate) {
		this.recommendDate = recommendDate;
	}
	
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(LocalDateTime deleteDate) {
		this.deleteDate = deleteDate;
	}
	public int getCountFlag() {
		return countFlag;
	}
	public void setCountFlag(int countFlag) {
		this.countFlag = countFlag;
	}

	
	
	
}
