import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import sun.rmi.runtime.Log;

import com.mysql.jdbc.DatabaseMetaData;

public class TestSQLite{

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
    	
    	
    	
        if (resultSet != null && resultSet.next()) 
        {
        	int authorID=resultSet.getInt(1);
        	this.addBook(authorID);
            sql = "Select * from comp589_books"; 
            this.query(sql);
        	this.addBook(authorID);
            sql = "Select * from comp589_books"; 
            this.query(sql);
        	System.out.println("Insert a new author ID: "+authorID);
        }

    	
    	
    	
    }
    
    //add a new book for author
    private int addBook(int authorId) throws SQLException{
    	String sql = "INSERT INTO comp589_books (title, author_id, category, price, publisher_id, publish_date) VALUES(?,?,?,?,?,?)";
    	stmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    	stmt.setString(1, "William's book");
    	stmt.setInt(2, authorId);
    	stmt.setString(3, "Computers");
    	stmt.setLong(4, (long) 101.11);
    	stmt.setInt(5, 3);
    	java.util.Date dt = new java.util.Date();

    	java.text.SimpleDateFormat sdf = 
    	     new java.text.SimpleDateFormat("yyyy-MM-dd");

    	String currentTime = sdf.format(dt);
    	stmt.setString(6, currentTime);
    	stmt.executeUpdate();
    	resultSet = stmt.getGeneratedKeys();
        if (resultSet != null && resultSet.next()) 
        {
        	int bookId=resultSet.getInt(1);
        	System.out.println("Insert a new book ID: "+bookId);
        	return bookId;
        }
        
        System.out.println("fail to insert to a new book");
        return 0;
    }

    private void testServer() throws Exception{
//    	mysql
//        Class.forName("com.mysql.jdbc.Driver");
//        // Setup the connection with the DB
//        connect = DriverManager
//            .getConnection("jdbc:mysql://mysql.cms.waikato.ac.nz/sl255?"
//                           + "user=sl255&password=123123");
    	
    	
        Class.forName("org.sqlite.JDBC");
        // Setup the connection with the DB
        connect = DriverManager
            .getConnection("jdbc:sqlite:/home/sl255/Sites/waikatoUni/computer&science/COMP589/Week4/SQLite/sl255.sqlite");

    }

    public static void main(String[] args){
        System.out.println("This is a test");
        try{
            TestSQLite me = new TestSQLite();
            me.testServer();
            me.testDatabase();


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void query(String sql) throws SQLException, ParseException{
    	System.out.println("this is query test");
    	
    	Statement queryStmt = connect.createStatement();
        ResultSet rs = queryStmt.executeQuery(sql);
    	java.sql.DatabaseMetaData meta = connect.getMetaData();
    	ResultSet columns = meta.getColumns("", "", "comp589_books", "");
    	while (columns.next()){
    		System.out.printf("%s \t", columns.getString("COLUMN_NAME"));
    	}
    	System.out.printf("\n");
        while (rs.next()) {
            int book_id = rs.getInt(1);
            String title = rs.getString(2);
            int author_id = rs.getInt(3);
            String category = rs.getString(4);
            long price = rs.getLong("price");
            int publisher_id = rs.getInt("publisher_id");
            String publish_date = rs.getString("publish_date");
            
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            
            Date date = dt.parse(publish_date);
            publish_date = new SimpleDateFormat("yyyy-MM-dd").format(date);
            
            System.out.println(book_id + "\t" + title +
                               "\t" + author_id + "\t" + category +
                               "\t" + price + "\t" + publisher_id+ "\t" + publish_date);
        }
    }
    

}