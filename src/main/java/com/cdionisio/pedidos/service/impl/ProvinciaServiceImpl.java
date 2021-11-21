package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import com.cdionisio.pedidos.repo.IProvinciaRepo;
import com.cdionisio.pedidos.service.interfaces.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProvinciaServiceImpl implements IProvinciaService {

    @Autowired
    private IProvinciaRepo repo;

    @Override
    public Flux<Provincia> list() {
        return repo.findAll();
    }

    @Override
    public Mono<Provincia> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Provincia> insert(Provincia provincia) {
        return repo.save(provincia);
    }

    @Override
    public Mono<Provincia> update(Provincia provincia) {
        return repo.save(provincia);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }

    @Override
    public Flux<Provincia> findByIdDepartament(String idDepartamento) {
        return repo.findByIdDepartamento(idDepartamento);
    }
}
