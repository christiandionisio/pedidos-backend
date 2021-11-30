package com.cdionisio.pedidos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.service.interfaces.IClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
@PreAuthorize("hasRole('ADMIN')")
public class ClienteController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);
	
	@Autowired
	private IClienteService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Cliente>>> listar() {
		Flux<Cliente> fluxClientes = service.list();
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fluxClientes));
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public Mono<ResponseEntity<Cliente>> getById(@PathVariable String id) {
		return service.getById(id)
				.flatMap( response -> Mono.just(new ResponseEntity<>(response, HttpStatus.OK)))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Void>> insertar(@Valid @RequestBody Cliente cliente) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
		LOGGER.info("Password encoded: {}", cliente.getPassword());
		return service.insert(cliente)
				.flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
	}
	
	@PutMapping
	public Mono<ResponseEntity<Void>> actualizar(@Valid @RequestBody Cliente cliente) {
		Mono<Cliente> clienteDB = service.getById(cliente.getId());
		
		return clienteDB.flatMap(res -> service.update(cliente))
				.map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
		return service.getById(id)
				.flatMap(res -> service.delete(res.getId())
								.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ExceptionHandler
	public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
		Map<String, Object> mapResponse = new HashMap<>();
		mapResponse.put("mensaje", "El correo ya existe");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(mapResponse);
	}

}
