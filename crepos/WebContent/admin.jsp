<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%if(request.getSession().getAttribute("employeeId")==null)
	{
		request.setAttribute("msg", "Session may expire.");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
		return;
	}%>

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
    <!-- Ignite UI Required Combined CSS files -->
    <link href="http://cdn-na.infragistics.com/igniteui/2016.2/latest/css/themes/infragistics/infragistics.theme.css" rel="stylesheet" />
    <link href="http://cdn-na.infragistics.com/igniteui/2016.2/latest/css/structure/infragistics.css" rel="stylesheet" />
 

    <script src="http://ajax.aspnetcdn.com/ajax/modernizr/modernizr-2.8.3.js"></script>
    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.1/jquery-ui.min.js"></script>

    <!-- Ignite UI Required Combined JavaScript files -->
    <script src="http://cdn-na.infragistics.com/igniteui/2016.2/latest/js/infragistics.core.js"></script>
    <script src="http://cdn-na.infragistics.com/igniteui/2016.2/latest/js/infragistics.lob.js"></script>

    <style>
        .ui-icon.ui-igtreegrid-expansion-indicator.ui-icon-minus {
             background: url(http://www.igniteui.com/images/samples/tree-grid/opened_folder.png) !important;
             background-repeat: no-repeat;
        }
        .ui-icon.ui-igtreegrid-expansion-indicator.ui-icon-plus {
             background: url(http://www.igniteui.com/images/samples/tree-grid/folder.png) !important;
             background-repeat: no-repeat;
        }
		.ui-icon-plus:before {
		    content: '' !important;
		}
		.ui-icon-minus:before{
		    content: '' !important;
		}
    </style>



    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

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
    <div class="container">
<div class="container">

<h1>Welcome , <%=request.getSession().getAttribute("employeeId") %></h1>
		<h3>Your manager is <%=request.getSession().getAttribute("managerId") %></h3>
	
		    <table id="treegrid"></table>
    <script>
        $(function () {

            var files=<%= request.getAttribute("final")%>;
            $("#treegrid").igTreeGrid({
               
                dataSource: files,
                autoGenerateColumns: false,
                primaryKey: "id",
                columns: [
                    { headerText: "ID", key: "id", width: "3000px", dataType: "number", hidden: true},
                    { headerText: "Employee Id", key: "name", width: "300px", dataType: "string" },
                    { headerText: "Role", key: "role", width: "300px", dataType: "string" },
                    { headerText: "parentId", key: "parentId", width: "300px", dataType: "number"},
                   ],
                childDataKey: "files",
                initialExpandDepth: 2,
                features: [
                {
                    name: "Selection",
                    multipleSelection: true
                },
                {
                    name: "Sorting"
                },
                {
                    name: "Filtering",
                    columnSettings: [
                        {
                            columnKey: "dateModified",
                            condition: "after"
                        },
                        {
                            columnKey: "size",
                            condition: "greaterThan"
                         }
                        ]
                }]
            });
        });
        
    </script>



    </div>
    <!-- /.container -->
	<script>
		$(document).ready(function () {
		    $('.admin').addClass('active');
		   
		});
	</script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
