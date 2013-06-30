package epos.main.java.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

public class ItemOrderVo extends ItemOrder{
	
	private String itemName;
	
	private String itemType;
	
	private int departmentId;
	
	private String departmentName;
	
	private String printerInfo;
	
	private String flavorType;
	
	private BigDecimal itemPrice;

	private Date createTime;
	
	public String getCreateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPrinterInfo() {
		return printerInfo;
	}

	public void setPrinterInfo(String printerInfo) {
		this.printerInfo = printerInfo;
	}

	public String getFlavorType() {
		return flavorType;
	}

	public void setFlavorType(String flavorType) {
		this.flavorType = flavorType;
	}
	
	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public static void main(String[] args) {
		ItemOrderVo vo = new ItemOrderVo();
		vo.setCreateTime(new Date());
		JSONObject jsonObj = JSONObject.fromObject(vo);
		System.out.println(jsonObj.toString());
		
	}
	
}
