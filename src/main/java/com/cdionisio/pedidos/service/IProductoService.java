package com.cdionisio.pedidos.service;

import com.cdionisio.pedidos.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoService {
    Flux<Producto> findProductos();
    Mono<Producto> findById(String id);
    Mono<Producto> registerProducto(Producto producto);
    Mono<Producto> updateProducto(Producto producto);
    Mono<Void> delete(Producto producto);
}
