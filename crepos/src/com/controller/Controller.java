package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.hibernate.property.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.service.Services;
import com.model.Directory;
import com.model.Files;
import com.model.DirectoryEmployee;
import com.model.Employee;
import com.model.LeaveRequest;
import com.model.Login;
import com.model.Payment;
import com.model.Register;
public class Controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String actionCode = request.getParameter("actionCode");
			Services services = new Services();
		HttpSession session = request.getSession();
		/*if(actionCode==null)
		{
			return;
		}*///check this error
	
		if(ServletFileUpload.isMultipartContent(request))
		{
			actionCode="upload";
		}
		if((session==null || session.getAttribute("employeeId")==null) && !actionCode.equals("logIn") && !actionCode.equals("register"))
		{
			request.setAttribute("msg", "Session may expire.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		if(actionCode.equals("logIn"))
		{
			try
			{
				Login login=new Login();
				login.setEmployeeId(Integer.parseInt(request.getParameter("employeeId")));
				login.setPassword(request.getParameter("password"));
				login=services.doLogin(login);
				if(login!=null)
				{
					int managerId=services.getManagerId(login.getEmployeeId());
					session.setAttribute("managerId",managerId);
					
					session.setAttribute("employeeId", login.getEmployeeId());
					session.setAttribute("role",login.getRole());
					List<Employee> employeeList=services.doGetEmployeeList();
					
					try {
						request.setAttribute("final", printEmployee(login.getEmployeeId(),employeeList));
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+request.getSession().getAttribute("role")+".jsp");
						dispatcher.forward(request, response);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				request.setAttribute("msg", "Login failed ! Incorrect EmployeeId or Password");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}
			catch(Exception e)
			{
				request.setAttribute("msg", "Login failed ! Incorrect EmployeeId or Password");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}
		}
		if(actionCode.equals("logOut"))
		{
			session.setAttribute("employeeId", null);
			session.setAttribute("role",null);
			session.invalidate();
			request.setAttribute("msg", "Log Out successfully.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;	
		}
		if(actionCode.equals("register"))
		{
			try{
				Register register =new Register();
				register.setFirstName(request.getParameter("firstName"));
				register.setEmailId(request.getParameter("emailId"));
				register.setEmployeeId(Integer.parseInt(request.getParameter("employeeId")));
				register.setGender(request.getParameter("gender"));
				register.setLastName(request.getParameter("lastName"));
				register.setPassword(request.getParameter("password"));
				register.setPhoneNumber(Integer.parseInt(request.getParameter("phoneNumber")));
				register.setDob(request.getParameter("dob"));
				register.setRole("employee");
				register.setQuestion(request.getParameter("question"));
				register.setAnswer(request.getParameter("answer"));
				Login login =new Login();
				login.setEmployeeId(Integer.parseInt(request.getParameter("employeeId")));
				login.setPassword(request.getParameter("password"));
				login.setRole("employee");
				Employee employee =new Employee();
				employee.setEmployeeId(Integer.parseInt(request.getParameter("employeeId")));
				employee.setAssignedManager(0);
				employee.setBonus(0.0);
				employee.setSalary(0.0);
				if(services.doAddRegister(register) && services.doAddLogin(login) && services.doAddEmployee(employee))
				{
					request.setAttribute("msg", "Successfully Registered.");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
				}
				else
				{
					request.setAttribute("msg", "Can not be Registered.");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/register.jsp");
					dispatcher.forward(request, response);
				}
				
			}
			catch(Exception e)
			{
				request.setAttribute("msg", "Can not be Registered.");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/register.jsp");
				dispatcher.forward(request, response);
			}
		}
		if(actionCode.equals("getProfile"))
		{
			int employeeId=(int) request.getSession().getAttribute("employeeId") ;
			Register register=services.doGetProfile(employeeId);
			session.setAttribute("profile", register);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateProfile.jsp");
			dispatcher.forward(request, response);
		}
		if(actionCode.equals("upload"))
		{
			List<FileItem> items;
			Register register=(Register) request.getSession().getAttribute("profile");
			Files file=new Files();
			String msg="";
	        Blob blob = null;
            String fileName=null;
			try {
                items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) 
                {
                    if (item.isFormField())
                    {
                        // Process normal fields here.
                    	if(item.getFieldName().equals("firstName"))
				        {
				        	register.setFirstName(item.getString());
				        }
				       if(item.getFieldName().equals("lastName"))
				        {
				        	register.setLastName(item.getString());
				        }
				       if(item.getFieldName().equals("phoneNumber"))
				        {
				        	register.setPhoneNumber(Integer.parseInt(item.getString()));
				        }
				       if(item.getFieldName().equals("emailId"))
				        {
				        	register.setEmailId(item.getString());
				        }
				       if(item.getFieldName().equals("question"))
				        {
				        	register.setQuestion(item.getString());
				        }
				       if(item.getFieldName().equals("answer"))
				        {
				        	register.setAnswer(item.getString());
				        }
				       if(item.getFieldName().equals("password"))
				        {
				        	register.setPassword(item.getString());
				        }
				       if(item.getFieldName().equals("dob"))
				        {
				        	register.setDob(item.getString());
				        }
		            	if(item.getFieldName().equals("parentDir1"))
                    	{
		            		   file.setParentDir(Integer.parseInt(item.getString()));
                    	}
        
		            	if(item.getFieldName().equals("actionCode"))
                    	{
                    	      actionCode=item.getString();
                  			
                    	}
        
                        System.out.println("fieldname:" + item.getFieldName());
                        System.out.println("value:" + item.getString());
                    } 
                    else {
                        // Process <input type="file"> here.
                        InputStream fileContent = item.getInputStream();
                        fileName=item.getName();
                        byte[] contents = IOUtils.toByteArray(fileContent);
                        if(contents.length >0)
                        {
	                        try {
	                            blob = new SerialBlob(contents);
	                        } catch (SerialException e) {
	                            e.printStackTrace();
	                        } catch (SQLException e) {
	                            e.printStackTrace();
	                        }
	
	                    }
                    }

                }
                if(actionCode.equals("uploadFile"))
                {
                	file.setFileName(fileName);
                    file.setFile(blob);
                    Date date = new Date();
    		        SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
    		        file.setDate(ft.format(date).toString() );
                    file.setOwnerId((int) request.getSession().getAttribute("employeeId"));
        		    if(file.getFile()!=null)
        		    {
			            if(services.doUploadFile(file))
					    {
					    	msg=msg+ "file uploaded.";
					   
					    }
					    else
					    {
					    	msg=msg+"file can not be uploaded.";
					    }
        		    }
        		    else
        		    {
        		    	msg=msg+"select file first.";
        		    }
    			    if(msg.equals(""))
    				{
    					msg=null;
    				}
    				request.setAttribute("msg", msg);
    				getDirectoriesFiles(request, response, services);
                }
                else
                {
                    register.setProfile(blob);
                    register.setEmployeeId( (int) request.getSession().getAttribute("employeeId"));
			        register.setRole((String) request.getSession().getAttribute("role"));
			        request.getSession().setAttribute("profile", register);
				    if(services.doUpdateProfile(register))
				    {
				    	request.setAttribute("msg", "profile updated.");
				    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateProfile.jsp");
						dispatcher.forward(request, response);
					
				    }
				    else
				    {
				    	request.setAttribute("msg", "profile can not be updated.");
				    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateProfile.jsp");
						dispatcher.forward(request, response);
				    }
               }
                
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(actionCode.equals("uploadFile"))
				{
					getDirectoriesFiles(request, response, services);
				}
				else
				{
					request.getSession().setAttribute("profile", register);
					request.setAttribute("msg", "profile can not be updated.");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/updateProfile.jsp");
					dispatcher.forward(request, response);
				}
			}

		}
		if(actionCode.equals("requestLeave"))
		{
			try{
				LeaveRequest leaveRequest =new LeaveRequest();
				leaveRequest.setEmployeeId((int) request.getSession().getAttribute("employeeId"));
				leaveRequest.setFromDate(request.getParameter("fromDate"));
				leaveRequest.setReason(request.getParameter("reason"));
				int managerId;
				if((managerId=services.getManagerId(leaveRequest.getEmployeeId()))>0)
				{
					leaveRequest.setManagerId(managerId);
				}
				else
				{
					request.setAttribute("msg", "You are not assigned with any manager.");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
					dispatcher.forward(request, response);
					return;
				}
				leaveRequest.setRequestDate(request.getParameter("requestDate"));
				leaveRequest.setStatus("pending");
				leaveRequest.setToDate(request.getParameter("toDate"));
				  Date date = new Date();
		            
		                
		          long date1=  Date.parse(leaveRequest.getFromDate()) ;
		          long date2 = Date.parse(leaveRequest.getToDate());
		          long date3=Date.parse(leaveRequest.getRequestDate());
		          if(date3<=date1)
		          {
		        	  if(date1<=date2)
		        	  {
		        	
		        		  SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
				            Date d1=ft.parse(leaveRequest.getFromDate()) ;
				            Date d2=ft.parse(leaveRequest.getToDate()) ;
				               long diff = d2.getTime() - d1.getTime();
				                long diff11= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				          int leaveDays=services.doGetLeaveDays(leaveRequest.getEmployeeId());
				            if(diff11+1 >leaveDays)
				            {
				            	request.setAttribute("msg", "you can not have leave more than "+leaveDays+" days");
								RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
								dispatcher.forward(request, response);
								return;
			        	
				            }
				            else
				            {
				                	    	    
				            }
		        	  }
		        	  else
		        	  {
		        		  request.setAttribute("msg", "from date can not be selected ago than to date.");
							RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
							dispatcher.forward(request, response);
							return;
		        	  }
		          }
		          else
		          {
		        	  request.setAttribute("msg", "At least select date of present or future.");
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
						dispatcher.forward(request, response);
						return;
		          }
				if(services.doAddLeaveRequest(leaveRequest))
				{
					request.setAttribute("msg", "Leave request posted.");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
					dispatcher.forward(request, response);
			
				}
				else
				{
					request.setAttribute("msg", "Leave request can not be posted.May dates clashes which are already applied.");
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
					dispatcher.forward(request, response);
				
				}
			}catch (Exception e)
			{
				e.printStackTrace();
				request.setAttribute("msg", "Leave request can not be posted.");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/requestLeave.jsp");
				dispatcher.forward(request, response);
			
			}
		}
		if(actionCode.equals("getRequestLeave"))
		{
			
			List<LeaveRequest> leaveRequest =services.doGetRequestLeave((int) request.getSession().getAttribute("employeeId"));
			request.setAttribute("leaveRequestList", leaveRequest);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/getRequestLeave.jsp");
			dispatcher.forward(request, response);
		}
		if(actionCode.equals("getActiveEmployee"))
		{
			getActiveEmployee(request,response,services);
		}
		if(actionCode.equals("getNewEmployee"))
		{
			getNewEmployee(request,response,services);
		}
		if(actionCode.equals("updateAssignedManager"))
		{
			int employeeId=Integer.parseInt(request.getParameter("employeeId"));
			int managerId=Integer.parseInt(request.getParameter("managerId"));
			String msg="";
			if(employeeId!=managerId)
			{
				if(services.doupdateAssignedManager(employeeId,managerId))
				{
					//delete all record with employee Id and copy all record of manager id if not private
					if(services.doDeleteEmployeeDirectory(employeeId))
					{
						
						
						if(services.doAddEmployeeDirectoryOfManager(employeeId,managerId))
						{
							msg=msg+"added all dirs";
						}
						else
						{
							msg=msg+" can not add dirs of manager "+managerId +" to "+employeeId;
						}
					}
					else
					{
						msg=msg+"can not delete old records of "+employeeId;
					}
					
					//delete all sub employees in deleteEmployeeDirectory
					
				}
				else{ 
					msg=msg+"can not update assigned manager "+managerId+" to "+employeeId;
				}
			}
			else
			{
				msg=msg+" employee and manager are same";
			}
			if(msg.equals(""))
			{
				msg=null;
			}
			
			request.setAttribute("msg", msg);
			
			getNewEmployee(request,response,services);
			}
		if(actionCode.equals("updateEmployee"))
		{
			List<Employee> employeeList=(List<Employee>) request.getSession().getAttribute("employeeList");
			List<Register> registerList=(List<Register>) request.getSession().getAttribute("registerList");
			int i=Integer.parseInt(request.getParameter("index"));
			String role=request.getParameter("role");
			int assignedSuperVision=0;
			Double salary,bonus;
			System.out.println("nul:"+request.getParameter("assignedSuperVision"));
			String msg="";

			if(request.getParameter("salary")!="" && request.getParameter("salary")!=null)
			{
				salary=Double.parseDouble(request.getParameter("salary"));
				if(salary!=employeeList.get(i).getSalary())
				{
					if(services.doUpdateSalaryEmployee(employeeList.get(i).getEmployeeId(),salary))
					{
						msg=msg+"salary updated";
					}
					else
					{
						msg=msg+"salary can not be updated";
					}
				}
				else
				{
					msg=msg+" salary is not changed";
				}
			}
			if(request.getParameter("bonus")!="" && request.getParameter("bonus")!=null)
			{
				bonus=Double.parseDouble(request.getParameter("bonus"));
				if(bonus!=employeeList.get(i).getBonus())
				{
					if(services.doUpdateBonusEmployee(employeeList.get(i).getEmployeeId(),bonus))
					{
						msg=msg+"Bonus updated";
					}
					else
					{
						msg=msg+"bonus can not be updated";
					}
				}
				else
				{
					msg=msg+" bonus is not changed";
				}
			}
			if( registerList.get(i).getEmployeeId()!=assignedSuperVision)
			{
				if(assignedSuperVision!=employeeList.get(i).getAssignedManager())
				{
					if(!services.doupdateAssignedManager(registerList.get(i).getEmployeeId(),assignedSuperVision))
					{
						msg=msg+"can not Assigned SuperVision "+assignedSuperVision+" to "+employeeList.get(i).getEmployeeId();
					}
					else
					{
						if(services.doDeleteEmployeeDirectory(registerList.get(i).getEmployeeId()))
						{
							if(services.doAddEmployeeDirectoryOfManager(registerList.get(i).getEmployeeId(),assignedSuperVision))
							{
								msg=msg+"added all dirs";
							}
							else
							{
								msg=msg+" can not add dirs of manager "+assignedSuperVision +" to "+registerList.get(i).getEmployeeId();
							}
						}
						else
						{
							msg=msg+"can not delete old records of "+registerList.get(i).getEmployeeId();
						}
							
					//have to delete subemployeeDirectory List	
						List<Employee> employee=services.doGetEmployeeList();//get list of employee
						List<Employee> employeeList1=getSubEmployeeList(registerList.get(i).getEmployeeId(), employee);//get sub empoloyees
						List<Integer> subEmployeeList=new ArrayList<Integer>();
						
						
						if(employeeList1!=null && employeeList1.size()>0)
						{
							for(Employee e:employeeList1)
							{
								subEmployeeList.add(e.getEmployeeId());
							}
						}
						//got subEmployee list
						if(services.doDeleteSubEmployeeDirectory(subEmployeeList))
						{
							msg=msg+"successfully deleted in sub employees";
							for(int j=0;j<subEmployeeList.size();j++)
							{
								if(j==0)
								{
									if(services.doAddEmployeeDirectoryOfManager(subEmployeeList.get(j),registerList.get(i).getEmployeeId()))
									{
										msg=msg+"added all dirs";
									}
									else
									{
										msg=msg+" can not add dirs of manager "+assignedSuperVision +" to "+registerList.get(i).getEmployeeId();
									}
								}
								else
								{
									if(services.doAddEmployeeDirectoryOfManager(subEmployeeList.get(j-1),subEmployeeList.get(j)))
									{
										msg=msg+"added all dirs";
									}
									else
									{
										msg=msg+" can not add dirs of manager "+assignedSuperVision +" to "+registerList.get(i).getEmployeeId();
									}
								}
							}
						}
						else
						{
							msg=msg+"can not delete of sub employees";
						}
						
					
					}
				}
				else
				{
					//msg=msg+"Already Assigned SuperVision "+assignedSuperVision+" to "+employeeList.get(i).getEmployeeId();
				}
			}
			else
			{
				msg=msg+" employee and manager are same";
			}
			if(!role.equals(registerList.get(i).getRole()))
			{
				System.out.println("emp:"+registerList.get(i).getEmployeeId());
				if(!services.doUpdateRole(registerList.get(i).getEmployeeId(),role))
				{
					msg=msg+"can not update the role Of "+employeeList.get(i).getEmployeeId();
				}
			}
			else{
				//msg=msg+"Already employee "+employeeList.get(i).getEmployeeId()+" role Of "+role;
			}
			if(msg.equals(""))
			{
				msg=null;
			}
			request.getSession().setAttribute("employeeList",null);
			request.getSession().setAttribute("registerList",null);
			
			request.setAttribute("msg", msg);
			getActiveEmployee(request,response,services);
		}
		if(actionCode.equals("getRequestLeaveManger"))
		{
			List<LeaveRequest> leaveRequest =services.doGetRequestLeaveManager((int) request.getSession().getAttribute("employeeId"));
			request.setAttribute("leaveRequestList", leaveRequest);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/approveRequestLeave.jsp");
			dispatcher.forward(request, response);
		}
		if(actionCode.equals("approveRequestLeave"))
		{
			int requestId=Integer.parseInt(request.getParameter("requestId"));
			String status=request.getParameter("status");
			String msg="";
			if(status!=null)
			{
				LeaveRequest leaveRequest=services.doUpdateRequestLeaveStatus(requestId,status);
				if(leaveRequest==null)
				{
					msg=msg+"can not update the status of Leave Request "+requestId;
				}
				else
				{
					if(status.equals(leaveRequest.getStatus()) && status.equals("approve"))
					{
						SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
			            Date d1;
						try {
							d1 = ft.parse(leaveRequest.getFromDate());
							   Date d2=ft.parse(leaveRequest.getToDate()) ;
							   long diff = d2.getTime() - d1.getTime();
				                long diff11= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				                System.out.println("diff:"+diff11+1);
				            if(services.doSubtractLeaveEmployee(leaveRequest.getEmployeeId(),((int)diff11+1)))
							{
									msg=msg+" leave request updated.";
							}  
				            else
				            {
				            	msg=msg+" can not subtract from employees days";
				            }
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			                 
					}
				}
				
			}
			else
			{
				msg=msg+"can not update the status of Leave Request "+requestId;
			}
			if(msg.equals(""))
			{
				msg=null;
			}
			request.setAttribute("msg", msg);
			List<LeaveRequest> leaveRequest =services.doGetRequestLeaveManager((int) request.getSession().getAttribute("employeeId"));
			request.setAttribute("leaveRequestList", leaveRequest);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/approveRequestLeave.jsp");
			dispatcher.forward(request, response);
		
		}
		if(actionCode.equals("getDirectoriesFiles"))
		{
			getDirectoriesFiles(request,response,services);
		
		}
		if(actionCode.equals("createDirectory"))
		{
			String msg="";
			String directoryName= request.getParameter("directoryName");
			String permission=request.getParameter("permission");
			int ownerId= Integer.parseInt(request.getParameter("ownerId"));
			Directory directory=new Directory();
			directory.setDirName(directoryName);
			directory.setOwnerId(ownerId);
			directory.setPermission(permission);
			int directoryId=services.doCreateDirectory(directory);
			System.out.println("dir:"+directoryId);
			if( directoryId != -1)
			{
				if(directory.getPermission().equals("default") || directory.getPermission().equals("protected"))
				{//for sub directories
					//get sub employees
					List<Employee> employee=services.doGetEmployeeList();//get list of employee
					List<Employee> employeeList1=getSubEmployeeList(ownerId, employee);//get sub empoloyees
					List<DirectoryEmployee> directoryEmployeeList=new ArrayList<DirectoryEmployee>();
					
					if(employeeList1!=null && employeeList1.size()>0)
					{
						for(Employee e:employeeList1)
						{
							DirectoryEmployee directoryEmployee=new DirectoryEmployee();
							directoryEmployee.setDirectoryId(directoryId);
							directoryEmployee.setEmployeeId(e.getEmployeeId());
							directoryEmployee.setPermission(directory.getPermission());
							directoryEmployeeList.add(directoryEmployee);
						}
					}
					if(services.doAddDirectoryEmployeeList(directoryEmployeeList))
					{
						msg=msg+"directory"+ directory.getDirName()+" created successfully. ";
					}
				}
			}
			else
			{
				msg=msg+" diectory already exist , try with different name";
			}
			if(msg.equals(""))
			{
				msg=null;
			}
			request.setAttribute("msg", msg);
			getDirectoriesFiles(request,response,services);
			
		}
		if(actionCode.equals("updatePermission"))
		{
			String directoryName=request.getParameter("directoryName");
			int directoryId=Integer.parseInt(request.getParameter("directoryId"));
			String oldPermission=request.getParameter("oldPermission");
			String permission =request.getParameter("permission");
			int ownerId=Integer.parseInt(request.getParameter("ownerId"));
			int employeeId=(int) request.getSession().getAttribute("employeeId");
			String msg="";
			if(!permission.equals(oldPermission))
			{
				if(ownerId==employeeId)
				{
					if(services.doUpdateOwnDirectoryPermission(ownerId,directoryId,permission))
					{
						msg=msg+"own directory permission updated. \n";
					}
					else
					{
						msg=msg+"own directory permission is not updated. \n";
					}
				}
				if( permission.equals("private"))
				{
					if(ownerId!=employeeId)
					{
						//do update directoryEmployee
						DirectoryEmployee directoryEmployee=new DirectoryEmployee();
						directoryEmployee.setDirectoryId(directoryId);
						directoryEmployee.setEmployeeId(employeeId);
						directoryEmployee.setPermission(permission);
						if(services.doUpdateDirectoryEmployee(directoryEmployee))
						{
							msg=msg+"can insert data in directoryEmployee check it too";
						}
					}
					System.out.println(" permission:"+permission+"  pub_private");
					List<Employee> employee=services.doGetEmployeeList();//get list of employee
					List<Employee> employeeList1=getSubEmployeeList(employeeId, employee);//get sub empoloyees
					List<Integer> employeeList=new ArrayList<Integer>();
					
					
					if(employeeList1!=null && employeeList1.size()>0)
					{
						for(Employee e:employeeList1)
						{
							employeeList.add(e.getEmployeeId());
						}
					}
					//got subEmployee list
					if(services.doDeleteSubEmployeeDirectory(employeeList,directoryId))
					{
						msg=msg+"successfully update permission "+permission+" of directory "+directoryName;
					}
					else
					{
						msg=msg+"can not delete of sub employees";
					}
					//delete ate dirs
					if(services.doDeleteSubEmployeeDirectoryATE(directoryId,employeeId))
					{
						msg=msg+"successfully update permission "+permission+" of directory "+directoryName;
					}
					else
					{
						msg=msg+"can not delete of sub employees";
					}
					
				}
				if(permission.equals("public"))
				{
					if(services.doDeleteSubEmployeeDirectoryPublic(directoryId))
					{
						msg=msg+"successfully update permission "+permission+" of directory "+directoryName;
					}
					else
					{
						msg=msg+"can not delete of sub employees";
					}
				}
				else if(permission.equals("protected") || permission.equals("default"))
				{
					
					if(ownerId!=employeeId)
					{
				
						if(permission.equals("protected"))
						{
							String superManagerPermission=services.getSuperManagerPermission(directoryId,employeeId);
							if(superManagerPermission==null || !permission.equals(superManagerPermission))
							{//can not change permission
								
								msg=msg+"parent have "+superManagerPermission+" permission for directory "+directoryId;
								request.setAttribute("msg", msg);
								System.out.println(" ret perm:"+permission+" suepe mana:"+ superManagerPermission);
								getDirectoriesFiles(request, response, services);
								return;
							}
							System.out.println(" perm:"+permission+" suepe mana:"+ superManagerPermission);
						}
						
						//do update directoryEmployee
						
						DirectoryEmployee directoryEmployee=new DirectoryEmployee();
						directoryEmployee.setDirectoryId(directoryId);
						directoryEmployee.setEmployeeId(employeeId);
						directoryEmployee.setPermission(permission);
						if(services.doUpdateDirectoryEmployee(directoryEmployee))
						{//won't affect
							msg=msg+"update own directoryEmployee "+directoryEmployee.getEmployeeId()+" dir:"+directoryEmployee.getDirectoryId()+" with permission:"+directoryEmployee.getPermission(); 
						}
						else
						{
							msg=msg+" couldnt update own directoryEmployee";
						}
					}
					
					System.out.println(" permission:"+permission+"  prot+default");
					List<Employee> employee=services.doGetEmployeeList();//get list of employee
					List<Employee> employeeList1=getSubEmployeeList(employeeId, employee);//get sub empoloyees
					List<Integer> employeeList=new ArrayList<Integer>();
					
					
					if(employeeList1!=null && employeeList1.size()>0)
					{
						for(Employee e:employeeList1)
						{
							employeeList.add(e.getEmployeeId());
						}
						if(services.doUpdateSubEmployeeDirectory(employeeList,permission,directoryId))
						{
							msg=msg+"successfully update permission "+permission+" of directory "+directoryName;
						}
						else
						{
							msg=msg+"can not delete of sub employees yaar";
						}
					}
					
					//got subEmployee list
					
				}
				else
				{
					msg=msg+"can not update anything.";
				}
			}
			else
			{
				msg=msg+" already it has "+oldPermission +" permission.";
			}
			if(msg.equals(""))
			{
				msg=null;
			}
			request.setAttribute("msg", msg);
			System.out.println("getting all directories");
			getDirectoriesFiles(request,response,services);
			
		}
		if(actionCode.equals("download"))
		{
			byte[] file = null ;
			Files downloadFile=services.getFileBlob(Integer.parseInt(request.getParameter("fileId")));
			 Blob blob=downloadFile.getFile();
			 try {
				file=blob.getBytes(1, (int)blob.length());
				 response.addHeader("Content-Disposition","attachment; filename="+downloadFile.getFileName());
				response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
				 OutputStream o = response.getOutputStream();
		           o.write(file);
		           o.flush();
		           o.close();
		          // listFile.setFileName(item.getName());
					
//		           getDirectoriesFiles(request,response,services);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				getDirectoriesFiles(request,response,services);
			}
		}
		if(actionCode.equals("addATE"))
		{
			int ateId=Integer.parseInt(request.getParameter("ateId"));//adding employee
			int directoryId=Integer.parseInt(request.getParameter("directoryId"));
			int employeeId=(int) request.getSession().getAttribute("employeeId");
			String permission="protected";
			String msg="";
			//adding own directoryEmployee			
			DirectoryEmployee directoryEmployee=new DirectoryEmployee();
			directoryEmployee.setDirectoryId(directoryId);
			directoryEmployee.setEmployeeId(ateId);
			directoryEmployee.setPermission(permission);
			directoryEmployee.setAteManagerId(employeeId);
			if(!services.alreadyHaveDirectory(directoryId,ateId))
			{
				
				
				if(services.doUpdateDirectoryEmployee(directoryEmployee))
				{
					msg=msg+"update own directoryEmployee "+directoryEmployee.getEmployeeId()+" dir:"+directoryEmployee.getDirectoryId()+" with permission:"+directoryEmployee.getPermission(); 
				}
				else
				{
					msg=msg+" couldnt update own directoryEmployee";
				}
				
				//adding subemployees in DirectoryEmployee with ate manager as current employee to all
				
				List<Employee> employee=services.doGetEmployeeList();//get list of employee
				List<Employee> employeeList1=getSubEmployeeList(ateId, employee);//get sub empoloyees
				List<Integer> employeeList=new ArrayList<Integer>();
				
				
				if(employeeList1!=null && employeeList1.size()>0)
				{
					for(Employee e:employeeList1)
					{
						employeeList.add(e.getEmployeeId());
					}
					if(services.doUpdateSubEmployeeDirectory(employeeList,permission,directoryId,employeeId))
					{
						msg=msg+"successfully update sub employee permission "+permission+" of directory "+directoryId;
					}
					else
					{
						msg=msg+"can not delete of sub employees yaar";
					}
				}
			}
			else
			{
				msg=msg+"employee "+ateId+" has already directory "+directoryId;
			}
		
			if(msg.equals(""))
			{
				msg=null;
			}
			request.setAttribute("msg", msg);
			System.out.println("getting all directories");
			getDirectoriesFiles(request,response,services);
		}if(actionCode.equals("getSubEmployees"))
		{
			int employeeId=(int)request.getSession().getAttribute("employeeId");
			List<Employee> employee=services.doGetEmployeeList();//get list of employee
			List<Employee> employeeList1=getSubEmployeeList(employeeId, employee);//get sub empoloyees
			List<Integer> employeeList=new ArrayList<Integer>();
			List<Register> registerList=null;
			
			if(employeeList1!=null && employeeList1.size()>0)
			{
				for(Employee e:employeeList1)
				{
					employeeList.add(e.getEmployeeId());
				}
				registerList=services.getSubEmployees(employeeList);
			}
			request.setAttribute("employeeList", employeeList1);
			request.setAttribute("registerList", registerList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/subEmployees.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		if(actionCode.equals("assignBonus"))
		{
			Double bonus=Double.parseDouble(request.getParameter("bonus"));
			Double addBonus=Double.parseDouble(request.getParameter("addBonus"));
			String msg="";
			int subEmployeeId=Integer.parseInt(request.getParameter("subEmployeeId"));
			if(services.doUpdateBonusEmployee(subEmployeeId, addBonus+bonus))
			{
				msg=msg+" bonus updated.";
			}
			else
			{
				msg=msg+" bonus can not be updated.";
			}
			
			int employeeId=(int)request.getSession().getAttribute("employeeId");
			List<Employee> employee=services.doGetEmployeeList();//get list of employee
			List<Employee> employeeList1=getSubEmployeeList(employeeId, employee);//get sub empoloyees
			List<Integer> employeeList=new ArrayList<Integer>();
			List<Register> registerList=null;
			
			if(employeeList1!=null && employeeList1.size()>0)
			{
				for(Employee e:employeeList1)
				{
					employeeList.add(e.getEmployeeId());
				}
				registerList=services.getSubEmployees(employeeList);
			}
			request.setAttribute("employeeList", employeeList1);
			request.setAttribute("registerList", registerList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/subEmployees.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(actionCode.equals("getPayment"))
		{
			String paymentDate=request.getParameter("paymentDate");
			int employeeId=(int)request.getSession().getAttribute("employeeId");
			List<Payment> paymentList=services.doGetPaymentList(paymentDate,employeeId);
			request.setAttribute("paymentList", paymentList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/payment.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(actionCode.equals("getSubEmployeeTree"))
		{
			List<Employee> employeeList=services.doGetEmployeeList();
			
			try {
				request.setAttribute("final", printEmployee(((int)request.getSession().getAttribute("employeeId")),employeeList));
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/"+request.getSession().getAttribute("role")+".jsp");
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}	
	
	

	public void getActiveEmployee(HttpServletRequest request,
			HttpServletResponse response, Services services) {
		// TODO Auto-generated method stub
		List<Employee> employeeList=services.doGetEmployee(1);
		List<Register> registerList = new ArrayList<Register>();
		List<Register> managerList=null;
		
		if(employeeList.size()>0)
		{
			Register register;
			for(Employee obj: employeeList)
			{
				register=services.doGetProfile(obj.getEmployeeId());
				registerList.add(register);
			}
			managerList=services.doGetManagerList();
			
		}
		request.setAttribute("managerList", managerList);
		request.setAttribute("employeeList", employeeList);
		request.setAttribute("registerList", registerList);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/activeEmployee.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void getNewEmployee(HttpServletRequest request,
			HttpServletResponse response, Services services) 
	{
		List<Employee> employeeList=services.doGetEmployee(0);
		List<Register> registerList = new ArrayList<Register>();
		List<Register> managerList=null;
		if(employeeList.size()>0)
		{
			Register register;
			for(Employee obj: employeeList)
			{
				register=services.doGetProfile(obj.getEmployeeId());
				registerList.add(register);
			}
			managerList=services.doGetManagerList();
		}
		request.setAttribute("managerList", managerList);
		request.setAttribute("employeeList", employeeList);
		request.setAttribute("registerList", registerList);
			try {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/newEmployee.jsp");
				dispatcher.forward(request, response);
				/*return;//check error
*/		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	
	}
	
	public void getDirectoriesFiles(HttpServletRequest request,
			HttpServletResponse response, Services services) {
		// TODO Auto-generated method stub
		int employeeId=(int) request.getSession().getAttribute("employeeId");
		List<Directory> ownDirectoryList = services.getOwnDirectoryList(employeeId);//get own directory list
		List<Integer> directoryIdList=null;
		List<Files> ownDirectoryFilesList=null;
		if(ownDirectoryList!=null && ownDirectoryList.size()>0)
		{
			directoryIdList=getDirectoryIdList(ownDirectoryList);
			ownDirectoryFilesList=services.getAllFiles(directoryIdList);
		}
		List<Directory> publicDirectoryList = services.getPublicDirectoryList();//get public directories
		List<Files> publicDirectoryFilesList=null;
		if(publicDirectoryList!=null && publicDirectoryList.size()>0)
		{
			directoryIdList=getDirectoryIdList(publicDirectoryList);
			publicDirectoryFilesList=services.getAllFiles(directoryIdList);
		}
		List<Directory> sharedDirectoryList=services.getSharedDirectoryList(employeeId);//get shared directory list
		List<DirectoryEmployee> sharedDirectoryEmployeeList =services.getSharedDirectoryEmployeeList(employeeId);//get directory employee to print permission
		List<Files> sharedDirectoryFilesList=null;
		if(sharedDirectoryList!=null&&sharedDirectoryList.size()>0)
		{
			directoryIdList=getDirectoryIdList(sharedDirectoryList);
			sharedDirectoryFilesList=services.getAllFiles(directoryIdList);
		}
		
		List<Employee> employee=services.doGetEmployeeList();//get list of employee
		List<Employee> employeeList1=getSubEmployeeList(employeeId, employee);//get sub empoloyees
		List<Integer> employeeList=new ArrayList<Integer>();
		
		
		if(employeeList1!=null && employeeList1.size()>0)
		{
			for(Employee e:employeeList1)
			{
				employeeList.add(e.getEmployeeId());
			}
		}
		List<Directory> subEmployeeDirectoryList=null;
		List<Files> subEmployeeDirectoryFilesList=null;
		if(employeeList.size()>0)
		{
			subEmployeeDirectoryList=services.getSubEmployeeDirectoryList(employeeList);// get subemployee list
		
			if(subEmployeeDirectoryList!=null && subEmployeeDirectoryList.size()>0)
			{
				directoryIdList=getDirectoryIdList(subEmployeeDirectoryList);
				subEmployeeDirectoryFilesList=services.getAllFiles(directoryIdList);
			}
		}
		
		
		List<Register> allEmployeeList=services.getAllEmployees();
		if(request.getSession().getAttribute("role").equals("manager"))
		{
			request.setAttribute("allEmployeeList", allEmployeeList);
			request.setAttribute("ownDirectoryList", ownDirectoryList);
			request.setAttribute("publicDirectoryList", publicDirectoryList);
			request.setAttribute("sharedDirectoryList", sharedDirectoryList);
			request.setAttribute("sharedDirectoryEmployeeList", sharedDirectoryEmployeeList);
			request.setAttribute("subEmployeeDirectoryList", subEmployeeDirectoryList);
			
			request.setAttribute("ownDirectoryFilesList", ownDirectoryFilesList);
			request.setAttribute("publicDirectoryFilesList", publicDirectoryFilesList);
			request.setAttribute("sharedDirectoryFilesList", sharedDirectoryFilesList);
			request.setAttribute("subEmployeeDirectoryFilesList", subEmployeeDirectoryFilesList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/managerDirectory.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if(request.getSession().getAttribute("role").equals("employee"))
		{
			request.setAttribute("publicDirectoryList", publicDirectoryList);
			request.setAttribute("sharedDirectoryList", sharedDirectoryList);
			request.setAttribute("sharedDirectoryEmployeeList", sharedDirectoryEmployeeList);
		
			request.setAttribute("ownDirectoryFilesList", ownDirectoryFilesList);
			request.setAttribute("publicDirectoryFilesList", publicDirectoryFilesList);
			request.setAttribute("sharedDirectoryFilesList", sharedDirectoryFilesList);
		
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/managerDirectory.jsp");
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	
	}

	
	public List<Integer> getDirectoryIdList(List<Directory> ownDirectoryList) {
		// TODO Auto-generated method stub
		List<Integer> directoryIdList=	new ArrayList<Integer>();
		for(Directory d:ownDirectoryList)
		{
			directoryIdList.add(d.getDirectoryId());
		}
		return directoryIdList;
	}

	public List<Employee> getSubEmployeeList(int id,List<Employee> employeeList)
	{
		List<Employee> list1=getSubEmployee(id,employeeList);
		if(list1.size()==0)
		{
			return null;
		}
		List<Employee> list2=null;
		List<Employee> list3 =new ArrayList<Employee>();
		for(Employee obj: list1)
		{
			list2=getSubEmployeeList(obj.getEmployeeId(),employeeList);
			if(list2!=null && list2.size()>0)
			{
				list3.addAll(list2);
			}
		}
		if(list3!=null && list3.size()>0)
		{
			list1.addAll(list3);
		}
		return list1;
	}
	
	public List<Employee> getSubEmployee(int managerId,List<Employee> employeeList) {
		// TODO Auto-generated method stub
		List<Employee> a=new ArrayList<Employee>();
		for(Employee obj:employeeList )
		{
			if(obj.getAssignedManager()==managerId && obj.getEmployeeId()!=managerId)
			{
				a.add(obj);
			}
		}
		return a;
	}

	public JSONArray printEmployee(int id,List<Employee> employeeList)
	{
		List<Employee> a=getSubEmployee(id,employeeList);
		Services service=new Services();
		
		for(Employee f:a)
		{
			System.out.println("id:"+f.getEmployeeId()+"   parent:"+f.getAssignedManager());
		}
		JSONArray ja = new JSONArray();
		
		JSONObject file1Json= new JSONObject();
		
		if (a.size()==0)
		{
			if(service.getEmployeeRole(id).equals("employee"))
			{
				return null;
			}
			return ja.put(getNullObject());
		}
		JSONObject dir1Json= new JSONObject();
		
		try
		{
			for(Employee obj: a)
			{
				JSONObject dirJson= new JSONObject();
				dirJson.put("name",obj.getEmployeeId());
				dirJson.put("id", obj.getEmployeeId());
				dirJson.put("role",(String) service.getEmployeeRole(obj.getEmployeeId()));
				dirJson.put("parentId", obj.getAssignedManager());
				JSONArray jb=printEmployee(obj.getEmployeeId(),employeeList);
				dirJson.put("files", jb);
				ja.put(dirJson);
			}
			
		}
		
		catch(Exception e)
		{
			return null;
		}
		return ja;
		
	}
	private JSONObject getNullObject() {
		// TODO Auto-generated method stub
		JSONObject obj=new JSONObject();
		return obj;
	}

}