package com.spring.blog.dto;

public class SearchDTO {
	
	private long page;
	private long recordSize;
	private long pageSize;
	private String keyword;
	private String serchType;
	private Pagination pagination;
	
	private int orderType;
	private String orderCol;
	
	
	public SearchDTO() {
		this.page = 1;
		this.recordSize = 10;
		this.pageSize = 5;
		this.orderType = 1;
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

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getOrderCol() {
		return orderCol;
	}

	public void setOrderCol(String orderCol) {
		this.orderCol = orderCol;
	}

	@Override
	public String toString() {
		return "SearchDTO [page=" + page + ", recordSize=" + recordSize + ", pageSize=" + pageSize + ", keyword="
				+ keyword + ", serchType=" + serchType + ", pagination=" + pagination + ", orderType=" + orderType
				+ ", orderCol=" + orderCol + ", getOffset()=" + getOffset() + ", getPage()=" + getPage()
				+ ", getRecordSize()=" + getRecordSize() + ", getPageSize()=" + getPageSize() + ", getKeyword()="
				+ getKeyword() + ", getSerchType()=" + getSerchType() + ", getPagination()=" + getPagination()
				+ ", getOrderType()=" + getOrderType() + ", getOrderCol()=" + getOrderCol() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
	
	
	
}
