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

        Integer id=null;
        try{
            id=Integer.parseInt(req.getParameter("id"));
        }catch (NumberFormatException nfe){
            resp.sendRedirect("/404");
        }
        try{
            String articleName= HomeService.getInstance().getBeginnerName(id);
            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("name",articleName);
            TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
            templateEngine.process("beginners", context, resp.getWriter());
        } catch (IllegalArgumentException iae){
            resp.sendRedirect("/404");
        }
    }


}
