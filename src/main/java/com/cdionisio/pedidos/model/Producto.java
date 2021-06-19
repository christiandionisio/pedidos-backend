package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "productos")
public class Producto {
	
	@Id
	private String idProducto;
	
	@Field(name = "nombre")
	private String nombre;
	
	@Field(name = "descripcion")
	private String descripcion;
	
	@Field(name = "tipo")
	private String tipo;
	
	@Field(name = "precio")
	private Double precio;
	
	@Field(name = "stock")
	private Integer stock;
	
	@Field(name = "fecha_registro")
	private String fechaRegistro;

}
