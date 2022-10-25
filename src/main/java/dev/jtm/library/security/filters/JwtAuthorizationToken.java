package dev.jtm.library.security.filters;

import dev.jtm.library.security.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static dev.jtm.library.security.utils.constants.JavaConstant.OPTIONS_HTTP_METHOD;
import static dev.jtm.library.security.utils.constants.JwtConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Component
@AllArgsConstructor
public class JwtAuthorizationToken extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)){
            response.setStatus(OK.value());
        } else {
            String authorizeHeader = request.getHeader(AUTHORIZATION);
            if (authorizeHeader == null || !authorizeHeader.startsWith(TOKEN_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizeHeader.substring(TOKEN_PREFIX.length());
            String username = jwtUtils.getSubject(token);

            if (jwtUtils.isValidToken(username, token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities = jwtUtils.getAuthorities(token);
                Authentication authentication = jwtUtils.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                SecurityContextHolder.clearContext();
                //SecurityContextHolder.clearContext();
            }
        }
        if(request.isRequestedSessionIdValid()){
            return ;
        }

        filterChain.doFilter(request, response);
    }
}
