<%@page import="java.security.acl.Owner"%>
<%@page import="com.model.DirectoryEmployee"%>
<%@page import="com.model.Directory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*" %>
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
      <%int employeeId=(Integer)request.getSession().getAttribute("employeeId") ;%>
      
    <!-- Navigation -->
 	<% String page1= role+"Inc.jsp"; %>
   	<jsp:include page="<%=page1 %>" />
     <!-- Page Content -->
	           
	           
    <div class="container">
    <h1>Payment Receipts</h1>
      <form action="<%=request.getContextPath()%>/controller" method="post" >
	 <input type="hidden" name="actionCode" value="getPayment" >
  
       <div class="form-group">
	        <div class="row">
	           	
	        		  <label class="col-lg-2 control-label">Payment Date:</label>
	       		<div class="col-lg-4">
	       				<div class="input-group">
								<div class="input-group-addon" >
								 <i class="fa fa-calendar"></i>
			 					</div>
								<input class="form-control datepicker" type="text"  placeholder="Payment Date" name="paymentDate" id="paymentDate" required>
					 	  </div>
				</div>
			   	<div class="col-lg-4">
		   	    	<input type="submit" class="btn btn-primary" value="Get Payment Sleep">
				</div>
			</div>
		</div>
  </form>  
	<table class="table">
		<thead>
			<td>Payment Sleep Number</td>
			<td>Payment Date</td>
			<td>Salary</td>
			<td>Bonus</td>
			<td>Total Salary</td>
		</thead>
		<tbody>
		
	<%if(request.getAttribute("paymentList")!=null && request.getAttribute("paymentList")!=null )
		{
		List<Payment> paymentList=(List<Payment>) request.getAttribute("paymentList");

			for(Payment payment:paymentList)
			{
			%>
			<tr>
		     		<td><%= payment.getPaymentId()%></td>
		     		<td><%= payment.getPaymentDate()%></td>
					<td><%= payment.getSalary()%></td>
					<td><%= payment.getBonus()%></td>
					<td><%= payment.getTotalSalary()%></td>
			</tr>
			<%
			}
		}%>
		</tbody>
	</table>
    
    
	</div>
	
	
    <!-- /.container -->
          
    <!-- jQuery Version 1.11.1 -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
    
    <!-- datepicker start  -->
<!-- jQuery 2.1.4 -->
    <script src="<%= request.getContextPath()%>/plugins/jQuery/jQuery-2.1.4.min.js"></script>

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
          $('.payment').addClass('active');
         });
	</script>

</body>

</html>
