package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/mini")
public class Mini extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        
        // Assuming acc is set somewhere in the context
        ServletContext context = getServletContext();
        Integer acc = (Integer) context.getAttribute("acc"); // You must set this attribute somewhere

        

        Connection con = null;
        PreparedStatement ps = null;
        int bal = 0;
        int accno = 0;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
            ps = con.prepareStatement("SELECT accno,status,bal,dat FROM bankfirst WHERE accno = ?");
            ps.setInt(1, acc);

           
            ResultSet rs = ps.executeQuery();
            out.println("<!DOCTYPE html>\r\n"
        			+ "<html lang=\"en\">\r\n"
        			+ "<head>\r\n"
        			+ "    <meta charset=\"UTF-8\">\r\n"
        			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
        			+ "    <title>Document</title>\r\n"
        			+ "    <style>\r\n"
        			+ "        body{\r\n"
        			+ "            background-color: aqua;\r\n"
        			+ "            \r\n"
        			+ "\r\n"
        			+ "        }\r\n"
        			+"          "
        			+ "        .login{\r\n"
        			+"             overflow:scroll;\r\n"
        			+"             overflow-x:hidden;\r\n"        			
        			+ "            background-color: white;\r\n"
        			+ "            border: 1px solid white;\r\n"
        			+ "            margin: auto;\r\n"
        			+ "           \r\n"
        			+ "            width: 900px;\r\n"
        			+ "            height: 500px;\r\n"
        			+ "           \r\n"
        			+ "            align-items: center;\r\n"
        			+ "            text-align: center;\r\n"
        			+ "           \r\n"
        			+ "            \r\n"
        			+ "        }\r\n"
        			+ "        table{\r\n"
        			+ "            width: inherit;\r\n"
        			+ "            width: 900px;\r\n"
        			+ "        }\r\n"
        			+ "        \r\n"
        			+ "       \r\n"
        			+ "        h2{\r\n"
        			+ "            background-color: blue;\r\n"
        			+ "            border: 1px solid white;\r\n"
        			+ "            align-items: center;\r\n"
        			+ "            margin: 30px auto;\r\n"
        			+ "            \r\n"
        			+ "\r\n"
        			+ "            text-align: center;\r\n"
        			+ "        }\r\n"
        			+ "       \r\n"
        			+ "        \r\n"
        			+ "    </style>\r\n"
        			+ "</head>\r\n"
        			+ "<body onload=\"getMeassage()\">\r\n"
        			+ "    <div class=\"login\">\r\n"
        			+ "        <h2>MINI STATEMENT</h2>\r\n"
        			+ "        <div class=\"log1\">\r\n"
        			+ "            <table border=\"\">\r\n"
        			+ "                <tr>\r\n"
        			+ "                    <th>ACC NO</th>\r\n"
        			+ "                    <th>STATUS</th>\r\n"
        			+ "                    <th>BALANCE</th>\r\n"
        			+ "                    <th>DATE</th>\r\n"
        			+ "                </tr>\r\n");
        			
            
           
            while(rs.next()) {
            	
            	String b=rs.getString("status");
            	int c=rs.getInt("bal");
            	String d=rs.getString("dat");
            	
                out.println("<tr id='abc'>\r\n"
                		+ "<td>"+acc+"</td>\r\n"
                		+ "<td>"+b+"</td>\r\n"
                		+ "<td>"+c+"</td>\r\n"
                		+ " <td>"+d+"</td>\r\n"
                		+ " </tr>");
            			
            }
            out.println("</table></br></br>\r\n"
            		+ "        </div>\r\n"
            		+ "        <button onclick=\"printStatement()\">PRINT MINI STATEMENT</button><br><br>\r\n"
            		+ "        <a href=\"main.html\">BACK</a>\r\n"
            		+ "        \r\n"
            		+ "        \r\n"
            		+ "    </div>\r\n"
            		+ "</body>\r\n"
            		+ "<script>\r\n"
            		+ "    function printStatement(){\r\n"
            		+ "        window.print();\r\n"
            		+ "    }\r\n"
            		+ "</script>\r\n"
            		+ "</html>");
        } catch (Exception e) {
            e.printStackTrace();
           // out.println("<html><body><p>Error: " + e.getMessage() + "</p></body></html>");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

}
