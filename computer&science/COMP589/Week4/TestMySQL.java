import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class TestMySQL{
   
    private Connection connect;

    private void testDatabase() throws Exception{
	
	
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
    
}
