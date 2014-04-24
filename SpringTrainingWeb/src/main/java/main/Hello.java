package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try {
			out.println();
			out.println("<html><title></title><body>Hello</body></html>");
		} finally {
			out.close();
		}
	}
}