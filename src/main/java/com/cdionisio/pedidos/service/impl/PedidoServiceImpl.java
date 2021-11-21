package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Pedido;
import com.cdionisio.pedidos.repo.IPedidoRepo;
import com.cdionisio.pedidos.service.interfaces.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private IPedidoRepo repo;

    @Override
    public Flux<Pedido> list() {
        return repo.findAll();
    }

    @Override
    public Mono<Pedido> getById(String id) {
        return repo.findById(id);
    }

    @Override
    public Mono<Pedido> insert(Pedido pedido) {
        return repo.save(pedido);
    }

    @Override
    public Mono<Pedido> update(Pedido pedido) {
        return repo.save(pedido);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }
}
