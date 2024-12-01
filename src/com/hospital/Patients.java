package com.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Patients {
	static Connection con=null;
	static Scanner scan = new Scanner(System.in);
	
	static String url="jdbc:mysql://localhost:3306/hospital";
	static String user = "root";
	static String password="root";
	
	public static void showPatientDetails()
	{
		
		String sql = "Select * from patients";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,user,password);
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			System.out.println("---------------+---------------+---------------+---------------");
			System.out.println(" Patient Id    |  Patient Name | Patient Age   | Patient Gender ");
			System.out.println("---------------+---------------+---------------+---------------");
			while(res.next())
			{
				System.out.printf("%-15s|%-15s|%-15s|%-15s\n",res.getInt(1),res.getString(2),res.getInt(3),res.getString(4));
				System.out.println("---------------+---------------+---------------+---------------");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void addPatient()
	{
		
		
		try {
			
		
			con = DriverManager.getConnection(url,user,password);
			String sql = "Insert into patients(name,age,gender)"+"values(?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			System.out.println("Enter patient name:");
			pstmt.setString(1, scan.next());
			System.out.println("Enter patient age:");
			pstmt.setInt(2, scan.nextInt());
			System.out.println("Enter patient gender");
			pstmt.setString(3, scan.next());
			
			int n = pstmt.executeUpdate();
			if(n>0)
			{
				System.out.println("Patient Added Successfully");
			}
			else
			{
				System.out.println("Patient Added Not Successfully");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id)
	{
		
		
	
		String sql ="select * from patients where id=?";
		try {
			con = DriverManager.getConnection(url,user,password);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet res = pstmt.executeQuery();
			if(res.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}

