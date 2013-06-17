package epos.main.java.vo;

import java.math.BigDecimal;
import java.util.List;

public class Item {

	private int itemId;
	
	private String itemName;
	
	private String picName;
	
	private String imageUrl;
	
	private String smallPicName;
	
	private String smallImageUrl;
	
	private BigDecimal price;
	
	private int itemTypeId;
	
	private String itemType;
	
//	private boolean ifCanOrderHalf;
	
	/**
	 * 菜品的口味对象
	 */
	private List<Flavor> flavors;
	
	/**
	 * 菜品口味flavorId组成的字符串数组
	 */
	private String flavorIds;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

//	public boolean isIfCanOrderHalf() {
//		return ifCanOrderHalf;
//	}
//
//	public void setIfCanOrderHalf(boolean ifCanOrderHalf) {
//		this.ifCanOrderHalf = ifCanOrderHalf;
//	}

	public List<Flavor> getFlavors() {
		return flavors;
	}

	public void setFlavors(List<Flavor> flavors) {
		this.flavors = flavors;
	}

	public String getFlavorIds() {
		return flavorIds;
	}

	public void setFlavorIds(String flavorIds) {
		this.flavorIds = flavorIds;
	}

	public String getSmallPicName() {
		return smallPicName;
	}

	public void setSmallPicName(String smallPicName) {
		this.smallPicName = smallPicName;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}	
	
}
