package com.model;

public class Employee {

	int employeeId;
	int assignedManager;
	Double salary;
	Double bonus;
	int leaveDays;
	
	public int getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getAssignedManager() {
		return assignedManager;
	}
	public void setAssignedManager(int assignedManager) {
		this.assignedManager = assignedManager;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	
	
}
