package ITI.projet.mpb.controllers.home;

import ITI.projet.mpb.services.HomeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookmaker")
public class BookmakerServlet extends GenericHomeServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idBook=null;
        String type=req.getParameter("type");
        try{
            idBook=Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException nfe){
            //redirection servlet 404
            resp.sendRedirect("/404");
        }

        //Partie Appel du service
        try{
            //appeler a partir du service l'article
            String bookName= HomeService.getInstance().getBookmakerName(idBook,type);
            if ("".equals(bookName)){
                resp.sendRedirect("/404");
            }else{
                //Partie Thymeleaf
                WebContext context = new WebContext(req, resp, req.getServletContext());
                //On ajoute ici les éléments du context
                context.setVariable("bookName", bookName);
                context.setVariable("type", type);
                TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
                templateEngine.process("bookmaker", context, resp.getWriter());
            }
        } catch (IllegalArgumentException iae){
            //redirection servlet 404
            resp.sendRedirect("/404");
        }
    }
}