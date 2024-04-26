package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SqlConnection {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/firstdb";
        String username = "postgres";
        String password = "123456";

        Scanner scan = new Scanner(System.in);
        System.out.println("tabloya eklenecek isim");
        String kullaniciAdi = scan.nextLine();

        System.out.println("tabloya eklenecek soyisim");
        String kullaniciSoyadi = scan.nextLine();

        System.out.println("tabloya eklenecek mail");
        String kullaniciMail = scan.nextLine();

        System.out.println("tabloya eklenecek yaş");
        String kullaniciYas = scan.nextLine();

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl,username,password);

            String insertSQL = "INSERT INTO odev (isim, soyisim, mail, yas) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);

            insertStatement.setString(1,kullaniciAdi);
            insertStatement.setString(2,kullaniciSoyadi);
            insertStatement.setString(3,kullaniciMail);
            insertStatement.setString(4,kullaniciYas);

            int affectedrow = insertStatement.executeUpdate();
            System.out.println("insert işlemi başarılı. toplam insert adedi :" + affectedrow);
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String kullaniciData = "kullanicibilgileri.txt";
            FileWriter writer = new FileWriter(kullaniciData);
            writer.write("isim : " + kullaniciAdi + "\n" );
            writer.write("soyisim : " +kullaniciSoyadi + "\n" );
            writer.write("mail : " +kullaniciMail + "\n");
            writer.write("yaş : " +kullaniciYas + "\n");

            writer.close();
            System.out.println("bilgiler başarılı bir şekilde dosyaya yazıldı.");

        }catch (IOException e){
            System.out.println("dosya yazma işlemi başarız : " + e.getMessage());
        }

    }
}