package com.cdionisio.pedidos.repo;

import org.springframework.data.mongodb.repository.Query;

import com.cdionisio.pedidos.model.Cliente;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClienteRepo extends IGenericRepo<Cliente> {

    @Query("{'correo' : ?0 }")
    Mono<Cliente> buscarPorCorreo(String correo);

    @Query("{ $and: [ { 'dni' : { $regex: ?0, $options:'i' } }, " +
            "{ 'nombres' : { $regex: ?1, $options:'i' } }, " +
            "{ 'apellidos' : { $regex: ?2, $options:'i' } } ] }")
    Flux<Cliente> findByFieldFilters(String dni, String nombres, String apellidos);

}
