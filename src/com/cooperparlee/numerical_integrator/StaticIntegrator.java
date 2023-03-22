package com.cooperparlee.numerical_integrator;

import java.io.IOException;

public class StaticIntegrator {
	public static void main (String[]args) {
		try {
			IntegrateToCSV integrate = new IntegrateToCSV();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
