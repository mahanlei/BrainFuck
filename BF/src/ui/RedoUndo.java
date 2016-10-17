package ui;

import java.util.ArrayList;

public class RedoUndo {
	ArrayList<String> codes = new ArrayList<>();
    ArrayList< String> arrayList=new ArrayList<>();
	public RedoUndo(String originalCodes) {
		codes.add(originalCodes);
	}

	public void save(String newCodes) {
		this.codes.add(newCodes);
	}
}
