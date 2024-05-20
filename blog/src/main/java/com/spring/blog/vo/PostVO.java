package com.spring.blog.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {
	
	private long id;
	private String title;
	private String content;
	private String writer;
	private String category;
	private LocalDateTime writeDate;
	private LocalDateTime updateDate;
	private LocalDateTime deleteDate;
	private String deleteYn;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public LocalDateTime getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(LocalDateTime writeDate) {
		this.writeDate = writeDate;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(LocalDateTime deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", content=" + content + ", writer=" + writer + ", category="
				+ category + ", writeDate=" + writeDate + ", updateDate=" + updateDate + ", deleteDate=" + deleteDate
				+ ", deleteYn=" + deleteYn + "]";
	}
	
	
	

}
