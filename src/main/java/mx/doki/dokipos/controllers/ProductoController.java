
package mx.doki.dokipos.controllers;

// @author Andrey

import java.util.List;
import mx.doki.dokipos.daos.ProductoDAO;
import mx.doki.dokipos.entities.Producto;


public class ProductoController 
{
    
    private ProductoDAO productoDAO = new ProductoDAO();
    
    
    // Obtiene la lista de productos filtrada por texto. Si 'busqueda' esta vacia, el DAO traera todos los productos.
    public List<Producto> buscarProductos(String busqueda) {
        if (busqueda == null) {
            busqueda = "";
        }
        return productoDAO.listarYFiltrar(busqueda.trim());
    }
    
    
    
    // Guarda un nuevo producto o actualiza un producto existente en la base de datos
    public String guardarProducto(String idStr, String codigo, String nombre, String precioStr, String stockStr) {
        
        // Validaciones de campos vacios
        if (codigo == null || codigo.trim().isEmpty()) return "Error: El codigo de barras es obligatorio.";
        if (nombre == null || nombre.trim().isEmpty()) return "Error: El nombre del producto es obligatorio.";
        if (precioStr == null || precioStr.trim().isEmpty()) return "Error: El precio de venta es obligatorio.";
        if (stockStr == null || stockStr.trim().isEmpty()) return "Error: El stock es obligatorio.";
        
        double precio;
        int stock;
        
        // Validar que los campos numericos sean validos
        try {
            precio = Double.parseDouble(precioStr.trim());
            if (precio < 0) return "Error: El precio no puede ser negativo.";
        } catch (NumberFormatException e) {
            return "Error: El precio debe ser un numero valido (ej: 25.50).";
        }
        
        try {
            stock = Integer.parseInt(stockStr.trim());
            if (stock < 0) return "Error: El stock no puede ser negativo.";
        } catch (NumberFormatException e) {
            return "Error: El stock debe ser un numero entero valido.";
        }
        
        // Revisar si es una insercion o una actualizacion
        mx.doki.dokipos.daos.ProductoDAO dao = new mx.doki.dokipos.daos.ProductoDAO();
        mx.doki.dokipos.entities.Producto p = new mx.doki.dokipos.entities.Producto();
        p.setCodigoBarras(codigo.trim());
        p.setNombre(nombre.trim());
        p.setPrecioVenta(precio);
        p.setStock(stock);
        
        if (idStr == null || idStr.isEmpty()) {
            // ES NUEVO
            if (dao.registrar(p)) {
                return "Exito: Producto registrado correctamente.";
            } else {
                return "Error: No se pudo registrar el producto.";
            }
        } else {
            // ES MODIFICACION
            p.setId(Integer.parseInt(idStr));
            if (dao.actualizar(p)) {
                return "Exito: Producto actualizado correctamente.";
            } else {
                return "Error: No se pudo actualizar el producto en el sistema.";
            }
        }
    }
    
}
