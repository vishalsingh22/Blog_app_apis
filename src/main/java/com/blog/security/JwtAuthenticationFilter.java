package com.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get token

        String requestToken=request.getHeader("Authorization");


        System.out.println(requestToken);


        String username=null;
        String token=null;
        if(requestToken!=null && requestToken.startsWith("Bearer")){
            token= requestToken.substring(7);
            System.out.println(token);
            try {

                username = this.jwtTokenHelper.getUsernameFromToken(token);


            }catch (IllegalArgumentException e){
                System.out.println("Unable to get Jwt Token");

            } catch (ExpiredJwtException e){
                System.out.println("Jwt Token has Expired");

            } catch (MalformedJwtException e){
                System.out.println("Invalid Jwt Token ");

            }

        }else {
            System.out.println("Jwt Token does not begin with Bearer");
        }
        // after getting token ,now Validate

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails=this.customUserDetailService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token,userDetails)){
                //set the Authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
              usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
                System.out.println("Invalid Jwt Token");
            }
        }else{
            System.out.println("Username is null or Context is null");
        }
            filterChain.doFilter(request,response);
    }
}
