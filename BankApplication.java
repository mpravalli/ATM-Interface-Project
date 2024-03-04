package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankApplication {
    static Connection conn;
    public static void main(String[] args){
        try {
            Class.forName("org.postgresql.Driver");
            //System.out.println("class loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("class is not found");
            throw new RuntimeException(e);
        }
        try {
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/demo","postgres","Pravallika@501");
            System.out.println("connection successfully");

        } catch (SQLException e) {

            System.out.println("connection failed");
            e.printStackTrace();
        }

        Scanner sc=new Scanner(System.in);
        boolean user=Operations.userAuthenticate(conn);
        if(user) {
            System.out.println("Welcome to our bank..!!");
            System.out.println("1.Deposit\n2.Withdraw\n3.Balance Enquiry\n4.Exit");
            System.out.println("Enter your choice");
            int n=sc.nextInt();
            switch (n) {
                case 1:
                    Operations.deposit(conn);
                    break;
                case 2:
                    Operations.withdraw(conn);
                    break;
                case 3:
                    Operations.balanceEnquiry(conn);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
        else{
            System.out.println("Invalid credentials");
        }
    }
}
