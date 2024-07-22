package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/main/*"}) /* Intercepta todas as requisições que vierem do projeto ou mapeamento*/
public class FilterAuthentication implements Filter {

    public FilterAuthentication (){}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { /* Inicia os processos ou recursos quando o servidor sobe o projeto */
        Filter.super.init(filterConfig);
    }

    /* Intercepta as requisições e as respostas no sistema */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String loggedUser = (String) session.getAttribute("usuario");

        String urlToAuthenticate = req.getServletPath();

        /* Validar se está logado senão redireciona para tela de login */

        if(loggedUser == null && !urlToAuthenticate.contains("/main/ServletLogin")){

            RequestDispatcher redirect = request.getRequestDispatcher("/index.jsp?url=" + urlToAuthenticate);
            request.setAttribute("msg", "Por favor realize o login!");
            redirect.forward(request,response);

        }else{
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() { /* Encerra os processos quando o servidor é parado */
        Filter.super.destroy();
    }
}
