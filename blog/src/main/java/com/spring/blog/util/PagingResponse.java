package com.spring.blog.util;

import java.util.ArrayList;
import java.util.List;

import com.spring.blog.dto.Pagination;

public class PagingResponse<T> {

	private List<T> list = new ArrayList<>();
	private Pagination pagination;
	
	public PagingResponse(List<T> list, Pagination pagination) {
		this.list.addAll(list);
		this.pagination = pagination;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	
}
