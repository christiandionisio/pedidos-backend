package com.cdionisio.pedidos.service.interfaces;

import com.cdionisio.pedidos.model.Pedido;
import reactor.core.publisher.Flux;

public interface IPedidoService extends ICrudGenericService<Pedido>{

    Flux<Pedido> buscarPorFacturaId(String facturaId);
}
