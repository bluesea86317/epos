package epos.main.java.core;

public class ActionConfig {
	private String actionPath;
	private String actionclass;
	private ActionForward actionForward;
	
	public String getActionPath() {
		return actionPath;
	}
	public void setActionPath(String actionPath) {
		this.actionPath = actionPath;
	}
	public String getActionclass() {
		return actionclass;
	}
	public void setActionclass(String actionclass) {
		this.actionclass = actionclass;
	}
	public ActionForward getActionForward() {
		return actionForward;
	}
	public void setActionForward(ActionForward actionForward) {
		this.actionForward = actionForward;
	}
	
	
}
