package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Factura;
import com.cdionisio.pedidos.repo.IFacturaRepo;
import com.cdionisio.pedidos.service.interfaces.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private IFacturaRepo repo;

    @Override
    public Flux<Factura> list() {
        return repo.findAll();
    }

    @Override
    public Mono<Factura> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Factura> insert(Factura factura) {
        return repo.save(factura);
    }

    @Override
    public Mono<Factura> update(Factura factura) {
        return repo.save(factura);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
}
