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

@WebServlet("/login")
public class ConnexionServlet extends GenericAppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user")==null) {
            WebContext context = new WebContext(req, resp, req.getServletContext());
            if (req.getSession().getAttribute("errorMsg")!=null){
                context.setVariable("errorMsg", req.getSession().getAttribute("errorMsg"));
                req.getSession().setAttribute("errorMsg", null);
            }else if (req.getSession().getAttribute("succesMsg")!=null) {
                context.setVariable("succesMsg", req.getSession().getAttribute("succesMsg"));
                req.getSession().setAttribute("succesMsg", null);
            }
            TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
            templateEngine.process("login", context, resp.getWriter());
        }else{
            resp.sendRedirect("/app/");}
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
                req.getSession().setAttribute("errorMsg", "Pseudo et/ou mot de passe incorrect");
                resp.sendRedirect("/login");
            }
    	} catch (IllegalArgumentException iae){
                req.getSession().setAttribute("errorMsg", iae.getMessage());
                resp.sendRedirect("/login");
            }catch (Exception e){
                 req.getSession().setAttribute("errorMsg", "Pseudo et/ou mot de passe incorrect");
                 resp.sendRedirect("/login");
        }
    	
    }
}
