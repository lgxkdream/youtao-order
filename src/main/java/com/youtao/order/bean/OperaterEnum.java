package com.youtao.order.bean;

/**
 * @title: OperaterEnum
 * @description: 操作符枚举
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:03:57
 */
public enum OperaterEnum {

	EQUAL("="), GREATER(">"), LESS("<"), GREATER_EQUAL(">="), LESS_EQUAL("<="), NOT_EQUAL("<>");

	private String operater;

	private OperaterEnum(String operater) {
		this.operater = operater;
	}

	@Override
	public String toString() {
		return this.operater;
	}
}
