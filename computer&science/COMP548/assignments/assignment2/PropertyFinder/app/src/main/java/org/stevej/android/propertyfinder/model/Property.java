package org.stevej.android.propertyfinder.model;

public class Property {
	private String title;
	private String price;
	private int thumbnail_id;

	public Property(String title, String price, int thumbnail_id) {
		this.title = title;
		this.price = price;
		this.thumbnail_id = thumbnail_id;
	}

	public String getTitle() {
		return title;
	}

	public String getPrice() {
		return price;
	}

	public int getThumbnailID() {
		return thumbnail_id;
	}

}
