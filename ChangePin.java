package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/pin")
public class Changepin extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int pin=Integer.parseInt(req.getParameter("pass"));
		int cpin=Integer.parseInt(req.getParameter("cpass"));
		ServletContext c=getServletContext();
		Integer acc=(Integer)c.getAttribute("acc");
		//int acc=Integer.parseInt(ac);
		Integer pwd=(Integer)c.getAttribute("pwd");
		//int pwd=Integer.parseInt(pw);
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps3=null;
		if(pin != cpin) {
			out.println("<script>");
    		out.println("alert('please enter correct pin not match')");
    		out.println("window.history.back();");
    		out.println("</script>");
		}
		else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
		        ps=con.prepareStatement("update bankfirst set pwd=? where accno=?");
			    ps1=con.prepareStatement("update banksecond set pwd=? where accno=?");
			    ps.setInt(1,pin);
			    ps.setInt(2,acc);
			    ps1.setInt(1,pin);
			    ps1.setInt(2,acc);
			    ps.executeUpdate();
			    ps1.executeUpdate();
			    out.println("<script>");
	    		out.println("alert('successfully changed')");
	    		out.println("window.location('main.html');");
	    		out.println("</script>");
		        
		        
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
			
		}

}
