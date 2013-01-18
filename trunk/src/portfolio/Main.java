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
		portfolio_configuration= new Configuration(file);
		portfolio_configuration.print_everything();
		portfolio_configuration.save("input/output.xml");
	}

}
