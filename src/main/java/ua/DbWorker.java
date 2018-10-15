package ua;

import java.sql.*;
import java.util.Scanner;

public class DbWorker {
    private DbProperties props = new DbProperties();
    private static Connection connection;

    public DbWorker() {
        try{
            connection = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    protected void initDB(){
        try(Statement st = connection.createStatement()){
            st.execute("DROP TABLE IF EXISTS Rooms");
            st.execute("CREATE TABLE Rooms (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "district VARCHAR(128) NOT NULL, address VARCHAR(128) DEFAULT NULL, " +
                    "space INT NOT NULL, roomsNumber INT NOT NULL, price INT NOT NULL);");
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    protected void addRoom(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter district - ");
        String district = sc.nextLine();
        System.out.print("Enter address - ");
        String address = sc.nextLine();
        System.out.print("Enter space - ");
        String sSpace = sc.nextLine();
        System.out.print("Enter number of rooms - ");
        String sRoomsNumber = sc.nextLine();
        System.out.print("Enter price - ");
        String sPrice = sc.nextLine();
        int space = Integer.parseInt(sSpace);
        int roomsNumber = Integer.parseInt(sRoomsNumber);
        int price = Integer.parseInt(sPrice);
        try(PreparedStatement ps = connection.prepareStatement("INSERT INTO Rooms " +
                "(district, address, space, roomsNumber, price) VALUES (?,?,?,?,?);")) {
            ps.setString(1, district);
            ps.setString(2, address);
            ps.setInt(3, space);
            ps.setInt(4, roomsNumber);
            ps.setInt(5, price);
            ps.executeUpdate();

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        System.out.println();
    }


    protected void selectRoom(){
        while(true){
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the apartment according to the parameters:");
        System.out.println("1 - district");
        System.out.println("2 - space");
        System.out.println("3 - number of rooms");
        System.out.println("-> ");
            String s = sc.nextLine();
            switch (s){
                case "1" :
                    selectDistrict();
                    return;
                case "2" :
                    selectSpace();
                    return;
                case "3" :
                    selectRoomsNumber();
                    return;
                default:
                    break;
            }
        }

    }
    protected void selectDistrict(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter district - ");
        String district = sc.nextLine();
        String query = "SELECT * FROM Rooms WHERE district= '" + district + "';";
        showResult(query);
    }
    protected void selectSpace(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter limit space - ");
        int space = sc.nextInt();
        String query = "SELECT * FROM Rooms WHERE space <= '" + space + "';";
        showResult(query);
    }
    protected void selectRoomsNumber(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rooms - ");
        int roomsNumber = sc.nextInt();
        String query = "SELECT * FROM Rooms WHERE roomsNumber = '" + roomsNumber + "';";
        showResult(query);
    }
    private static void showResult(String query){
        try {
            PreparedStatement st = connection.prepareStatement(query);

            try {
                ResultSet rs = st.executeQuery();

                try {
                    ResultSetMetaData md = rs.getMetaData();
                    for (int i = 1; i < md.getColumnCount(); i++) {
                        System.out.print(md.getColumnName(i) + "\t\t");
                    }
                    System.out.println();

                    while (rs.next()) {
                        for (int i = 1; i < md.getColumnCount(); i++) {
                            System.out.print(rs.getString(i) + "\t\t");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } finally {
                    rs.close();
                }
            }
            finally {
                st.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
