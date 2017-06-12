package com.model;

public class Directory {
	int directoryId;
	String dirName;
	int ownerId;
	String permission;
	public int getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(int directoryId) {
		this.directoryId = directoryId;
	}
	
	public String getDirName() {
		return dirName;
	}
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
}
