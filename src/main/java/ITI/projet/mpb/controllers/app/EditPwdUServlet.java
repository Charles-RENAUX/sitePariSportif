package ITI.projet.mpb.controllers.app;

import ITI.projet.mpb.pojos.Client;
import ITI.projet.mpb.services.ClientService;
import ITI.projet.mpb.services.MotDePasseServiceHash;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/app/editpwd")
public class EditPwdAServlet extends GenericAppServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            WebContext context = new WebContext(req, resp, req.getServletContext());
            if (req.getSession().getAttribute("errorMsg")!=null){
                context.setVariable("errorMsg", req.getSession().getAttribute("errorMsg"));
                req.getSession().setAttribute("errorMsg", null);}
            TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
            templateEngine.process("editpwd", context, resp.getWriter());
}
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
            String pseudo= (String) req.getSession().getAttribute("user");
        if (!req.getParameter("pwd1").equals(req.getParameter("pwd2"))){
            req.getSession().setAttribute("errorMsg","Les deux mots de passe ne correspondent pas");
            resp.sendRedirect("/app/editpwd");
        }else{
            try{
                Client client= ClientService.getInstance().getClientViaPseudo(pseudo);
                if(MotDePasseServiceHash.validerMotDePasse(req.getParameter("pwd1"),client.getMotDePasse())) {
                    client.setMotDePasse(MotDePasseServiceHash.genererMotDePasse(req.getParameter("pwd1")));
                    ClientService.getInstance().editClient(client);
                }else{
                    req.getSession().setAttribute("errorMsg","L'ancien mot de passe est incorrect'");
                }
                resp.sendRedirect("/app/editpwd");

                }catch (Exception e){
                req.getSession().setAttribute("errorMsg","Une erreur de session est survenue");
                resp.sendRedirect("/app/editpwd");
            }

        }
    }
}
