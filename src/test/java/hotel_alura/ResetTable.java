package hotel_alura;

import java.sql.*;

public class ResetTable {
  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/hotel";
    String user = "root";
    String password = "alura123";

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      // Borrar todos los registros de la tabla
      String deleteSql = "DROP TABLE HUESPEDES";
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(deleteSql);

//      // Reiniciar el valor del id
//      String resetSql = "ALTER TABLE reserva AUTO_INCREMENT = 1";
//      stmt.executeUpdate(resetSql);
      
      System.out.println("La tabla ha sido reiniciada.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}