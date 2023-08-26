package com.reg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/lo")
public class log extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
		
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session = request.getSession();
		String url = "jdbc:mysql://localhost:3306/abc?useSSL=false";
	    String user = "root";
	    String password = "parthamvj";
	    Connection con=null;
		PreparedStatement ps=null;
		RequestDispatcher rs1= null;
		if(uemail==null||uemail.equals(""))
		{
			request.setAttribute("status","invalidEmail");
			rs1=request.getRequestDispatcher("login.jsp");
			 rs1.forward(request, response);
		}
		if(upwd==null||upwd.equals(""))
		{
			request.setAttribute("status","invalidPassword");
			rs1=request.getRequestDispatcher("login.jsp");
			 rs1.forward(request, response);
		}
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(url, user, password);
		     ps=con.prepareStatement("select * from users where uemail=? and upwd=?");
		      ps.setString(1, uemail);
		      ps.setString(2, upwd);
		
		      ResultSet rs=ps.executeQuery();
		      if(rs.next())
		      {
		    	  session.setAttribute("name", rs.getString("uname"));
		        rs1= request.getRequestDispatcher("index.jsp");
		      }
		      else
		      {
		    	  request.setAttribute("status", "failed");
		    	  rs1= request.getRequestDispatcher("login.jsp");
		      }
		      rs1.forward(request, response);
		}
		      catch(Exception e)
		      {
		    	  e.printStackTrace();
		      }
		      
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
