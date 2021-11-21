package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoService extends ICrudGenericService<Producto>{
    Mono<PageSupport<Producto>> findPageableProductos(Pageable page);
}
