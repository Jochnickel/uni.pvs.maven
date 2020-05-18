package de.jj22.uni.pvs.blatt04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.ToLongFunction;

public class Main {
	public static void main(String[] args) {
		final long[] time1 = getExecutionTime(filename -> {
			try {
				return calcSumFromFileLines1(filename);
			} catch (Exception e) {
				throw new Error(e);
			}
		}, "bigInput.txt");
		final long[] time2 = getExecutionTime(filename -> {
			try {
				return calcSumFromFileLines2(filename);
			} catch (Exception e) {
				throw new Error(e);
			}
		}, "bigInput.txt");
		System.out.format("Calculated %d in %d seconds with Scanner\n", time1[1], time1[0]);
		System.out.format("Calculated %d in %d seconds with String to Long\n", time2[1], time2[0]);
	}

	/*
	* @return ExecutionTime, Result
	*/
	public static long[] getExecutionTime(ToLongFunction<String> strToLongFunc, String str) {
		final long startTime = System.currentTimeMillis();
		final long result = strToLongFunc.applyAsLong(str);
		final long execTime = System.currentTimeMillis() - startTime;
		return new long[] { execTime, result };
	}

	public static long calcSumFromFileLines1(String filename) throws FileNotFoundException {
		Scanner sc = new Scanner(new FileInputStream(filename));
		long sum = 0;
		while (sc.hasNextInt()) {
			sum += sc.nextInt();
		}
		return sum;
	}

	public static long calcSumFromFileLines2(String filename) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		long sum = 0;
		while (br.ready()) {
			sum += Integer.parseInt(br.readLine());
		}
		br.close();
		return sum;
	}
}
