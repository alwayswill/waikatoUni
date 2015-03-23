import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class TestMySQL{

    private Connection connect;
    public PreparedStatement stmt = null;
    private ResultSet resultSet = null;
    private void testDatabase() throws Exception{
    	String sql = "insert into comp589_authors (first_name, last_name, country, website) values(?,?,?,?)";
    	
    	stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    	//'William', 'Lee', 'cn', 'http://www.baidu.com'
    	stmt.setString(1, "William");
    	stmt.setString(2, "Lee");
    	stmt.setString(3, "cn");
    	stmt.setString(4, "http://www.baidu.com");
    	stmt.executeUpdate();
    	resultSet = stmt.getGeneratedKeys();
    	int authorID=resultSet.getInt(1);
    	
        if (resultSet != null && resultSet.next()) 
        {
        	System.out.println("Insert a new author ID: "+authorID);
        }

        
       

    	
    	
    	
    }

    private void testServer() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager
            .getConnection("jdbc:mysql://mysql.cms.waikato.ac.nz/sl255?"
                           + "user=sl255&password=123123");

    }

    public static void main(String[] args){
        System.out.println("This is a test");
        try{
            TestMySQL me = new TestMySQL();
            me.testServer();
            me.testDatabase();


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //   Now get some metadata from the database
        // Result set get the result of the SQL query
        
        System.out.println("The columns in the table are: ");
        
        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
          System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
      }

}