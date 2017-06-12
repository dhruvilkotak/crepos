package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.service.Services;
import com.model.*;

/**
 * Servlet implementation class Controller1
 */
@WebServlet("/Controller1")
public class Controller1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	JSONObject json      = new JSONObject();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Services service=new Services();
	     SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
	      
		  Date d1 = null,d2 = null;
		try {
			d1 = ft.parse("03/05/2017");
			  d2=ft.parse("03/07/2017") ;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         
             long diff = d2.getTime() - d1.getTime();
              long diff11= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
              	System.out.println("diff:"+diff11);
    
		List<Employee> employeeList=service.doGetEmployeeList();
		for(Employee f:employeeList)
		{
			out.println("id:"+f.getEmployeeId()+"  parent:"+f.getAssignedManager());
		}
		try {
			json.put("final", printEmployee(-1,employeeList));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
	}
	public JSONArray printEmployee(int id,List<Employee> employeeList)
	{
		List<Employee> a=getSubEmployee(id,employeeList);
		for(Employee f:a)
		{
			System.out.println("id:"+f.getEmployeeId()+"  parent:"+f.getAssignedManager());
		}
		JSONArray ja = new JSONArray();
		
		JSONObject file1Json= new JSONObject();
		
		if (a.size()==0)
		{
			
			return ja.put(getNullObject());
		}
		JSONObject dir1Json= new JSONObject();
		
		try
		{
			for(Employee obj: a)
			{
				JSONObject dirJson= new JSONObject();
				dirJson.put("name",obj.getEmployeeId());
				dirJson.put("id", obj.getEmployeeId());
				dirJson.put("parentId", obj.getAssignedManager());
				JSONArray jb=printEmployee(obj.getEmployeeId(),employeeList);
				dirJson.put("files", jb);
				ja.put(dirJson);
			}
			
		}
		
		catch(Exception e)
		{
			return null;
		}
		return ja;
		
	}

	private List<Employee> getSubEmployee(int managerId,List<Employee> employeeList) {
		// TODO Auto-generated method stub
		List<Employee> a=new ArrayList<Employee>();
		for(Employee obj:employeeList )
		{
			if(obj.getAssignedManager()==managerId && obj.getEmployeeId()!=managerId)
			{
				a.add(obj);
			}
		}
		return a;
	}
	private JSONObject getNullObject() {
		// TODO Auto-generated method stub
		JSONObject obj=new JSONObject();
		return obj;
	}

}
