package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "productos")
public class Producto {
	
	@Id
	private String id;

	@NotEmpty(message = "{notempty.nombre}")
	@Field(name = "nombre")
	private String nombre;
	
	@Field(name = "descripcion")
	private String descripcion;
	
	@Field(name = "tipo")
	private String tipo;

	@NotNull(message = "{notnull.precio}")
	@Field(name = "precio")
	private Double precio;
	
	@Field(name = "stock")
	private Integer stock;
	
	@Field(name = "fecha_registro")
	private String fechaRegistro;

}
