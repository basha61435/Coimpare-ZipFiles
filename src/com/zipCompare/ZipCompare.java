package com.zipCompare;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipCompare {
	public static void main(String[] args1) {
		String[] args = { "C:\\Users\\Basha\\Downloads\\demo1.zip", "D:\\Zip\\delete Folder\\demo1.zip"};
		if (args.length != 2) {
			System.out.println("Usage: zipcompare [file1] [file2]");
			System.exit(1);
		}
		// extract the zip file stored into file1
		ZipFile file1;
		try {
			file1 = new ZipFile(args[0]);
		} catch (IOException e) {
			System.out.println("Could not open zip file " + args[0] + ": " + e);
			return;
		}
		// extract the zip file stored into file1
		ZipFile file2;
		try {
			file2 = new ZipFile(args[1]);
		} catch (IOException e) {
			System.out.println("Could not open zip file " + args[0] + ": " + e);
			return;
		}
		System.out.println("Comparing " + args[0] + " with " + args[1] + ":");
		// Add the all entries in set1
		Set set1 = new LinkedHashSet();
		for (Enumeration e = file1.entries(); e.hasMoreElements();) {
			set1.add(((ZipEntry) e.nextElement()).getName());
		}
		// Add the all entries in set1
		Set set2 = new LinkedHashSet();
		for (Enumeration e = file2.entries(); e.hasMoreElements();) {
			set2.add(((ZipEntry) e.nextElement()).getName());
		}
		int errcount = 0;
		int filecount = 0;
		int missMatchContentFile = 0;
		// compare the entries name same are not
		for (Iterator i = set1.iterator(); i.hasNext();) {
			String name = (String) i.next();
//			System.out.println("name is "+name);
			if (!set2.contains(name)) {
				System.out.println();
				System.out.println(name + "    not found in    " + args[1]);
				errcount += 1;
				continue;
			}
			// compare the content of the files
			try {
				String path1 = "";
				String path2 = "";
				InputStream inputStream1 = null;
				InputStream inputStream2 = null;
				ZipEntry entry1 = (file1.getEntry(name));
				ZipEntry entry2 = (file2.getEntry(name));
				if (!entry1.isDirectory()) {
					inputStream1 = file1.getInputStream(entry1);
					path1 = args[0] + File.separator + entry1.getName();
				} else {
					inputStream1 = null;
				}
				if (!entry2.isDirectory()) {
					// System.out.println("entry name 2 "+entry2.getName());
					inputStream2 = file2.getInputStream(entry2);
					path2 = args[1] + File.separator + entry2.getName();
				} else {
					inputStream2 = null;
				}
				if (inputStream1 != null && inputStream2 != null) {
					boolean result =CompareFiles. filesCompare(inputStream1, inputStream2, path1, path2);

					if (!result) {
						System.out.println();
						System.out.println(entry1 + " content is not matched ");
						missMatchContentFile += 1;
						// continue;
					}
				}
			} catch (Exception e) {
				System.out.println(name + ": IO Error " + e);
				e.printStackTrace();
				errcount += 1;
				continue;
			}
			filecount += 1;
		}
		System.out.println();
		System.out.println("File1 Entries  " + file1.size() + "  File2 Entries  " + file2.size());
		System.out.println();
		System.out.println(filecount + " entries are equals");
		if (errcount > 0) {
			System.out.println();
			System.out.println(errcount + " entries is not found");
		}
		if (missMatchContentFile > 0) {
			System.out.println();
			System.out.println(missMatchContentFile + " file content is not match");
		}
	}
	// taking a InputStream return if content is matched or not
//	public static boolean compareFiles(InputStream inputStream1, InputStream inputStream2, String path1, String path2)
//			throws IOException {
//		List<String> file1Lines = readFile(inputStream1);
//		List<String> file2Lines = readFile(inputStream2);
//
//		if (file1Lines.size() != file2Lines.size()) {
//			if (file1Lines.size() > file2Lines.size()) {
//				int file2Size = file2Lines.size();
////				System.out.println("file1 size  " + file1Lines.size() + " file2 size  " + file2Lines.size());
//				for (int i = 0; i < file2Lines.size(); i++) {
//					if (!file1Lines.get(i).equals(file2Lines.get(i))) {
//						System.out.println();
//						System.out.println(" file1 Line Lenght  1 " + file1Lines.get(i));
//						System.out.println();
//						System.out.println(" file2 Line  Lenght  1  " + file2Lines.get(i));
//					}
//				}
//			} else {
//				for (int i = 0; i < file1Lines.size(); i++) {
//					if (!file2Lines.get(i).equals(file1Lines.get(i))) {
//						System.out.println();
//						System.out.println(" file1 Line Lenght  2 " + file1Lines.get(i));
//						System.out.println();
//						System.out.println(" file2 Line  Lenght 2  " + file2Lines.get(i));
//					}
//				}
//			}
//			return false;
//		}
//		for (int i = 0; i < file1Lines.size(); i++) {
//			// compare the content of two files in line by line
//			if (!file1Lines.get(i).equals(file2Lines.get(i))) {
//				System.out.println();
//				System.out.println(" file1 Line Content " + file1Lines.get(i));
//				System.out.println();
//				System.out.println(" file2 Line  Content " + file2Lines.get(i));
//				return false;
//			}
//		}
//		return true;
//	}
//	private static List<String> readFile(InputStream filePath) throws IOException {
//		List<String> lines = new ArrayList<>();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(filePath));
//		String line = reader.readLine();
//		while (line != null) {
//			// skip the empty lines and single comments
//			if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
//				lines.add(line);
//			}
//			line = reader.readLine();
//		}
//		reader.close();
//		return lines;
//	}

}
