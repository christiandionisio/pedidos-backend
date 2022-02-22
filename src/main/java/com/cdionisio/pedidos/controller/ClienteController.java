package com.cdionisio.pedidos.controller;

import com.cdionisio.pedidos.dtos.ClienteDTO;
import com.cdionisio.pedidos.dtos.RegistroClienteDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

	ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping
	public Mono<ResponseEntity<Flux<ClienteDTO>>> listar() {
		LOGGER.info("Init listarClientes");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Flux<ClienteDTO> fluxClientes = service.list().map(cliente -> modelMapper.map(cliente, ClienteDTO.class));

		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fluxClientes));
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public Mono<ResponseEntity<ClienteDTO>> getById(@PathVariable String id) {
		LOGGER.info("Init getById");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return service.getById(id)
				.flatMap( response ->
						Mono.just(new ResponseEntity<>(modelMapper.map(response, ClienteDTO.class), HttpStatus.OK))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Void>> insertar(@Valid @RequestBody RegistroClienteDTO cliente) {
		LOGGER.info("Init insertarCliente");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return service.insert(modelMapper.map(cliente, Cliente.class))
				.flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
	}
	
	@PutMapping
	public Mono<ResponseEntity<Void>> actualizar(@Valid @RequestBody ClienteDTO cliente) {
		LOGGER.info("Init actualizarCliente");

		LOGGER.info("Busqueda Cliente");
		Mono<Cliente> clienteDB = service.getById(cliente.getId());

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return clienteDB.flatMap(res -> service.update(modelMapper.map(cliente, Cliente.class)))
				.map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
		LOGGER.info("Init eliminarCliente");
		return service.getById(id)
				.flatMap(res -> service.delete(res.getId())
								.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ExceptionHandler
	public Mono<ResponseEntity<Map<String, Object>>> handleDuplicateKeyException(DuplicateKeyException ex) {
		LOGGER.error("Exception {}", ex.getMessage());
		Map<String, Object> mapResponse = new HashMap<>();
		mapResponse.put("mensaje", "El correo ya existe");
		return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(mapResponse));
	}

	@ExceptionHandler
	public Mono<ResponseEntity<Map<String, Object>>> handleExpiredJwtException(ExpiredJwtException ex) {
		LOGGER.error("Exception {}", ex.getMessage());
		Map<String, Object> mapResponse = new HashMap<>();
		mapResponse.put("mensaje", "Sesion expirada");
		return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapResponse));
	}

}
