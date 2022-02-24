package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.security.User;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface IClienteService extends ICrudGenericService<Cliente>{

    Mono<User> buscarPorCorreo(String correo);
    Mono<PageSupport<Cliente>> findPageableClientes(Pageable page);

}
