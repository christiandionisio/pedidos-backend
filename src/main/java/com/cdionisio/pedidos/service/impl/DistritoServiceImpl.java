package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.ubicacion.Distrito;
import com.cdionisio.pedidos.repo.IDistritoRepo;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.service.interfaces.IDistritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DistritoServiceImpl extends CrudGenericServiceImpl<Distrito> implements IDistritoService {

    @Autowired
    private IDistritoRepo repo;

    @Override
    public Flux<Distrito> findByIdProvincia(String idProvincia) {
        return repo.obtenerPorIdProvinicia(idProvincia);
    }

    @Override
    protected IGenericRepo<Distrito> getRepo() {
        return repo;
    }
}
