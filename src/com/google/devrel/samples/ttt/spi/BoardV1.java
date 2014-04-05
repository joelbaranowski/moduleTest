/* Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devrel.samples.ttt.spi;

import java.util.*;

import sun.misc.CEFormatException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.devrel.samples.ttt.*;
import com.google.gson.*;

/**
 * Defines v1 of a Board resource as part of the tictactoe API, which provides
 * clients the ability to query for a computer's next move given an input
 * board.
 */
@Api(
    name = "tictactoe",
    version = "v1",
    clientIds = {Ids.WEB_CLIENT_ID, Ids.ANDROID_CLIENT_ID, Ids.IOS_CLIENT_ID},
    audiences = {Ids.ANDROID_AUDIENCE}
)
public class BoardV1 {
  public static final char X = 'X';
  public static final char O = 'O';
  public static final char DASH = '-';
  
  /**
   * Provides the ability to insert a new Score entity.
   * 
   * @param board object representing the state of the board
   * @return the board including the computer's move
   */
  @ApiMethod(name = "board.getmove", path="getmove", httpMethod = "POST")
  public Board getmove(Board board, @Named("id") int id, @Named("row") int row, @Named("col") int col) {
	  System.out.println("id: " + id);
	  
	  
	CellContainer cellContainer = CellContainer.fromJson(board.getState());
	ArrayList<Cell> cells = cellContainer.cells;
	
	ArrayList<Cell> unoccupiedCells = new ArrayList<Cell>();
	for (Cell cell : cells ) {
		System.out.println("row: " + row + " | cell.x: " + cell.x);
		if(row == cell.x && col == cell.y){
			cell.val += (id+1)*10;
			CellContainer updateContainer = new CellContainer(cells);
			Board updated = new Board(CellContainer.toJson(updateContainer));
			return updated;
		}

		if (cell.val <= 10) {
			unoccupiedCells.add(cell);
		}
	}
	
	if(id == 0)
		System.out.println("what");
	Random randomG = new Random();
    Cell randomfreeCell = unoccupiedCells.get(randomG.nextInt(unoccupiedCells.size()));
	randomfreeCell.val = randomfreeCell.val + (id+1)*10;
	
	CellContainer updateContainer = new CellContainer(cells);
	Board updated = new Board(CellContainer.toJson(updateContainer));
    return updated;
  }
  
  
  @ApiMethod(name = "board.getboard", path="getboard", httpMethod = "POST")
  public Board getBoard() {
	  Board newBoard = new Board();
	  return newBoard;
  }
  
  /**
   * Checks for a victory condition.
   * @param {string} boardString Current state of the board.
   * @return {number} Status code for the victory state.
   */
  @ApiMethod(name = "board.checkForVictory", path="checkForVictory", httpMethod = "POST")
  public Status checkForVictory (Board board) {
	
	CellContainer cellContainer = CellContainer.fromJson(board.getState());
	ArrayList<Cell> cells = cellContainer.cells;
	
  	int num_free = 0;
  	int score_player1 = 0;
  	int score_player2 = 0;
  	for (int i = 0; i < cells.size(); i++) {
  		Cell cell = cells.get(i);
  	
  		if (0 < cell.val && cell.val <= 10) {
  			num_free += 1;
  		} else if (10 < cell.val && cell.val <= 20) {
  			score_player1 += cell.val - 10;
  		} else if (20 < cell.val && cell.val <= 30) {
  			score_player2 += cell.val - 20;
  		}
  	}

  	Status s = new Status();

  	System.out.println("num free:" + num_free);
  	if (num_free > 0) {
  		s.setStatus(0);
  		return s;
  	}
  	if (score_player1 > score_player2) {
  		s.setStatus(1);
  		return s;
  	}
  	if (score_player1 < score_player2) {
  		s.setStatus(2);
  		return s;
  	}
  	
  	s.setStatus(3);
  	return s;

  };
  
  public class Status{
	  private int status;
	  
	  public int getStatus(){
		  return status;
	  }
	  
	  public void setStatus(int status){
		  this.status = status;
	  }
  }

}
