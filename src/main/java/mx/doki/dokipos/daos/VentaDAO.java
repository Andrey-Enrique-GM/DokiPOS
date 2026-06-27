
package mx.doki.dokipos.daos;

// @author Andrey

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import mx.doki.dokipos.entities.DetalleVenta;
import mx.doki.dokipos.entities.Venta;


public class VentaDAO 
{
    
     // Guarda el maestro de la venta, sus desgloses de articulos en detalle_ventas y descuenta las existencias de la tabla productos de forma segura
    public boolean procesarTransaccionVenta(Venta venta, List<DetalleVenta> detalles) {
        String queryVenta = "INSERT INTO ventas (total, id_usuario) VALUES (?, ?)";
        String queryDetalle = "INSERT INTO detalles_ventas (id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String queryStock = "UPDATE productos SET stock = stock - ? WHERE id = ?";
        
        Connection con = null;
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;
        
        try {
            con = Conexion.obtener();
            con.setAutoCommit(false); // Iniciamos la transaccion
            
            // Insertar la Venta Maestra
            psVenta = con.prepareStatement(queryVenta, PreparedStatement.RETURN_GENERATED_KEYS);
            psVenta.setDouble(1, venta.getTotal());
            psVenta.setInt(2, venta.getUsuario().getId());
            psVenta.executeUpdate();
            
            // Recuperar el ID asignado por MySQL a esta venta
            int idVentaGenerado = -1;
            try (ResultSet rs = psVenta.getGeneratedKeys()) {
                if (rs.next()) {
                    idVentaGenerado = rs.getInt(1);
                }
            }
            
            if (idVentaGenerado == -1) throw new SQLException("No se pudo obtener el ID de la venta.");
            
            // Insertar los detalles y actualizar existencias uno por uno
            psDetalle = con.prepareStatement(queryDetalle);
            psStock = con.prepareStatement(queryStock);
            
            for (DetalleVenta d : detalles) {
                // Insertar detalle
                psDetalle.setInt(1, idVentaGenerado);
                psDetalle.setInt(2, d.getProducto().getId());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.getSubtotal());
                psDetalle.addBatch(); // Se acumula en lote para mayor velocidad
                
                // Descontar inventario
                psStock.setInt(1, d.getCantidad());
                psStock.setInt(2, d.getProducto().getId());
                psStock.addBatch();
            }
            
            // Ejecutar los lotes en la base de datos
            psDetalle.executeBatch();
            psStock.executeBatch();
            
            con.commit(); // Si todo salio bien, guardamos los cambios de forma permanente
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error en la transaccion de venta, aplicando Rollback: " + e.getMessage());
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            // Cerrar flujos de forma segura
            try {
                if (psVenta != null) psVenta.close();
                if (psDetalle != null) psDetalle.close();
                if (psStock != null) psStock.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
}
