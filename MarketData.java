

public class MarketData {
	
		public double Price; // market price of security
		public double S; // stock price
		public double r; // interest rate
		public double sigma; // volatility
		public double t0; // current time

		
		public MarketData(){
			
			
			Price = S = r = sigma = t0 = 0;
		}
		
		
		public MarketData(double Price, double S, double sigma, double t0,double r){
			
			this.Price=Price;
			this.S= S;
			this.sigma = sigma;
			this.t0=t0;
			this.r = r;
			
			
		}
		
		
		
		
}
