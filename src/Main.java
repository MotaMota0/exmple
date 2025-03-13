import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String passw = "user";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Удалить пользователя");
            System.out.println("3. Показать всех пользователей");
            System.out.println("4. Обновить email пользователя");
            System.out.println("5. Выход");
            System.out.print("Введите номер операции: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.print("Введите ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите email: ");
                    String email = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    createUser(id, name, email, password);
                    break;
                case 2:
                    System.out.print("Введите ID пользователя для удаления: ");
                    int deleteId = scanner.nextInt();
                    deletedId(deleteId);
                    break;
                case 3:
                    readData();
                    break;
                case 4:
                    System.out.print("Введите ID пользователя для обновления email: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Введите новый email: ");
                    String newEmail = scanner.nextLine();
                    updateDataEmail(newEmail, updateId);
                    break;
                case 5:
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }

    public static void createUser(int id, String name, String email, String password) {
        String sql = "INSERT INTO users_table (id, name_user, email, password_hash) VALUES (?, ?, ?, ?);";
        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setInt(1, id);
            pr.setString(2, name);
            pr.setString(3, email);
            pr.setString(4, password);
            int rowsInserted = pr.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Пользователь успешно добавлен!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletedId(int id) {
        String sql = "DELETE FROM users_table WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(sql)) {

            pr.setInt(1, id);
            int rowsDeleted = pr.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Пользователь удален.");
            } else {
                System.out.println("Пользователь с таким ID не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void readData() {
        String sql = "SELECT * FROM users_table";
        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(sql);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name_user");
                String email = rs.getString("email");
                String password = rs.getString("password_hash");
                System.out.println("ID: " + id + ", Имя: " + name + ", Email: " + email + ", Пароль: " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDataEmail(String email, int id) {
        String sql = "UPDATE users_table SET email = ? WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, user, passw);
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, email);
            pr.setInt(2, id);
            int rowsUpdated = pr.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Email успешно обновлен!");
            } else {
                System.out.println("Пользователь с таким ID не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
