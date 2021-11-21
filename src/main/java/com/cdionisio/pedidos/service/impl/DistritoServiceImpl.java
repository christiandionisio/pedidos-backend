package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import com.cdionisio.pedidos.repo.IDistritoRepo;
import com.cdionisio.pedidos.service.interfaces.IDistritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DistritoServiceImpl implements IDistritoService {

    @Autowired
    private IDistritoRepo repo;

    @Override
    public Flux<Distrito> list() {
        return repo.findAll();
    }

    @Override
    public Mono<Distrito> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Distrito> insert(Distrito distrito) {
        return repo.save(distrito);
    }

    @Override
    public Mono<Distrito> update(Distrito distrito) {
        return repo.save(distrito);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }

    @Override
    public Flux<Distrito> findByIdProvincia(String idProvincia) {
        return repo.obtenerPorIdProvinicia(idProvincia);
    }
}
