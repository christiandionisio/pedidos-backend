package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import reactor.core.publisher.Flux;

public interface IDistritoService extends ICrudGenericService<Distrito>{
    Flux<Distrito> findByIdProvincia(String idProvincia);
}
