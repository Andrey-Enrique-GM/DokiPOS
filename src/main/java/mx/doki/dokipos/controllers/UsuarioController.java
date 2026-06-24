
package mx.doki.dokipos.controllers;

// @author Andrey

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import mx.doki.dokipos.daos.UsuarioDAO;
import mx.doki.dokipos.entities.Usuario;

         
public class UsuarioController 
{
    
    // Metodo principal para procesar el Login
    public String procesarLogin(String username, String password) {
        
        // 1. Validar que los campos no esten vacios
        if (username == null || username.trim().isEmpty()) {
            return "Error: El campo de usuario no puede estar vacio";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Error: El campo de contraseña no puede estar vacio";
        }

        // 2. Convertir contraseña ingresada a SHA-256 para compararla en la BD
        String passwordHash = convertirSHA256(password);
        if (passwordHash == null) {
            return "Error: Fallo critico al procesar la seguridad del sistema";
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        // 3. Buscar en la base de datos
        Usuario usuario = usuarioDAO.buscarUsuario(username.trim(), passwordHash);

        if (usuario != null) {
            // Guardamos temporalmente el rol en el mensaje para que la vista sepa a donde redirigir
            return "Exito: Bienvenido " + usuario.getNombre() + " (" + usuario.getRol() + ")";
        } else {
            return "Error: Usuario o contraseña incorrectos";
        }
    }
    
    
    
    // Algoritmo nativo de Java para hashear la contraseña
    public String convertirSHA256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error al hashear: " + e.getMessage());
            return null;
        }
    }
    
}
