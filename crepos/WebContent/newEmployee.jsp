<%@page import="com.model.Register"%>
<%@page import="com.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.LeaveRequest" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%if(request.getSession().getAttribute("employeeId")==null)
	{
		request.setAttribute("msg", "Session may expire.");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
		return;
	}%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%if(request.getSession().getAttribute("role")!=null && !request.getSession().getAttribute("role").equals("admin"))
	{
	String role=(String)request.getSession().getAttribute("role");
	if(role.equals("admin") || role.equals("manager"))
	{
		
		response.sendRedirect(request.getContextPath()+ "/controller?actionCode=getSubEmployeeTree");
				return;
	}
	response.sendRedirect(request.getContextPath()+ "/"+role+".jsp");
	return;
	}%>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<%String role=(String)request.getSession().getAttribute("role"); %>
   <title>CRepos - <%=role %></title>
 
    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <style>
    body {
        padding-top: 70px;
        /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
    }
    </style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
  
    <!-- Navigation -->
 	<% String page1= role+"Inc.jsp"; %>
   	<jsp:include page="<%=page1 %>" />
     <!-- Page Content -->
  <%if((request.getAttribute("msg")!=null))
      {
    	  %>
      <div class="alert alert-info">
            <strong><%= request.getAttribute("msg") %></strong>
            
        </div>
      <%
		request.setAttribute("msg", null);
      } %>
     	           
    <div class="container">
    <h1>In Active Employee / New Employee</h1>
	<table class="table">
		<thead>
			<td>Profile Picture</td>
			<td>Employee Id</td>
			<td>First Name</td>
			<td>Last Name</td>
			<td>Role</td>
			<td>Under SuperVision</td>
		</thead>
		<tbody>
		
	<%if(request.getAttribute("employeeList")!=null && request.getAttribute("registerList")!=null )
		{
		List<Employee> employeeList=(List<Employee>) request.getAttribute("employeeList");
		List<Register> registerList=(List<Register>) request.getAttribute("registerList");
		
			for(int i=0;i<employeeList.size();i++ )
			{
			%><tr>
			   <%if(registerList.get(i).getProfile()!=null) {
	  
					byte[] image = null ;
					Blob blob=registerList.get(i).getProfile();
					image=blob.getBytes(1, (int)blob.length());
					String encode = Base64.getEncoder().encodeToString(image);
					request.setAttribute("imgBase", encode);
				%>
				 <td style="height: 20%;width: 20%;">  <img src="data:image/jpeg;base64,${imgBase}" class="col img-circle" alt="<%= registerList.get(i).getFirstName()%>" style="height: 20%;width: 90%;"></td>
	          <%}
	        else
	        { %>
	        <td style="height: 20%;width: 20%;">
	          <img src="" class="avatar img-circle" alt="<%= registerList.get(i).getFirstName()%>" style="height: 100%;width: 90%;">
	          </td>
	          <%} %>
	     
	     		<td><%= registerList.get(i).getEmployeeId()%></td>
				<td><%= registerList.get(i).getFirstName()%></td>
				<td><%= registerList.get(i).getLastName()%></td>
				<td><%= registerList.get(i).getRole()%></td>
				<td><div class="form-group">
						  <label >Manger :</label>
						  <select class="form-control" id="managerList" onchange="myFunction(this.value,'<%= registerList.get(i).getEmployeeId()%>')">
						  <option selected="true" disabled>--Select-- </option>    
														
						  <%if(request.getAttribute("managerList")!=null)
						  	{ 
								List<Register> managerList= (List<Register>)request.getAttribute("managerList");
						  		for(Register obj:managerList)
						  		{%>
						    		<option value="<%=obj.getEmployeeId() %>"><%=obj.getFirstName() %> <%=obj.getLastName() %> (<%=obj.getEmployeeId() %>)</option>
						    	<%}
						  	}%>
						  </select>
						</div>
<%= employeeList.get(i).getAssignedManager()%></td>
			</tr>
			<%}
		}%>
		</tbody>
	</table>
	</div>
	<script>
			function myFunction(managerId,employeeId) {
			 	 $.ajax({
					type:"POST",
					url : 'controller?actionCode=updateAssignedManager',
					data : {
						managerId :managerId,
						employeeId: employeeId,
					},
					dataType:"json",
					success : function(responseText) {
						var list=responseText;
						 $.each(list, function (index, item) {
								
							}); 
						 	
					}
				});	 
			 	location.reload(true);
			 	
			}
		</script>
	
    <!-- /.container -->

    <!-- jQuery Version 1.11.1 -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
   <script>
		$(document).ready(function () {
		    $('.newEmployee').addClass('active');
		   
		});
	</script>
       

</body>

</html>
