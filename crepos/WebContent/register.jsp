<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%if(request.getSession().getAttribute("employeeId")!=null && request.getSession().getAttribute("role")!=null)
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

<html class="no-js" lang="">

    <!-- Mirrored from radiustheme.com/demo/html/redchili/redchili/food-menu-details.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Feb 2017 22:05:18 GMT -->
    <!-- Added by HTTrack --><meta http-equiv="content-type" content="text/html;charset=UTF-8" /><!-- /Added by HTTrack -->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Crepos | Register</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
	  <!-- Bootstrap CSS --> 
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
	
	
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
	
	
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- Normalize CSS --> 
        <link rel="stylesheet" href="css/normalize.css">

        <!-- Main CSS -->         
        <link rel="stylesheet" href="css/main.css">

      
        <!-- Animate CSS --> 
        <link rel="stylesheet" href="css/animate.min.css">

        <!-- Font-awesome CSS-->
        <link rel="stylesheet" href="css/font-awesome.min.css">

        <!-- Owl Caousel CSS -->
        <link rel="stylesheet" href="vendor/OwlCarousel/owl.carousel.min.css">
        <link rel="stylesheet" href="vendor/OwlCarousel/owl.theme.default.min.css">

        <!-- Main Menu CSS -->      
        <link rel="stylesheet" href="css/meanmenu.min.css">

        <!-- nivo slider CSS -->
        <link rel="stylesheet" href="vendor/slider/css/nivo-slider.css" type="text/css" />
        <link rel="stylesheet" href="vendor/slider/css/preview.css" type="text/css" media="screen" />

        <!-- Switch Style CSS -->
        <link rel="stylesheet" href="css/switch-style.css">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/style.css">

        <!-- loginstyle -->
        <link rel="stylesheet" href="css/loginStyle.css" media="screen" type="text/css" />

        <!-- Modernizr Js -->
        <script src="js/modernizr-2.8.3.min.js"></script>

    </head>
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

        <!-- Add your site or application content here -->
        <div class="wrapper">
            <!-- Header Area Start Here -->
            <header>                
                <div class="header2-area">
                    <div class="header-bottom-area" id="sticker" style=" border-bottom: 0px solid #1b1919;">
                        <div class="container">
                            <div class="row">                         
                                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
                                    <div class="logo-area">
                                        <span><h3>CRepos </h3></span>
                                        <!--     <a href="index.html"><img class="img-responsive" src="img/cafeteria.jpg" alt="logo"></a>
                                        -->  </div>
                                </div>  

                            </div>

                        </div> 
                    </div>
                </div>
            </header>
            <!-- Header Area End Here -->
       <%if((request.getAttribute("msg")!=null))
      {
    	  %>
      <div class="alert alert-info">
            <strong><%= request.getAttribute("msg") %></strong>
            
        </div>
      <%
		request.setAttribute("msg", null);
      } %>
      
           <!-- Single Menu Area Start Here -->
            <div class="Signin-Box">
                <h2>Sign Up</h2>
                <form action="<%=request.getContextPath()%>/controller" method="post" onsubmit="return validatePassword()">

                    <input type="text" name="firstName" placeholder="First Name" required>
                     <input type="text" name="lastName" placeholder="Last Name" required>
                    <div class="gender">
                        <input type="radio" value="male" id="male" name="gender" checked/>
                        <label for="male" >Male</label>
                        <input type="radio" value="female" id="female" name="gender" />
                        <label for="female" >Female</label>
                    </div>
                     <div class="input-group">
								<input class="datepicker" type="text"  placeholder="Birth Date" name="dob" id="dob" required>
			 			  </div>
                     <%
					  Date date = new Date();
                	            SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
	            		 date=ft.parse(ft.format(date)) ;
					%>
	              <input class="form-control" type="hidden" name="today" value="<%=ft.format(date).toString() %>" id="today" disabled>
	        
                   
                    <input type="text" name="phoneNumber" placeholder="Phone Number" required>
                    <input type="text" name="emailId" placeholder="Email ID" required>
                    <input type="text" name="employeeId" placeholder="Employee Id" required>
                    <input type="password" id="password" name="password" placeholder="Password" required>
                    <input type="password" id="cpass" name="cpass" placeholder="Confirm Password"  required>
       <!-- onkeyup="return validatePassword()" -->
                    <input type="text" name="question" placeholder="Security Question" required>
                    <input type="text" name="answer" placeholder="Answer" required>  
                    <input type="hidden" name="actionCode" value="register" >
                    <input type="submit" name="login" class="login login-submit" value="Sign Up" style="background-color: #171616" >
                </form>

                <div class="Reg-ForgotP">
                    <a href="login.jsp">Existing User? Click here to login.</a>
                </div>
            </div>


        </div>
     <!-- Style Switch End Here -->

  <!-- jQuery Version 1.11.1 -->
    <script src="<%=request.getContextPath()%>/js/jquery.js"></script>


    <!-- jquery-->  
     <script src="js/jquery-2.2.4.min.js" type="text/javascript"></script>
    
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
	
	function validatePassword() {
	    var pass1 = document.getElementById("password").value;
	    var pass2 = document.getElementById("cpass").value;
	    if (pass1 != pass2) {
	        alert("Passwords do not Match!!!");
	        return false;
	    }
	    
	    var pass1 = document.getElementById("today").value;
	    var pass2 = document.getElementById("dob").value;

	   	var date1=Date.parse(pass1) ;
		var	date2=Date.parse(pass2) ;
		if(date1 < date2)
			{
				alert('Do you have time machine.You came in past');
				document.getElementById("fromDate").value="";
				return false;
			} 
		else
		return true;
	}
	</script>

    <!-- Plugins js -->
    <script src="js/plugins.js" type="text/javascript"></script>

    <!-- Bootstrap js -->
    <script src="js/bootstrap.min.js" type="text/javascript"></script>

    <!-- WOW JS -->     
    <script src="js/wow.min.js"></script>

    <!-- Nivo slider js -->     
    <script src="vendor/slider/js/jquery.nivo.slider.js" type="text/javascript"></script>
    <script src="vendor/slider/home.js" type="text/javascript"></script>

    <!-- Owl Cauosel JS -->
    <script src="vendor/OwlCarousel/owl.carousel.min.js" type="text/javascript"></script>

    <!-- Meanmenu Js -->
    <script src="js/jquery.meanmenu.min.js" type="text/javascript"></script>

    <!-- Srollup js -->
    <script src="js/jquery.scrollUp.min.js" type="text/javascript"></script>

    <!-- jquery.counterup js -->
    <script src="js/jquery.counterup.min.js"></script>
    <script src="js/waypoints.min.js"></script>

    <!-- Switch js -->
    <script src="js/switch-style.js" type="text/javascript"></script>

    <!-- Custom Js -->
    <script src="js/main.js" type="text/javascript"></script>

</body>

<!-- Mirrored from radiustheme.com/demo/html/redchili/redchili/food-menu-details.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Feb 2017 22:05:24 GMT -->
</html>
