package ITI.projet.mpb.controllers.home;

import ITI.projet.mpb.services.HomeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/beginner")
public class BeginnerServlet extends GenericHomeServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameBeginner=req.getParameter("name");
        try {
            List<String> resMap= HomeService.getInstance().getBeginnerName(nameBeginner);
            if (resMap.size()==0) {
                resp.sendRedirect("/404");}
            else{
                String resName=resMap.get(0);
            Integer resInt=Integer.parseInt(resMap.get(1));
            String resTitle=resMap.get(2);
                if ("".equals(resName)) {
                    resp.sendRedirect("/404");}
                else {
                    WebContext context = new WebContext(req, resp, req.getServletContext());
                    context.setVariable("name", resName);
                    context.setVariable("img",resInt);
                    context.setVariable("title",resTitle);
                    TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
                    templateEngine.process("beginner", context, resp.getWriter());
            }}
        } catch (IllegalArgumentException iae) {
            resp.sendRedirect("/405");
        }
    }

}
