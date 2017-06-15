package com.juanjoflores.mantenimientoapp.DB;

import com.orm.SugarRecord;

/**
 * Created by Contabilidad 5 on 15/06/2017.
 */

public class TareasAsignadas extends SugarRecord<TareasAsignadas> {



    String nombreTarea;
    String descripcion;
    String duracion;
    String fecha_alta;

    Usuarios tecnico;
    TipoTareas tipo_tarea;
    StatusTareas estatus;

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Usuarios getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuarios tecnico) {
        this.tecnico = tecnico;
    }

    public TipoTareas getTipo_tarea() {
        return tipo_tarea;
    }

    public void setTipo_tarea(TipoTareas tipo_tarea) {
        this.tipo_tarea = tipo_tarea;
    }

    public StatusTareas getEstatus() {
        return estatus;
    }

    public void setEstatus(StatusTareas estatus) {
        this.estatus = estatus;
    }

    public TareasAsignadas() {
    }

    public TareasAsignadas(String nombreTarea, String descripcion, String duracion, String fecha_alta, Usuarios tecnico, TipoTareas tipo_tarea, StatusTareas estatus) {
        this.nombreTarea = nombreTarea;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.fecha_alta = fecha_alta;
        this.tecnico = tecnico;
        this.tipo_tarea = tipo_tarea;
        this.estatus = estatus;
    }
}
