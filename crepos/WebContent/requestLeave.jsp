<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.Register" %>
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
	<%String role=null;
	if(request.getSession().getAttribute("role")!=null)
	{
		role=(String)request.getSession().getAttribute("role"); %>
   <title>CRepos - <%=role %></title>
 <%} %>

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
	  <form action="<%=request.getContextPath()%>/controller" method="post" >
	 <input type="hidden" name="actionCode" value="requestLeave" >
               
    <div class="container">
    <h1>Request Leave</h1>
	  	<hr>
		<div class="row">
	      <!-- left column -->
	      
	      <!-- edit form column -->
	      <div class="col-md-9 personal-info">
	          <div class="form-group">
	            <label class="col-lg-3 control-label">Request Date:</label>
	            <div class="col-lg-8">
	            <%
					  Date date = new Date();
	            SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
	            		 date=ft.parse(ft.format(date)) ;
					%>
	              <input class="form-control" type="text" value="<%=ft.format(date).toString() %>" id="requestDate" disabled>
	              <input class="form-control" type="hidden" name="requestDate" value="<%=ft.format(date).toString() %>" >
	          
	            </div>
	          </div>
	           <div class="form-group">
	         	   <label class="col-lg-3 control-label">From Date:</label>
	            		<div class="col-lg-8">
	        
			        		<div class="input-group">
								<div class="input-group-addon" >
								 <i class="fa fa-calendar"></i>
			 						</div>
								<input class="form-control datepicker" type="text"  placeholder="From Date" name="fromDate" id="fromDate" required>
			 			  </div>
			 			</div>
			   </div>
			 			  
  					  
			    <div class="form-group">
	         	   <label class="col-lg-3 control-label">To Date:</label>
	            		<div class="col-lg-8">
	        
			        		<div class="input-group">
								<div class="input-group-addon" >
								 <i class="fa fa-calendar"></i>
			 						</div>
								<input class="form-control datepicker" type="text"  placeholder="To Date" name="toDate" id="toDate" required>
			 			  </div>
			 			</div>
				   </div>
			   
       <div class="form-group">
	            <label class="col-lg-3 control-label">Reason:</label>
	            <div class="col-lg-8">
	              <input class="form-control" type="text" name="reason" onchange="checkDateFrom()" required>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-md-3 control-label"></label>
	            <div class="col-md-8">
	                
	              <input type="submit" class="btn btn-primary" value="Save Changes">
	              <span></span>
	              <input type="reset" class="btn btn-default" value="Cancel">
	            </div>
	          </div>
	      </div>
	  </div>
	</div>
	<hr>
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
          $('.requestLeave').addClass('active');
         });
	</script>
 
</body>

</html>
