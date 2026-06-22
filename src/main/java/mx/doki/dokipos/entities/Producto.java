
package mx.doki.dokipos.entities;

// @author Andrey

public class Producto 
{
    
    // Atributos
    private int id;
    private String codigoBarras;
    private String nombre;
    private double precioVenta;
    private int stock;
    
    
    
    // Constructores
    public Producto(int id, String codigoBarras, String nombre, double precioVenta, int stock) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public Producto(String codigoBarras, String nombre, double precioVenta, int stock) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public Producto() {
    }
    
    
    
    // to String
    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", codigoBarras=" + codigoBarras + ", nombre=" + nombre + ", precioVenta=" + precioVenta + ", stock=" + stock + '}';
    }
    
    
    
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
}
