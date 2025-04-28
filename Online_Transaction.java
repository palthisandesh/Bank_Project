package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/withdeaw")
public class With extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        int to=Integer.parseInt(req.getParameter("to"));
        int accno=Integer.parseInt(req.getParameter("no"));
        ServletContext context = getServletContext();
        Integer acc = (Integer) context.getAttribute("acc"); // You must set this attribute somewhere
        Integer pwd=(Integer)context.getAttribute("pwd");
        LocalDate cu=LocalDate.now();
		String date=cu.toString();
        Connection con = null;
        PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		PreparedStatement ps3=null;
		PreparedStatement ps4=null;
		PreparedStatement ps5=null;
		String st="UPI WITHDRAW";
		String sta="UPI DEPOSIT";
		int b=0;
		int c=0;
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
		    ps3=con.prepareStatement("select * from bankthird where accno=? ");
		    ps3.setInt(1, accno);
		    ResultSet rs1=ps3.executeQuery();
		    if(rs1.next()) {
		    	if(b>=to && to>0) {
		    		ps=con.prepareStatement("update bankthird set bal=bal-? where accno=?");
				    ps.setInt(1,to);
				    ps.setInt(2,acc);
				    ps4=con.prepareStatement("update bankthird set bal=bal+? where accno=?");
				    ps4.setInt(1,to);
				    ps4.setInt(2,accno);
				    ps4.executeUpdate();
				    ps1=con.prepareStatement("insert into bankfirst values(?,?,?,?,?)");
				    ps1.setInt(1,acc);
				    ps1.setInt(2,pwd);
				    ps1.setString(3,st);
				    ps1.setInt(4,to);
				    ps1.setString(5,date);
		    		ps1.executeUpdate();
		    		ps.executeUpdate();
		    		ps5=con.prepareStatement("insert into bankfirst values(?,?,?,?,?)");
				    ps5.setInt(1,accno);
				    ps5.setInt(2,pwd);
				    ps5.setString(3,sta);
				    ps5.setInt(4,to);
				    ps5.setString(5,date);
		    		ps5.executeUpdate();
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
		    }else {
		    	
		    	con.rollback();
		    	out.println("<script>");
			    out.println("alert('INVALID ACCOUNT NUMBER')");
		    	out.println("window.location('main.html');");
		    	out.println("</script>"); 
		    }
		}
		catch(Exception e) {
			System.out.println(e);
		}	
			finally {
				try {
					if(ps!=null) {
						ps.close();
					}
					if(con!=null) {
						con.close();
					}
				}
				catch(Exception ex) {
					System.out.println(ex);
				}
				
			}
		}
	

}
