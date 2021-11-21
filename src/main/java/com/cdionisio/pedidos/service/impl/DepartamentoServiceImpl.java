package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.ubicacion.Departamento;
import com.cdionisio.pedidos.repo.IDepartamentoRepo;
import com.cdionisio.pedidos.service.interfaces.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartamentoServiceImpl implements IDepartamentoService {

    @Autowired
    IDepartamentoRepo repo;

    @Override
    public Flux<Departamento> list() {
        return repo.findAll();
    }

    @Override
    public Mono<Departamento> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Departamento> insert(Departamento departamento) {
        return repo.save(departamento);
    }

    @Override
    public Mono<Departamento> update(Departamento departamento) {
        return repo.save(departamento);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
}
