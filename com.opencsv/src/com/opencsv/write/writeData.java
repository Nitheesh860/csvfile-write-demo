package com.opencsv.write;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class writeData{
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException
	{
	String filePath = "/home/samcouser/Documents/csv/Students.csv";
	File file = new File(filePath);
  
    try {
        
        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile);
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] { "Name", "Class", "Marks" });
        data.add(new String[] { "Aman", "10", "620" });
        data.add(new String[] { "Suraj", "10", "630" });
        writer.writeAll(data);
  
       
        writer.close();
        
    }
    
    catch (IOException e) {
        e.printStackTrace();
    }
	Connection con = null;
	Statement stmt = null;

	Class.forName("org.h2.Driver");
	String filename = "/home/samcouser/Documents/csv/Students.csv";
	try 
	(CSVReader reader = new CSVReader(new FileReader(filename))) 
	{
		List<String[]> r = reader.readAll();

		r.forEach(x -> System.out.println(Arrays.toString(x)));
	}

	try {
		con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
		stmt = con.createStatement();
		stmt.execute("drop table CSV if exists");
		stmt.execute("CREATE TABLE CSV(NAME VARCHAR(20),CLASS varchar(20),MARKS varchar(20))");
		System.out.println("TABLE IS CREATED");
		stmt.execute(
				"insert into CSV ( NAME,CLASS,MARKS ) select \"NAME\", \"CLASS\", \"MARKS\" from CSVREAD( '/home/samcouser/Documents/csv/Students.csv', 'NAME,CLASS,MARKS', null )");
		ResultSet rs = stmt.executeQuery("select * from CSV");
		stmt.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}

}

}