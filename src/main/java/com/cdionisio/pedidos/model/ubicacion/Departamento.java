package com.cdionisio.pedidos.model.ubicacion;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Document("departamentos")
public class Departamento {

    @Id
    private String id;

    @Field(name = "nombre")
    @NotEmpty(message = "{notempty.nombre}")
    private String nombre;

    @Field(name = "codigoPostal")
    private String codigoPostal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
