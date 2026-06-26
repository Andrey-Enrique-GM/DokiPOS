
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
    
    
    
    public java.util.List<mx.doki.dokipos.entities.Producto> listarYFiltrar(String busqueda) {
        java.util.List<mx.doki.dokipos.entities.Producto> lista = new java.util.ArrayList<>();
        // Buscaremos coincidencia exacta o parcial tanto en codigo de barras como en nombre
        String query = "SELECT id, codigo_barras, nombre, precio_venta, stock FROM productos "
                     + "WHERE codigo_barras LIKE ? OR nombre LIKE ?";
        
        try (java.sql.Connection con = Conexion.obtener();
             java.sql.PreparedStatement ps = con.prepareStatement(query)) {
            
            // El comodin '%' permite buscar coincidencias parciales (Ejemplo, "cup" traera "Cupcake")
            String parametro = "%" + busqueda + "%";
            ps.setString(1, parametro);
            ps.setString(2, parametro);
            
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mx.doki.dokipos.entities.Producto p = new mx.doki.dokipos.entities.Producto();
                    p.setId(rs.getInt("id"));
                    p.setCodigoBarras(rs.getString("codigo_barras"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecioVenta(rs.getDouble("precio_venta"));
                    p.setStock(rs.getInt("stock"));
                    lista.add(p);
                }
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error en ProductoDAO.listarYFiltrar: " + e.getMessage());
        }
        return lista;
    }
    
    
    
    public boolean registrar(mx.doki.dokipos.entities.Producto p) {
        String query = "INSERT INTO productos (codigo_barras, nombre, precio_venta, stock) VALUES (?, ?, ?, ?)";
        try (java.sql.Connection con = Conexion.obtener();
             java.sql.PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, p.getCodigoBarras());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getPrecioVenta());
            ps.setInt(4, p.getStock());
            
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            System.err.println("Error en ProductoDAO.registrar: " + e.getMessage());
            return false;
        }
    }
    
    
    
    public boolean actualizar(mx.doki.dokipos.entities.Producto p) {
        String query = "UPDATE productos SET codigo_barras = ?, nombre = ?, precio_venta = ?, stock = ? WHERE id = ?";
        try (java.sql.Connection con = Conexion.obtener();
             java.sql.PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, p.getCodigoBarras());
            ps.setString(2, p.getNombre());
            ps.setDouble(3, p.getPrecioVenta());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getId());
            
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            System.err.println("Error en ProductoDAO.actualizar: " + e.getMessage());
            return false;
        }
    }
    
}
