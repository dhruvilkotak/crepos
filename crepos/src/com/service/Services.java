package com.service;

import java.sql.Blob;
import java.util.List;

import com.model.Files;
import com.doa.DAO;
import com.model.Directory;
import com.model.DirectoryEmployee;
import com.model.Employee;
import com.model.LeaveRequest;
import com.model.Login;
import com.model.Payment;
import com.model.Register;
public class Services {

	DAO dao = new DAO();

	public Login doLogin(Login login) {
		// TODO Auto-generated method stub
		return dao.doLogin(login);
	}

	public boolean doAddRegister(Register register) {
		// TODO Auto-generated method stub
		return dao.doAddRegister(register);
	}

	public boolean doAddLogin(Login login) {
		// TODO Auto-generated method stub
		return dao.doAddLogin(login);
	}

	public boolean doAddEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return dao.doAddEmployee(employee);
	}

	public Register doGetProfile(int employeeId) {
		// TODO Auto-generated method stub
		return dao.doGetProfile(employeeId);
	}

	public boolean doUpdateProfile(Register register) {
		// TODO Auto-generated method stub
		return dao.doUpdateProfile(register);
	}

	public boolean doAddLeaveRequest(LeaveRequest leaveRequest) {
		// TODO Auto-generated method stub
		return dao.doAddLeaveRequest(leaveRequest);
	}

	public List<LeaveRequest> doGetRequestLeave(int employeeId) {
		// TODO Auto-generated method stub
		return dao.doGetRequestLeave(employeeId);
	}

	public int getManagerId(int employeeId) {
		// TODO Auto-generated method stub
		return dao.getManagerId(employeeId);
	}

	public List<Employee> doGetEmployee(int i) {
		// TODO Auto-generated method stub
		return dao.doGetEmployee(i);
	}

	public List<Register> doGetManagerList() {
		// TODO Auto-generated method stub
		return dao.doGetManagerList();
	}

	public boolean doupdateAssignedManager(int employeeId, int managerId) {
		// TODO Auto-generated method stub
		return dao.doupdateAssignedManager(employeeId,managerId);
	}

	public boolean doUpdateRole(int employeeId, String role) {
		// TODO Auto-generated method stub
		return dao.doUpdateRole(employeeId,role);
	}

	public List<LeaveRequest> doGetRequestLeaveManager(int managerId) {
		// TODO Auto-generated method stub
		return dao.doGetRequestLeaveManager(managerId);
	}

	public LeaveRequest doUpdateRequestLeaveStatus(int requestId, String status) {
		// TODO Auto-generated method stub
		return dao.doUpdateRequestLeaveStatus(requestId,status);
	}

	public List<Employee> doGetEmployeeList() {
		// TODO Auto-generated method stub
		return dao.doGetEmployeeList();
	}

	public List<Directory> getOwnDirectoryList(int employeeId) {
		// TODO Auto-generated method stub
		return dao.getOwnDirectoryList(employeeId);
	}

	public List<Directory> getSharedDirectoryList(int employeeId) {
		// TODO Auto-generated method stub
		return dao.getSharedDirectoryList(employeeId);
	}

	public List<Directory> getSubEmployeeDirectoryList(
			List<Integer> employeeList) {
		// TODO Auto-generated method stub
		return dao.getSubEmployeeDirectoryList(employeeList);
	}

	public List<Directory> getPublicDirectoryList() {
		// TODO Auto-generated method stub
		return dao.getPublicDirectoryList();
	}

	public List<DirectoryEmployee> getSharedDirectoryEmployeeList(int employeeId) {
		// TODO Auto-generated method stub
		return dao.getSharedDirectoryEmployeeList(employeeId);
	}

	public int doCreateDirectory(Directory directory) {
		// TODO Auto-generated method stub
		return dao.doCreateDirectory(directory);
	}

	public boolean doAddDirectoryEmployeeList(
			List<DirectoryEmployee> directoryEmployeeList) {
		// TODO Auto-generated method stub
		return dao.doAddDirectoryEmployeeList(directoryEmployeeList);
	}

	public boolean doDeleteSubEmployeeDirectory(List<Integer> employeeList, int directoryId) {
		// TODO Auto-generated method stub
		return dao.doDeleteSubEmployeeDirectory(employeeList,directoryId);
	}

	public boolean doUpdateOwnDirectoryPermission(int ownerId, int directoryId,
			String permission) {
		// TODO Auto-generated method stub
		return dao.doUpdateOwnDirectoryPermission(ownerId,directoryId,permission);
	}

	public boolean doUpdateSubEmployeeDirectory(List<Integer> employeeList, String permission, int directoryId
			) {
		// TODO Auto-generated method stub
		return dao.doUpdateSubEmployeeDirectory(employeeList,permission,directoryId);
	}

	public boolean doUpdateDirectoryEmployee(DirectoryEmployee directoryEmployee) {
		// TODO Auto-generated method stub
		return dao.doUpdateDirectoryEmployee(directoryEmployee);
	}

	public boolean doDeleteSubEmployeeDirectoryPublic(int directoryId) {
		// TODO Auto-generated method stub
		return dao.doDeleteSubEmployeeDirectoryPublic(directoryId);
	}

	public boolean doDeleteEmployeeDirectory(int employeeId) {
		// TODO Auto-generated method stub
		return dao.doDeleteEmployeeDirectory(employeeId);
	}

	public boolean doAddEmployeeDirectoryOfManager(int employeeId, int managerId) {
		// TODO Auto-generated method stub
		return dao.doAddEmployeeDirectoryOfManager(employeeId,managerId);
	}

	public List<Files> getAllFiles(List<Integer> directoryIdList) {
		// TODO Auto-generated method stub
		return dao.getAllFiles(directoryIdList);
	}

	public Files getFileBlob(int fileId) {
		// TODO Auto-generated method stub
		return dao.getFileBlob(fileId);
	}

	public boolean doUploadFile(Files file) {
		// TODO Auto-generated method stub
		return dao.doUploadFile(file);
	}

	public String getSuperManagerPermission(int directoryId, int employeeId) {
		// TODO Auto-generated method stub
		return dao.getSuperManagerPermission(directoryId,employeeId);
	}

	public boolean doDeleteSubEmployeeDirectory(List<Integer> subEmployeeList) {
		// TODO Auto-generated method stub
		return dao.doDeleteSubEmployeeDirectory(subEmployeeList);
	}

	public boolean doUpdateSubEmployeeDirectory(List<Integer> employeeList,
			String permission, int directoryId, int employeeId) {
		// TODO Auto-generated method stub
		return dao.doUpdateSubEmployeeDirectory(employeeList, permission, directoryId,employeeId);
	}

	public List<Register> getAllEmployees() {
		// TODO Auto-generated method stub
		return dao.getAllEmployees();
	}

	public boolean doDeleteSubEmployeeDirectoryATE(int directoryId, int employeeId) {
		// TODO Auto-generated method stub
		return dao.doDeleteSubEmployeeDirectoryATE(directoryId,employeeId);
	}

	public boolean doUpdateSalaryEmployee(int employeeId, Double salary) {
		// TODO Auto-generated method stub
		return dao.doUpdateSalaryEmployee(employeeId,salary);
	}

	public boolean doUpdateBonusEmployee(int employeeId, Double bonus) {
		// TODO Auto-generated method stub
		return dao.doUpdateBonusEmployee(employeeId,bonus);
	}

	public List<Register> getSubEmployees(List<Integer> employeeList) {
		// TODO Auto-generated method stub
		return dao.getSubEmployees(employeeList);
	}

	public List<Payment> doGetPaymentList(String paymentDate, int employeeId) {
		// TODO Auto-generated method stub
		return dao.doGetPaymentList(paymentDate,employeeId);
	}

	public int doGetLeaveDays(int employeeId) {
		// TODO Auto-generated method stub
		return dao.doGetLeaveDays(employeeId);
	}

	public boolean doSubtractLeaveEmployee(int employeeId, int diff11) {
		// TODO Auto-generated method stub
		return dao.doSubtractLeaveEmployee(employeeId,diff11);
	}

	public Object getEmployeeRole(int id) {
		// TODO Auto-generated method stub
		return dao.getEmployeeRole(id);
	}

	public boolean alreadyHaveDirectory(int directoryId, int ateId) {
		return dao.alreadyHaveDirectory(directoryId, ateId);
	}
	

}
