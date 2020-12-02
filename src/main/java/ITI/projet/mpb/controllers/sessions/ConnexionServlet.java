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
    	try{
            System.out.println("ok");
            Client client = ClientService.getInstance().getClientViaPseudo(req.getParameter("pseudo"));
            if(MotDePasseServiceHash.validerMotDePasse(req.getParameter("mdp"), client.getMotDePasse() ))
            {
                HttpSession session = req.getSession();
                session.setAttribute("user", req.getParameter("pseudo"));
                if (client.getidRole()==2){
                    session.setAttribute("admin",0);
                    resp.sendRedirect("/admin/");
                }else{
                    resp.sendRedirect("/app/");
                }

            }else{
                resp.sendRedirect("/papa1");
            }
    	} catch (Exception e){
                resp.sendRedirect("/papa2");
            }
    	
    }
}
