package com.example.gym_system.controller;

import com.example.gym_system.domain.user.DataAuthentication;
import com.example.gym_system.domain.user.User;
import com.example.gym_system.infra.security.DataTokenJWT;
import com.example.gym_system.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DataAuthentication data){
            var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var authentication = manager.authenticate(authenticationToken);
            var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
    }

}
