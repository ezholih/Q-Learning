package neu.edu.lihui.nqueen;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;
import jxl.write.WriteException;

public class NQQLearning {

	private final int n;
	private Integer[] col;
	private int coln = 1;
	private int[][] PT;
	private double[][] qTable;
	private int runcount1 = 0;
	private boolean finished;
	private ChessBoard board;

	public NQQLearning(int num) {
		// TODO Auto-generated constructor stub
		this.n = num;
		this.col = new Integer[n+1];
		this.PT = new int[n+1][n+1];
		this.qTable = new double[n+1][n+1];
		this.finished = false;
	}

	public void queens(int i) {
		int j;
		int jp = 0;
		
		for (j = 1; j <= n; j++) {
			if (promising(i, j)) {
				if (jp == 0)
					jp = j;
				if (PT[i][jp] > PT[i][j])
					jp = j;
			}
		}
		if (jp != 0) {
			coln++;
			col[coln] = jp;
			System.out.print("[" + coln + ", " + jp + "], ");
			PT[coln][jp]++;
			runcount1++;
			if (coln != n)
				queens(coln + 1);
			else {
				System.out.println("Number of position a queen " + runcount1);
				finished = true;
				QTcount();
			}
		} else {
			System.out.println("Episod finished!");
			QTcount();
		}
	}

	private boolean promising(int i, int j) {

		for (int k = 1; k < i; k++) {
			if (col[k] == j)
				return false;
			if (k - i == col[k] - j)
				return false;
			if (k - i == j - col[k])
				return false;
		}

		return true;
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
	
	public void training(int iteration){
		for(int i = 0; i < iteration; i++){
			int initRow = getRandom(n) + 1;
			System.out.println("Initial row is: " + initRow);
			coln = 1;
			col = new Integer[n+1];
			col[coln] = initRow;
			System.out.print("[" + coln + ", " + initRow + "], ");
			PT[coln][initRow]++;
			queens(coln+1);
//			printTable();
			if(finished) break;
		}
		board = new ChessBoard(80, n);
		for(int i = 1; i <= n; i++){
			board.setColor(col[i]-1, i-1, Color.cyan);
		}
		printQtable();
		createExcel();
	}

//	private void printTable(){
//		for(int i = 1; i < n+1; i++){
//			for(int j = 1; j < n+1; j++){
//				System.out.print(PT[j][i] + ", ");
//			}
//			System.out.println();
//		}
//	}
	
	private void printQtable(){
		for(int i = 1; i < n+1; i++){
			for(int j = 1; j < n+1; j++){
				if(col[j] == i && j != n){ 
					qTable[i][j] = qTable[i][j]+Math.pow(0.9, n-j);
				}
				System.out.print(qTable[i][j] + ", ");
			}
			System.out.println();
		}
	}
	
	private void createExcel(){
		File file = new File("QTable.xls");
		try {
			WritableWorkbook workBook = Workbook.createWorkbook(file);
			WritableSheet sheet = workBook.createSheet("Qtable", 0);
			for(int i = 1; i <= n; i++){
				for(int j = 1; j<=n; j++){
					Number number = new Number(j, i, qTable[i][j]);
					sheet.addCell(number);
				}
			}
			workBook.write();
			workBook.close();
		} catch (IOException | WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void QTcount(){
		for(int i = 1; i <= n; i++){
			if(col[i] != null){
				if(i ==n ){
					qTable[col[i]][i]++;
					break;
				}
				qTable[col[i]][i] = 1 + 0.9*max(i+1);
			}
		}
		
	}
	
	private double max(int col){
		double result=0;
		for(int i = 1; i <= n; i++){
			if(col <= n){
				if(qTable[i][col] >= result)
					result = qTable[i][col];
			}
		}
		
		return result;
	}
	
//	public static void main(String[] args) {
//		NQQLearning ql = new NQQLearning(10);
//		ql.training(500);
//	}
}
