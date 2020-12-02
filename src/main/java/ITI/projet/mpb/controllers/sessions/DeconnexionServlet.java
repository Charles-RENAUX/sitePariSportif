package ITI.projet.mpb.controllers.sessions;

import ITI.projet.mpb.controllers.app.GenericAppServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/disconnect")
public class DeconnexionServlet extends GenericAppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //On désactivera ici la session
    	HttpSession session = req.getSession();
    	session.invalidate();
        //On redirige vers l'accueil
        resp.sendRedirect("/");


    }



}
