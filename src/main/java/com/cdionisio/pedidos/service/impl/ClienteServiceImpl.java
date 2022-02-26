package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.security.Role;
import com.cdionisio.pedidos.security.User;
import com.cdionisio.pedidos.service.interfaces.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.repo.IClienteRepo;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl extends CrudGenericServiceImpl<Cliente> implements IClienteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);

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
		roles.add(Role.ROLE_USER);

		return monoCliente.flatMap(u -> Mono.just(new User(u.getCorreo(), u.getPassword(), true, roles)));
	}

	@Override
	public Mono<PageSupport<Cliente>> findPageableClientes(Pageable page) {
		LOGGER.info("Obteniendo lista de clientes paginado");
		return repo.findAll()
				.collectList()
				.map(list -> new PageSupport<>(
								list.stream()
										.skip((long)page.getPageNumber()*page.getPageSize())
										.limit(page.getPageSize())
										.collect(Collectors.toList()),
								page.getPageNumber(),
								page.getPageSize(),
								list.size()
						)
				);
	}

	@Override
	public Mono<PageSupport<Cliente>> findPageableClientesByFilters(Pageable page, String dni,
																	String nombres, String apellidos) {

		LOGGER.info("Obteniendo lista de clientes paginado por filtros");
		return repo.findByFieldFilters(dni, nombres, apellidos)
				.collectList()
				.map(list -> new PageSupport<>(
								list.stream()
										.skip((long)page.getPageNumber()*page.getPageSize())
										.limit(page.getPageSize())
										.collect(Collectors.toList()),
								page.getPageNumber(),
								page.getPageSize(),
								list.size()
						)
				);
	}
}
