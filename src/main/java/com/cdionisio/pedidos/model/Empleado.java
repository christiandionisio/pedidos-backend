package com.cdionisio.pedidos.model;

import com.cdionisio.pedidos.security.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "empleados")
public class Empleado {
	
	@Id
	private String idEmpleado;
	
	@Field(name = "nombres")
	private String nombres;
	
	@Field(name = "apellidos")
	private String apellidos;
	
	@Field(name = "dni")
	private String dni;
	
	@Field(name = "correo")
	private String correo;

	@Field(name = "role")
	private List<Role> roles;

	@Field(name = "fecha_nacimiento")
	private String fechaNacimiento;
	
	@Field(name = "password")
	private String password;

	public String getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(String idEmpleado) {
		this.idEmpleado = idEmpleado;
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

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRole() {
		return roles;
	}

	public void setRole(List<Role> roles) {
		this.roles = roles;
	}
}
