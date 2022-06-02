package com.cdionisio.pedidos.repo;

import com.cdionisio.pedidos.model.Pedido;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

public interface IPedidoRepo extends IGenericRepo<Pedido> {

    @Query("{'idFactura' : ?0 }")
    Flux<Pedido> buscarPorFacturaId(String facturaId);
}
