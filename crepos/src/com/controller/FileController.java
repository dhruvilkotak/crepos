package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.model.Files;
import com.model.Register;
import com.service.Services;

/**
 * Servlet implementation class FileController
 */
@WebServlet("/fileController")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String actionCode = request.getParameter("actionCode");
		Services services = new Services();
		HttpSession session = request.getSession();
	
		if(ServletFileUpload.isMultipartContent(request))
		{
			actionCode="uploadFile";
			
		}
		if(actionCode.equals("uploadFile"))
		{
			List<FileItem> items;
			Files file=new Files();
			String msg="";
            try {
                items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) 
                {
                    if (item.isFormField())
                    {
                    	if(item.getFieldName().equals("parentDir"))
                    	{
                    	      file.setParentDir(Integer.parseInt(item.getString()));
                  			
                    	}
                    } 
                    else {
                        // Process <input type="file"> here.
                        InputStream fileContent = item.getInputStream();
                        file.setFileName(item.getName());
                        byte[] contents = IOUtils.toByteArray(fileContent);
                        if(contents.length >0)
                        {
	                        Blob blob = null;
	                        try {
	                            blob = new SerialBlob(contents);
	                        } catch (SerialException e) {
	                            e.printStackTrace();
	                        } catch (SQLException e) {
	                            e.printStackTrace();
	                        }
	
	                        file.setFile(blob);
                        }
                    }

                }
				Date date = new Date();
		        SimpleDateFormat ft =  new SimpleDateFormat ("MM/dd/yyyy");
		        file.setDate(ft.format(date).toString() );
                file.setOwnerId((int) request.getSession().getAttribute("employeeId"));
                file.setParentDir(Integer.parseInt(request.getParameter("parentDir1")));
			    if(services.doUploadFile(file))
			    {
			    	msg=msg+ "file uploaded.";
			   
			    }
			    else
			    {
			    	msg=msg+"file can not be uploaded.";
			    }
			    if(msg.equals(""))
				{
					msg=null;
				}
				request.setAttribute("msg", msg);
				Controller c=new Controller();
				c.getDirectoriesFiles(request, response, services);
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Controller c=new Controller();
				c.getDirectoriesFiles(request, response, services);
		
			}


		}
	}

}
