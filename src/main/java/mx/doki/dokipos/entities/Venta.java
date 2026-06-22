
package mx.doki.dokipos.entities;

// @author Andrey

import java.sql.Timestamp;


public class Venta 
{
    
    // Atributos
    private int id;
    private Timestamp fechaHora;
    private double total;
    private Usuario usuario; // Guardamos el objeto completo del cajero que atiende
    
    
    
    // Constructores
    public Venta(int id, Timestamp fechaHora, double total, Usuario usuario) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.total = total;
        this.usuario = usuario;
    }
    
    public Venta(Timestamp fechaHora, double total, Usuario usuario) {
        this.fechaHora = fechaHora;
        this.total = total;
        this.usuario = usuario;
    }
    
    public Venta() {
    }    
    
    
    
    // to String
    @Override
    public String toString() {
        return "Venta{" + "id=" + id + ", fechaHora=" + fechaHora + ", total=" + total + ", usuario=" + usuario + '}';
    }
    
    
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
