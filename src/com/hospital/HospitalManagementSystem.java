package com.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagementSystem {

	private static Connection con;
	static Scanner scan = new Scanner(System.in);
	static String url="jdbc:mysql://localhost:3306/hospital";
	static String user = "root";
	static String password="root";
	static Patients patient =new Patients();
	static Doctors doctor = new Doctors();
	
	public static void main(String[] args) {
	
		while(true) {
		try {
			System.out.println("Welcome to UNION Hospital");
			System.out.println("1.Add patients");
			System.out.println("2.View patients");
			System.out.println("3.View Doctors");
			System.out.println("4.Book An Appointment");
			System.out.println("5.Exit");
			
			System.out.println("Enter your choice");
			int choice = scan.nextInt();
		
		
			switch(choice)
			{
				case 1:
					//add patients
					Patients.addPatient();
					System.out.println();
					break;
				case 2:
					//view patients
					Patients.showPatientDetails();
					System.out.println();
					break;
				case 3:
					//view doctors
					Doctors.showDoctorDetails();
					System.out.println();
					break;
				case 4:
					//book appointment
					bookAppointments(patient,doctor,con);
					System.out.println();
					break;
				case 5:
					//exit
					return;
				default:
					System.out.println("Enter your choice !!");
					System.out.println();
					
					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
		
	}
	
	public static void bookAppointments(Patients patient, Doctors doctor,Connection con)
	{
		System.out.println("Enter patient id:");
		int patient_id = scan.nextInt();
		System.out.println("Enter doctors id:");
		int doctors_id = scan.nextInt();
		System.out.println("Enter appointment date(yyyy-mm-dd):");
		String date = scan.next();
		
		if(patient.getPatientById(patient_id) && doctor.getDoctorsById(doctors_id))
		{
			if(checkAvailability(doctors_id,date,con))
			{
				String sql = "insert into appointments(patientId,doctorId,date) values(?,?,?)";
				try {
					con = DriverManager.getConnection(url,user,password);
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, patient_id);
					pstmt.setInt(2, doctors_id);
					pstmt.setString(3, date);
					
					int n = pstmt.executeUpdate();
					
					if(n>0)
					{
						System.out.println("Appointment is booked");
					}
					else
					{
						System.out.println("Appoint is not bookoed");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				System.out.println("Doctor is not available..");
			}
		}
		else
		{
			System.out.println("Either doctor or patient is not available");
		}
	}
	
	public static boolean checkAvailability(int doctors_id, String date,Connection con)
	{
		String sql = "select count(*) from appointments where id=? and date=?";
		try {
			con = DriverManager.getConnection(url,user,password);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, doctors_id);
			pstmt.setString(2, date);
			
			ResultSet res=pstmt.executeQuery();
			
			if(res.next())
			{
				int n = res.getInt(1);
				if(n==0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
		

}
