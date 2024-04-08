package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class ModuloConnection.
 */
public class ModuloConnection {
	
	/** The Constant USUARIO. */
	private static final String USUARIO = "root";
	
	/** The Constant SENHA. */
	private static final String SENHA = "zeca";
	
	/** The Constant URL. */
	private static final String URL = "jdbc:mysql://localhost:3306/dbagenda?characterEncoding=utf-8";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 * @throws SQLException the SQL exception
	 */
	public static Connection conectar() throws SQLException {		
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
		return conexao;
	}
	
//	public void testeConexao() {
//		try {
//			Connection connection = connection();
//			System.out.println(connection);
//			connection.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
}
