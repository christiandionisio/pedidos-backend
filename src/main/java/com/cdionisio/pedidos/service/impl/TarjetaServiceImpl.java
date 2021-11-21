package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Tarjeta;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.repo.ITarjetaRepo;
import com.cdionisio.pedidos.service.interfaces.ITarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaServiceImpl extends CrudGenericServiceImpl<Tarjeta> implements ITarjetaService {

    @Autowired
    private ITarjetaRepo repo;


    @Override
    protected IGenericRepo<Tarjeta> getRepo() {
        return repo;
    }
}
