package com.model;

public class LeaveRequest {
int requestId;
int employeeId;
int managerId;
String requestDate;
String fromDate;
String toDate;
String status;
String reason;

public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public int getRequestId() {
	return requestId;
}
public void setRequestId(int requestId) {
	this.requestId = requestId;
}
public int getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(int employeeId) {
	this.employeeId = employeeId;
}
public int getManagerId() {
	return managerId;
}
public void setManagerId(int managerId) {
	this.managerId = managerId;
}
public String getRequestDate() {
	return requestDate;
}
public void setRequestDate(String requestDate) {
	this.requestDate = requestDate;
}
public String getFromDate() {
	return fromDate;
}
public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}
public String getToDate() {
	return toDate;
}
public void setToDate(String toDate) {
	this.toDate = toDate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
