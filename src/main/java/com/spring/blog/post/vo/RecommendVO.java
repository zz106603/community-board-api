package com.spring.blog.post.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendVO {

	private long id;
	private String userId;
	private long postId;
	private LocalDateTime recommendDate;
	private String deleteYn;
	private LocalDateTime deleteDate;
	private int countFlag;
	
	public static RecommendVO from(RecommendVO recom) {
		return RecommendVO.builder()
				.id(recom.getId())
				.userId(recom.getUserId())
				.postId(recom.getPostId())
				.recommendDate(recom.getRecommendDate())
				.deleteYn(recom.getDeleteYn())
				.deleteDate(recom.getDeleteDate())
				.countFlag(recom.getCountFlag())
				.build();
	}
	
	
}
