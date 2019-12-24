
public class Main {

	public static void main(String[] args) {
		
		double Price=13;
		double S=100;
		double sigma =.1;
		double t0=0;
		double r=0.1; // PUT IT IN DECIMAL 
		int n =4;
		double T =0.4;
		double strike =100;
		boolean put = true;
		boolean call = false;
		boolean AM = true;
		boolean EU = false;
		double window_begin= 0.3;
		double window_end= 0.4;
		double tol = .000001;
		int max_iter=  100;
		Output firstoutput = new Output();															// MUST PICK A DERIVATIVE AND PUT IT IN YOURSELF 
																									//WILL HAVE TO PICK ONE DERIVATIVE AT A TIME AND PASS IT IN
	Derivative firstone = new VanillaOption(strike,T,put,call,EU,AM);               				//CREATING THE DERIVATIVE OBJECT
	MarketData vals = new MarketData(Price, S, sigma, t0, r);	       									// CREATING THE MARKETDATA VALUES
	Derivative firstbermudan = new BermudanOption(strike,T,window_begin,window_end,put,call);		//CREATING THE BERMUDA OPTION, will need to pass in to use it!
	firstoutput=Library.binom(firstone,vals, n);													// PASSING INTO MY LIBRARY CLASS ALSO PASSING INTO OUTPUT
																									// ALSO PASSING INTO OUTPUT AFTER THAT
	System.out.println(firstoutput.FV+" << The fair value"+" ,"+firstoutput.fugit+" << The fugit");
	

	Library.impvol(firstone, vals, n, max_iter, tol, firstoutput);
	System.out.println(firstoutput.impvol+" The impvol ");
	System.out.println(  firstoutput.num_iter+" The num_iter" );
	
	}

}
