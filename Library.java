

public final class Library {
public static Output binom(final Derivative deriv, final MarketData mkt, int n){
	
	boolean put = deriv.put;
	boolean call = deriv.call;
	boolean AM =deriv.AM;
	boolean EU = deriv.EU;
	 double Price=mkt.Price; // market price of security
	 double S=mkt.S; // stock price
	 double r=mkt.r; // interest rate
	 double sigma=mkt.sigma; // volatility
	 double t0=mkt.t0; // current time
	 double deltat= (deriv.T-t0)/n;
	 double ngiven = n;
	 int num_ite=0;
	 double ntemp=ngiven;
	 double nntemp=ngiven;
	 double u = Math.exp(sigma*Math.sqrt(deltat));
	 double d = (1/u);
	double e_rdeltat = Math.exp(r*deltat);
	double presentvalue = Math.exp((-r)*deltat);
	 double p = (e_rdeltat-d)/(u-d);
	 double q = 1-p;
	Node[][] holding = new Node[n+1][n+1];							//CREATING THE ARRAY OF SIZE N+1, A LOT OF SPACE COMPLEXITY 
	double timmm=deriv.T- deltat;
	double fvforoutput = 0 ;
	double fugitforoutput=0;

	for(double i =0; i<=ngiven;i++) // for columns
	{
		
		for (double j = 0; j<=ngiven;j++) {
			num_ite++;
			if(i==0) {											// THIS IS THE CELLS THAT WILL BE VISITED
																// THESE CELLS WILL HAVE THE NODE OBJECTS IN THEM 
			holding[(int) i][(int) j]= new Node();				
			}
			
			if(j==0) {
				holding[(int) i][(int) j]= new Node();
				}
			

			if((i>0)&&(j>0)&&(j<=(ntemp)-i)) {
				
				holding[(int) i][(int) j]= new Node();
			}
			}
	}
	
								
	
	for(double i =0; i<=ngiven;i++) // FOR COLUMNS
	{
		
		for (double j = 0; j<=ngiven;j++) {
			num_ite++;											//CREATING STOCK PRICE FOR EACH NODE WORKING FORWARD AND ALSO THE INTRIN VALUE
			if(i==0&&j<=ngiven) {								
				
			holding[(int) i][(int) j].stock = S*Math.pow(u, j); // STOCK PRICES FOR ALL UPPER OUTTER LAYER 
			if(put == true) {
				holding[(int) i][(int) j].intri = deriv.strike-holding[(int) i][(int) j].stock; //  INTRIN
				}
				if(call == true) {
					holding[(int) i][(int) j].intri = holding[(int) i][(int) j].stock-deriv.strike; // INTRIN	
				}
			}
			
			if(j==0&&i>0) {
				holding[(int) i][(int) j].stock = S*Math.pow(d, i);  // STOCK PRICES FOR MOST DOWN LAYER 
				if(put == true) {
					holding[(int) i][(int) j].intri = deriv.strike-holding[(int) i][(int) j].stock; // INTRIN
					}
					if(call == true) {
						holding[(int) i][(int) j].intri = holding[(int) i][(int) j].stock-deriv.strike; // INTRIN
					}
				
				}
			

			if((i>0)&&(j>0)&&(j<=(nntemp)-i)) {
				
				holding[(int) i][(int) j].stock=holding[(int) i-1][(int) (j)].stock*d; /// GETTING STOCK PRICES FROM ABOVE STOCK USING d ( the middle childs)
				if(put == true) {
				holding[(int) i][(int) j].intri = deriv.strike-holding[(int) i][(int) j].stock; // INTRIN
				}
				if(call == true) {
					holding[(int) i][(int) j].intri = holding[(int) i][(int) j].stock-deriv.strike; // INTRIN	
				}
				
			}
			
			if(j==(nntemp)-i) {    // ALL THE LEAF NODES ARE GIVEN THEIR WEAPONS HERE 
				
			
				deriv.terminalCondition(holding[(int) i][(int) j]);
				holding[(int) i][(int) j].t= deriv.T;

			}
			}
	}
	
	
	
	
	
	// JUST FINISHED GIVING ALL THE STOCK PRICES 
	
//	for(int i = 0; i<=ngiven;i++) // for columns
//	{
//		
//		for (int j = 0; j<=ngiven;j++) {
//			
//			if(i==0&&j<=ngiven) {
//				
//				System.out.print(holding[i][j].stock+"          ");
//			}
//			
//			if(j==0&&i>0) {
//				System.out.print(holding[i][j].stock+"          ");
//				}
//			
//
//			if((i>0)&&(j>0)&&(j<=(ntemp)-i)) {
//				
//				System.out.print(holding[i][j].stock+"          ");
//			
//				
//			}
//			
//			}
//		System.out.println();
//		System.out.println();
//	}
	
//	System.out.println("**************************************************************");
//	System.out.println("**************************************************************");
//	System.out.println("**************************************************************");
	for(int j=(int) (n-1); j >=0;j--)	{					// THIS LOOP DOES NOT START IN THE TERMINAL NODES ,( N-1)
	
		int pp=j;											// WORKING BACKWARDS THROUGH MY 2D ARRAY, WILL CALCULATE FUGIT VALUES, A t TO USE FOR BERMUDA LATER
		if(j<n-1) {											// ALSO AS WORKING BACKWARDS I AM ALSO CALCULATING THE THE PAYOFFS 
		timmm= timmm- deltat;
		}
		for(int i=0;i<=j;i++) {
			holding[i][pp].fugit =  ((holding[i+1][pp].fugit*q)+(holding[i][pp+1].fugit*p)); //oG WORKING BACKWARDS TO FILL IN ALL THE fugits
			holding[i][pp].deltat=timmm;
			double cheker = presentvalue * ((holding[i+1][pp].payoff*q)+(holding[i][pp+1].payoff*p)); //oG WORKING BACKWARDS TO FILL IN ALL THE PAYOFFS
			if(cheker<=0) {
			holding[i][pp].payoff=0;					    // IF PRESENT VALUE IS LESS THAN ZERO, PAY OFF WILL BE SET TO 0
			}else {
				holding[i][pp].payoff=cheker;
			}
			holding[i][pp].t= timmm;
			deriv.valuationTest(holding[i][pp]);			// CALLING MY VALUATION TEST ON EVERY NODE AFTER PAYOFF AND INTRIN AND FUGIT WAS SET
		
				fvforoutput=	holding[i][pp].payoff;		// UPDATING THIS VALUE STRAIGHT TO THE ROOT, IT WILL CONTAIN THE FUGIT 
				fugitforoutput =holding[i][pp].fugit;		// AND PAYOFFS FOR THE LAST NODE TRAVERSED 
				
			
			
			pp--;// MUST KEEP ALWAYS 
		}	
	}
	

//	for(int i =0; i<=ngiven;i++) // for columns
//	{
//		
//		for (int j = 0; j<=ngiven;j++) {
//			
//			if(i==0&&j<=ngiven) {
//				
//				System.out.print(holding[i][j].payoff+"          ");
//			}
//			
//			if(j==0&&i>0) {
//				System.out.print(holding[i][j].payoff+"          ");
//				}
//			
//
//			if((i>0)&&(j>0)&&(j<=(ntemp)-i)) {
//				
//				System.out.print(holding[i][j].payoff
//						+"          ");
//			
//				
//			}
//			
//			}
//		System.out.println();
//		System.out.println();
//	}	

	 Output hereee = new Output(fvforoutput,fugitforoutput);
	return hereee;
	}
public static int impvol(final Derivative deriv, final MarketData mkt, int n,
int max_iter, double tol, Output out) {
	Output trying ;
	double low = .01;
	double high = 2;
	double temp ;
	double diff;
	int counter=0;
	System.out.println("^^^^^^^^^^^^^^^The binomial function^^^^^^^^^^^^^^^");
	System.out.println("****************************************************************");
	System.out.println("****************************************************************");
	System.out.println("****************************************************************");
	
	
	
	
	
	while(counter<max_iter) {
		MarketData vals = new MarketData(mkt.Price, mkt.S, low, mkt.t0, mkt.r);	
		trying = binom( deriv,  vals,  n);
		diff = trying.FV-mkt.Price;
		
		if(Math.abs(diff)<tol) {
			//return low
			out.impvol = low;
			out.num_iter = counter;
			return 0;
			
		}
		
		MarketData vals_for_high = new MarketData(mkt.Price, mkt.S, high, mkt.t0, mkt.r);	
		trying = binom( deriv,  vals_for_high,  n);
		diff = trying.FV-mkt.Price;
		if(Math.abs(diff)<tol) {
			//return high
			out.impvol = high;
			out.num_iter = counter;
			return 0;
			
			
		}
		
		
		temp = ((low+high)/2);
		MarketData mid = new MarketData(mkt.Price, mkt.S, temp, mkt.t0, mkt.r);
		
		trying = binom( deriv,  mid,  n);
		diff = trying.FV-mkt.Price;
		if(diff< 0) {
			low = temp;
		}
		else {
			high = temp;
		}
		
		
		
		counter++;
	}
	Output retval = new Output();
	retval.impvol = retval.num_iter = 0;
	return 1;
	
}
	
	
	
	
}
