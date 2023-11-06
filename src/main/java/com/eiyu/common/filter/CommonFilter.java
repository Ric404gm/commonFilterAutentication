package com.eiyu.common.filter;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CommonFilter extends GenericFilterBean  {

    private static final Logger LOGGER =  LoggerFactory.getLogger(CommonFilter.class); 
    private static final String REGEX_HEADER = "Bearer ([a-zA-Z0-9-_.]+)$";

    @Value("${asp.login.defualt.user}") 
	private String secret ;

    @Autowired
    private ContainerTokenUserComponent containerTokenUserComponent;

    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

            LOGGER.info(" *  Start CommonFilter  * ");
            HttpServletRequest requestHttp = (HttpServletRequest) request;
            HttpServletResponse responseHttp = (HttpServletResponse) response;

            Optional<String> optionalHeader = Optional.of( requestHttp.getHeader("Authorization"));
            

            if( !optionalHeader.isPresent())
            {
                
                LOGGER.error(" Ha ocurrido un error al autenticar : ");
                responseHttp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return ;
            }
            
            if(!optionalHeader.get().matches(REGEX_HEADER))
            {

                LOGGER.error(" Ha ocurrido un error al autenticar : ");
                responseHttp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return ;
            }


            try
            {
                Claims tokenClaims =    this.getAllClaimsFromToken(optionalHeader.get().substring(7));
                LOGGER.info( " * Claims Token  *: {} " ,tokenClaims.toString() );
                this.containerTokenUserComponent.setCurrentUser( String.valueOf(tokenClaims.get("current_token_user")));

            }
            catch (JwtException e){
                LOGGER.error(" Ha ocurrido un error al autenticar : ", e);
                responseHttp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return ; 
            }
            catch(Exception e){
                LOGGER.error(" Ha ocurrido un error al autenticar : ", e);
                responseHttp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid token.");
                return ;
            }

            LOGGER.info( " * Autentication  Ok *");
            chain.doFilter(request, response);

    }

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

    
    
}
