package org.ubuntuone.music.organizer.model;

public class Meta {
	
	private String status;
	private String message;
	private Integer total;
	private String limit;
	private String offset;
	private String next_batch;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public String getLimit() {
		return limit;
	}
	
	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	public String getOffset() {
		return offset;
	}
	
	public void setOffset(String offset) {
		this.offset = offset;
	}
	
	public String getNext_batch() {
		return next_batch;
	}
	
	public void setNext_batch(String next_batch) {
		this.next_batch = next_batch;
	}
	
}
