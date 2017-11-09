package com.au.example.meetinger.persistence.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * Meting item type
 * For example action item, decision...
 * 
 * @author ayhanu
 *
 */
@Entity(name = "ITEM_TYPE")
public class ItemType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToMany(targetEntity = Item.class, mappedBy = "itemType")
	@OrderBy("name ASC")
	private List<Item> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
