package com.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Doctors {
	
	private static Connection con;
	static Scanner scan = new Scanner(System.in);
	static String url="jdbc:mysql://localhost:3306/hospital";
	static String user = "root";
	static String password="root";
	
	
	public static void showDoctorDetails()
	{
		
		
		String sql = "Select * from doctors";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,user,password);
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			System.out.println("---------------+---------------+---------------+");
			System.out.println(" Doctors Id    |  Doctor Name  | Specialization|  ");
			System.out.println("---------------+---------------+---------------+");
			while(res.next())
			{
				System.out.printf("%-15s|%-15s|%-15s|\n",res.getInt(1),res.getString(2),res.getString(3));
				System.out.println("---------------+---------------+---------------|");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorsById(int id)
	{
		String sql ="select * from doctors where id=?";
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
