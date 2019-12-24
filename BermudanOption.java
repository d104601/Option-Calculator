

public class BermudanOption extends Derivative{
	public double window_begin;
	public double window_end;
	
	public	BermudanOption(){
		
		T =0;
		strike =0;
		put =false;
		call = false;
	}
	
	
	public	BermudanOption(double strike ,double T,double window_begin,double window_end, boolean put,boolean call){
		this.strike= strike;
		this.T=T;
		this.window_begin=window_begin;
		this.window_end = window_end;
		this.put=put;
		this.call=call;
	}
	
	
	public void terminalCondition(Node n) {
		// virtual function
		n.fugit= T; // ALL LEAF NODES GOT THERE FUGETS NOW
		n.deltat = T;
		
		if(call==true) {
		if(n.stock< strike) {
			n.payoff =0;
			n.intri =0;
		}else {
			n.payoff= n.stock-strike ;
		}
		}
		
		if(put==true) {
		if(n.stock> strike) {
			n.payoff =0;
			n.intri =0;
		}else {
			n.payoff= strike-n.stock ;}
		
		}
	}
	public void valuationTest(Node n) {
		
	
		
if(window_begin>=T) {										// IF THE WINDOW BEGIN IS PAST THE T THEN THERE WILL BE NO EEX
	return;
}
		
if(n.t>=window_begin&&n.t<=window_end + .00001) {		   // CHECKING THE t THAT WAS SET FOR EVERYNODE IN THE NODE OBJECT 
			if(n.intri> n.payoff) {						   // TOO SEE IF IT WILL BE WITHIN THE WINDOW BY COMPARING THE NODES 't' TO THE
				n.fugit= n.deltat;					       // BEGIN WINDOW AND WILL EEX IF THE INTRIN IS GREATER
				
			n.payoff= Math.max(n.intri, n.payoff);
			}
			
		
}
		
		
		
		
	} // virtual function

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
