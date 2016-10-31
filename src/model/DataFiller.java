package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import model.collection.GameCollectionManager;

public class DataFiller {
	
	private final List<String> dataLines = new ArrayList<>();
	private final Map<Integer, List<String>> dataMap = new HashMap<>();
	private GameCollectionManager gameCollectionManager;
	private SimpleDateFormat sdf;
	
	public DataFiller() {
		gameCollectionManager =  new GameCollectionManager();
		fillDataLines();
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	private void fillDataLines() {
		InputStream input = getClass().getResourceAsStream("games.txt");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = reader.readLine()) != null) 
				dataLines.add(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addToDataMap(int index) {
		String line = dataLines.get(index);
		List<String> data = new ArrayList<>();
		int id = Integer.parseInt(getNextValue(line));
		
		line = removeLastPart(line);
		while (line.contains(",")) {
			data.add(getNextValue(line));
			line = removeLastPart(line);
		}
		line = line.trim();
		data.add(line);
		dataMap.put(id, data);
	}
	
	private String removeLastPart(String line) {
		line = line.replaceFirst(line.substring(0, line.indexOf(",") + 1), "");
		return line;
	}
	
	private String getNextValue(String line) {
		return line.substring(0, line.indexOf(","));
	}
	
	public void insertGame(int id) {
		List<String> data = dataMap.get(id);
		Date date = getDate(data);
		if (date == null)
			return;
		Document document = gameCollectionManager.createGameDocument(id, data.get(1), "", "", 10, 5, data.get(3), data.get(5), 5, date);
		gameCollectionManager.insertNewGameDocument(document);
	}
	
	private Date getDate(List<String> data) {
		List<Integer> values = new ArrayList<>();
		for (int i = 7 ; i < data.size() ; i++)
			if (parseInt(data.get(i)))
				values.add(Integer.parseInt(data.get(i)));
		String date = "";
		for (int i = 0 ; i < values.size() ; i++) {
			if (i < values.size() - 1)
				date += values.get(i) + "/";
			else date += values.get(i);
		}
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean parseInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException exception) {	}
		return false;
	}
	
	public List<String> getDataLines() {
		return dataLines;
	}

}
