package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import reactor.core.publisher.Flux;

public interface IProvinciaService extends ICrudGenericService<Provincia>{

    Flux<Provincia> findByIdDepartament(String idDepartamento);

}
