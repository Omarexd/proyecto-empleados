package edu.umg.programacion2.proyecto.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mariadb://127.0.0.1:3306/proyecto_empleados";

    private static final String USUARIO = "proyecto_app";
    private static final String CONTRASENA = System.getenv("DB_PASSWORD");

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
