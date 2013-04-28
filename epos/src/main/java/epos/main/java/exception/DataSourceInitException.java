package epos.main.java.exception;

@SuppressWarnings("serial")
public class DataSourceInitException extends Exception {
	
	public DataSourceInitException(String message){
		super(message);
		this.printExcep();
	}
	
	public void printExcep(){
		System.out.println(this.getMessage());
	}
}
