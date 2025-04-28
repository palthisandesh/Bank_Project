package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/login")
public class Login extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		res.setContentType("text/html");
		String accno=req.getParameter("accno");
		int acc=Integer.parseInt(accno);
		int pwd=Integer.parseInt(req.getParameter("pwd"));
		ServletContext c=getServletContext();
		c.setAttribute("acc", acc);
		c.setAttribute("pwd",pwd);
		PrintWriter out=res.getWriter();
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement p1=null;
		int aa=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	    	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
		    ps=con.prepareStatement("select* from bankfirst where accno=? and pwd=?");
		    ps.setInt(1, acc);
		    ps.setInt(2, pwd);
		    p1=con.prepareStatement("insert into bankthird values(?,?)");
		    p1.setInt(1,acc);
		    p1.setInt(2,aa);
		    ResultSet rs=ps.executeQuery();
		    if(rs.next()) {
		    	int a=rs.getInt(1);
		    	int b=rs.getInt(2);
		    	if(acc==a && pwd==b) {
		    		RequestDispatcher d=req.getRequestDispatcher("main.html");
		    		d.forward(req, res);
		    	}
		    	else {
		    		out.println("<script>");
		    		out.println("alert('invalid acc no or password')");
		    		out.println("window.location='login.html';");
		    		out.println("</script>");
		    	}
		    }
		    else {
	    		out.println("<script>");
	    		out.println("alert('invalid creditials please try again')");
	    		out.println("window.location='login.html';");
	    		out.println("</script>");
	    	}
		    con.close();
		    	
		    }
		catch(Exception e) {
			System.out.println(e);
		
	}
		finally {
			try {
				if(con!=null)
					con.close();
				if(ps!=null || p1!=null)
					ps.close();
				    p1.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	

}
