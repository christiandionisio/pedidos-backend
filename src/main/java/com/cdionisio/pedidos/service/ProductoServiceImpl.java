package com.cdionisio.pedidos.service;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.repo.IProductoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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
        return productoRepo.save(producto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return productoRepo.deleteById(id);
    }

    @Override
    public Mono<PageSupport<Producto>> findPageableProductos(Pageable page) {
        return productoRepo.findAll()
                .collectList()
                .map(list -> new PageSupport<>(
                            list.stream()
                                    .skip(Long.valueOf(page.getPageNumber()*page.getPageSize()))
                                    .limit(Long.valueOf(page.getPageSize()))
                                    .collect(Collectors.toList()),
                            page.getPageNumber(),
                            page.getPageSize(),
                            list.size()
                        )
                );
    }
}
