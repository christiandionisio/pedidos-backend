package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.service.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.repo.IClienteRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	
	@Autowired
	private IClienteRepo repo;

	@Override
	public Flux<Cliente> list() {
		return repo.findAll();
	}

	@Override
	public Mono<Cliente> getById(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Cliente> insert(Cliente cliente) {
		return repo.save(cliente);
	}

	@Override
	public Mono<Cliente> update(Cliente cliente) {
		return repo.save(cliente);
	}

	@Override
	public Mono<Void> delete(String id) {
		return repo.deleteById(id);
	}

	
}
