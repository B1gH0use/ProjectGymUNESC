package PackageDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class testSQL {
	public static void main(String[] args) {
		try {
			Connection conn = connectionFactory.getConnection("localhost", 3306, "unesc", "root", "root");
			if(conn != null) {
				System.out.println("Conectado");
			}
		} catch (SQLException e) {
			System.out.println("Exce��o na conex�o: " + e.getMessage());
		}
	}
}
