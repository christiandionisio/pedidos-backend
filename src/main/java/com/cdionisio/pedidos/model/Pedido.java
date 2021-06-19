package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "pedidos")
public class Pedido {
	
	@Id
	private String idPedido;
	
	@Field(name = "factura")
	private Factura factura;
	
	@Field(name = "producto")
	private Producto producto;
	
	@Field(name = "cantidad")
	private Integer cantidad;
	
	@Field(name = "fecha_pedido")
	private String fechaPedido;
	
	@Field(name = "fecha_atendido")
	private String fechaAtendido;

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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
