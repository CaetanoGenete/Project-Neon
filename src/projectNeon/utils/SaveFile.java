package projectNeon.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class SaveFile {

	Properties properties = new Properties();
	
	public void saveConfiguration(String path ,String key, int value, String comment) {
		try {
			File file = new File(path);
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = "", oldText = "", existingLine = null, existingComment = null;
			
			boolean commentExists = false;

			while((line = reader.readLine()) != null) {
				if(getKey(line).matches(key)) existingLine = line;
				if(line != null && !line.contains(":") && comment != null) {
					if(!line.matches(comment))existingComment = line;
					else commentExists = true;
				}
				if(line != "") oldText += line + "\r\n";
			}
			reader.close();
			
			String newText = oldText;
			
			if(existingComment != null && comment != null) {
				System.out.println(comment);
				if(!existingComment.matches(comment)) {
					if(!commentExists)oldText += "}\r\n" + comment + "\r\n{\r\n";
				}
			} else {
				if(comment != null) {
					if(!commentExists)oldText += comment + "\r\n{\r\n";
				}
			}
			if(existingLine != null) newText = oldText.replace(key + ":" + getValue(existingLine, key), key + ":" + value);
			else newText = oldText + "    " + key + ":" + value + "\r\n";
			
			FileWriter writer = new FileWriter(path);
			writer.write(newText);
			writer.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveConfiguration(String path ,String key, String value, String comment) {
		try {
			File file = new File(path);
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = "", oldText = "", existingLine = null, existingComment = null;
			
			boolean commentExists = false;

			while((line = reader.readLine()) != null) {
				if(getKey(line).matches(key)) existingLine = line;
				if(line != null && !line.contains(":") && comment != null) {
					if(!line.matches(comment))existingComment = line;
					else commentExists = true;
				}
				if(line != "") oldText += line + "\r\n";
			}
			reader.close();
			
			String newText = oldText;
			
			if(existingComment != null && comment != null) {
				System.out.println(comment);
				if(!existingComment.matches(comment)) {
					if(!commentExists)oldText += comment + "\r\n{\r\n";
				}
			} else {
				if(comment != null) {
					if(!commentExists)oldText += comment + "\r\n{\r\n";
				}
			}
			if(existingLine != null) newText = oldText.replace(key + ":" + getValue(existingLine, key), key + ":" + value);
			else newText = oldText + "    " + key + ":" + value + "\r\n";
			
			FileWriter writer = new FileWriter(path);
			writer.write(newText);
			writer.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String loadConfiguration(String path, String key) {
		try {
			File file = new File(path);
			
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = "";
			
			while((line = reader.readLine()) != null) {
				if(line.contains(key)) return getValue(line, key);
			}
			
			reader.close();
			
			return null;
			
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getValue(String line, String key) {
		return line.replace(key + ":", "").replace("    ", "");
	}
	
	public String getKey(String line) {
		String result = "";
		for(int i = 0; i < line.length(); i++) {
			String letter = String.valueOf(line.charAt(i));
			if(letter.matches(":")) return result;
			if(!letter.matches(" "))result += letter;
		}
		return line;
	}
	
}
