package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Empleado;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Mono;

public interface IEmpleadoRepo extends IGenericRepo<Empleado> {

    @Query("{'correo' : ?0 }")
    Mono<Empleado> buscarPorCorreo(String correo);
}
