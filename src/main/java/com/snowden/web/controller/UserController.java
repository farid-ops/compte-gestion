package com.snowden.web.controller;
import com.snowden.core.model.Client;
import com.snowden.config.token.JWTUtils;
import com.snowden.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserController(@Qualifier("userDetailsServiceImpl")UserDetailsService userDetailsService,
                          JWTUtils jwtUtils,
                          AuthenticationManager authenticationManager){
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authentication(@RequestBody Client client) throws Exception{
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(client.getUsername());
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(client.getUsername(), client.getPassword()));
        }catch (BadCredentialsException e){
            throw new RuntimeException("invalid username or password ",e);
        }
        String jwt = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(SecurityConstants.TOKEN_PREFIX+jwt);
    }
}
