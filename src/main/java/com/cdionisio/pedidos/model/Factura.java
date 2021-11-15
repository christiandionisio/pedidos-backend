package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "facturas")
public class Factura {
	
	@Id
	private String id;
	
	@Field(name = "idCliente")
	private String idCliente;
	
//	@Field(name = "mesa")
//	private Mesa mesa;
	
//	@Field(name = "empleado")
//	private Empleado empleado;
	
	@Field(name = "total")
	private Double total;
	
	@Field(name = "fechaEmision")
	private String fechaEmision;

	@Field(name = "estado")
	private String estado;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String idFactura) {
		this.id = idFactura;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}
