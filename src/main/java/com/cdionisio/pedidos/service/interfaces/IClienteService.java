package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Cliente;
import com.cdionisio.pedidos.security.User;
import reactor.core.publisher.Mono;

public interface IClienteService extends ICrudGenericService<Cliente>{

    Mono<User> buscarPorCorreo(String correo);

}
