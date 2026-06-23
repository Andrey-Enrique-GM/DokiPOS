
package mx.doki.dokipos.daos;

// @author Andrey

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.doki.dokipos.entities.Producto;


public class ProductoDAO 
{
    
    public Producto obtenerPorCodigo(String codigoBarras) {
        String query = "SELECT id, codigo_barras, nombre, precio_venta, stock FROM productos WHERE codigo_barras = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, codigoBarras);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id"));
                    producto.setCodigoBarras(rs.getString("codigo_barras"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecioVenta(rs.getDouble("precio_venta"));
                    producto.setStock(rs.getInt("stock"));
                    return producto;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO.obtenerPorCodigo: " + e.getMessage());
        }
        return null;
    }
    
    
    
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        String query = "UPDATE productos SET stock = ? WHERE id = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en ProductoDAO.actualizarStock: " + e.getMessage());
            return false;
        }
    }
    
}
