package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClienteService {
	
	Flux<Cliente> list();
	Mono<Cliente> getById(String id);
	Mono<Cliente> insert(Cliente cliente);
	Mono<Cliente> update(Cliente cliente);
	Mono<Void> delete(String id);

}
