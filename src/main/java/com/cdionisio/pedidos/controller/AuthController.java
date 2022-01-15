package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.model.Empleado;
import com.cdionisio.pedidos.security.*;
import com.cdionisio.pedidos.service.interfaces.IClienteService;
import com.cdionisio.pedidos.service.interfaces.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEmpleadoService empleadoService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar){
        return clienteService.buscarPorCorreo(ar.getUsername())
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
        return clienteService.insert(cliente)
                .flatMap(res -> {
                    User userDetails = new User(res.getCorreo(), res.getPassword(), true, Arrays.asList(Role.ROLE_USER));
                    String token = jwtUtil.generateToken(userDetails);
                    Date expiracion = jwtUtil.getExpirationDateFromToken(token);
                    return Mono.just(new ResponseEntity<AuthResponse>(new AuthResponse(token, expiracion), HttpStatus.OK));
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PostMapping("/login-employee")
    public Mono<ResponseEntity<?>> loginEmpleado(@RequestBody AuthRequest ar){
        return empleadoService.buscarPorCorreo(ar.getUsername())
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

    @PostMapping("/register-employee")
    public Mono<ResponseEntity<AuthResponse>> registerEmpleado(@RequestBody Empleado empleado){
        return empleadoService.insert(empleado)
                .flatMap(res -> {
                    User userDetails = new User(res.getCorreo(), res.getPassword(), true, res.getRole());
                    String token = jwtUtil.generateToken(userDetails);
                    Date expiracion = jwtUtil.getExpirationDateFromToken(token);
                    return Mono.just(new ResponseEntity<AuthResponse>(new AuthResponse(token, expiracion), HttpStatus.OK));
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @ExceptionHandler
    public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put("mensaje", "El correo ya existe");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mapResponse);
    }
}

