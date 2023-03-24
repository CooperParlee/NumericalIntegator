package com.cooperparlee.numerical_integrator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntegrateToCSV {
	
	public IntegrateToCSV () throws IOException
	{
		long t1 = new Date().getTime();
		
		main(0 , 1, 2147483646, "Simpson", 15);
		main(0 , 1, 2147483646, "Midpoint", 15);
		main(0 , 1, 2147483646, "Trapezoid", 15);
		
		System.out.println("Completed. Took " + (new Date().getTime() - t1) + " millis.");
	}	
	public void main(int a, int b, int n, String intCase, int problemNumber) throws IOException{
		double dx = ((double)b-a)/n;
		
		List<String[]> lines = new ArrayList<>();
		
		lines.add(new String[] {
				 "", "a=", Integer.toString(a), "b=", Integer.toString(b), "n=", Integer.toString(n), "f(x)=", "", "Case", intCase
		});
		lines.add(new String[] {
				"", "i", "x_i", "f(x_i)"
		});
		
		double sum = 0;
		
		double integralApprox = 0;
		
		switch (intCase) {
			case "Simpson":
				if (!isEven(n)) {
					System.out.println("Error: n is not odd!");
					return;
				}
				for (int i = 0; i <= n; i++) {
					double fxI;
					double i_x = i*dx + a;
					
					if (i == 0 || i == n) {
						fxI = fx(i_x);
					} else if (!isEven(i)){
						fxI = fx(i_x)*2;
					} else {
						fxI = fx(i_x)*4;
					}
					
					sum += fxI;
					
					lines.add(new String[] {"", Integer.toString(i), Double.toString(i_x), Double.toString(fxI)});
				}
				
				integralApprox = sum * dx/3;
				
			break;
			case "Trapezoid":
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
				
				integralApprox = sum * dx/2;
				
			break;
			case "Midpoint":
				for (int i = 1; i <= n; i++) {
					double fxI;
					double x_bar = (i + (i-1))*dx/2.0 + a;
					
					fxI = fx(x_bar);
					
					sum += fxI;
					
					lines.add(new String[] {"", Integer.toString(i), Double.toString(x_bar), Double.toString(fxI)});
				}
				integralApprox = sum * dx;
			break;
			default:
				System.out.println("That's not a case dumbass.");
			return;
		}
		
		
		lines.add(new String[] {"", "Sum :", Double.toString(sum)});
		lines.add(new String[] {"", "Tn :", Double.toString(integralApprox)});
		
		File csvOutputFile = new File("C:\\Users\\cparl\\OneDrive\\Programming Workspaces\\Numerical Integrator\\output\\out " + problemNumber +" - " + intCase + " n" + n +".csv");
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	        lines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
		
	}
	
	private static double fx (double x) {
		return Math.pow(x, 2)/(1+Math.pow(x, 4));
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
	
	public boolean isEven (double value) {
		return (value / 2.0) == Math.floor(value / 2.0); 
	}
}
