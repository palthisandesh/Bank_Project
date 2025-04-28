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
@WebServlet("/deposit")
public class Deposite extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int amount=Integer.parseInt(req.getParameter("amount"));
		LocalDate cu=LocalDate.now();
		String date=cu.toString();
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		PreparedStatement ps3=null;
		ServletContext c=getServletContext();
		Integer acc=(Integer)c.getAttribute("acc");
		//int acc=Integer.parseInt(ac);
		Integer pwd=(Integer)c.getAttribute("pwd");
		//int pwd=Integer.parseInt(pw);
		String s="deposit";
		if(acc ==0 || pwd ==0) {
			throw new NullPointerException("ckci");
		}
		if(amount<=0) {
			out.println("<script>");
    		out.println("alert('please enter amount')");
    		out.println("window.history.back();");
    		out.println("</script>");
		}
		else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
		        ps=con.prepareStatement("insert into bankfirst values(?,?,?,?,?)");
			    ps1=con.prepareStatement("update bankthird set bal=bal+? where accno=?");
		        ps3=con.prepareStatement("select bal from bankthird where accno=?");
		        ps3.setInt(1,acc);
		        ps1.setInt(1,amount);
		        ps1.setInt(2,acc);
		        ps1.executeUpdate();
		        int sa=0;
		        ResultSet rs=ps3.executeQuery();
		        if(rs.next()) {
		        	sa=rs.getInt("bal");
		        }
			    ps.setInt(1, acc);
		        ps.setInt(2,pwd);
		        ps.setString(3,s);
		        ps.setInt(4,amount);
		        ps.setString(5,date);
		        ps.executeUpdate();
		        out.println("<script>");
	    		out.println("alert('amount update successfully')");
	    		out.println("window.location('main.html');");
	    		out.println("</script>");
			}
			catch(Exception e) {
				System.out.print(e);
			}
		}
	}
	
	

}
