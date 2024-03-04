package org.example;

import java.sql.*;
import java.util.Scanner;

public class Operations {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        Connection conn;
        try {
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/demo","postgres","Pravallika@501");
            System.out.println("connection successfully");

        } catch (SQLException e) {
            System.out.println("connection failed");
            //e.printStackTrace();
        }
    }
    public static boolean userAuthenticate(Connection conn){
        System.out.println("enter userid:");
        String uid=sc.next();
        System.out.println("enter pin:");
        int pin=sc.nextInt();
        String q="select *from bankdata where uid=?";
        try {
            PreparedStatement ps=conn.prepareStatement(q);
            ps.setString(1,uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
               String userid=rs.getString(1);
               int pinp=rs.getInt(2);
               if(uid.equalsIgnoreCase(userid) && pin==pinp){
                   return true;
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }
    public static void deposit(Connection conn){
        System.out.println("enter account number:");
        int ac=sc.nextInt();
        System.out.println("enter amount to deposit");
        int amount=sc.nextInt();
        if(amount>0) {
            try {
                String que = "update bankdata set balance=balance+? where acno=?";
                PreparedStatement ps = conn.prepareStatement(que);
                ps.setInt(1, amount);
                ps.setInt(2, ac);
                int row=ps.executeUpdate();
                if(row>0) System.out.println("Amount is Deposited successfully");
                else System.out.println("Deposite not successful:Invalid details");
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else System.out.println("Deposite not successful:Invalid details");

    }
    public static void withdraw(Connection conn){
        int bal=0;
        System.out.println("enter account number:");
        int ac=sc.nextInt();
        System.out.println("enter amount to withdraw");
        int amount=sc.nextInt();
        if(amount<=0) {
            System.out.println("Invalid amount");
            throw new RuntimeException();
        }
        try {
            String q="select *from bankdata where acno=?";
            String que="update bankdata set balance=balance-? where acno=?";
            PreparedStatement p=conn.prepareStatement(q);
            PreparedStatement ps=conn.prepareStatement(que);
            p.setInt(1,ac);
            ResultSet rs=p.executeQuery();
            if(rs.next())
                bal=rs.getInt("balance");
            if(bal>amount) {
                ps.setInt(1, amount);
                ps.setInt(2, ac);
                ps.executeUpdate();
                System.out.println("Amount is withdraw successfully");
                conn.close();
            }
            else System.out.println("With draw not possible");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void balanceEnquiry(Connection conn){
        System.out.println("enter account number:");
        int ac=sc.nextInt();
        try {
            String que="select *from bankdata where acno=?";
            PreparedStatement ps=conn.prepareStatement(que);
            ps.setInt(1,ac);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                int a = rs.getInt(3);
                String s = rs.getString(4);
                int t = rs.getInt(5);
                System.out.println("Account no:" + a + "\n Account Holdername:" + s + "\n Balance:" + t);
                conn.close();
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        	System.out.println("invalid");
        }
    }
}
