package epos.main.java.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ItemOrder {
	
	private int itemOrderId;
	
	private int itemId;
	
	private String itemName;
	
	private int itemCount;	
	
	private BigDecimal price;
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
	
	private int billNo;
	
	private Date createTime;

	
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	
	
	
}
