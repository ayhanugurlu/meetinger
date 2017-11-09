package com.au.example.meetinger.persistence.model;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Meting items.
 * 
 * @author ayhanu
 *
 */
@StaticMetamodel(Item.class)
public class Item_ {

	public static volatile SingularAttribute<Item, Long> id;

	public static volatile SingularAttribute<Item, ItemType> itemType;

	public static volatile SingularAttribute<Item, String> value;

	public static volatile ListAttribute<Item, Meeting> meeting;

}
