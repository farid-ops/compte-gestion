package com.snowden.config.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CustomAuthenticationProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                        BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (this.bCryptPasswordEncoder.matches(userDetails.getPassword(), password)){
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("Something went wrong");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
