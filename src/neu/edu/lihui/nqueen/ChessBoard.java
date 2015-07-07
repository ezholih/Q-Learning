package neu.edu.lihui.nqueen;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessBoard extends JFrame {  
  
     
    private static final long serialVersionUID = 1L;  
      
    private JPanel jp=new JPanel();  
    private int boardSize;
    private int gridSize;
    private JLabel[][] labels;
          
    public ChessBoard(int grid, int board){  
        this.boardSize = board;
        this.gridSize = grid;
        this.labels = new JLabel[boardSize][boardSize];
        jp.setLayout(null);
        
        //Construct chess board with label array. 
        for(int i=0;i<boardSize;i++){  
            for(int j=0;j<boardSize;j++)  
            {  
                Color color=Color.white;  
                JLabel label=new JLabel();
                labels[i][j] = label;
                label.setSize(gridSize, gridSize);  
                label.setLocation(i*gridSize, j*gridSize);  
                if((i+j)%2==0)  
                     color=Color.black;  
                  
                label.setOpaque(true);  
                label.setBackground(color);  
                label.setBorder(BorderFactory.createLineBorder(Color.black));  
                jp.add(label);  
                add(jp, BorderLayout.CENTER);  
            }  
        }
        
        this.setSize(gridSize*boardSize+10,gridSize*boardSize+35);  
        this.setLocationRelativeTo(null);  
        this.setVisible(true);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }  
    
    //Set specific color when a queen is placed on chess board
    public void setColor(int row, int col, Color color){
    	labels[col][row].setBackground(color);
    	labels[col][row].setBorder(BorderFactory.createLineBorder(color));
    	try {
			Thread.currentThread().sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //restore original color when a queen is removed from chess board
    public void restoreColor(int row, int col){
    	if(labels[Math.abs(col-1)][row].getBackground() == Color.black){
    		labels[col][row].setBackground(Color.white);
    		labels[col][row].setBorder(BorderFactory.createLineBorder(Color.white));
    	}else{
    		labels[col][row].setBackground(Color.black);
    		labels[col][row].setBorder(BorderFactory.createLineBorder(Color.black));
    	}
    }
  
//    public static void main(String[] args) {  
//  
////        try {  
////            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());    
////        }catch (Exception e) {  
////            e.printStackTrace();  
////        }  
//         Chess chessBord=new Chess(50, 10);  
////         chessBord.setSize(510,535);  
////         chessBord.setLocationRelativeTo(null);  
////         chessBord.setVisible(true);  
////         chessBord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
//         chessBord.setColor(1, 0, Color.green);
//         chessBord.restoreColor(1, 0);
//    }  
}

