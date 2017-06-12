<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
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
        <title>CRepos | Login</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- Normalize CSS --> 
        <link rel="stylesheet" href="css/normalize.css">

        <!-- Main CSS -->         
        <link rel="stylesheet" href="css/main.css">

        <!-- Bootstrap CSS --> 
        <link rel="stylesheet" href="css/bootstrap.min.css">

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
    <body >
    
    
            <div class="wrapper">
            <!-- Header Area Start Here -->
            <header>                
                <div class="header2-area">
                    <div class="header-bottom-area" id="sticker" style=" border-bottom: 0px solid #1b1919;">
                        <div class="container">
                            <div class="row">                         
                                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
                                    <div class="logo-area">
                                        <span><h3>CRepos</h3></span>
                                        <!--     <a href="index.html"><img class="img-responsive" src="img/cafeteria.jpg" alt="logo"></a>
                                        -->  </div>
                                </div>  

                            </div>

                        </div> 
                    </div>
                </div>
            </header>
    
    
            <!-- Single Menu Area Start Here -->
 <%if((request.getAttribute("msg")!=null))
      {
    	  %>
      <div class="alert alert-info">
            <strong><%= request.getAttribute("msg") %></strong>
            
        </div>
      <%
		request.setAttribute("msg", null);
      } %>


            <div class="Signin-Box">
                <h2>Sign In</h2><br>

              
                <form action="<%=request.getContextPath()%>/controller" method="post">
                    <input type="text" name="employeeId" placeholder="User Id">
                    <input type="password" name="password" placeholder="Password">
                    <input type="hidden" name="actionCode" value="logIn">
                    <input type="submit" class="login login-submit" value="Log In"style="background-color: #171616">
                </form>

                <div class="Reg-ForgotP">
                    <a href="register.jsp">New User? Click here to register.</a>
                    <br>
                    <!-- <a href="#">Forgot Password?</a> -->
                </div>
            </div>



        </div>
    <link id="switch_style" href="#" rel="stylesheet" type="text/css">
    <div class="style-switch left" id="switch-style">

    </div>
    <!-- Style Switch End Here -->
    <!-- Preloader Start Here -->
    <div id="preloader"></div>
    <!-- Preloader End Here -->
    <!-- jquery-->  

    <script src="js/jquery-2.2.4.min.js" type="text/javascript"></script>

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
</body >

<!-- Mirrored from radiustheme.com/demo/html/redchili/redchili/food-menu-details.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 24 Feb 2017 22:05:24 GMT -->
</html>
