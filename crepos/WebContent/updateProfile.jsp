<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.Register" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%if(request.getSession().getAttribute("employeeId")==null)
	{
		request.setAttribute("msg", "Session may expire.");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
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
    <link href="css/bootstrap.min.css" rel="stylesheet">

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
      
      
<% Register register=(Register)request.getSession().getAttribute("profile");%>
    <!-- Navigation -->
    <% String page1= role+"Inc.jsp"; %>
   <jsp:include page="<%=page1 %>" />
    <!-- Page Content -->
	  <form action="<%=request.getContextPath()%>/controller" method="post" enctype="multipart/form-data" onsubmit="return validatePassword()">
	 <input type="hidden" name="actionCode" value="updateProfile" >
               
    <div class="container">
    <h1>Edit Profile</h1>
	  	<hr>
		<div class="row">
	      <!-- left column -->
	      <div class="col-md-3">
	        <div class="text-center">
	        <%if(register.getProfile()!=null) {
	  
		byte[] image = null ;
		Blob blob=register.getProfile();
		image=blob.getBytes(1, (int)blob.length());
		String encode = Base64.getEncoder().encodeToString(image);
		request.setAttribute("imgBase", encode);
	%>
	          <img src="data:image/jpeg;base64,${imgBase}" class="avatar img-circle" alt="<%= register.getFirstName()%>" style="height: 100%;width: 100%;">
	          <%}
	        else
	        { %>
	          <img src="" class="avatar img-circle" alt="<%= register.getFirstName()%>" style="height: 100%;width: 100%;">
	          
	          <%} %>
	          <h6>Upload a photo...</h6>
	          
	          <input type="file" class="form-control" name="file">
	        </div>
	      </div>
	      
	      <!-- edit form column -->
	      <div class="col-md-9 personal-info">
	        <!-- <div class="alert alert-info alert-dismissable">
	          <a class="panel-close close" data-dismiss="alert">×</a> 
	          <i class="fa fa-coffee"></i>
	          This is an <strong>.alert</strong>. Use this to show important messages to the user.
	        </div>
	         --><h3>Personal info</h3>
	        
	          <div class="form-group">
	            <label class="col-lg-3 control-label">First name:</label>
	            <div class="col-lg-8">
	            
	              <input class="form-control" type="text" value="<%=register.getFirstName() %>" name="firstName" required>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-lg-3 control-label">Last name:</label>
	            <div class="col-lg-8">
	              <input class="form-control" type="text" value="<%=register.getLastName() %>" name="lastName" required>
	            </div>
	          </div>
	          <div class="form-group">
	         	   <label class="col-lg-3 control-label">DOB:</label>
	            		<div class="col-lg-8">
	        
			        		<div class="input-group">
								<div class="input-group-addon" >
								 <i class="fa fa-calendar"></i>
			 						</div>
								<input class="form-control datepicker" type="text" name="dob" id="dob" value="<%=register.getDob() %>" required>
			 			  </div>
			 			</div>
			   </div>
			
	          <div class="form-group">
	            <label class="col-lg-3 control-label">Phone Number:</label>
	            <div class="col-lg-8">
	              <input class="form-control" type="text" value="<%=register.getPhoneNumber() %>" name="phoneNumber" required>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-lg-3 control-label">Email Id:</label>
	            <div class="col-lg-8">
	              <input class="form-control" type="text" value="<%=register.getEmailId() %>" name="emailId" required>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-md-3 control-label">Employee Id:</label>
	            <div class="col-md-8">
	              <input class="form-control" type="text" value="<%=register.getEmployeeId() %>" name="employeeId" disabled>
	            </div>
	          </div>
	          
	          <div class="form-group">
	            <label class="col-md-3 control-label"> Old Password:</label>
	            <div class="col-md-8">
	              <input class="form-control" type="password" " name="password" id="oldPassword" required>
	              <input type="hidden" name="oldPassword1" id="oldPassword1" value="<%=register.getPassword() %>" >
	            </div>
	          </div>  
	          <div class="form-group">
	            <label class="col-md-3 control-label">Password:</label>
	            <div class="col-md-8">
	              <input class="form-control" type="password" value="" name="password" id="password" disabled>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-md-3 control-label" >Confirm password:</label>
	            <div class="col-md-8">
	              <input class="form-control" type="password" value="" name="cpass" id="cpass"  disabled>
	            </div>
	          </div>
	           <div class="form-group">
	            <label class="col-md-3 control-label">security question:</label>
	            <div class="col-md-8">
	              <input class="form-control" type="text" value="<%=register.getQuestion() %>" name="question" required>
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="col-md-3 control-label">Answer:</label>
	            <div class="col-md-8">
	              <input class="form-control" type="password" value="<%=register.getAnswer() %>" name="answer" required>
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


	<script>
	
	function validatePassword() {
	    var pass1 = document.getElementById("oldPassword").value;
	    var pass2 = document.getElementById("oldPassword1").value;
	    var ok = false;
	    if (pass1 == pass2) {
	        //alert("Passwords Do not match");
	        document.getElementById("oldPassword").style.borderColor = "#E34234";
	        document.getElementById("oldPassword1").style.borderColor = "#E34234";
	        ok = true;
	    }
	    else {
	        alert("Passwords do not Match!!!");
	    }
	    return ok;
	}
	</script>

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
		    $('.profile').addClass('active');
		   
		});
	</script>
  
</body>

</html>
