package org.ikea;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateTokenServlet extends HttpServlet {
	private static String addItemPage = "/jsp/addItem.jsp";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("token");
		String password = request.getParameter("p");

		request.setAttribute("USER", username);
		request.setAttribute("PASSWORD", password);

		ServletContext context = getServletContext();

		System.out.println("Redirecting to" + addItemPage);
		RequestDispatcher dispatcher = context.getRequestDispatcher(addItemPage);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
