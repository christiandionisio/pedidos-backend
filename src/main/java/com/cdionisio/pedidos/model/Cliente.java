package com.cdionisio.pedidos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Document(collection = "clientes")
public class Cliente {
	
	@Id
	private String id;

	@NotEmpty(message = "El campo no debe ir vacio")
	@Field(name = "nombres")
	private String nombres;
	
	@Field(name = "apellidos")
	private String apellidos;

	@Size(min = 8, max = 8, message = "Debe tener 8 caracteres")
	@Field(name = "dni")
	private String dni; 

	@Email(message = "Formato email invalido")
	@Field(name = "correo")
	@Indexed(unique = true)
	private String correo;

	@NotEmpty(message = "Contraseña obligatoria")
	@Size(min = 6, message = "La contraseña debe tener 6 o más caracteres")
	@Field(name = "password")
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
