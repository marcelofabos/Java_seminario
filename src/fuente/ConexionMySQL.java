/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fuente;
/* https://senatipe-my.sharepoint.com/:f:/g/personal/castillol_senati_pe/EqUCe7HqzCZHq_dSV024k4sB4S9Mg87xz0i-AtKQGhjwJA?e=NH5J5m */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class ConexionMySQL {
    private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/libreria";
    private static final String USER = "root";
    private static final String PWD = "";
    
    static {
        try {
            Class.forName(CONTROLADOR);
        }catch(ClassNotFoundException e){
            System.out.println("Error al cargar el controlador");
        }
    }
    
    public Connection Conectar(){
        
        Connection cnx = null;
        
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException e) {
            System.out.println("Error en la conexión");
        }
        
        return cnx;
        
    }
}
