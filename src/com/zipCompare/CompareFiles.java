package com.zipCompare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CompareFiles {
	public static boolean filesCompare(InputStream inputStream1, InputStream inputStream2, String path1, String path2)
			throws IOException {
		List<String> file1Lines = readFile(inputStream1);
		List<String> file2Lines = readFile(inputStream2);

		if (file1Lines.size() != file2Lines.size()) {
			if (file1Lines.size() > file2Lines.size()) {
				int file2Size = file2Lines.size();
//				System.out.println("file1 size  " + file1Lines.size() + " file2 size  " + file2Lines.size());
				for (int i = 0; i < file2Lines.size(); i++) {
					if (!file1Lines.get(i).equals(file2Lines.get(i))) {
						System.out.println();
						System.out.println(" file1 Line Lenght  1 " + file1Lines.get(i));
						System.out.println();
						System.out.println(" file2 Line  Lenght  1  " + file2Lines.get(i));
					}
				}
			} else {
				for (int i = 0; i < file1Lines.size(); i++) {
					if (!file2Lines.get(i).equals(file1Lines.get(i))) {
						System.out.println();
						System.out.println(" file1 Line Lenght  2 " + file1Lines.get(i));
						System.out.println();
						System.out.println(" file2 Line  Lenght 2  " + file2Lines.get(i));
					}
				}
			}
			return false;
		}
		for (int i = 0; i < file1Lines.size(); i++) {
			// compare the content of two files in line by line
			if (!file1Lines.get(i).equals(file2Lines.get(i))) {
				System.out.println();
				System.out.println(" file1 Line Content " + file1Lines.get(i));
				System.out.println();
				System.out.println(" file2 Line  Content " + file2Lines.get(i));
				return false;
			}
		}
		return true;
	}
	private static List<String> readFile(InputStream filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(filePath));
		String line = reader.readLine();
		while (line != null) {
			// skip the empty lines and single comments
			if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
				lines.add(line);
			}
			line = reader.readLine();
		}
		reader.close();
		return lines;
	}

}
