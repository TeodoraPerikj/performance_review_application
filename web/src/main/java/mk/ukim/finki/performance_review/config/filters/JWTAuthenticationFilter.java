package mk.ukim.finki.performance_review.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.performance_review.config.JwtAuthConstants;
import mk.ukim.finki.performance_review.model.User;
import mk.ukim.finki.performance_review.model.dto.UserDetailsDto;
import mk.ukim.finki.performance_review.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.performance_review.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User creds = null;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (creds ==null) {
            return super.attemptAuthentication(request, response);
//            throw new InvalidUserCredentialsException();
        }
        UserDetails userDetails = userService.loadUserByUsername(creds.getUsername());
        if (!passwordEncoder.matches(creds.getPassword(),userDetails.getPassword())) {
            throw new PasswordsDoNotMatchException();
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(),creds.getPassword(),userDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        User userDetails = (User) authResult.getPrincipal();
//        String token = JWT.create()
//                .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(userDetails)))
//                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtAuthConstants.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes()));
//        response.addHeader(JwtAuthConstants.HEADER_STRING,JwtAuthConstants.TOKEN_PREFIX+token);
//        response.getWriter().append(token);
        this.generateJwt(response, authResult);
        super.successfulAuthentication(request, response, chain, authResult);

    }

    public String generateJwt(HttpServletResponse response, Authentication authResult) throws JsonProcessingException {
        User userDetails = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(new ObjectMapper().writeValueAsString(UserDetailsDto.of(userDetails)))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes()));
        response.addHeader(JwtAuthConstants.HEADER_STRING, JwtAuthConstants.TOKEN_PREFIX + token);
        return JwtAuthConstants.TOKEN_PREFIX + token;
    }

}

