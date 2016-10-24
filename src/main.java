import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
	public static void main(String[] args)
	{
		String usr ="postgres";
		String pwd ="8686";
		String url ="jdbc:postgresql://localhost:5432/postgres";

		try
		{
			Class.forName("org.postgresql.Driver");
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
		}

		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
	}
}
