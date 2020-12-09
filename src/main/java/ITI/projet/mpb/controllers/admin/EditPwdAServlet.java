package ITI.projet.mpb.controllers.admin;

import ITI.projet.mpb.controllers.app.GenericAppServlet;
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

@WebServlet("/admin/editpwd")
public class EditPwdAServlet extends GenericAdminServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            WebContext context = new WebContext(req, resp, req.getServletContext());
            if (req.getSession().getAttribute("errorMsg")!=null){
                context.setVariable("errorMsg", req.getSession().getAttribute("errorMsg"));
                req.getSession().setAttribute("errorMsg", null);}
        if (req.getSession().getAttribute("succesMsg")!=null){
            context.setVariable("succesMsg", req.getSession().getAttribute("succesMsg"));
            req.getSession().setAttribute("succesMsg", null);}


        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
            templateEngine.process("editpwd", context, resp.getWriter());
}
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
            String pseudo= (String) req.getSession().getAttribute("user");
        if (!req.getParameter("pwd1").equals(req.getParameter("pwd2"))){
            req.getSession().setAttribute("errorMsg","Les deux mots de passe ne correspondent pas");
            resp.sendRedirect("/admin/editpwd");
        }else if(req.getParameter("pwd1").length()<8){
            req.getSession().setAttribute("errorMsg","Le mot de passe doit faire 8 caracteres au minimum");
            resp.sendRedirect("/admin/editpwd");
        }

        else{
            try{
                Client client= ClientService.getInstance().getClientViaPseudo(pseudo);
                if(MotDePasseServiceHash.validerMotDePasse(req.getParameter("pwdOld"),client.getMotDePasse())) {
                    client.setMotDePasse(MotDePasseServiceHash.genererMotDePasse(req.getParameter("pwd1")));
                    ClientService.getInstance().editClient(client);
                    req.getSession().setAttribute("succesMsg","Mot de Passe mis à jour");
                }else{
                    req.getSession().setAttribute("errorMsg","L'ancien mot de passe est incorrect");
                }
                resp.sendRedirect("/admin/editpwd");

                }catch (Exception e){
                req.getSession().setAttribute("errorMsg","Une erreur de session est survenue");
                resp.sendRedirect("/admin/editpwd");
            }

        }
    }
}
