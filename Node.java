

public final class Node {

	double stock;
	double fugit;
	double fairvalue;
	double payoff;
	boolean put ;
   	boolean call ;
	double intri;
	double deltat;
	double t;
	
	public	Node(){
		fugit = fairvalue = payoff=stock=intri=deltat=0;
		put=call=false;
		
	}
	
	
}
