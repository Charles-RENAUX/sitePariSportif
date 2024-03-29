
package ITI.projet.mpb.controllers.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/admin/*")
public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		//Conversion avec les bons objets
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (request.getSession().getAttribute("admin")==null){
			response.sendRedirect("/app/");
		}else{
			chain.doFilter(request,response);
		}
		//Client roleClient = ClientService.getInstance().getRole();
		//request.setAttribute("Role", roleClient);
		
	}
	
	@Override
	public void destroy() {
	}


}
