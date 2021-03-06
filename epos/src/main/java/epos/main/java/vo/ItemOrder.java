package epos.main.java.vo;

import java.math.BigDecimal;

public class ItemOrder {
	
	private int itemOrderId;
	
	private int itemId;
	
	private int itemCount;	
	
	private BigDecimal price;
	
	private int flavorId;
	
//	菜品清单打印状态
	private int printingStatus;
		public final static int PRINTING_STATUS_NO = 0;
		public final static int PRINTING_STATUS_YES = 1;
	
//	菜品提供状态, 标示是否已经上菜
	private int providingStatus;
		public final static int PROVIDING_STATUS_NO = 0;
		public final static int PROVIDING_STATUS_YES = 1;
//	支付状态, 标示是否已经买单
	private int paymentStatus;
		public final static int PAYMENT_STATUS_NO = 0;
		public final static int PAYMENT_STATUS_YES = 1;
	
	private int tableNo;
	
	private String billNo;
	
//	菜单类型， 1代表点菜或者加菜   0代表退菜
	private int orderType = ORDER_TYPE_BOOK;
		public final static int ORDER_TYPE_BOOK = 1;
		public final static int ORDER_TYPE_CANCEL = 0;

	
	public int getItemOrderId() {
		return itemOrderId;
	}

	public void setItemOrderId(int itemOrderId) {
		this.itemOrderId = itemOrderId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}	

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getPrintingStatus() {
		return printingStatus;
	}

	public void setPrintingStatus(int printingStatus) {
		this.printingStatus = printingStatus;
	}

	public int getProvidingStatus() {
		return providingStatus;
	}

	public void setProvidingStatus(int providingStatus) {
		this.providingStatus = providingStatus;
	}
	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getTableNo() {
		return tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(int flavorId) {
		this.flavorId = flavorId;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}	
}
