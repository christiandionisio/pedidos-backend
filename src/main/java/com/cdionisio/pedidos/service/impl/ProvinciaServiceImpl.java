package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.ubicacion.Provincia;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.repo.IProvinciaRepo;
import com.cdionisio.pedidos.service.interfaces.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProvinciaServiceImpl extends CrudGenericServiceImpl<Provincia> implements IProvinciaService {

    @Autowired
    private IProvinciaRepo repo;



    @Override
    public Flux<Provincia> findByIdDepartament(String idDepartamento) {
        return repo.obtenerPorIdDepartamento(idDepartamento);
    }

    @Override
    protected IGenericRepo<Provincia> getRepo() {
        return repo;
    }
}
