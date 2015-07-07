package neu.edu.lihui.nqueen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class NQBacktracking {
	
	private Integer[] chessBoard;
	private int size;
	int queenCount = 0;
	LinkedList<Index> stack;
	HashSet<Index> set;
	private int counter;
	private ChessBoard board;
	
	public NQBacktracking(int n) {
		// TODO Auto-generated constructor stub
		this.chessBoard = new Integer [n];
		this.size = n;
		this.counter = 0;
//		this.board = new ChessBoard(80, n);
	}
	
	public boolean solution(){
		//initialize chess board
		board = new ChessBoard(80, size);
		//No solution can be found when chess board is too small
		if (size < 4) 
			return false;
		stack = new LinkedList<>();								//create stack to store result
		chessBoard[0] = 0;
		queenCount = 1;											//Set start position
		Index id = new Index(1, 0);	
		stack.push(id);											//Push first queen to stack
		board.setColor(id.row, id.col, Color.green);
		while(!stack.isEmpty() && queenCount < size){
			id = stack.peek();
			if(!canFind(chessBoard, id)){
				//No further move can be made, go back to last position.
				Index idx = stack.pop();
				board.restoreColor(idx.row, idx.col);		
				queenCount--;
				counter++;
			}
		}
		
		//Solution find, print out stack
		if(queenCount == size){
			for(Index index : stack){
				System.out.print(index);
			}
			System.out.println();
			System.out.println("Total moves: " + counter);
			return true;
		}
			
		else
			return false;
	}
	
	//Check if current position is valid to place a queen
	private boolean next(LinkedList<Index> st, Index index){
		
		for(Index id : st){
			if(id.row == index.row || id.col == index.col ||
					(id.row - index.row == id.col - index.col)
					|| (id.row - index.row == index.col - id.col)){
				return false;
			}
		}
		
		return true;
	}
	
	//Try to find current index's promising child node
	private boolean canFind(Integer[] queens, Index id){
		//Try to find a valid position
		Index temp = null;
		boolean result = false;
		
		for(int i = 0; i<size; i++){
			temp = new Index(i, id.col+1);
			//Get previous position in order to check if next position has already been used.
			Index pre = stack.peek();						
			if(next(stack, temp) && !pre.isExist(temp)){
				//Add new position to previous position in order to be traceable when do backtrack!
				stack.peek().children.add(temp);
				stack.push(temp); 
				board.setColor(temp.row, temp.col, Color.green);
				queenCount++;
				result = true;
				counter++;
				break;
			}
		}		
		return result;
		
	}
	
	private class Index{
		int row;
		int col;
		ArrayList<Index> children;//Keep track of next column of used position. 
		
		public Index(int r, int c) {
			this.row = r;
			this.col = c;
			this.children = new ArrayList<NQBacktracking.Index>();
		}
		
		public boolean isExist(Index id){
			for(Index index: children){
				if(index.equals(id)) return true;
			}
			return false;
		}
		
		@Override
		public boolean equals(Object obj) {
			boolean result = false;
			if(obj instanceof Index){
				Index id = (Index)obj;
				return result = ((this instanceof Index) && id.col == this.col && id.row == this.row);
			}
			return result;
		}
		
		@Override
		public int hashCode(){
			return (41*(41 + row) + col);
		}
		
		@Override
		public String toString(){
			return "["+String.valueOf(row)+" ," + String.valueOf(col) + "], "; 
		}
	}

}

