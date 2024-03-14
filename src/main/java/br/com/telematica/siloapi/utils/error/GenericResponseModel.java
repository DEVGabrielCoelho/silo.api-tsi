package br.com.telematica.siloapi.utils.error;

import io.swagger.v3.oas.annotations.media.Schema;

public class GenericResponseModel {

	@Schema(name = "code", type = "Integer", description = "Request Code.", example = "200")
	private Integer code;
	@Schema(name = "description", type = "String", description = "Request description.", example = "Description")
	private String desc;
	@Schema(name = "date", type = "Date", description = "Request date.", example = "200")
	private String date;
	@Schema(name = "resultListSize", type = "Integer", description = "Request result list size.", example = "200")
	private Integer listSize;
	@Schema(name = "resultObject", type = "Integer", description = "Request result object.")
	private Object object;

	public GenericResponseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GenericResponseModel(Integer code, String desc, String date, Integer listSize, Object object) {
		super();
		this.code = code;
		this.desc = desc;
		this.date = date;
		this.listSize = listSize;
		this.object = object;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenericResponseModel [code=");
		builder.append(code);
		builder.append(", desc=");
		builder.append(desc);
		builder.append(", date=");
		builder.append(date);
		builder.append(", listSize=");
		builder.append(listSize);
		builder.append(", object=");
		builder.append(object);
		builder.append("]");
		return builder.toString();
	}

}
