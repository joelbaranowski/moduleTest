package com.google.devrel.samples.ttt;

import java.io.Serializable;
public class Cell implements Serializable{
	  public int x;
	  public int y;
	  public int val;
	  
	  public Cell() {
		  
	  }
	  
	  public Cell(int x, int y, int val) {
		  this.x = x;
		  this.y = y;
		  this.val = val;
	  }
}
	