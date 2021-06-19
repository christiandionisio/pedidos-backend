package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "clientes")
public class Cliente {
	
	@Id
	private String idCliente;
	
	@Field(name = "nombres")
	private String nombres;
	
	@Field(name = "apellidos")
	private String apellidos;
	
	@Field(name = "dni")
	private String dni; 
	
	@Field(name = "correo")
	private String correo;

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String id_cliente) {
		this.idCliente = id_cliente;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	

}
