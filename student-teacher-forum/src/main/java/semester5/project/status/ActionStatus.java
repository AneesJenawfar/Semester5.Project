package semester5.project.status;

public class ActionStatus {

	private String message;

	public ActionStatus(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ActionStatus [message=" + message + "]";
	}

}
