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

@WebServlet("/bala")
public class Balance extends HttpServlet {
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
            ps = con.prepareStatement("SELECT * FROM bankthird WHERE accno = ?");
            ps.setInt(1, acc);

           
            ResultSet rs = ps.executeQuery();
            
           
            if (rs.next()) {
                
                bal = rs.getInt(2);    
                
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
                		+"          span{\r\n"
                		+"              color:red}"
                		+ "        .login{\r\n"
                		+ "            background-color: white;\r\n"
                		+ "            border: 1px solid white;\r\n"
                		+ "            margin: auto;\r\n"
                		+ "            margin-left: 200px;\r\n"
                		+ "            margin-right: 200px;\r\n"
                		+ "            width: 500px;\r\n"
                		+ "            height: 400px;\r\n"
                		+ "            margin-top: 100px;\r\n"
                		+ "            align-items: center;\r\n"
                		+ "            text-align: center;\r\n"
                		+ "            margin-left: 500px;\r\n"
                		+ "            \r\n"
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
                		+ "        h3{\r\n"
                		+ "            color: brown;\r\n"
                		+ "            text-align: center;\r\n"
                		+ "        }\r\n"
                		+ "        #input{\r\n"
                		+ "            font-size: 20px;\r\n"
                		+ "            margin-right: 200px;\r\n"
                		+ "        }\r\n"
                		+ "        input[password]{\r\n"
                		+ "            font-size: 30px;\r\n"
                		+ "        }\r\n"
                		+ "    </style>\r\n"
                		+ "</head>\r\n"
                		+ "<body onload=\"getMeassage()\">\r\n"
                		+ "    <div class=\"login\">\r\n"
                		+ "        <h2>ATM MANAGEMENT SYSTEM</h2>\r\n"
                		+ "        <div class=\"log1\">\r\n"
                		+ "            <h3>BALANCE</h3>\r\n"
                		+ "            <label>ACC NO: </label>\r\n"
                		+ "            <span id=\"input\">"+ acc+"</span><br><br>\r\n"
                		+ "            <label>Balance:</label>\r\n"
                		+ "            <span id=\"input\">"+bal+"</span><br><br>\r\n"
                		+ "        </div>\r\n"
                		+ "        <a href=\"login.html\">LOGIN</a><nr><br><br>\r\n"
                		+ "        <a href=\"main.html\">BACK</a>\r\n"
                		+ "        \r\n"
                		+ "    </div>\r\n"
                		+ "</body>\r\n"
                		+ "</html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body><p>Error: " + e.getMessage() + "</p></body></html>");
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
