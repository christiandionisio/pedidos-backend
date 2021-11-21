package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Tarjeta;
import com.cdionisio.pedidos.repo.ITarjetaRepo;
import com.cdionisio.pedidos.service.interfaces.ITarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TarjetaServiceImpl implements ITarjetaService {

    @Autowired
    private ITarjetaRepo repo;

    @Override
    public Flux<Tarjeta> list() {
        return repo.findAll();
    }

    @Override
    public Mono<Tarjeta> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Tarjeta> insert(Tarjeta tarjeta) {
        return repo.save(tarjeta);
    }

    @Override
    public Mono<Tarjeta> update(Tarjeta tarjeta) {
        return repo.save(tarjeta);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
}
