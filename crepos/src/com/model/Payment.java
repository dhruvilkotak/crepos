package com.model;

public class Payment {
int employeeId;
int paymentId;
Double totalSalary;
Double salary;
Double bonus;
String paymentDate;


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
public int getEmployeeId() {
	return employeeId;
}
public void setEmployeeId(int employeeId) {
	this.employeeId = employeeId;
}
public int getPaymentId() {
	return paymentId;
}
public void setPaymentId(int paymentId) {
	this.paymentId = paymentId;
}
public Double getTotalSalary() {
	return totalSalary;
}
public void setTotalSalary(Double totalSalary) {
	this.totalSalary = totalSalary;
}
public String getPaymentDate() {
	return paymentDate;
}
public void setPaymentDate(String paymentDate) {
	this.paymentDate = paymentDate;
}


}
