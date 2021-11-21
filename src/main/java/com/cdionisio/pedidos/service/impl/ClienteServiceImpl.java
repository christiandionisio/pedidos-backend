package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.service.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.repo.IClienteRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl extends CrudGenericServiceImpl<Cliente> implements IClienteService {
	
	
	@Autowired
	private IClienteRepo repo;


	@Override
	protected IGenericRepo<Cliente> getRepo() {
		return repo;
	}
}
