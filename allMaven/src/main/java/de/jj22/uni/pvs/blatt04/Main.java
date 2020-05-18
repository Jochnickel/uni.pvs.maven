package de.jj22.uni.pvs.blatt04;

import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;

public class Main 
{
	public static void main( String[] args )
	{
		String fn = "bigInput.txt";
		ToLongFunction<String> foo1 = (filename) -> {calcSumFromFileLines1(filename);};
		final long time1 = getExecutionTime(()->{calcSumFromFileLines1()}, args);
	}

	public static long getExecutionTime(Consumer f, Object... args) {
		final long startTime = System.currentTimeMillis();
		f.accept(args);
		return System.currentTimeMillis() - startTime;
	}

	public static long calcSumFromFileLines1(String filename){
		Scanner sc = new Scanner(new FileInputStream(filename));
		long sum = 0;
		while (sc.hasNextInt()){
			sum += sc.nextInt();
		}
		return sum;
	}

	public static long calcSumFromFileLines2(String filename){
		BufferedReader br = new BufferedReader(new FileInputStream(filename));
		long sum = 0;
		while (br.ready()){
			sum += Integer.parseInt(br.readLine());
		}
		return sum;
	}
}
