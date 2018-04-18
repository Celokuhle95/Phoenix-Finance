package com.phoenix.finance.entity.client_enum;

public enum Title {
	PROF("Prof"), DR("Dr"), MR("Mr"), MRS("Mrs"), MISS("Miss");

	private String title;

	private Title(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
