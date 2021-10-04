package com.snowden.config.token;

import com.snowden.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JWTRequestFilter(JWTUtils jwtUtils,
                            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService){
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {
        String authorizationRequest = request.getHeader(SecurityConstants.HEADER);

        String username = null;
        String jwt = null;

        if (authorizationRequest != null && authorizationRequest.startsWith(SecurityConstants.TOKEN_PREFIX))
        {
            jwt = authorizationRequest.substring(7);
            username = this.jwtUtils.extractSubject(jwt);
        }

        if (username != null && SecurityContextHolder.getContext() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            this.jwtUtils.validateToken(jwt, userDetails);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println(username+" this user is authenticated");
        }
        filterChain.doFilter(request, response);
    }
}
