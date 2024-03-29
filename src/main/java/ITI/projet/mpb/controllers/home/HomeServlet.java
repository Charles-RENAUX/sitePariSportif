package ITI.projet.mpb.controllers.home;


import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class HomeServlet extends GenericHomeServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            WebContext context = new WebContext(req, resp, req.getServletContext());
            TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
            templateEngine.process("index", context, resp.getWriter());
        }
    }
