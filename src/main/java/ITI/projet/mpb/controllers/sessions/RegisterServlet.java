package ITI.projet.mpb.controllers.sessions;

import ITI.projet.mpb.controllers.app.GenericAppServlet;
import ITI.projet.mpb.exceptions.ClientAlreadyException;
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
		if (req.getSession().getAttribute("errorMsg") != null) {
			context.setVariable("errorMsg", req.getSession().getAttribute("errorMsg"));
			req.getSession().setAttribute("errorMsg", null);
		}
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
		System.out.println(req.getParameter("mdp"));
		if (req.getParameter("mdp").length() < 8) {
			req.getSession().setAttribute("errorMsg", "Le mot de passe doit avoir 8 caractères au moins");
			resp.sendRedirect("/register");
		} else {
			boolean Confirmation = MotDePasseServiceHash.validerMotDePasse(req.getParameter("mdp2"),Mdp);
			if (Confirmation) {
				try {
					Client newClient = new Client(Nom, Prenom, Mail, Pseudo, Mdp, 1);
					ClientService.getInstance().addClient(newClient);
					// si creation OK on redirige vers la connexion avec un msg succes
					req.getSession().setAttribute("succesMsg", "Inscription Réussie. Merci de vous connecter");
					resp.sendRedirect("/login");
				} catch (ClientAlreadyException cae) {
					//Si un client possède deja un attribut fournit dans le formulaire de connexion
					req.getSession().setAttribute("errorMsg", cae.getMessage());
					resp.sendRedirect("/register");
				} catch (IllegalArgumentException iae) {
					// Si erreur on ajoute le message d'erreur dans la session et on redirige sur la page de creation
					req.getSession().setAttribute("errorMsg", iae.getMessage());
					resp.sendRedirect("/register");}


			} else {
				req.getSession().setAttribute("errorMsg", "Les deux mot de passes ne sont pas identiques");
				resp.sendRedirect("/register");
			}
		}
	}
}
