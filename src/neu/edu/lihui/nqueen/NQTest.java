package neu.edu.lihui.nqueen;

public class NQTest {
	
	public static void main(String[] args) {
		NQBacktracking bt = new NQBacktracking(8);
		NQMentoCarloBT mcbt = new NQMentoCarloBT(8);
		NQQLearning ql = new NQQLearning(8);
		boolean result = false;
		
		bt.solution();
		while(!result){
			result = mcbt.solution();
		}
		ql.training(1000);
	}
}
