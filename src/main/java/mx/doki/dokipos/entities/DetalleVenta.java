
package mx.doki.dokipos.entities;

// @author Andrey

public class DetalleVenta 
{
    
    // Atributos
    private int id;
    private int idVenta; // ID del ticket al que pertenece este renglon
    private Producto producto; // Objeto Producto que contiene el nombre, precio, etc.
    private int cantidad;
    private double subtotal;
    
    
    
    // Constructores
    public DetalleVenta(int id, int idVenta, Producto producto, int cantidad, double subtotal) {
        this.id = id;
        this.idVenta = idVenta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleVenta(int idVenta, Producto producto, int cantidad, double subtotal) {
        this.idVenta = idVenta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleVenta() {
    }
    
    
    
    // to String
    @Override
    public String toString() {
        return "DetalleVenta{" + "id=" + id + ", idVenta=" + idVenta + ", producto=" + producto + ", cantidad=" + cantidad + ", subtotal=" + subtotal + '}';
    }
    
    
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
}
