package ITI.projet.mpb.controller.app;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/disconnect")
public class DeconnexionServlet extends GenericAppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //On désactivera ici la session

        //On redirige vers l'accueil
        resp.sendRedirect("/");


    }



}
