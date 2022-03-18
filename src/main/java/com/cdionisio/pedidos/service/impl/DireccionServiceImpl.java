package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Direccion;
import com.cdionisio.pedidos.repo.IDireccionRepo;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.service.interfaces.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DireccionServiceImpl extends CrudGenericServiceImpl<Direccion> implements IDireccionService {

    @Autowired
    private IDireccionRepo repo;

    @Override
    protected IGenericRepo<Direccion> getRepo() {
        return repo;
    }

    @Override
    public Flux<Direccion> getByIdCliente(String idCliente) {
        return repo.buscarPorIdCliente(idCliente);
    }
}
