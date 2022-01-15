package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Empleado;
import com.cdionisio.pedidos.security.User;
import reactor.core.publisher.Mono;

public interface IEmpleadoService extends ICrudGenericService<Empleado> {

    Mono<User> buscarPorCorreo(String correo);
}
