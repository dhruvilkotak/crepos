package com.model;

public class DirectoryEmployee {
	
	int directoryEmployeeId;
	int directoryId;
	int employeeId;
	String permission;
	int ateManagerId;
	
	public int getAteManagerId() {
		return ateManagerId;
	}
	public void setAteManagerId(int ateManagerId) {
		this.ateManagerId = ateManagerId;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getDirectoryEmployeeId() {
		return directoryEmployeeId;
	}
	public void setDirectoryEmployeeId(int directoryEmployeeId) {
		this.directoryEmployeeId = directoryEmployeeId;
	}
	public int getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(int directoryId) {
		this.directoryId = directoryId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	

}
