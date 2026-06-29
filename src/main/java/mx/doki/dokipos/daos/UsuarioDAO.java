
package mx.doki.dokipos.daos;

// @author Andrey

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.doki.dokipos.entities.Usuario;


public class UsuarioDAO 
{
    
    public Usuario buscarUsuario(String username, String passwordHash) {
        String query = "SELECT id, username, nombre, rol FROM usuarios WHERE username = ? AND password = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario; // Retorna el usuario encontrado
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAO.buscarUsuario: " + e.getMessage());
        }
        return null; // Si no coincide o hay error, retorna null
    }
    
    
    
    public List<Usuario> listarYFiltrar(String busqueda) {
        List<Usuario> lista = new ArrayList<>();
        // Buscaremos coincidencias en username o en el nombre completo
        String query = "SELECT id, username, password, nombre, rol FROM usuarios "
                     + "WHERE username LIKE ? OR nombre LIKE ?";
        
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            String parametro = "%" + busqueda + "%";
            ps.setString(1, parametro);
            ps.setString(2, parametro);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setNombre(rs.getString("nombre"));
                    u.setRol(rs.getString("rol"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAO.listarYFiltrar: " + e.getMessage());
        }
        return lista;
    }
    
    
    
    public boolean registrar(Usuario u) {
        String query = "INSERT INTO usuarios (username, password, nombre, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword()); // Aqui ya vendra en SHA-256 desde el Controller
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getRol());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAO.registrar: " + e.getMessage());
            return false;
        }
    }
    
    
    
    public boolean actualizar(Usuario u) {
        // Inicialmente preparamos el query asumiendo que modificaremos la contraseña
        String query = "UPDATE usuarios SET username = ?, password = ?, nombre = ?, rol = ? WHERE id = ?";
        
        // Pero si viene vacia o nula, significa que no se altero en el formulario
        boolean cambiarPassword = (u.getPassword() != null && !u.getPassword().trim().isEmpty());
        
        if (!cambiarPassword) {
            query = "UPDATE usuarios SET username = ?, nombre = ?, rol = ? WHERE id = ?";
        }
        
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            if (cambiarPassword) {
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPassword());
                ps.setString(3, u.getNombre());
                ps.setString(4, u.getRol());
                ps.setInt(5, u.getId());
            } else {
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getNombre());
                ps.setString(3, u.getRol());
                ps.setInt(4, u.getId());
            }
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAO.actualizar: " + e.getMessage());
            return false;
        }
    }
    
}
