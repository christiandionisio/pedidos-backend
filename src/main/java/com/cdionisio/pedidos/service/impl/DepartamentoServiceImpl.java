package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.ubicacion.Departamento;
import com.cdionisio.pedidos.repo.IDepartamentoRepo;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.service.interfaces.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartamentoServiceImpl extends CrudGenericServiceImpl<Departamento> implements IDepartamentoService {

    @Autowired
    IDepartamentoRepo repo;


    @Override
    protected IGenericRepo<Departamento> getRepo() {
        return repo;
    }
}
