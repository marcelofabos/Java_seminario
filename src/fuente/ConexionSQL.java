/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuente;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ConexionSQL {
    private static final String CONTROLADOR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;database=bd_ventas_java;" + "user=sa;password=123456;loginTimeout=30;";
    
    
    static {
        try { 
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador");
        }
    }
    
    public Connection Conectar() {
        Connection cnx = null;
        
        try {
            cnx = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error en la conexión");
        }
        
        return cnx;
    }
}
