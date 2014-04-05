package com.google.devrel.samples.ttt;

import java.util.ArrayList;

import com.google.gson.*;
public class CellContainer {
	
	public ArrayList<Cell> cells;

	public CellContainer() {
		cells = new ArrayList<Cell>();
	}
	
	public CellContainer(ArrayList<Cell> cellsIn) {
		this.cells = cellsIn;
	}
	
	public void add(Cell cell) {
		cells.add(cell);
	}
	
	public static CellContainer fromJson(String str) {
		Gson gson = new Gson();
		return gson.fromJson(str, CellContainer.class);
	}
	
	public static String toJson(CellContainer container) {
		Gson gson = new Gson();
		System.out.println("to json: " + gson.toJson(container, CellContainer.class));
		return gson.toJson(container, CellContainer.class);
	}
}