
public class VanillaOption extends Derivative {

	
	public	VanillaOption(){
		
		T =0;
		strike =0;
		put =false;
		call = false;
		AM=false;
		EU=false;
		
	}
	
	
	public	VanillaOption(double strike ,double T, boolean put,boolean call,boolean EU, boolean AM ){
		this.strike= strike;
		this.T=T;
		this.put =put;
		this.call = call;
		this.AM=AM;
		this.EU=EU;
	}
	
	
	
	
	// data members
	public void terminalCondition(Node n) {
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
if(EU==true) {
	
	return;
}

if(AM==true) {

	if(n.intri> n.payoff) {
		n.fugit= n.deltat;
	//	n.fugit= n.t;	
	n.payoff= Math.max(n.intri, n.payoff);
	}
	
}



	}
}
