package com.mvc.dao.impl;

public enum IsDelete {
	// 0:未删除；1：已删除
	NO(0), YES(1);

	public int value;

	private IsDelete(int value) {
		this.value = value;
	}
}
