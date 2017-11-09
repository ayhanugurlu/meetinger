package com.au.example.meetinger.resource.dto;

public class ItemDto {

	private Long id;

	private ItemTypeDto itemType;

	private String value;
	
	private Long meeetingId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemTypeDto getItemType() {
		return itemType;
	}

	public void setItemType(ItemTypeDto itemType) {
		this.itemType = itemType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getMeeetingId() {
		return meeetingId;
	}

	public void setMeeetingId(Long meeetingId) {
		this.meeetingId = meeetingId;
	}
	
	

}
