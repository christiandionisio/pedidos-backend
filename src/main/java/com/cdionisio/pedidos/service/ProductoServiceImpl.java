package com.cdionisio.pedidos.service;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.repo.IProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoRepo productoRepo;

    @Override
    public Flux<Producto> findProductos() {
        return productoRepo.findAll();
    }

    @Override
    public Mono<Producto> findById(String id) {
        return productoRepo.findById(id);
    }

    @Override
    public Mono<Producto> registerProducto(Producto producto) {
        return productoRepo.insert(producto);
    }

    @Override
    public Mono<Producto> updateProducto(Producto producto) {
        return productoRepo.insert(producto);
    }

    @Override
    public Mono<Void> delete(Producto producto) {
        return productoRepo.delete(producto);
    }
}
