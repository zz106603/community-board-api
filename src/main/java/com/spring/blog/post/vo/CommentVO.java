package com.spring.blog.post.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentVO {

	private long id;
	private String userId;
	private long postId;
	private String comment;
	private LocalDateTime commentDate;
	private String deleteYn;
	private LocalDateTime deleteDate;

}
