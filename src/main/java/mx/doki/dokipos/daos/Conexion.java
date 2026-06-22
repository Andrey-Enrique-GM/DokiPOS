
package mx.doki.dokipos.daos;

// @author Andrey

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion 
{
    
    private static String usuario = "root";
    private static String clave = "chihuahua";
    private static String url = "localhost:3307";
    
    private static Connection con;
    
    
    /**
     * Metodo estatico para obtener la conexion a la base de datos.
     * @return Objeto Connection activo.
     */
    public static Connection obtener() {
        try {
            // Verificamos si la conexion es nula o se cerro por tiempo de inactividad
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, usuario, clave);
                System.out.println("Conexion establecida con el servidor!");
            }
        } catch (Exception ex) {
            System.err.println("Error de conexion: " + ex.getMessage());
        }
        return con;
    }
    
    
}
