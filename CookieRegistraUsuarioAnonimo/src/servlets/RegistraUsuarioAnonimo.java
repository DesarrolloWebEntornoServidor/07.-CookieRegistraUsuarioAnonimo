package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RegistraUsuarioAnonimo")
public class RegistraUsuarioAnonimo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String mensaje;
        // Recepción de cookies
        mensaje = recibeCookie(request, response);

        //Salida
        generarPagina(out, mensaje);
    }

	private String recibeCookie(HttpServletRequest request,
			HttpServletResponse response) {
		String mensaje = "";
		Cookie[] listaCookies = request.getCookies();        
        // se comprueba si el usuario tiene una cookie uid

        if (listaCookies != null) {  // se han recibido Cookies desde el cliente
            int longitud = 0;
        	longitud = listaCookies.length;
            for (int i = 0; i < longitud; i++) {
                if (listaCookies[i].getName().equals("uid")) {
                    mensaje = "Vaya, tu cara me suena..., te conozco como " +  listaCookies[i].getValue() + " <br />";  // existe una cookie  uid
                }
            }
            if (mensaje.equals("")) {  // no se encontró la cookie uid
            	mensaje = "Vaya, no te conocía... pero te recordaré";
            	Random r = new Random();
                int aleatorio = r.nextInt(10000) + 1;  // se genera un hash para identificar al usuario 
            	Cookie laCookie = new Cookie("uid", Integer.toString(aleatorio));
            	response.addCookie(laCookie);  //Añadir las cookies a la respuesta
            }
        } else {  // no se recibieron cookies
        	mensaje = "Vaya, no te conocía... pero te recordaré";
        	Random r = new Random();
            int aleatorio = r.nextInt(10000) + 1; // se genera un hash para identificar al usuario 
            Cookie laCookie = new Cookie("uid", Integer.toString(aleatorio));
        	response.addCookie(laCookie);  //Añadir las cookies a la respuesta
        }
		return mensaje;
	}

	private void generarPagina(PrintWriter out, String mensaje) {
		out.println("<html>");
        out.println("<head>");
        out.println("	<meta charset=\"utf-8\" />");
        out.println("	<title>Registra Usuario Anónimo</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Registra Usuario Anónimo</h1>");
        out.println(mensaje);            
        out.println("</body>");
        out.println("</html>");
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    
}