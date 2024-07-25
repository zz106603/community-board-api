package com.spring.blog.post.dto;

import lombok.Getter;
import lombok.Setter;

public class Pagination {
	
	private long totalRecordCount; //전체 데이터 수
	private long totalPageCount; //전체 페이지 수
	private long startPage; //첫 페이지 번호
	private long endPage; //끝 페이지 번호
	private long limitStart; //LIMIT 시작 위치
	private boolean existPrevPage; //이전 페이지 존재 여부
	private boolean existNextPage; //다음 페이지 존재 여부
	
	public Pagination(long totalRecordCount, SearchDTO params) {
		//총 게시글 수가 1개 이상이면
		if(totalRecordCount > 0) {
			this.totalRecordCount = totalRecordCount;
			calculation(params);
		}
	}
	
	private void calculation(SearchDTO params) {
		
		//전체 페이지 수 계산
		totalPageCount = ((totalRecordCount-1) / params.getRecordSize()) + 1;
		
		//현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
		if(params.getPage() > totalPageCount) {
			params.setPage(totalPageCount);
		}
		
		//첫 페이지 번호 계산
		startPage = ((params.getPage() - 1) / params.getPageSize()) * params.getPageSize() + 1;
		
		//끝 페이지 번호 계산
		endPage = startPage + params.getPageSize() - 1;
		
		//끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 저장
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		//Limit 위치 계산
		limitStart = (params.getPage() - 1) * params.getRecordSize();
		
		//이전 페이지 존재 여부 확인
		existPrevPage = startPage != 1;
		
		//다음 페이지 존재 여부 확인
		existNextPage = (endPage * params.getRecordSize()) < totalRecordCount;
	}

	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public long getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public long getStartPage() {
		return startPage;
	}

	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}

	public long getEndPage() {
		return endPage;
	}

	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}

	public long getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}

	public boolean isExistPrevPage() {
		return existPrevPage;
	}

	public void setExistPrevPage(boolean existPrevPage) {
		this.existPrevPage = existPrevPage;
	}

	public boolean isExistNextPage() {
		return existNextPage;
	}

	public void setExistNextPage(boolean existNextPage) {
		this.existNextPage = existNextPage;
	}
	
	

}
