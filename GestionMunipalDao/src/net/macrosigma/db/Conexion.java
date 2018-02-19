package net.macrosigma.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private Connection conn;

	public Conexion() {
		// ConexionDS();
		// ConexionN();
		ConexionP();
	}

	// public void ConexionN() {
	// Properties props = new Properties();
	// try
	// {
	// props.put("user", "sernac");
	// props.put("password", "sernac");
	// Driver driver = (Driver)
	// Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	// DriverManager.registerDriver(driver);
	// //String url = "jdbc:oracle:thin:@192.168.10.61:1521:orcl";
	// String url = "jdbc:oracle:thin:@macrosigma-HP:1521:XE";
	// //String url =
	// "jdbc:oracle:thin:@192.168.10.27:1521:macseguros";//Desarrollo Base:27
	// Servidor:28
	// //String url =
	// "jdbc:oracle:thin:@192.168.10.13:1521:macseguros";//Produccion Base:13
	// Servidor:13
	// conn = DriverManager.getConnection(url, props);
	// conn.setAutoCommit(false);
	// } catch(Exception ex) {
	// System.out.println(ex.getMessage());
	// }
	// }

	public void ConexionP() {
//		Properties props = new Properties();
		String usuario = "";
		String clave = "";
		String url = "";
		try {

//			usuario = "snacionales";
//			clave = "snacionales";
//			url = "jdbc:postgresql://localhost:5432/db_sistema_gestion_municipal";

			/**
			 * path conection Local
			 */
			// String usuario = "snacionales";
			// String clave = "snacionales";
			// String url = "jdbc:postgresql://192.168.1.10:5432/macrosn";

			/**
			 * path conection Publica
			 */
			usuario = "snacionales";
			clave = "snacionales";
			url = "jdbc:postgresql://181.199.108.18:5432/db_sistema_gestion_municipal";

			DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver").newInstance());
			conn = DriverManager.getConnection(url, usuario, clave);

			// conn = DriverManager.getConnection(url, usuario, clave);
			conn.setAutoCommit(false);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

	// public void ConexionDS(){
	// DataSource ds = null;
	// InitialContext ic;
	// try {
	// Driver driver = (Driver)
	// Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
	// DriverManager.registerDriver(driver);
	// ic = new InitialContext();
	// ds = (DataSource)ic.lookup( "java:jboss/OracleDS" );
	// conn = ds.getConnection();
	// conn.setAutoCommit(false);
	// }catch(Exception e){
	// System.out.println( getClass() + " DS Exception->" +e.getMessage());
	// }
	// DataSource ds = null;
	// PreparedStatement pr = null;
	// InitialContext ic;
	// try {
	// ic = new InitialContext();
	// ds = (DataSource)ic.lookup( "java:jboss/OracleDS" );
	// conn = ds.getConnection();
	// pr =
	// conn.prepareStatement("SELECT secfp, tipofp, descripcion FROM
	// cxc_forma_pago");
	// ResultSet rs = pr.executeQuery();
	// while (rs.next()) {
	// System.out.println("<br> " +rs.getString("secfp") + " | "
	// +rs.getString("descripcion"));
	// }
	// rs.close();
	// pr.close();
	// }catch(Exception e){
	// System.out.println("Exception->" +e.getMessage());
	// System.out.println("Exception thrown " +e);
	// }finally{
	// if(conn != null){
	// cerrar();
	// }
	// }
	// }

	public Connection getConexion() {
		return conn;
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String cerrar() {
		try {
			conn.close();
		} catch (SQLException e) {
			return e.getMessage();
		}
		return "";
	}

	public static String jbossReporte() {
		return System.getenv("JBOSSREPORTE");
	}

}
