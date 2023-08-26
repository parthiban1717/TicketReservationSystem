package com.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Reg")
public class RegSign extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String Reupwd=request.getParameter("re pass");
		String umobile=request.getParameter("contact");
		 String url = "jdbc:mysql://localhost:3306/abc?useSSL=false";
	     String user = "root";
	     String password = "parthamvj";
		Connection con=null;
		PreparedStatement ps=null;
		RequestDispatcher rd=null;
		
		if(uname==null||uname.equals(""))
		{
			request.setAttribute("status","invalidname");
			rd=request.getRequestDispatcher("registration.jsp");
			 rd.forward(request, response);
		}
		if(uemail==null||uemail.equals(""))
		{
			request.setAttribute("status","invalidemail");
			rd=request.getRequestDispatcher("registration.jsp");
			 rd.forward(request, response);
		}
		if(upwd==null||upwd.equals(""))
		{
			request.setAttribute("status","invalidupwd");
			rd=request.getRequestDispatcher("registration.jsp");
			 rd.forward(request, response);
		}
		else if(!upwd.equals(Reupwd))
		{
			request.setAttribute("status","invalidconfirmpassword");
			rd=request.getRequestDispatcher("registration.jsp");
			 rd.forward(request, response);
		}
		if(umobile==null||umobile.equals(""))
		{
			request.setAttribute("status","invalidmobile");
			rd=request.getRequestDispatcher("registration.jsp");
			 rd.forward(request, response);
		}  
		else if(umobile.length()>10)
		{
			request.setAttribute("status","invalidmobilelength");
			rd=request.getRequestDispatcher("registration.jsp");
			 rd.forward(request, response);
		}
		
		try
		{
			        Class.forName("com.mysql.cj.jdbc.Driver");
			         con = DriverManager.getConnection(url, user, password);
			         ps = con.prepareStatement("insert into users(uname, upwd,uemail,umobile) values(?,?,?,?)");
			         ps.setString(1,uname);
			         ps.setString(2,upwd);
			         ps.setString(3,uemail);
			         ps.setString(4,umobile);
			         
			         int rowcount =ps.executeUpdate();
			         rd=request.getRequestDispatcher("registration.jsp");
			         if(rowcount>0)
			         {
			        	 request.setAttribute("status", "success");
			        	 
			         }
			         else
			         {
			        	 request.setAttribute("status", "failed");
			         }
			         rd.forward(request,response);
			     } catch (ClassNotFoundException e) {
			         e.printStackTrace();
			     } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		finally
		{
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
