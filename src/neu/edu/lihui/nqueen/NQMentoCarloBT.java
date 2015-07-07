package neu.edu.lihui.nqueen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class NQMentoCarloBT {
	
	private Integer[] chessBoard;
	private int size;
	int queenCount = 0;
	LinkedList<Index> stack;
	HashSet<Index> set;
	private int counter;
	private ChessBoard board;
	
	public NQMentoCarloBT(int n) {
		// TODO Auto-generated constructor stub
		this.chessBoard = new Integer [n];
		this.size = n;
		this.counter = 0;
		
	}
	
	public boolean solution(){
		//No solution can be found when chessboard is too small
		counter = 0;
		this.board = new ChessBoard(80, size);
		if (size < 4) 
			return false;
		
		stack = new LinkedList<>();
		chessBoard[0] = 0;
		queenCount = 1;
		Index id = new Index(1, 0);	
		stack.push(id);
		board.setColor(id.row, id.col, Color.yellow);
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
	
	//Find all promising children for current node and randomly select one as return value 
	private Index next(LinkedList<Index> st, int col){
		ArrayList<Index> promisingNodes = new ArrayList<>();
		boolean valid;
		
		for (int i = 0; i < size; i++) {
			Index candidate = new Index(i, col);
			valid = true;
			for (Index id : st) {
				if (id.row == candidate.row || id.col == candidate.col
						|| (id.row - candidate.row == id.col - candidate.col)
						|| (id.row - candidate.row == candidate.col - id.col)) {
					valid = false;
					break;
				}
			}
			if(valid) promisingNodes.add(candidate);
		}
		
		if(promisingNodes.size() != 0){
			int rad = getRandom(promisingNodes.size());
			return promisingNodes.get(rad);
		}
		return null;
	}
	
	private boolean canFind(Integer[] queens, Index id){
		//Try to find a valid position
		Index temp = null;
		boolean result = false;
		
		//Prepare previous node to keep track of selected promising children
		Index pre = stack.peek();
		//Get next queen's position randomly. 
		temp = next(stack, id.col+1);
		if(temp != null && !pre.isExist(temp)){
			stack.peek().children.add(temp);
			stack.push(temp);
			board.setColor(temp.row, temp.col, Color.yellow);
			queenCount++;
			counter++;
			result = true;
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
			this.children = new ArrayList<NQMentoCarloBT.Index>();
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
			return "["+String.valueOf(row)+", " + String.valueOf(col) + "], "; 
		}
	}
	
	private int getRandom(int bound)
	{
		int n = bound;

		int[] radArray = new int[bound];
		for(int i = 0; i<n ; i++){
			radArray[i] = i;
		}
		
		for (int i = 0; i < n; i++) {
			int r = i + (int) (Math.random() * (n - i));
			int temp = radArray[r];
			radArray[r] = radArray[i];
			radArray[i] = temp;
		}
		return radArray[0];
	}
}
