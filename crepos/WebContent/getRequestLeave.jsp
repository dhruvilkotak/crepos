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

<%if(request.getSession().getAttribute("role")!=null &&request.getSession().getAttribute("role").equals("admin"))
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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<%String role=(String)request.getSession().getAttribute("role"); %>
   <title>CRepos - <%=role %></title>
 
 <script>
 function checkDate() {
	 var pass1 = document.getElementById("requestDate").value;
	    var pass2 = document.getElementById("fromDate").value;

	   	var date1=Date.parse(pass1) ;
		var	date2=Date.parse(pass2) ;
		if(date2 < date1)
			{
				alert('From date can not be past date');
				document.getElementById("fromDate").value="";
				return false;
			} 
		
		 pass1 = document.getElementById("fromDate").value;
	     pass2 = document.getElementById("toDate").value;
	 	var date1=Date.parse(pass1) ;
		var	date2=Date.parse(pass2) ;
		if(date2 < date1)
			{
				alert('To date can not be less than from Date');
				document.getElementById("toDate").value="";
				return false;
			}

		return true;
		}

           </script>	

    <!-- Bootstrap Core CSS -->
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet">

<!-- datepicker start -->
<!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
<!-- iCheck -->
    <script src="<%=request.getContextPath()%>/plugins/datepicker/bootstrap-datepicker.js"></script>
   
    <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/datepicker/datepicker.css">
   
<!-- datepicker End-->



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
  <%if((request.getAttribute("msg")!=null))
      {
    	  %>
      <div class="alert alert-info">
            <strong><%= request.getAttribute("msg") %></strong>
            
        </div>
      <%
		request.setAttribute("msg", null);
      } %>
      
    <!-- Navigation -->
 	<% String page1= role+"Inc.jsp"; %>
   	<jsp:include page="<%=page1 %>" />
     <!-- Page Content -->
	  <form action="<%=request.getContextPath()%>/controller" method="post" onsubmit="return checkDate()">
	 <input type="hidden" name="actionCode" value="requestLeave" >
               
    <div class="container">
    <h1>Request Leave</h1>
	<table class="table">
		<thead>
			<td>Request Id</td>
			<td>Manager Id</td>
			<td>Request Date</td>
			<td>From Date</td>
			<td>To Date</td>
			<td>Reason</td>
			<td>status</td>
		</thead>
		<tbody>
		
	<%if(request.getAttribute("leaveRequestList")!=null)
		{
		List<LeaveRequest> leaveRequest=(List<LeaveRequest>) request.getAttribute("leaveRequestList");
			for(LeaveRequest obj:leaveRequest )
			{
			%><tr>
				<td><%= obj.getRequestId() %></td>
				<td><%= obj.getManagerId()%></td>
				<td><%= obj.getRequestDate()%></td>
				<td><%= obj.getFromDate()%></td>
				<td><%= obj.getToDate()%></td>
				<td><%= obj.getReason()%></td>
				<td><%= obj.getStatus()%></td>
					</tr>
			<%}
		}%>
		</tbody>
	</table>
	</div>
	</form>
	
    <!-- /.container -->
          
    <!-- jQuery Version 1.11.1 -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
    
    <!-- datepicker start  -->
<!-- jQuery 2.1.4 -->
    <script src="<%=request.getContextPath()%>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- iCheck -->
    <script src="<%=request.getContextPath()%>/plugins/iCheck/icheck.min.js"></script>
  	  <script src="<%=request.getContextPath()%>/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- datepicker -->
    <script src="<%=request.getContextPath()%>/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script>
    $(function(){
		 $('.datepicker').datepicker();
		});
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
           </script>
<!--    datepicker end -->
	<script>
	  $(document).ready(function () {
          $('.viewRequestLeave').addClass('active');
         });
	</script>

</body>

</html>
