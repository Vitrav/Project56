package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Database {
	
	private final String driver = "org.postgresql.Driver";
	private final String url = "jdbc:postgresql://localhost/Project5";
	private final String username = "postgres";
	private final String password = "132132";
	private Connection con;
	
	private static Database instance;
	
	public Database(Connection con) {
		this.con = con;
	}

	private Database() {
		instance = this;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}
	}

	public void createTable(String tableName, List<String> values, String primaryKey) {
		String tableValues = valuesToString(values);

		try {
			String statement = primaryKey == "" ? "CREATE TABLE IF NOT EXISTS " + tableName + "(" + tableValues + ")"
					: "CREATE TABLE IF NOT EXISTS " + tableName + "(" + tableValues + ", " + "PRIMARY KEY(" + primaryKey
							+ "))";
			con.prepareStatement(statement).executeUpdate();
			System.out.println(tableName + " table created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createTable(String tableName, List<String> values) {
		createTable(tableName, values, "");
	}
	
	public void createTable(String tableName, List<String> values, List<String> primaryKeys) {
		String tableValues = valuesToString(values);
		String keyValues = valuesToString(primaryKeys);

		try {
			String statement = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + tableValues + ", " + "PRIMARY KEY(" + keyValues
							+ "))";
			con.prepareStatement(statement).executeUpdate();
			System.out.println(tableName + " table created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dropTable(String tableName) {
		try {
			con.prepareStatement("DROP TABLE " + tableName).executeUpdate();
			System.out.println(tableName + " table removed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertInto(String tableName, List<String> values) {
		String tableValues = valuesToString(values);

		try {
			con.prepareStatement("INSERT INTO " + tableName + " VALUES(" + tableValues + ")").executeUpdate();
			System.out.println("Values added.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateValue(String tableName, String rowName, String newValue, String checkRowName, String checkValue) {
		try {
			con.prepareStatement("UPDATE " + tableName + " SET " + rowName + " = '" + newValue + "'" + " WHERE "
					+ checkRowName + " = " + checkValue).executeUpdate();
			System.out.println(rowName + " changed to " + newValue + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRow(String tableName, String value1, String value2) {
		try {
			con.prepareStatement("DELETE FROM " + tableName + " WHERE " + "'" +  value1 + "'" + " = " + "'" + value2 + "'").executeUpdate();
			System.out.println("Row from " + tableName + " deleted.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteRow(String tableName, String value1, String value2, String value3, String value4) {
		try {
			con.prepareStatement("DELETE FROM " + tableName + " WHERE " + "'" +  value1 + "'" + " = " + "'" + value2 + "'" + " AND " + "'" + value3 + "'" + " = " + "'" + value4 + "'").executeUpdate();
			System.out.println("Row from " + tableName + " deleted.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addForeignKey(String tableName, String key, String refTable, String refKey) {
		try {
			con.prepareStatement("ALTER TABLE " + tableName + " ADD FOREIGN KEY(" + key + ")" + " REFERENCES "
					+ refTable + "(" + refKey + ")").executeUpdate();
			System.out.println("Foreign key added.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropForeignKey(String tableName, String key) {
		try {
			con.prepareStatement("ALTER TABLE " + tableName + " DROP FOREIGN KEY " + key).executeUpdate();
			System.out.println("Foreign key removed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet runSelectAllStatement(String tableName) {
		try {
			return con.createStatement().executeQuery("SELECT * FROM " + tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet runSelectStatement(String tableName, List<String> values) {
		String stateValues = valuesToString(values);

		try {
			return con.createStatement().executeQuery("SELECT " + stateValues + " FROM " + tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void printResultSet(ResultSet rs, Integer rows) {
		try {
			while (rs.next()) {
				String row = "";
				for (Integer i = 1 ; i < rows + 1 ; i++) 
					row += rs.getString(i) + " ";
				System.out.println(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getValues(String statement) {
		try {
			return con.createStatement().executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Connection getConnection() {
		return con;
	}

	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}
	
	private String valuesToString(List<String> values) {
		String tableValues = "";
		for (Integer i = 0; i < values.size(); i++) 
			tableValues += i != values.size() - 1 ? ", " : "";
		return tableValues;
	}

}
