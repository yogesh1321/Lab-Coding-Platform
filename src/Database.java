

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.*;

import com.mysql.jdbc.PreparedStatement;

public class Database {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String Db_URL = "jdbc:mysql://localhost/project";
	static final String user = "root";
	static final String pass = "ibn8180631";
	Connection conn = null;
	boolean connect()
	{
		
		try
		{
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to Database");
			conn = DriverManager.getConnection(Db_URL,user,pass);
			System.out.println("Database Connected Successfully");
			return true;
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	Connection getconnection()
	{
		return conn;
	}
	
	
	public static void main(String args[])
	{
		
	}
	

}
