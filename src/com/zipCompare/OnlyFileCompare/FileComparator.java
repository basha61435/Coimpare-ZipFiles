package com.zipCompare.OnlyFileCompare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileComparator {
	public static boolean compareFiles(String file1Path, String file2Path) throws IOException {
		List<String> file1Lines = readFile(file1Path);
		List<String> file2Lines = readFile(file2Path);
		for (String a : file1Lines) {
			System.out.println(a);
		}
		if (file1Lines.size() != file2Lines.size()) {
			return false;
		}

		for (int i = 0; i < file1Lines.size(); i++) {
			if (!file1Lines.get(i).equals(file2Lines.get(i))) {
				return false;
			}
		}

		return true;
	}

	private static List<String> readFile(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line = reader.readLine();

		while (line != null) {

			if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
//				System.out.println("line "+line);

				lines.add(line);

			}
//			System.out.println("line "+line);
//			lines.add(s);
			line = reader.readLine();
		}

		reader.close();
		return lines;
	}

	public static void main(String[] args) throws IOException {
		boolean result = FileComparator.compareFiles(
				"D:\\basha\\Spring Boot Projects\\Zip1\\src\\com\\basha\\ZipCompare.java",
				"D:\\basha\\Spring Boot Projects\\Zip1\\src\\com\\basha\\ZipCompare.java");
		System.out.println(result);
	}

	
}
