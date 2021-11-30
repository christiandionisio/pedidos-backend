package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.security.*;
import com.cdionisio.pedidos.service.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private IClienteService service;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar){
        return service.buscarPorCorreo(ar.getUsername())
                .map((userDetails) -> {

                    if(BCrypt.checkpw(ar.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails);
                        Date expiracion = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponse(token, expiracion));
                    }else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorLogin("credenciales incorrectas", new Date()));
                    }
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@RequestBody Cliente cliente){
        return service.insert(cliente)
                .flatMap(res -> {
                    User userDetails = new User(res.getCorreo(), res.getPassword(), true, Arrays.asList(Role.ROLE_USER));
                    String token = jwtUtil.generateToken(userDetails);
                    Date expiracion = jwtUtil.getExpirationDateFromToken(token);
                    return Mono.just(new ResponseEntity<AuthResponse>(new AuthResponse(token, expiracion), HttpStatus.OK));
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }
}

