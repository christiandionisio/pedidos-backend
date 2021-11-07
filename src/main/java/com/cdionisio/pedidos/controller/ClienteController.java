package com.cdionisio.pedidos.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.service.IClienteService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
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
	public Mono<ResponseEntity<Cliente>> getById(@PathVariable String id) {
		return service.getById(id)
				.flatMap( response -> Mono.just(new ResponseEntity<>(response, HttpStatus.OK)))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Void>> insertar(@Valid @RequestBody Cliente cliente) {
		return service.insert(cliente)
				.flatMap(res -> Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
	}
	
	@PutMapping
	public Mono<ResponseEntity<Void>> actualizar(@Valid @RequestBody Cliente cliente) {
		Mono<Cliente> clienteDB = service.getById(cliente.getIdCliente());
		
		return clienteDB.flatMap(res -> service.update(cliente))
				.map(res -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
		return service.getById(id)
				.flatMap(res -> service.delete(res.getIdCliente())
								.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
