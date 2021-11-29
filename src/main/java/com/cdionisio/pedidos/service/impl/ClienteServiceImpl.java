package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.security.Role;
import com.cdionisio.pedidos.security.User;
import com.cdionisio.pedidos.service.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.repo.IClienteRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl extends CrudGenericServiceImpl<Cliente> implements IClienteService {
	
	
	@Autowired
	private IClienteRepo repo;


	@Override
	protected IGenericRepo<Cliente> getRepo() {
		return repo;
	}

	@Override
	public Mono<User> buscarPorCorreo(String correo) {
		Mono<Cliente> monoCliente = repo.buscarPorCorreo(correo);

		List<Role> roles = new ArrayList<Role>();
		roles.add(Role.ROLE_ADMIN);

		return monoCliente.flatMap(u -> Mono.just(new User(u.getCorreo(), u.getPassword(), true, roles)));
	}
}
