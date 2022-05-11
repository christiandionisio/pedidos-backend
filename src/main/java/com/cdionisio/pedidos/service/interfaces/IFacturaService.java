package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Factura;
import reactor.core.publisher.Flux;

public interface IFacturaService extends ICrudGenericService<Factura> {

    Flux<Factura> findFacturasByFilters(String estado, String fecha);
}
