package com.cdionisio.pedidos.model;

import com.cdionisio.pedidos.security.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "empleados")
public class Empleado {
	
	@Id
	private String idEmpleado;

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

	@Field(name = "role")
	private String role;

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

	public String getRole() {
		return role;
	}

	public List<Role> getRoleParsed() {
		List<Role> roles = new ArrayList<>();
		if (this.role.equals(Role.ROLE_USER.toString())) {
			roles.add(Role.ROLE_USER);
		}

		if (this.role.equals(Role.ROLE_ADMIN.toString())) {
			roles.add(Role.ROLE_ADMIN);
		}

		if (this.role.equals(Role.ROLE_CAJERO.toString())) {
			roles.add(Role.ROLE_CAJERO);
		}

		return roles;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
