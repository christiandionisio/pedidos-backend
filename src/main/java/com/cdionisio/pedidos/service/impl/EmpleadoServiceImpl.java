package com.cdionisio.pedidos.service.impl;

import com.cdionisio.pedidos.model.Empleado;
import com.cdionisio.pedidos.repo.IEmpleadoRepo;
import com.cdionisio.pedidos.repo.IGenericRepo;
import com.cdionisio.pedidos.security.User;
import com.cdionisio.pedidos.service.interfaces.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmpleadoServiceImpl extends CrudGenericServiceImpl<Empleado> implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepo repo;

    @Override
    protected IGenericRepo<Empleado> getRepo() {
        return repo;
    }

    @Override
    public Mono<User> buscarPorCorreo(String correo) {
        Mono<Empleado> monoEmpleado = repo.buscarPorCorreo(correo);
        return monoEmpleado.flatMap(u -> Mono.just(new User(u.getCorreo(), u.getPassword(), true, u.getRole())));
    }

}
