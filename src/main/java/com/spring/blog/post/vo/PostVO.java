package com.spring.blog.post.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostVO {
	
	private long id;
	private String title;
	private String content;
	private String writer;
	private String category;
	private long selectCount;
	private long recomCount;
	private LocalDateTime writeDate;
	private LocalDateTime updateDate;
	private LocalDateTime deleteDate;
	private String deleteYn;
	
	public static PostVO from(PostVO post) {
		return PostVO.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.writer(post.getWriter())
				.category(post.getCategory())
				.selectCount(post.getSelectCount())
				.recomCount(post.getRecomCount())
				.writeDate(post.getWriteDate())
				.updateDate(post.getUpdateDate())
				.deleteDate(post.getDeleteDate())
				.deleteYn(post.getDeleteYn())
				.build();
	}
	
	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", title=" + title + ", content=" + content + ", writer=" + writer + ", category="
				+ category + ", writeDate=" + writeDate + ", updateDate=" + updateDate + ", deleteDate=" + deleteDate
				+ ", deleteYn=" + deleteYn + "]";
	}
	
	
	

}
