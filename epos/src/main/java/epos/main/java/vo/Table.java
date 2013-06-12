package epos.main.java.vo;

public class Table {

	private int tableId;
	
	private int tableNo;
	
	private String tableName;
	
	private int seatingNum;
	
//	餐台状态
	private int tableStatus;
		public final static int STATUS_FREE = 0; //空闲
		public final static int STATUS_ACTIVED = 1; //在用
		public final static int STATUS_CHECKED = 2; //已买单
		public final static int STATUS_PAID = 3; //已付款
		public final static int STATUS_ORDERED = 4; //已预订
	
	public int getTableNo() {
		return tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(int tableStatus) {
		this.tableStatus = tableStatus;
	}

	public int getSeatingNum() {
		return seatingNum;
	}

	public void setSeatingNum(int seatingNum) {
		this.seatingNum = seatingNum;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	
}
