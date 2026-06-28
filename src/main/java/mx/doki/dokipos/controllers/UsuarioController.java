
package mx.doki.dokipos.controllers;

// @author Andrey

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import mx.doki.dokipos.daos.UsuarioDAO;
import mx.doki.dokipos.entities.Usuario;

         
public class UsuarioController 
{
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    
    
    // Metodo principal para procesar el Login
    public String procesarLogin(String username, String password) {
        
        // Validar que los campos no esten vacios
        if (username == null || username.trim().isEmpty()) {
            return "Error: El campo de usuario no puede estar vacio";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Error: El campo de contraseña no puede estar vacio";
        }

        // Convertir contraseña ingresada a SHA-256 para compararla en la BD
        String passwordHash = convertirSHA256(password);
        if (passwordHash == null) {
            return "Error: Fallo critico al procesar la seguridad del sistema";
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        // Buscar en la base de datos
        Usuario usuario = usuarioDAO.buscarUsuario(username.trim(), passwordHash);

        if (usuario != null) {
            // Guardamos temporalmente el rol en el mensaje para que la vista sepa a donde redirigir
            return "Exito: Bienvenido " + usuario.getNombre() + " (" + usuario.getRol() + ")";
        } else {
            return "Error: Usuario o contraseña incorrectos";
        }
    }
    
    
    
    public List<Usuario> buscarUsuarios(String busqueda) {
        if (busqueda == null) {
            busqueda = "";
        }
        return usuarioDAO.listarYFiltrar(busqueda.trim());
    }
    
    
    
    public String guardarUsuario(String idStr, String username, String passwordTxtPlano, String nombre, String rol) {
        
        // Validaciones generales comunes
        if (username == null || username.trim().isEmpty()) return "Error: El nombre de usuario es obligatorio.";
        if (nombre == null || nombre.trim().isEmpty()) return "Error: El nombre completo es obligatorio.";
        if (rol == null || rol.trim().isEmpty()) return "Error: El rol es obligatorio.";
        
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = new Usuario();
        u.setUsername(username.trim());
        u.setNombre(nombre.trim());
        u.setRol(rol.trim());
        
        if (idStr == null || idStr.isEmpty()) {
            // ES NUEVO: La contraseña es estrictamente obligatoria
            if (passwordTxtPlano == null || passwordTxtPlano.trim().isEmpty()) {
                return "Error: La contraseña es obligatoria para un usuario nuevo.";
            }
            
            // Ciframos a SHA-256 antes de enviar al DAO
            u.setPassword(convertirSHA256(passwordTxtPlano.trim()));
            
            if (dao.registrar(u)) {
                return "Exito: Usuario registrado correctamente.";
            } else {
                return "Error: No se pudo registrar el usuario.";
            }
        } else {
            // ES MODIFICACION
            u.setId(Integer.parseInt(idStr));
            
            // Si el administrador escribio algo, se cifra, si no, se manda null para que el DAO la ignore
            if (passwordTxtPlano != null && !passwordTxtPlano.trim().isEmpty()) {
                u.setPassword(convertirSHA256(passwordTxtPlano.trim()));
            } else {
                u.setPassword(null);
            }
            
            if (dao.actualizar(u)) {
                return "Exito: Usuario actualizado correctamente.";
            } else {
                return "Error: No se pudo actualizar el usuario en el sistema.";
            }
        }
    }
    
    
    
    // Metodo auxiliar para encriptar en SHA-256
    public String convertirSHA256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al cifrar contraseña: " + e.getMessage());
            return null;
        }
    }
    
}
