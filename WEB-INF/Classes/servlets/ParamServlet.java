package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ParamServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Obtenemos un objeto Print Writer para enviar respuesta
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		pw.println("<HTML><HEAD><TITLE>Leyendo parámetros</TITLE></HEAD>");
		pw.println("<BODY BGCOLOR=\"#CCBBAA\">");
		pw.println("<H2>Leyendo parámetros desde un formulario html</H2><P>");
		pw.println("<UL>\n");
		pw.println("Te llamas " + req.getParameter("NOM") + "<BR>");
		pw.println("y tienes "  + req.getParameter("EDA") + " años<BR>");
		pw.println("</BODY></HTML>");
		pw.close();
	}
} 