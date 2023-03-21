package com.cooperparlee.numerical_integrator;

public class IntegrateTest {
	final static int a = 0;
	final static int b = 2;
	final static int n = 10;
	
	final static double dx = ((double)b-a)/n;
	
	public static void main(String[] args){
		double sum = 0;
		
		System.out.println("Initial Values: a: " + a + " b: " + b + " n: "+ n + " dx: " + dx);
		
		for (int i = 0; i <= n; i++) {
			double fxI;
			double i_x = i*dx + a;
			
			if (i == 0 || i == n) {
				fxI = fx(i_x);
			} else {
				fxI = fx(i_x)*2;
			}
			
			sum += fxI;
			
			System.out.println("i: " + i + " x_i: " + i_x + " f(x_I): " + fxI);
		}
		
		System.out.println("Sum: " + sum);
		double Tn = sum * dx/2;
		
	}
	
	private static double fx (double x) {
		return Math.exp(x)/(1.0+Math.pow(x, 2));
	}
}
