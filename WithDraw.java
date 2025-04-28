package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/cash")
public class Cash extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		String deposit=req.getParameter("action");
		int s=0;
		if("Rs.100".equals(deposit)) {
			s=100;
		}
		if("Rs.1000".equals(deposit)) {
			s=1000;
		}
		if("Rs.5000".equals(deposit)) {
			s=5000;
		}
		if("Rs.500".equals(deposit)) {
			s=500;
		}
		if("RS.2000".equals(deposit)) {
			s=2000;
		}
		if("Rs.10000".equals(deposit)) {
			s=10000;
		}
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		ServletContext c=getServletContext();
		LocalDate cu=LocalDate.now();
		String date=cu.toString();
		Integer acc=(Integer)c.getAttribute("acc");
		//int acc=Integer.parseInt(ac);
		Integer pwd=(Integer)c.getAttribute("pwd");
		//int pwd=Integer.parseInt(pw);
		String sa="Withdraw";
		int b=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	    	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
		    con.setAutoCommit(false);
		    
		    //ps1.executeUpdate();
		    ps2=con.prepareStatement("select * from bankthird where accno=?;");
		    ps2.setInt(1,acc);
		    ResultSet rs=ps2.executeQuery();
		    if(rs.next()) {
		    	b=rs.getInt("bal");
		    }
		    if(b>=s && s>0) {
	    		ps=con.prepareStatement("update bankthird set bal=bal-? where accno=?");
			    ps.setInt(1,s);
			    ps.setInt(2,acc);
			   
			    ps1=con.prepareStatement("insert into bankfirst values(?,?,?,?,?)");
			    ps1.setInt(1,acc);
			    ps1.setInt(2,pwd);
			    ps1.setString(3,sa);
			    ps1.setInt(4,s);
			    ps1.setString(5,date);
	    		ps1.executeUpdate();
	    		ps.executeUpdate();
	    		con.commit();
		    	out.println("<script>");
		    	out.println("alert('amount successfully send')");
	    		out.println("window.location('main.html');");
	    		out.println("</script>");
	    	
	    	}
	    	 else {
			    	
			    	con.rollback();
			    	out.println("<script>");
				    out.println("alert('INSUFFICENT FUNDS')");
			    	out.println("window.location('main.html');");
			    	out.println("</script>");
			    	
			    }
		   
		    
        	
	       
	    }
		catch(Exception e) {
			System.out.println(e);
		}
	}

	

}
