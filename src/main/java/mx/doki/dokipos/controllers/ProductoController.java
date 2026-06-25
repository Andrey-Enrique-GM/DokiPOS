
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
    
}
