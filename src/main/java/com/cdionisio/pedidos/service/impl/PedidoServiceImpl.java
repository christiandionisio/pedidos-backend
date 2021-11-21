package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Pedido;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.repo.IPedidoRepo;
import com.cdionisio.pedidos.service.interfaces.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl extends CrudGenericServiceImpl<Pedido> implements IPedidoService {

    @Autowired
    private IPedidoRepo repo;


    @Override
    protected IGenericRepo<Pedido> getRepo() {
        return repo;
    }
}
