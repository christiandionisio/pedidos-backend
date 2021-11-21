package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.service.interfaces.ICrudGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CrudGenericServiceImpl<T> implements ICrudGenericService<T> {

    @Autowired
    protected abstract IGenericRepo<T> getRepo();

    @Override
    public Flux<T> list() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> getById(String id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<T> insert(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<T> update(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<Void> delete(String id) {
        return getRepo().deleteById(id);
    }
}
