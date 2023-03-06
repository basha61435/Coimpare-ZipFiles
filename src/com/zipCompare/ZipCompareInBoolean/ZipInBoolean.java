package com.zipCompare.ZipCompareInBoolean;


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
public class ZipInBoolean {
	public static void main(String[] args1) {
		boolean isZipsareSame = true;
		int differentFileCount = 0;
		String[] args = { "C:\\Users\\Basha\\Downloads\\demo1.zip", "D:\\Zip\\delete Folder\\demo1.zip"};
		if (args.length != 2) {
			System.out.println("Usage: zipcompare [file1] [file2]");
		}
		//extract the zip1file
		ZipFile file1;
		try {
			file1 = new ZipFile(args[0]);
		} catch (IOException e) {
			System.out.println("Could not open zip file " + args[0] + ": " + e);
			return;
		}
		//extract the zip2file
		ZipFile file2;
		try {
			file2 = new ZipFile(args[1]);
		} catch (IOException e) {
			System.out.println("Could not open zip file " + args[0] + ": " + e);
			return;
		}
		//store a zip1 entries  in set1
		Set set1 = new LinkedHashSet();
		for (Enumeration e = file1.entries(); e.hasMoreElements();) {
			set1.add(((ZipEntry) e.nextElement()).getName());
		}
		//store a zip2 entries  in set2
		Set set2 = new LinkedHashSet();
		for (Enumeration e = file2.entries(); e.hasMoreElements();) {
			set2.add(((ZipEntry) e.nextElement()).getName());
		}
		for (Iterator i = set1.iterator(); i.hasNext();) {
			//check the two zip sizes
			if(set1.size()!=set2.size())
				{
					isZipsareSame=false;
					break;
				}
			String name = (String) i.next();
			// ckeck the entries names are preset in zip2
			if (!set2.contains(name)) {
				isZipsareSame=false;
				break;
			}
			try {
				String path1 = "";
				String path2 = "";
				InputStream inputStream1 = null;
				InputStream inputStream2 = null;
				ZipEntry entry1 = (file1.getEntry(name));
				ZipEntry entry2 = (file2.getEntry(name));
				// only allows a files in zip1
				if (!entry1.isDirectory()) {
					inputStream1 = file1.getInputStream(entry1);
					path1 = args[0] + File.separator + entry1.getName();
				} else {
					inputStream1 = null;
				}
				// only allows a files in zip2
				if (!entry2.isDirectory()) {
					inputStream2 = file2.getInputStream(entry2);
					path2 = args[1] + File.separator + entry2.getName();
				} else {
					inputStream2 = null;
				}
				if (inputStream1 != null && inputStream2 != null) {
					boolean result = compareFiles(inputStream1, inputStream2, path1, path2);
					if (!result) {
						differentFileCount++;
						break;
					}
				}
			} catch (Exception e) {
				System.out.println(name + ": IO Error " + e);
				e.printStackTrace();
				continue;
			}
		}
		if (differentFileCount > 0) {
			isZipsareSame = false;
		}
		if(isZipsareSame==true)
		{
			System.out.println("ZipFiles are Matched");
		}
		else {
			System.out.println("ZipFiles are Not Matched");
		}
	}
	public static boolean compareFiles(InputStream inputStream1, InputStream inputStream2, String path1, String path2)
			throws IOException {
		List<String> file1Lines = readFile(inputStream1);
		List<String> file2Lines = readFile(inputStream2);
		// compare the file1Lines and file2Lines sizes 
		if (file1Lines.size() != file2Lines.size()) {
			return false;
		}
		for (int i = 0; i < file1Lines.size(); i++) {
			// compare the content of files in line by line
			if (!file1Lines.get(i).equals(file2Lines.get(i))) {
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
			// does not allow empty lines and single line comments
			if (!line.trim().isEmpty() && !line.trim().startsWith("//")) {
				lines.add(line);
			}
			line = reader.readLine();
		}
		reader.close();
		return lines;
	}
}

