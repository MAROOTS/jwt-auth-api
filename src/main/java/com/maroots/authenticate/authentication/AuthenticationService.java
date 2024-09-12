package com.maroots.authenticate.authentication;

import com.maroots.authenticate.config.JwtService;
import com.maroots.authenticate.user.User;
import com.maroots.authenticate.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse register(User request) {
    var user= new User();
    user.setName(request.getName());
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(request.getRole());
    userRepository.save(user);
    String token = jwtService.generateToken(user,generateExtraClaims(user));
    return new AuthenticationResponse(token);

    }


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        authenticationManager.authenticate(authToken);

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user,generateExtraClaims(user));
        return new AuthenticationResponse(jwt);
    }

    private Map<String,Object> generateExtraClaims(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("role", user.getRole().name());
        return claims;
    }
}
