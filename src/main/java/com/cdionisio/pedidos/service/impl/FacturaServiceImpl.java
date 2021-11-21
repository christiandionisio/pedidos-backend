package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Factura;
import com.cdionisio.pedidos.repo.IFacturaRepo;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.service.interfaces.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaServiceImpl extends CrudGenericServiceImpl<Factura> implements IFacturaService {

    @Autowired
    private IFacturaRepo repo;

    @Override
    protected IGenericRepo<Factura> getRepo() {
        return repo;
    }

}
