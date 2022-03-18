package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Direccion;
import reactor.core.publisher.Flux;

public interface IDireccionService extends ICrudGenericService<Direccion> {

    public Flux<Direccion> getByIdCliente(String idCliente);
}
