
package mx.doki.dokipos.app;

// @author Andrey

import mx.doki.dokipos.views.LoginView;


public class App 
{
    
    public static void main(String[] args) {
        // Lanzar de forma segura la interfaz grafica del Login
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView login = new LoginView();
                login.setVisible(true);
                login.setLocationRelativeTo(null); // Centra la ventana en la pantalla
            }
        });
    }
    
}
