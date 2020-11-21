package ITI.projet.mpb.controllers.app;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import ITI.projet.mpb.entitie.Client;
import ITI.projet.mpb.services.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends GenericAppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("register", context, resp.getWriter());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //implementer le traitement du formulaire de contact
    	String Prenom = req.getParameter("prenom");
    	String Nom = req.getParameter("nom");
    	String Mail = req.getParameter("email");
    	String Pseudo = req.getParameter("pseudo");
    	String Mdp = req.getParameter("mdp");
    	boolean Confirmation = req.getParameter("mdp2")==req.getParameter("mdp");
    	
    	if (Confirmation==true)
    	{
    		try {
    			Client newClient = new Client(Nom,Prenom,Mail,Pseudo,Mdp);
    			ClientService.getInstance().addClient(newClient);
    			Client finishClient = ClientService.getInstance().getClient(Pseudo);
    			// si creation ok on affiche le Client qui vient d'etre cree
    			resp.sendRedirect(String.format("client?id=%d", finishClient.getId()));
    		} catch(IllegalArgumentException iae) {
    			// Si erreur on ajoute le message d'erreur dans la session et on redirige sur la page de creation
    			req.getSession().setAttribute("errorMessage", iae.getMessage());
    			resp.sendRedirect("newfilm");
    		}
    	}
    	// verification du pseudo deja pris +erreurs 
    	
    }



}
