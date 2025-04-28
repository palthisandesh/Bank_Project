package com.project;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/main")
public class Main extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException {
		res.setContentType("text/html");
		String deposit=req.getParameter("action");
		if("deposite".equals(deposit)) {
			RequestDispatcher re=req.getRequestDispatcher("deposite.html");
			re.forward(req, res);
		}
		if("Fastcash".equals(deposit)) {
			RequestDispatcher re=req.getRequestDispatcher("fastcash.html");
			re.forward(req, res);
		}
		if("changepin".equals(deposit)) {
			RequestDispatcher re=req.getRequestDispatcher("change.html");
			re.forward(req, res);
		}
		if("withdraw".equals(deposit)) {
			RequestDispatcher re=req.getRequestDispatcher("withdraw.html");
			re.forward(req, res);
		}
		if("mini".equals(deposit)) {
			RequestDispatcher re=req.getRequestDispatcher("mini.html");
			re.forward(req, res);
		}
		if("balance".equals(deposit)) {
			RequestDispatcher re=req.getRequestDispatcher("balance.html");
			re.forward(req, res);
			//res.sendRedirect("Balance.java");
		}
	}
		

}
