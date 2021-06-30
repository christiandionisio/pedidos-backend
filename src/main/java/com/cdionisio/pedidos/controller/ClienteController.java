package com.cdionisio.pedidos.controller;

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

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
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
	public Mono<ResponseEntity<Mono<Cliente>>> getById(@PathVariable String id) {
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.getById(id)));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Mono<Cliente>>> insertar(@RequestBody Cliente cliente) {
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.insert(cliente)));
	}
	
	@PutMapping
	public Mono<ResponseEntity<Mono<Cliente>>> actualizar(@RequestBody Cliente cliente) {
		
		Mono<Cliente> clienteDB = service.getById(cliente.getIdCliente());
		
		if (clienteDB == null) {
			System.out.println("Cliente no registrado en la BD!!");
			return null;
		}
		
		
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.update(cliente)));
		
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
		return service.getById(id)
				.flatMap(res -> service.delete(res.getIdCliente())
								.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
