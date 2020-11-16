package ITI.projet.mpb.controllers.home;

import ITI.projet.mpb.services.HomeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/beginner")
public class BeginnerServlet extends GenericHomeServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idBeginner=null;
        try{
            idBeginner=Integer.parseInt(req.getParameter("id"));
            try{
                String articleName= HomeService.getInstance().getBeginnerName(idBeginner);
                if ("".equals(articleName)){
                    resp.sendRedirect("/404");
                }else {
                    WebContext context = new WebContext(req, resp, req.getServletContext());
                    context.setVariable("name", articleName);
                    TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
                    templateEngine.process("beginner", context, resp.getWriter());
                }
            } catch (IllegalArgumentException iae){
                resp.sendRedirect("/404");
            }
        }catch (NumberFormatException nfe){
            resp.sendRedirect("/404");
        }

    }


}
