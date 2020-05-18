package de.jj22.uni.pvs.blatt04;

import java.io.BufferedReader;
import java.util.Scanner;

public class Main 
{
	public static void main( String[] args )
	{
		final long time1 = getExecutionTime(calc, args)
	}

	public static long getExecutionTime(Function f, Object ...args){
		final long startTime = System.currentTimeMillis();
		f(args);
		return System.currentTimeMillis() - startTime;
	}

	public static calcSumFromFileLines1(String filename){
		Scanner sc = new Scanner(new FileInputStream(filename));
		long sum = 0;
		while (sc.hasNextInt()){
			sum += sc.nextInt();
		}
		return sum;
	}

	public static calcSumFromFileLines2(String filename){
		BufferedReader br = new BufferedReader(new FileInputStream(filename));
		long sum = 0;
		while (br.ready()){
			sum += Integer.parseInt(br.readLine());
		}
		return sum;
	}
}
