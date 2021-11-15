package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "pedidos")
public class Pedido {
	
	@Id
	private String id;
	
	@Field(name = "id_factura")
	private String idFactura;
	
	@Field(name = "id_producto")
	private String idProducto;
	
	@Field(name = "cantidad")
	private Integer cantidad;
	
	@Field(name = "fecha_pedido")
	private String fechaPedido;
	
	@Field(name = "fecha_atendido")
	private String fechaAtendido;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getFechaAtendido() {
		return fechaAtendido;
	}

	public void setFechaAtendido(String fechaAtendido) {
		this.fechaAtendido = fechaAtendido;
	}

}
