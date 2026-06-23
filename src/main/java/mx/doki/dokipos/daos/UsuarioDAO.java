
package mx.doki.dokipos.daos;

// @author Andrey

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
}
