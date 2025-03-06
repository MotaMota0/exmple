import java.sql.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passw = "user";
    public static void main(String[] args) {
        //createTable();
        //deletedId(1);
        //readData();
        updateDataEmail("march@gmail.com",3);

    }

    public static void createTable(){
        String sql = "INSERT INTO users_table (id, name_user, email,password_hash) VALUES (3, 'March', 'march@example.com','1234');";

        try(Connection con =DriverManager.getConnection(url,user,passw);
            PreparedStatement pr = con.prepareStatement(sql))
        {

            int rs = pr.executeUpdate();

            if (rs > 0) {
                System.out.println("Данные успешно вставлены!");
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static  void  deletedId(int id){
        String sql= "DELETE FROM users_table WHERE id = ?";

        try(Connection con =DriverManager.getConnection(url,user,passw);
            PreparedStatement pr = con.prepareStatement(sql))
        {
            pr.setInt(1,id);
            pr.executeUpdate();
            System.out.println("Success");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void readData(){
        String sql= "Select * from users_table";

        try(Connection con =DriverManager.getConnection(url,user,passw);
            PreparedStatement pr = con.prepareStatement(sql);
            ResultSet rs = pr.executeQuery())
        {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name_user");
                String email = rs.getString("email");
                String password = rs.getString("password_hash");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Password: " + password);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateDataEmail(String email,int id){
        System.out.println("Update email with id \n Your update email "+email+" and id "+ id);
        String sql = "update users_table set email = ? where id = ? ";

        try(Connection con =DriverManager.getConnection(url,user,passw);
            PreparedStatement pr = con.prepareStatement(sql))
        {
            pr.setString(1,email);
            pr.setInt(2,id);

            pr.executeUpdate();
            System.out.println("Success");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}