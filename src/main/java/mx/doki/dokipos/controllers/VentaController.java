
package mx.doki.dokipos.controllers;

// @author Andrey

import java.util.List;
import mx.doki.dokipos.daos.VentaDAO;
import mx.doki.dokipos.entities.DetalleVenta;
import mx.doki.dokipos.entities.Venta;


public class VentaController 
{
    
    private VentaDAO ventaDAO = new VentaDAO();
    
    
    public String registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        // Validar que la venta lleve articulos
        if (detalles == null || detalles.isEmpty()) {
            return "Error: No hay productos agregados en el carrito.";
        }
        
        // Validar que el total sea coherente
        if (venta.getTotal() <= 0) {
            return "Error: El total de la venta debe ser mayor a $0.00.";
        }
        
        // Procesar en base de datos
        if (ventaDAO.procesarTransaccionVenta(venta, detalles)) {
            return "Exito: Venta completada correctamente.";
        } else {
            return "Error: No se pudo registrar la venta. Verifique las existencias en inventario.";
        }
    }
    
}
