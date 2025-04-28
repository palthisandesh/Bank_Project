package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/sigup")
public class Sigup extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		res.setContentType("text/html");
		String accno=req.getParameter("acc");
		int acc=Integer.parseInt(accno);
		String fname=req.getParameter("fname");
		String name=req.getParameter("name");
		String address=req.getParameter("add");
		int pin=Integer.parseInt(req.getParameter("pin"));
		String option=req.getParameter("opt");
		String occ=req.getParameter("occ");
		int phone=Integer.parseInt(req.getParameter("phone"));
		String dob=req.getParameter("dob");
		LocalDate current=LocalDate.now();
		String a=current.toString();
		PrintWriter out=res.getWriter();
		int balnce=0;
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement ps2=null;
		PreparedStatement ps1=null;
		if(accno.isEmpty() || fname.isEmpty() || name.isEmpty() || address.isEmpty() || pin==0 || option.isEmpty() || occ.isEmpty() || phone==0 || dob.isEmpty()) {
			out.println("<script>");
    		out.println("alert('all field are required')");
    		out.println("window.history.back();");
    		out.println("</script>");
		}
		else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
		        if(acc!=0 && fname!=null && name!=null && address!=null && pin!=0 && option!=null && phone!=0 && dob!=null) {
		        	ps=con.prepareStatement("insert into banksecond values(?,?,?,?,?,?,?,?,?)");
			        ps.setInt(1, acc);
			        ps.setString(2,name);
			        ps.setString(3,fname);
			        ps.setString(4,address);
			        ps.setInt(5,pin);
			        ps.setString(6,option);
			        ps.setString(7,occ);
			        ps.setInt(8,phone);
			        ps.setString(9,dob);
			        ps1=con.prepareStatement("insert into bankfirst values(?,?,'success',0,?)");
			        ps1.setInt(1,acc);
			        ps1.setInt(2,pin);
			        ps1.setString(3,a);
			        ps.executeUpdate();		    
			        ps1.executeUpdate();
			        ps2=con.prepareStatement("insert into bankthird values(?,?)");
			        ps2.setInt(1, acc);
			        ps2.setInt(2, balnce);
			        ps2.executeUpdate();
			        out.println("<script>");
		    		out.println("alert('ACCOUNT SUCCESSFULLY CREATED')");
		    		out.println("window.location('login.html');");
		    		out.println("</script>");
		        }
		        else {
		        	out.println("<script>");
		    		out.println("alert('PLEASE FILL ALL DETAILS')");
		    		out.println("window.location('sigup.html');");
		    		out.println("</script>");
		        }
			    
		        }
			catch(Exception ex) {
			    System.out.println(ex);
			    }
			finally {
				try {
					if(ps!=null) {
						con.close();
					}
				    if(ps1!=null) {
					    con.close();
				    }
			
				   if(con!=null) {
					    con.close();
			     	}
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	}

}
