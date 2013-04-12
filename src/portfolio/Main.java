package portfolio;

public class Main {

	/**
	 * @param args
	 * @author Mauro
	 */
	private static Configuration portfolio_configuration;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String file="input/input.xml";
		// reading the input file
		portfolio_configuration= new Configuration(file);
		// printing out the information read in input
		portfolio_configuration.print_everything();
		// solving the training problems
		portfolio_configuration.solve_problems();
		//configuring the portfolio, following the structure given in input
		portfolio_configuration.configure();
		//saving the information received in input, not the configured portfolio
		portfolio_configuration.save("input/output.xml");
	}

}
