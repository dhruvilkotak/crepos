package com.model;

import java.sql.Blob;

public class Files {

	int fileId;
	String fileName;
	int parentDir;
	int ownerId;
	Blob file;
	String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getParentDir() {
		return parentDir;
	}
	public void setParentDir(int parentDir) {
		this.parentDir = parentDir;
	}

	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}

	
}
