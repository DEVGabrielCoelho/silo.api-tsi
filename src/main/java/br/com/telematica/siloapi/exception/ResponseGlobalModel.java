package br.com.telematica.siloapi.exception;

public class ResponseGlobalModel {
	private boolean error;
	private String message;
	private String dateRequest;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateRequest() {
		return dateRequest;
	}

	public void setDateRequest(String dateRequest) {
		this.dateRequest = dateRequest;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseGlobalModel [error=").append(error).append(", ");
		if (message != null) {
			builder.append("message=").append(message).append(", ");
		}
		if (dateRequest != null) {
			builder.append("dateRequest=").append(dateRequest);
		}
		builder.append("]");
		return builder.toString();
	}

	public ResponseGlobalModel(boolean error, String message, String dateRequest) {
		super();
		this.error = error;
		this.message = message;
		this.dateRequest = dateRequest;
	}

	public ResponseGlobalModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
