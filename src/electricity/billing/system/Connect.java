
package electricity.billing.system;
import java.sql.*;
public class Connect {
  Connection c;
  Statement s;
  Connect() {
    try {
      // Class.forName("com.mysql.cj.jdbc.Driver");  // 1st register driver class

       c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebs", "root", "123456"); // 2nd create connection
          
       s = c.createStatement();      // 3rd create statement under connection 
       
    } catch(Exception e) {
       e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new Connect();
  }
}