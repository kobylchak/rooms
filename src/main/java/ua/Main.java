package ua;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DbWorker db = new DbWorker();
        db.initDB();
        while(true){
            System.out.println("1 - add room");
            System.out.println("2 - select room");
            System.out.println("3 - exit");
            System.out.print(" -> ");
            int choise = scanner.nextInt();
            switch (choise){
                case 1 :
                    db.addRoom();
                    break;
                case 2 :
                    db.selectRoom();
                    break;
                default:
                    return;
            }
        }
    }
}
