package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.dtos.ClienteDTO;
import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.security.User;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IClienteService extends ICrudGenericService<Cliente>{

    Mono<User> buscarPorCorreo(String correo);
    Mono<PageSupport<ClienteDTO>> findPageableClientes(Pageable page);
    Mono<PageSupport<ClienteDTO>> findPageableClientesByFilters(Pageable page, String dni, String nombres, String apellidos, String correo);

}
