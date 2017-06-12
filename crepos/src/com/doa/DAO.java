package com.doa;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.model.Directory;
import com.model.DirectoryEmployee;
import com.model.Employee;
import com.model.Files;
import com.model.LeaveRequest;
import com.model.Login;
import com.model.Payment;
import com.model.Register;
//import com.sun.mail.imap.Utility;
import com.utility.Connection;


public class DAO {

	private SessionFactory sessionFactory = Connection.getSessionFactory();
	String status = "failed";
	public Login doLogin(Login login) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			
			List<Login> loginList = session.createCriteria(Login.class)
					.add(Restrictions.eq("employeeId", login.getEmployeeId()))
					.add(Restrictions.eq("password", login.getPassword()))
					.list();
			tx.commit();
			return loginList.get(0);
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

	}
	public boolean doAddRegister(Register register) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(register);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doAddLogin(Login login) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(login);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doAddEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(employee);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public Register doGetProfile(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Register> registerList = session.createCriteria(Register.class)
					.add(Restrictions.eq("employeeId", employeeId))
				.list();
			tx.commit();
			return registerList.get(0);
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateProfile(Register register) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.update(register);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doAddLeaveRequest(LeaveRequest leaveRequest) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<LeaveRequest> leaveRequestList = session.createCriteria(LeaveRequest.class)
							.add(Restrictions.disjunction()
							.add(Restrictions.eq("status", "approve"))
							.add(Restrictions.eq("status", "pending"))
							)
				.list();
			long leaveFrom = Date.parse(leaveRequest.getFromDate());
			   long leaveTo=Date.parse(leaveRequest.getToDate()) ;
			if(leaveRequestList.size()>0)
			{
				long objFrom;
				long objTo;
				for(LeaveRequest obj: leaveRequestList)
				{	
					objFrom=Date.parse(obj.getFromDate());
					objTo=Date.parse(obj.getToDate());
					/*if(leaveFrom>objFrom && leaveFrom>objTo )
					{
						
					}
					else if(leaveFrom<objTo && leaveTo <objFrom )
					{
						
					}
					else */
					if(!(leaveTo<objFrom || leaveFrom > objTo))
					{

						tx.commit();
						return false;	
					}
						
				}
			}
				session.save(leaveRequest);
				tx.commit();
				return true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public List<LeaveRequest> doGetRequestLeave(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<LeaveRequest> leaveRequestList = session.createCriteria(LeaveRequest.class)
					.add(Restrictions.eq("employeeId", employeeId))
				.list();
			tx.commit();
			return leaveRequestList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public int getManagerId(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Employee> employee = session.createCriteria(Employee.class)
					.add(Restrictions.eq("employeeId", employeeId))
				.list();
			tx.commit();
			return employee.get(0).getAssignedManager();
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			session.close();
		}
	}
	public List<Employee> doGetEmployee(int i) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Employee> employee;
			if(i==0)
			{
				employee = session.createCriteria(Employee.class)
						.add(Restrictions.eq("assignedManager",0))
					.list();
			}else
			{
				employee= session.createCriteria(Employee.class)
						.add(Restrictions.gt("assignedManager",0))
					.list();
			}
			tx.commit();
			return employee;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Register> doGetManagerList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			 Query query = session.createQuery("from Register where  (role = 'manager' or role='admin') and employeeId in (select employeeId from Employee where assignedManager <> 0)");
				   List<Register> managerList;
				   managerList=query.list();	   
			tx.commit();
			return managerList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public boolean doupdateAssignedManager(int employeeId, int managerId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Employee.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List results = cr.list();   
			Employee obj=(Employee) results.get(0);
			obj.setAssignedManager(managerId);
			session.update(obj);
			
			cr=session.createCriteria(LeaveRequest.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List<LeaveRequest> results1 = cr.list();   
			for(LeaveRequest obj1: results1)
			{
				obj1.setManagerId(managerId);
				session.update(obj1);
			}
			
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateRole(int employeeId, String role) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Register.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List results = cr.list();   
			Register obj=(Register) results.get(0);
			obj.setRole(role);
			session.update(obj);
			
			cr=session.createCriteria(Login.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			results = cr.list();   
			Login obj1=(Login) results.get(0);
			obj1.setRole(role);
			session.update(obj1);
			
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public List<LeaveRequest> doGetRequestLeaveManager(int managerId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<LeaveRequest> leaveRequestList = session.createCriteria(LeaveRequest.class)
					.add(Restrictions.eq("managerId", managerId))
					.add(Restrictions.eq("status","pending"))
				.list();
			tx.commit();
			return leaveRequestList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public LeaveRequest doUpdateRequestLeaveStatus(int requestId, String status) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(LeaveRequest.class);
			cr.add(Restrictions.eq("requestId", requestId));
			List results = cr.list();   
			LeaveRequest obj=(LeaveRequest) results.get(0);
			obj.setStatus(status);
			session.update(obj);
			tx.commit();
			return obj;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Employee> doGetEmployeeList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Employee> employee;
				employee = session.createCriteria(Employee.class)
					.list();
			tx.commit();
			return employee;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Directory> getOwnDirectoryList(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Directory> directoryList;
			directoryList = session.createCriteria(Directory.class)
						.add(Restrictions.eq("ownerId", employeeId))
						.add(Restrictions.ne("permission","public"))
					.list();
			tx.commit();
			return directoryList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Directory> getSharedDirectoryList(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Integer> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("employeeId", employeeId))
						.setProjection(Projections.property("directoryId"))
					.list();
			List<Directory> directoryList=new ArrayList<Directory>();
		if(directoryEmployeeList.size()>0)
		{
			directoryList = session.createCriteria(Directory.class)
							.add(Restrictions.in("directoryId", directoryEmployeeList))
						.list();
		}
			tx.commit();
			return directoryList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Directory> getSubEmployeeDirectoryList(
			List<Integer> employeeList) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Directory> directoryList;
			directoryList = session.createCriteria(Directory.class)
						.add(Restrictions.in("ownerId", employeeList))
						.add(Restrictions.ne("permission", "public"))
					.list();
			tx.commit();
			return directoryList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Directory> getPublicDirectoryList() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<Directory> directoryList;
			directoryList = session.createCriteria(Directory.class)
						.add(Restrictions.eq("permission", "public"))
					.list();
			tx.commit();
			return directoryList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<DirectoryEmployee> getSharedDirectoryEmployeeList(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("employeeId", employeeId))
					.list();
			tx.commit();
			return directoryEmployeeList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public int doCreateDirectory(Directory directory) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			List<Directory> directoryList;
			directoryList = session.createCriteria(Directory.class)
						.add(Restrictions.eq("ownerId", directory.getOwnerId()))
						.add(Restrictions.eq("dirName", directory.getDirName()))
					.list();
			if(directoryList.size()>0)
			{
				tx.commit();
				return -1;
			}
			else
			{
				session.save(directory);
				tx.commit();
				List<Directory> directoryList1;
				directoryList1 = session.createCriteria(Directory.class)
							.add(Restrictions.eq("ownerId", directory.getOwnerId()))
							.add(Restrictions.eq("dirName", directory.getDirName()))
						.list();
				
				return directoryList1.get(0).getDirectoryId();
			}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			session.close();
		}
	}
	public boolean doAddDirectoryEmployeeList(
			List<DirectoryEmployee> directoryEmployeeList) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			for(DirectoryEmployee obj: directoryEmployeeList)
			{
				session.save(obj);
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doDeleteSubEmployeeDirectory(List<Integer> employeeList, int directoryId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.in("employeeId", employeeList))
						.add(Restrictions.eq("directoryId", directoryId))
					.list();
			for(DirectoryEmployee obj:directoryEmployeeList)
			{
				session.delete(obj);
			}
			tx.commit();
			return true;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateOwnDirectoryPermission(int ownerId, int directoryId,
			String permission) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Directory.class);
			cr.add(Restrictions.eq("directoryId", directoryId));
			cr.add(Restrictions.eq("ownerId", ownerId));
			List results = cr.list();   
			Directory obj=(Directory) results.get(0);
			obj.setPermission(permission);
			session.update(obj);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateSubEmployeeDirectory(List<Integer> employeeList, String permission, int directoryId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.in("employeeId", employeeList))
						.add(Restrictions.eq("directoryId", directoryId))
					.list();
			session.clear();
			if(directoryEmployeeList.size()>0)
			{
				for(DirectoryEmployee obj:directoryEmployeeList)
				{
					obj.setPermission(permission);
					session.update(obj);
				}
			}
			else
			{
				for(Integer i: employeeList)
				{
					DirectoryEmployee obj=new DirectoryEmployee();
					obj.setDirectoryId(directoryId);
					obj.setEmployeeId(i);
					obj.setPermission(permission);
					session.save(obj);
			//		tx.commit();
					
				}
			}
			tx.commit();
			return true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateDirectoryEmployee(DirectoryEmployee directoryEmployee) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("employeeId", directoryEmployee.getEmployeeId()))
						.add(Restrictions.eq("directoryId", directoryEmployee.getDirectoryId()))
					.list();
//			session.clear();
			if(directoryEmployeeList.size()>0)
			{
				
				for(DirectoryEmployee obj:directoryEmployeeList)
				{
					System.out.println("perm :"+obj.getPermission());
					obj.setPermission(directoryEmployee.getPermission());
					System.out.println(" >0 :"+obj.getDirectoryId()+" emp:"+obj.getEmployeeId()+" perm:"+obj.getPermission());
					session.update(obj);
				}
			}
			else
			{
				System.out.println("adding directoryEmployee while updating");
				session.save(directoryEmployee);
			}
			tx.commit();
			return true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doDeleteSubEmployeeDirectoryPublic(int directoryId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("directoryId", directoryId))
					.list();
			for(DirectoryEmployee obj:directoryEmployeeList)
			{
				session.delete(obj);
			}
			tx.commit();
			return true;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doDeleteEmployeeDirectory(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("employeeId", employeeId))
					.list();
			for(DirectoryEmployee obj:directoryEmployeeList)
			{
				session.delete(obj);
			}
			tx.commit();
			return true;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doAddEmployeeDirectoryOfManager(int employeeId, int managerId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("employeeId", managerId))
						.add(Restrictions.ne("permission","private"))
					.list();
			session.clear();
			if(directoryEmployeeList.size()>0)
			{
				System.out.println("diemlist:"+directoryEmployeeList);
				for(DirectoryEmployee obj:directoryEmployeeList)
				{
					DirectoryEmployee directoryEmployee=new DirectoryEmployee();
						directoryEmployee.setEmployeeId(employeeId);
					directoryEmployee.setDirectoryId(obj.getDirectoryId());
					directoryEmployee.setPermission(obj.getPermission());
					session.save(directoryEmployee);
				}
			}
			
			
			List<Directory> directoryList;
			directoryList = session.createCriteria(Directory.class)
						.add(Restrictions.eq("ownerId", managerId))
						.add(Restrictions.ne("permission","private"))
						.add(Restrictions.ne("permission","public"))
					.list();
			if(directoryList.size()>0)
			{
				System.out.println("dirlist:"+directoryList);
				for(Directory obj:directoryList)
				{
					DirectoryEmployee directoryEmployee1=new DirectoryEmployee();
						directoryEmployee1.setEmployeeId(employeeId);
					directoryEmployee1.setDirectoryId(obj.getDirectoryId());
					directoryEmployee1.setPermission(obj.getPermission());
					session.save(directoryEmployee1);
				}
			}
			tx.commit();
			return true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public List<Files> getAllFiles(List<Integer> directoryIdList) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Files> filesList;
			filesList = session.createCriteria(Files.class)
						.add(Restrictions.in("parentDir", directoryIdList))
					.list();
			tx.commit();
			return filesList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public Files getFileBlob(int fileId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<Files> filesList;
			filesList = session.createCriteria(Files.class)
						.add(Restrictions.eq("fileId", fileId))
					.list();
			tx.commit();
			return filesList.get(0);
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public boolean doUploadFile(Files file) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(file);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public String getSuperManagerPermission(int directoryId, int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			String permission=null;
			List<Integer> managerId;
			List<String> directoryEmloyeePermission;
			List<String> directoryPermission;
			managerId = session.createCriteria(Employee.class)
						.add(Restrictions.eq("employeeId",employeeId))
						.setProjection(Projections.property("assignedManager"))
						.list();
			if(managerId!=null && managerId.size()>0)
			{
				directoryEmloyeePermission=session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("employeeId",managerId.get(0)))
						.add(Restrictions.eq("directoryId",directoryId))
						.setProjection(Projections.property("permission"))
						.list();
				if(directoryEmloyeePermission!=null && directoryEmloyeePermission.size()>0)
				{
					permission=directoryEmloyeePermission.get(0);
				}
				else
				{
					directoryPermission=session.createCriteria(Directory.class)
							.add(Restrictions.eq("ownerId",managerId.get(0)))
							.add(Restrictions.eq("directoryId",directoryId))
							.setProjection(Projections.property("permission"))
							.list();
					
					if(directoryPermission!=null && directoryPermission.size()>0)
					{
						permission=directoryPermission.get(0);
					}
				}
			}
			
			tx.commit();
			return permission;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	
	}
	public boolean doDeleteSubEmployeeDirectory(List<Integer> subEmployeeList) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.in("employeeId", subEmployeeList))
					.list();
			for(DirectoryEmployee obj:directoryEmployeeList)
			{
				session.delete(obj);
			}
			tx.commit();
			return true;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateSubEmployeeDirectory(List<Integer> employeeList,
			String permission, int directoryId, int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.in("employeeId", employeeList))
						.add(Restrictions.eq("directoryId", directoryId))
						.add(Restrictions.eq("ateManagerId", 0))
					.list();
			session.clear();
			if(directoryEmployeeList.size()>0)
			{
				for(DirectoryEmployee obj:directoryEmployeeList)
				{
					System.out.println("should never come here");
					obj.setPermission(permission);
					obj.setAteManagerId(employeeId);
					session.update(obj);
				}
			}
			else
			{
				for(Integer i: employeeList)
				{
					DirectoryEmployee obj=new DirectoryEmployee();
					obj.setDirectoryId(directoryId);
					obj.setEmployeeId(i);
					obj.setPermission(permission);
					obj.setAteManagerId(employeeId);
					session.save(obj);
			//		tx.commit();
					
				}
			}
			tx.commit();
			return true;
			
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public List<Register> getAllEmployees() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<Register> allEmployeeList;
			allEmployeeList = session.createCriteria(Register.class)
						.list();
			
			tx.commit();
			return allEmployeeList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	
	}
	public boolean doDeleteSubEmployeeDirectoryATE(int directoryId, int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Login loggedIn = new Login();
			List<DirectoryEmployee> directoryEmployeeList;
			directoryEmployeeList = session.createCriteria(DirectoryEmployee.class)
						.add(Restrictions.eq("ateManagerId", employeeId))
						.add(Restrictions.eq("directoryId", directoryId))
					.list();
			for(DirectoryEmployee obj:directoryEmployeeList)
			{
				session.delete(obj);
			}
			tx.commit();
			return true;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	public boolean doUpdateSalaryEmployee(int employeeId, Double salary) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Employee.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List results = cr.list();   
			Employee obj=(Employee) results.get(0);
			obj.setSalary(salary);
			session.update(obj);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}	
	}
	public boolean doUpdateBonusEmployee(int employeeId, Double bonus) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Employee.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List results = cr.list();   
			Employee obj=(Employee) results.get(0);
			obj.setBonus(bonus);
			session.update(obj);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}	
	}
	public List<Register> getSubEmployees(List<Integer> employeeList) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<Register> registerList;
			registerList = session.createCriteria(Register.class)
						.add(Restrictions.in("employeeId", employeeList))
					.list();
			
			tx.commit();
			return registerList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public List<Payment> doGetPaymentList(String paymentDate, int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			List<Payment> paymentList;
			if(paymentDate==null )
			{
				paymentList = session.createCriteria(Payment.class)
						.add(Restrictions.eq("employeeId", employeeId))
					.list();
			}
			else
			{
				paymentList = session.createCriteria(Payment.class)
						.add(Restrictions.eq("employeeId", employeeId))
						.add(Restrictions.eq("paymentDate", paymentDate))
					.list();
			}
			tx.commit();
			return paymentList;
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public int doGetLeaveDays(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		List<Integer> leavrequest=null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			leavrequest = session.createCriteria(Employee.class)
						.add(Restrictions.eq("employeeId", employeeId))
						.setProjection(Projections.property("leaveDays"))
					.list();
			tx.commit();
			return leavrequest.get(0);
					
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return -1;
		} finally {
			session.close();
		}
	}
	public boolean doSubtractLeaveEmployee(int employeeId, int diff11) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Employee.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List results = cr.list();   
			Employee obj=(Employee) results.get(0);
			obj.setLeaveDays(obj.getLeaveDays()-diff11);
			session.update(obj);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}	
	}
	public String getEmployeeRole(int employeeId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Login.class);
			cr.add(Restrictions.eq("employeeId", employeeId));
			List results = cr.list();   
			Login obj=(Login) results.get(0);
			tx.commit();
			return obj.getRole();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	public boolean alreadyHaveDirectory(int directoryId, int ateId) {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			Criteria cr=session.createCriteria(Directory.class);
			cr.add(Restrictions.eq("ownerId", ateId));
			cr.add(Restrictions.eq("directoryId", directoryId));
			List results = cr.list();   
			if(results.size()>0)
			{
				tx.commit();
				return true;
			}
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			cr=session.createCriteria(DirectoryEmployee.class);
			cr.add(Restrictions.eq("employeeId", ateId));
			cr.add(Restrictions.eq("directoryId", directoryId));
			List results1 = cr.list();   
			if(results1.size()>0)
			{
				tx.commit();
				return true;
			}
			tx.commit();
			return false;
		
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}

}