package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Producto;
import com.cdionisio.pedidos.pagination.PageSupport;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.repo.IProductoRepo;
import com.cdionisio.pedidos.service.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl extends CrudGenericServiceImpl<Producto> implements IProductoService {

    @Autowired
    private IProductoRepo productoRepo;

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

    @Override
    protected IGenericRepo<Producto> getRepo() {
        return productoRepo;
    }
}
