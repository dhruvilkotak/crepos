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
    <%if(role.equals("manager"))
    	{%>
    	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#createDirectory">Create Directory</button>
					  <!-- Modal -->
					    <form action="<%=request.getContextPath()%>/controller" method="post">
				 <input type="hidden" name="actionCode" value="createDirectory" >
  
						  <div class="modal fade" id="createDirectory" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">Create Directory</h4>
						        </div>
						        <div class="modal-body">
						          <table class="table">
										<thead style="text-align: center;">
											<td>Directory Name</td>
											<td>Permission</td>
											<td>Owner Id</td>
										</thead>
										<tbody>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										           		   <input class="form-control" type="text" name="directoryName" required>	         
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										            
										            	<select class="form-control" name="permission" style="width: 150%;">
														  		<option value="default" selected>Default</option>
														  		<option value="public">Public</option>
														  		<option value="protected">Protected</option>
														  		<option value="private">Private</option>
														  </select>
										            </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
													<input class="form-control" type="text" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" disabled>
													<input type="hidden" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" >
										             </div>
										          </div>
											</td>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						        <button type="submit" class="btn btn-info">Create Directory</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
		</form>				
    <%} %>
    <%if(request.getAttribute("ownDirectoryList")!=null)
		{
		List<Directory> ownDirectoryList=(List<Directory>) request.getAttribute("ownDirectoryList");
		if(ownDirectoryList.size()>0)
		{
		%>
		<h1>Own Directory List</h1>
	
		<table class="table">
		<thead>
			<td>Directory Id</td>
			<td>Directory Name</td>
			<td>Owner Id</td>
			<td>Permission</td>
			<td></td>
		</thead>
		<tbody>
		<% 
		for(Directory obj:ownDirectoryList )
			{%>
				<tr>
					<td><%= obj.getDirectoryId() %></td>
					<td><%= obj.getDirName()%></td>
					<td><%= obj.getOwnerId()%></td>
					<td><%= obj.getPermission() %></td>
					<td>
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#view<%= obj.getDirectoryId() %>">View Directory</button>
					  <!-- Modal -->
						  <div class="modal fade" id="view<%= obj.getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">View Directory <%= obj.getDirName() %></h4>
						        </div>
						        <div class="modal-body">
			
						<form action="<%=request.getContextPath()%>/controller" method="post" enctype="multipart/form-data">
						   	<div class="row">
							      <!-- left column -->
							      <div class="col-md-3">
								 	  <h6>Upload File</h6>
	          							<input type="file" class="form-control" name="file">
						        </div>
						        <div class="col-md-3">
			        			 <input type="hidden" name="actionCode" value="uploadFile" >
  	          					 <input type="hidden" name="parentDir1" value="<%=obj.getDirectoryId()%>">
  	          					
  	          						<button type="submit" class="btn btn-info">Upload</button>
							   	</div>
						        </div>
						</form>					        
						
						
						          <table class="table">
										<thead style="text-align: center;">
											<td>File Name</td>
											<td>File Id</td>
											<td>Owner Id</td>
											<td>Uploaded Date</td>
											<td>Download</td>
										</thead>
										<tbody>
										<%if(request.getAttribute("ownDirectoryFilesList")!=null)
											{
											List<Files> ownDirectoryFilesList=(List<Files>)request.getAttribute("ownDirectoryFilesList");
											for(Files file:ownDirectoryFilesList)
											{
												if(file.getParentDir()== obj.getDirectoryId())
												{
											%>
											<tr>
								<form action="<%=request.getContextPath()%>/controller" method="post">
										<input type="hidden" name="actionCode" value="download" >
										<input type="hidden" name="fileId" value="<%=file.getFileId() %>" >
										
											<td>
												<%= file.getFileName() %>
											</td>
											<td>
												<%= file.getFileId()%>
											</td>
											<td>
												<%= file.getOwnerId() %>
											</td>
											<td>
												<%= file.getDate() %>
											</td>
											<td>
												<button type="submit" class="btn btn-info">Download</button>
							   				</td>
				    				</form>
						    				</tr>
											<%}
											}
											} %>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
				
					</td>
					<td>

		<%if(role.equals("manager"))
    	{%>
    	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#<%= obj.getDirectoryId() %>">Update Permission</button>
					  <!-- Modal -->
			<form action="<%=request.getContextPath()%>/controller" method="post">
				 <input type="hidden" name="actionCode" value="updatePermission" >
  
						  <div class="modal fade" id="<%= obj.getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">Update Permission</h4>
						        </div>
						        <div class="modal-body">
						          <table class="table">
										<thead style="text-align: center;">
											<td>Directory Name</td>
											<td>Permission</td>
											<td>Owner Id</td>
										</thead>
										<tbody>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										           		   <input class="form-control" type="text"  value="<%=obj.getDirName() %>" disabled>
										           		   <input type="hidden" name="directoryName" value="<%= obj.getDirName() %>">
										           		   <input type="hidden" name="directoryId" value="<%= obj.getDirectoryId() %>"> 	         
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										             <input type="hidden" name="oldPermission" value="<%= obj.getPermission()%>">
													
										            	<select class="form-control" name="permission" style="width: 150%;">
										            		  		<option value="default" <%if(obj.getPermission().equals("default")) {%>selected<%} %>>Default</option>
														  		<option value="public" <%if(obj.getPermission().equals("public")) {%>selected<%} %>>Public</option>
														  		<option value="protected" <%if(obj.getPermission().equals("protected")) {%>selected<%} %>>Protected</option>
														  		<option value="private" <%if(obj.getPermission().equals("private")) {%>selected<%} %>>Private</option>
														  </select>
										            </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
													<input class="form-control" type="text" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" disabled>
													<input type="hidden" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" >
										             </div>
										          </div>
											</td>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						        <button type="submit" class="btn btn-info">Update Permission</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
		</form>				
    <%} %>
					
					
					</td>
					
					<td>
		<%if(role.equals("manager") && obj.getPermission().equals("protected"))
    	{%>
    	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ate<%= obj.getDirectoryId() %>">Add ATE</button>
					  <!-- Modal -->
			<form action="<%=request.getContextPath()%>/controller" method="post">
				 <input type="hidden" name="actionCode" value="addATE" >
  
						  <div class="modal fade" id="ate<%= obj.getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">Add ATE </h4>
						        </div>
						        <div class="modal-body">
						          <table class="table">
										<thead style="text-align: center;">
											<td>Directory Name</td>
											<td>Permission</td>
											<td>Owner Id</td>
											<td>Employee Id</td>
										</thead>
										<tbody>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										           		   <input class="form-control" type="text"  value="<%=obj.getDirName() %>" disabled>
										           		   <input type="hidden" name="directoryName" value="<%= obj.getDirName() %>">
										           		   <input type="hidden" name="directoryId" value="<%= obj.getDirectoryId() %>"> 	         
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										               <input class="form-control" type="text"  value="<%=obj.getPermission() %>" disabled>
										           	 <input type="hidden" name="permission" value="<%= obj.getPermission()%>">
													</div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
													<input class="form-control" type="text" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" disabled>
													<input type="hidden" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" >
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
														<select class="form-control" id="ateId" name="ateId"  style="width: 150%;">
														  
														  <%if(request.getAttribute("allEmployeeList")!=null)
														  	{ 
																List<Register> allEmployeeList= (List<Register>)request.getAttribute("allEmployeeList");
														  		for(Register register:allEmployeeList)
														  		{%>
														    		<option value="<%=register.getEmployeeId() %>" ><%=register.getFirstName() %> <%=register.getLastName() %> (<%=register.getEmployeeId() %>)</option>
														    	<%}
														  	}%>
														  </select>
										            
													 </div>
										          </div>
											</td>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						        <button type="submit" class="btn btn-info">Add ATE</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
		</form>				
    <%} %>
	
					</td>
					
				</tr>
			<%}%>
		</tbody>
		</table>
		<%}
			}%>
	
	<%if(request.getAttribute("publicDirectoryList")!=null)
		{
		List<Directory> publicDirectoryList=(List<Directory>) request.getAttribute("publicDirectoryList");
		if(publicDirectoryList.size()>0)
		{
		%>
	<h1>Public Directory List</h1>
	<table class="table">
		<thead>
			<td>Directory Id</td>
			<td>Directory Name</td>
			<td>Owner Id</td>
			<td>Permission</td>
		</thead>
		<tbody>
		<% 
		for(Directory obj:publicDirectoryList )
			{%>
				<tr>
					<td><%= obj.getDirectoryId() %></td>
					<td><%= obj.getDirName()%></td>
					<td><%= obj.getOwnerId()%></td>
					<td><%= obj.getPermission()%></td>

<td>
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#view<%= obj.getDirectoryId() %>">View Directory</button>
					  <!-- Modal -->
						  <div class="modal fade" id="view<%= obj.getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">View Directory <%= obj.getDirName() %></h4>
						        </div>
						        <div class="modal-body">
			
						<form action="<%=request.getContextPath()%>/controller" method="post" enctype="multipart/form-data">
						   	<div class="row">
							      <!-- left column -->
							      <div class="col-md-3">
								 	  <h6>Upload File</h6>
	          							<input type="file" class="form-control" name="file">
						        </div>
						        <div class="col-md-3">
			        			 <input type="hidden" name="actionCode" value="uploadFile" >
  	          					 <input type="hidden" name="parentDir1" value="<%=obj.getDirectoryId()%>">
  	          					
  	          						<button type="submit" class="btn btn-info">Upload</button>
							   	</div>
						        </div>
						</form>					        
						
						
						          <table class="table">
										<thead style="text-align: center;">
											<td>File Name</td>
											<td>File Id</td>
											<td>Owner Id</td>
											<td>Uploaded Date</td>
											<td>Download</td>
										</thead>
										<tbody>
										<%if(request.getAttribute("publicDirectoryFilesList")!=null)
											{
											List<Files> publicDirectoryFilesList=(List<Files>)request.getAttribute("publicDirectoryFilesList");
											for(Files file:publicDirectoryFilesList)
											{
												if(file.getParentDir()== obj.getDirectoryId())
												{
											%>
											<tr>
								<form action="<%=request.getContextPath()%>/controller" method="post">
										<input type="hidden" name="actionCode" value="download" >
										<input type="hidden" name="fileId" value="<%=file.getFileId() %>" >
										
											<td>
												<%= file.getFileName() %>
											</td>
											<td>
												<%= file.getFileId()%>
											</td>
											<td>
												<%= file.getOwnerId() %>
											</td>
											<td>
												<%= file.getDate() %>
											</td>
											<td>
												<button type="submit" class="btn btn-info">Download</button>
							   				</td>
				    				</form>
						    				</tr>
											<%}
											}
											} %>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
				
					</td>
					<td>


				<td>

		<%if(employeeId==obj.getOwnerId())
    	{%>
    	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#<%= obj.getDirectoryId() %>">Update Permission</button>
					  <!-- Modal -->
			<form action="<%=request.getContextPath()%>/controller" method="post">
				 <input type="hidden" name="actionCode" value="updatePermission" >
  
						  <div class="modal fade" id="<%= obj.getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">Update Permission</h4>
						        </div>
						        <div class="modal-body">
						          <table class="table">
										<thead style="text-align: center;">
											<td>Directory Name</td>
											<td>Permission</td>
											<td>Owner Id</td>
										</thead>
										<tbody>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										           		   <input class="form-control" type="text"  value="<%=obj.getDirName() %>" disabled>
										           		   <input type="hidden" name="directoryName" value="<%= obj.getDirName() %>">
										           		   <input type="hidden" name="directoryId" value="<%= obj.getDirectoryId() %>"> 	         
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										            	 <input type="hidden" name="oldPermission" value="<%= obj.getPermission()%>">
													
										            	<select class="form-control" name="permission" style="width: 150%;">
										            	  		<option value="default" <%if(obj.getPermission().equals("default")) {%>selected<%} %>>Default</option>
														  		<option value="public" <%if(obj.getPermission().equals("public")) {%>selected<%} %>>Public</option>
														  		<option value="protected" <%if(obj.getPermission().equals("protected")) {%>selected<%} %>>Protected</option>
														  		<option value="private" <%if(obj.getPermission().equals("private")) {%>selected<%} %>>Private</option>
														  </select>
										            </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
													<input class="form-control" type="text" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" disabled>
													<input type="hidden" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" >
										             </div>
										          </div>
											</td>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						        <button type="submit" class="btn btn-info">Update Permission</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
		</form>				
    <%} %>
					
					
					</td>



				</tr>
			<%}%>
				</tbody>
	</table>
	<% }
		}%>
	
	<%if(request.getAttribute("sharedDirectoryList")!=null && request.getAttribute("sharedDirectoryEmployeeList")!=null)
		{
		List<Directory> sharedDirectoryList=(List<Directory>) request.getAttribute("sharedDirectoryList");
		List<DirectoryEmployee> sharedDirectoryEmployeeList=(List<DirectoryEmployee>) request.getAttribute("sharedDirectoryEmployeeList");
		if(sharedDirectoryList.size()>0)
		{
		%>
		<h1>Shared Directory List</h1>
		<table class="table">
		<thead>
			<td>Directory Id</td>
			<td>Directory Name</td>
			<td>Owner Id</td>
			<td>Permission</td>
		</thead>
		<tbody>
		<% 
		for(int i=0;i<sharedDirectoryList.size();i++ )
			{%>
				 <tr>
					<td><%= sharedDirectoryList.get(i).getDirectoryId() %></td>
					<td><%= sharedDirectoryList.get(i).getDirName()%></td>
					<td><%= sharedDirectoryList.get(i).getOwnerId()%></td>
					<td><%= sharedDirectoryEmployeeList.get(i).getPermission()%></td>

<td>
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#view<%= sharedDirectoryList.get(i).getDirectoryId() %>">View Directory</button>
					  <!-- Modal -->
						  <div class="modal fade" id="view<%= sharedDirectoryList.get(i).getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">View Directory <%=sharedDirectoryList.get(i).getDirName() %></h4>
						        </div>
						        <div class="modal-body">
			
						<form action="<%=request.getContextPath()%>/controller" method="post" enctype="multipart/form-data">
						   	<div class="row">
							      <!-- left column -->
							      <div class="col-md-3">
								 	  <h6>Upload File</h6>
	          							<input type="file" class="form-control" name="file">
						        </div>
						        <div class="col-md-3">
			        			 <input type="hidden" name="actionCode" value="uploadFile" >
  	          					 <input type="hidden" name="parentDir1" value="<%=sharedDirectoryList.get(i).getDirectoryId()%>">
  	          					
  	          						<button type="submit" class="btn btn-info">Upload</button>
							   	</div>
						        </div>
						</form>					        
						
						
						          <table class="table">
										<thead style="text-align: center;">
											<td>File Name</td>
											<td>File Id</td>
											<td>Owner Id</td>
											<td>Uploaded Date</td>
											<td>Download</td>
										</thead>
										<tbody>
										<%if(request.getAttribute("sharedDirectoryFilesList")!=null)
											{
											List<Files> sharedDirectoryFilesList=(List<Files>)request.getAttribute("sharedDirectoryFilesList");
											for(Files file:sharedDirectoryFilesList)
											{
												if(file.getParentDir()== sharedDirectoryList.get(i).getDirectoryId())
												{
											%>
											<tr>
								<form action="<%=request.getContextPath()%>/controller" method="post">
										<input type="hidden" name="actionCode" value="download" >
										<input type="hidden" name="fileId" value="<%=file.getFileId() %>" >
										
											<td>
												<%= file.getFileName() %>
											</td>
											<td>
												<%= file.getFileId()%>
											</td>
											<td>
												<%= file.getOwnerId() %>
											</td>
											<td>
												<%= file.getDate() %>
											</td>
											<td>
												<button type="submit" class="btn btn-info">Download</button>
							   				</td>
				    				</form>
						    				</tr>
											<%}
											}
											} %>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
				
					</td>
					<td>



	<td>

		<%if(role.equals("manager"))
    	{%>
    	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#<%=  sharedDirectoryList.get(i).getDirectoryId() %>">Update Permission</button>
					  <!-- Modal -->
			<form action="<%=request.getContextPath()%>/controller" method="post">
				 <input type="hidden" name="actionCode" value="updatePermission" >
  
						  <div class="modal fade" id="<%=  sharedDirectoryList.get(i).getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">Update Permission</h4>
						        </div>
						        <div class="modal-body">
						          <table class="table">
										<thead style="text-align: center;">
											<td>Directory Name</td>
											<td>Permission</td>
											<td>Owner Id</td>
										</thead>
										<tbody>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										           		   <input class="form-control" type="text"  value="<%= sharedDirectoryList.get(i).getDirName() %>" disabled>
										           		   <input type="hidden" name="directoryName" value="<%=  sharedDirectoryList.get(i).getDirName() %>">
										           		   <input type="hidden" name="directoryId" value="<%=  sharedDirectoryList.get(i).getDirectoryId() %>"> 	         
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										             <input type="hidden" name="oldPermission" value="<%= sharedDirectoryEmployeeList.get(i).getPermission()%>">
													
										            	<select class="form-control" name="permission" style="width: 150%;">
										            		  		<option value="default" <%if(sharedDirectoryEmployeeList.get(i).getPermission().equals("default")) {%>selected<%} %>>Default</option>
														  		<option value="protected" <%if(sharedDirectoryEmployeeList.get(i).getPermission().equals("protected")) {%>selected<%} %>>Protected</option>
														  		<option value="private" <%if(sharedDirectoryEmployeeList.get(i).getPermission().equals("private")) {%>selected<%} %>>Private</option>
														  </select>
										            </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
													<input class="form-control" type="text" name="ownerId" value="<%= sharedDirectoryList.get(i).getOwnerId() %>" disabled>
													<input type="hidden" name="ownerId" value="<%=sharedDirectoryList.get(i).getOwnerId() %>" >
										             </div>
										          </div>
											</td>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						        <button type="submit" class="btn btn-info">Update Permission</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
		</form>				
    <%} %>
					
					
					</td>



				<td>
		<%if(role.equals("manager") && sharedDirectoryEmployeeList.get(i).getPermission().equals("protected"))
    	{%>
    	<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ate<%= sharedDirectoryList.get(i).getDirectoryId()  %>">Add ATE</button>
					  <!-- Modal -->
			<form action="<%=request.getContextPath()%>/controller" method="post">
				 <input type="hidden" name="actionCode" value="addATE" >
  
						  <div class="modal fade" id="ate<%= sharedDirectoryList.get(i).getDirectoryId()  %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">Add ATE </h4>
						        </div>
						        <div class="modal-body">
						          <table class="table">
										<thead style="text-align: center;">
											<td>Directory Name</td>
											<td>Permission</td>
											<td>Owner Id</td>
											<td>Employee Id</td>
										</thead>
										<tbody>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										           		   <input class="form-control" type="text"  value="<%= sharedDirectoryList.get(i).getDirName() %>" disabled>
										           		   <input type="hidden" name="directoryName" value="<%=  sharedDirectoryList.get(i).getDirName() %>">
										           		   <input type="hidden" name="directoryId" value="<%= sharedDirectoryList.get(i).getDirectoryId() %>"> 	         
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										               <input class="form-control" type="text"  value="<%=sharedDirectoryEmployeeList.get(i).getPermission() %>" disabled>
										           	 <input type="hidden" name="permission" value="<%=sharedDirectoryEmployeeList.get(i).getPermission()%>">
													</div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
													<input class="form-control" type="text" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" disabled>
													<input type="hidden" name="ownerId" value="<%= request.getSession().getAttribute("employeeId") %>" >
										             </div>
										          </div>
											</td>
											<td>
												<div class="form-group">
										            <div class="col-lg-8">
										            <select class="form-control" id="ateId" name="ateId"  style="width: 150%;">
														  
														  <%if(request.getAttribute("allEmployeeList")!=null)
														  	{ 
																List<Register> allEmployeeList= (List<Register>)request.getAttribute("allEmployeeList");
														  		for(Register register:allEmployeeList)
														  		{%>
														    		<option value="<%=register.getEmployeeId() %>" ><%=register.getFirstName() %> <%=register.getLastName() %> (<%=register.getEmployeeId() %>)</option>
														    	<%}
														  	}%>
														  </select>
										            
														<input class="form-control" type="text" name="ateId" >
													 </div>
										          </div>
											</td>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						        <button type="submit" class="btn btn-info">Add ATE</button>
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
		</form>				
    <%} %>
	
					</td>
	
				</tr>
			<%}%>
		</tbody>
	</table>
	
	<% 	}
	}%>
	
	<%if(request.getAttribute("subEmployeeDirectoryList")!=null)
		{
		List<Directory> subEmployeeDirectoryList=(List<Directory>) request.getAttribute("subEmployeeDirectoryList");
		if(subEmployeeDirectoryList.size()>0)
		{
		%>
		<h1>Sub Employee Directory List</h1>
		<table class="table">
		<thead>
			<td>Directory Id</td>
			<td>Directory Name</td>
			<td>Owner Id</td>
			<td>Permission</td>
		</thead>
		<tbody>
		<% 
		for(Directory obj:subEmployeeDirectoryList )
			{%>
			  <form action="<%=request.getContextPath()%>/controller" method="post" >
				 <input type="hidden" name="actionCode" value="approveRequestLeave" >
					<tr>
					<td><%= obj.getDirectoryId() %></td>
					<td><%= obj.getDirName()%></td>
					<td><%= obj.getOwnerId()%></td>
					<td><%= obj.getPermission()%></td>
					
	<td>
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#view<%= obj.getDirectoryId() %>">View Directory</button>
					  <!-- Modal -->
						  <div class="modal fade" id="view<%= obj.getDirectoryId() %>" role="dialog">
						    <div class="modal-dialog">
						    
						      <!-- Modal content-->
						      <div class="modal-content" style="width: 800px;">
						        <div class="modal-header">
						          <button type="button" class="close" data-dismiss="modal">&times;</button>
						          <h4 class="modal-title">View Directory <%= obj.getDirName() %></h4>
						        </div>
						        <div class="modal-body">
			
						<form action="<%=request.getContextPath()%>/controller" method="post" enctype="multipart/form-data">
						   	<div class="row">
							      <!-- left column -->
							      <div class="col-md-3">
								 	  <h6>Upload File</h6>
	          							<input type="file" class="form-control" name="file">
						        </div>
						        <div class="col-md-3">
			        			 <input type="hidden" name="actionCode" value="uploadFile" >
  	          					 <input type="hidden" name="parentDir1" value="<%=obj.getDirectoryId()%>">
  	          					
  	          						<button type="submit" class="btn btn-info">Upload</button>
							   	</div>
						        </div>
						</form>					        
						
						
						          <table class="table">
										<thead style="text-align: center;">
											<td>File Name</td>
											<td>File Id</td>
											<td>Owner Id</td>
											<td>Uploaded Date</td>
											<td>Download</td>
										</thead>
										<tbody>
										<%if(request.getAttribute("subEmployeeDirectoryFilesList")!=null)
											{
											List<Files> subEmployeeDirectoryFilesList=(List<Files>)request.getAttribute("subEmployeeDirectoryFilesList");
											for(Files file:subEmployeeDirectoryFilesList)
											{
												if(file.getParentDir()== obj.getDirectoryId())
												{
											%>
											<tr>
								<form action="<%=request.getContextPath()%>/controller" method="post">
										<input type="hidden" name="actionCode" value="download" >
										<input type="hidden" name="fileId" value="<%=file.getFileId() %>" >
										
											<td>
												<%= file.getFileName() %>
											</td>
											<td>
												<%= file.getFileId()%>
											</td>
											<td>
												<%= file.getOwnerId() %>
											</td>
											<td>
												<%= file.getDate() %>
											</td>
											<td>
												<button type="submit" class="btn btn-info">Download</button>
							   				</td>
				    				</form>
						    				</tr>
											<%}
											}
											} %>
										</tbody>
									</table>
						        </div>
						        <div class="modal-footer">
						          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						        </div>
						      </div>
						    </div>
						  </div>
				
					</td>
					
					
				</tr>
			</form>
			<%}
		%>	</tbody>
		</table>
		<%} 
		}%>
	</div>
	
	
    <!-- /.container -->
          
    <!-- jQuery Version 1.11.1 -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
    
    <!-- datepicker start  -->
<!-- jQuery 2.1.4 -->
    <script src="<%= request.getContextPath()%>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script>
	  $(document).ready(function () {
          $('.directories').addClass('active');
         });
	</script>

</body>

</html>
