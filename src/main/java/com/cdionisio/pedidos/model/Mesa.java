package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "mesas")
public class Mesa {
	
	@Id
	private String idMesa;
	
	@Field(name = "capacidad")
	private Integer capacidad;
	
	@Field(name = "descripcion")
	private String descripcion;

	public String getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(String id_mesa) {
		this.idMesa = id_mesa;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
