package org.openstack.swift.api;

public enum ListObjectsFilter {
	Prefix("prefix"), Delimiter("delimiter"), Path("path"), Marker("marker");

	private String filterName;

	private ListObjectsFilter(String filterName) {
		this.filterName = filterName;
	}

	public String getFilterName() {
		return filterName;
	}
}
