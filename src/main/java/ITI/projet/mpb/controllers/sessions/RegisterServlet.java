package ITI.projet.mpb.controllers.sessions;

import ITI.projet.mpb.controllers.app.GenericAppServlet;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import ITI.projet.mpb.pojos.Client;
import ITI.projet.mpb.services.ClientService;
import ITI.projet.mpb.services.MotDePasseServiceHash;

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
    	String Mdp = MotDePasseServiceHash.genererMotDePasse(req.getParameter("mdp"));
    	boolean Confirmation = MotDePasseServiceHash.genererMotDePasse(req.getParameter("mdp"))==MotDePasseServiceHash.genererMotDePasse(req.getParameter("mdp2"));
    	
    	if (Confirmation==true)
    	{
    		try {
    			Client newClient = new Client(Nom,Prenom,Mail,Pseudo,Mdp,1);
    			ClientService.getInstance().addClient(newClient);
    			Client finishClient = ClientService.getInstance().getClientViaPseudo(Pseudo);
    			// si creation ok on affiche le Client qui vient d'etre cree
    			resp.sendRedirect(String.format("client?id=%d", finishClient.getId()));
    		} catch(IllegalArgumentException iae) {
    			// Si erreur on ajoute le message d'erreur dans la session et on redirige sur la page de creation
    			req.getSession().setAttribute("errorMessage", iae.getMessage());
    			resp.sendRedirect("newClient");
    		}
    	}
    	// verification du pseudo deja pris +erreurs 
    	
    }



}
