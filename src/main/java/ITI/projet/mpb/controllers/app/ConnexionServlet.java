package ITI.projet.mpb.controllers.app;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import ITI.projet.mpb.daos.app.ClientDao;
import ITI.projet.mpb.entitie.Client;
import ITI.projet.mpb.services.ClientService;
import ITI.projet.mpb.services.MotDePasseServiceHash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/connect")
public class ConnexionServlet extends GenericAppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("connect", context, resp.getWriter());
        
    }

    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Session de l'utilisateur
    	Client client = ClientService.getInstance().getClient(req.getParameter("pseudo"));
    	if(MotDePasseServiceHash.validerMotDePasse(req.getParameter("mdp"), client.getMotDePasse() ))
    	{
    		HttpSession session = req.getSession();
        	session.setAttribute("user", req.getParameter("pseudo"));
    	}
    	
    }



}
