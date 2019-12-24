

public class Output {
	public double FV; // fair value
	public double fugit; // fugit
	public double impvol; // implied volatility
	public int num_iter; // number of iterations to compute implied volatility

	
	public Output(){
		FV= fugit = impvol = num_iter=0;
	}
	
	public Output(double FV, double fugit, double impvol, int num_iter){
		
		this.FV= FV;
		this.fugit = fugit;
		this.impvol = impvol;
		this.num_iter = num_iter;
		
	}
	
	public Output(double FV, double fugit){
		
		this.FV= FV;
		this.fugit = fugit;
		
	}

	public Output(int num_iter){
	this.num_iter=num_iter;
}


}
