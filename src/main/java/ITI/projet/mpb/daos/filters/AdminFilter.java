package ITI.projet.mpb.daos.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import ITI.projet.mpb.entitie.Client;
import ITI.projet.mpb.services.ClientService;



@WebFilter("/*")
public class AdminFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Mise en place de getRole 
		
		//Client roleClient = ClientService.getInstance().getRole();
		//request.setAttribute("Role", roleClient);
		
		chain.doFilter(request, response);
		
	}
	
	@Override
	public void destroy() {
	}


}
