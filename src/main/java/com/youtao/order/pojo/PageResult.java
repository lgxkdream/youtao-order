package com.youtao.order.pojo;

import java.util.List;

/**
 * @title: PageResult
 * @description: 
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:11:40
 */
public class PageResult<T> {

	private Integer totle;

	private List<T> data;

	public PageResult() {
	}

	public PageResult(Integer totle, List<T> data) {
		this.totle = totle;
		this.data = data;
	}

	public Integer getTotle() {
		return totle;
	}

	public void setTotle(Integer totle) {
		this.totle = totle;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
