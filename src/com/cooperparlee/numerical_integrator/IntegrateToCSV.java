package com.cooperparlee.numerical_integrator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegrateToCSV {
	final static int a = 0;
	final static int b = 2;
	final static int n = 10;
	
	
	final static double dx = ((double)b-a)/n;
	
	public IntegrateToCSV () throws IOException
	{
		main();
	}	
	public void main() throws IOException{
		List<String[]> lines = new ArrayList<>();
		
		lines.add(new String[] {
				 "", "a=", Integer.toString(a), "b=", Integer.toString(b), "n=", Integer.toString(n), "f(x)="
		});
		lines.add(new String[] {
				"", "i", "x_i", "f(x_i)"
		});
		
		double sum = 0;
		
		for (int i = 0; i <= n; i++) {
			double fxI;
			double i_x = i*dx + a;
			
			if (i == 0 || i == n) {
				fxI = fx(i_x);
			} else {
				fxI = fx(i_x)*2;
			}
			
			sum += fxI;
			
			lines.add(new String[] {"", Integer.toString(i), Double.toString(i_x), Double.toString(fxI)});
		}
		
		double Tn = sum * dx/2;
		
		lines.add(new String[] {"", "Sum :", Double.toString(sum)});
		lines.add(new String[] {"", "Tn :", Double.toString(Tn)});
		
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
		
		File csvOutputFile = new File("C:\\Users\\cparl\\OneDrive\\Programming Workspaces\\Numerical Integrator\\out " + LocalDateTime.now().format(myFormatObj) + ".csv");
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	        lines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
		
	}
	
	private static double fx (double x) {
		return Math.exp(x)/(1.0+Math.pow(x, 2));
	}
	
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
}
