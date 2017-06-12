<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%if(request.getSession().getAttribute("employeeId")==null)
	{
		request.setAttribute("msg", "Session may expire.");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
		return;
	}%>
</head>
<body>
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
	                <li class="admin">
	                 	<a class="navbar-brand" href="<%=request.getContextPath()%>/controller?actionCode=getSubEmployeeTree">Admin</a> 
	                </li>
                    <li class="profile">
                        <a href="<%=request.getContextPath()%>/controller?actionCode=getProfile"><%=request.getSession().getAttribute("employeeId") %></a>
                    </li>
                    <li class="activeEmployee">
                        <a href="<%=request.getContextPath()%>/controller?actionCode=getActiveEmployee">Active Employee</a>
                    </li>
                    <li class="newEmployee">
                        <a href="<%=request.getContextPath()%>/controller?actionCode=getNewEmployee">New Employee</a>
                    </li>
                    <li class="payment">
                        <a href="<%=request.getContextPath()%>/controller?actionCode=getPayment">Payment Sleeps</a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/controller?actionCode=logOut">LogOut</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>


</body>
</html>