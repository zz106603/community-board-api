package com.spring.blog.dto;

public class SearchDTO {
	
	private long page;
	private long recordSize;
	private long pageSize;
	private String keyword;
	private String serchType;
	private Pagination pagination;
	
	
	public SearchDTO() {
		this.page = 1;
		this.recordSize = 10;
		this.pageSize = 5;
	}
	
	public long getOffset() {
		return (page - 1) * recordSize;
	}
	
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	public long getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(long recordSize) {
		this.recordSize = recordSize;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSerchType() {
		return serchType;
	}
	public void setSerchType(String serchType) {
		this.serchType = serchType;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	
	
	
}
