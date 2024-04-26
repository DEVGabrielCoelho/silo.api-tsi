package br.com.telematica.siloapi.model.dto;

public class ResponseAuthDTO {

	private String token;
	private String data;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseAuthDTO [");
		if (token != null) {
			builder.append("token=").append(token).append(", ");
		}
		if (data != null) {
			builder.append("data=").append(data);
		}
		builder.append("]");
		return builder.toString();
	}

	public ResponseAuthDTO(String token, String data) {
		super();
		this.token = token;
		this.data = data;
	}

	public ResponseAuthDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
