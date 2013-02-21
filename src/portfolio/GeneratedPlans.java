package portfolio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.util.Vector;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.jdom.JDOMXPath;
import org.jdom.*;

import languages.xml.*;

public class GeneratedPlans {

	//This class is used for storing the plans generated by the planners
	private List<List<Element>> Plans;
	//Plans is structed in 2 level. The outer list represents the planners, the inner the training instances
	//e.g. Plans.at(1).at(2) indicates the plan generated by the 2nd planner for the 3rd problem.
	
	String allocation; // the allocation strategy
	String selection; //the selection strategy
	String target; //the target to optimize
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public GeneratedPlans(){
		Plans = new ArrayList<List<Element>>();
	}
	
	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	
	public void save_plan(int planner, int problem, Element xmlPlan){
		if(Plans.size() <= planner)
			Plans.add(new ArrayList<Element>());
		List<Element> single= Plans.get(planner);
		single.add(xmlPlan);
		Plans.set(planner, single);
		return;
	}
	
	public Element get_plan(int planner, int problem){
		return Plans.get(planner).get(problem);
	}
	
	public void print_all_plans(){
		for(int i=0; i < Plans.size(); i++)
			for(int j=0; j < Plans.get(i).size(); j++){
				XMLUtilities.printXML(Plans.get(i).get(j));
			}
	}
	
	public ConfiguredPortfolio portfolio_configuration(int min, int max){
		ConfiguredPortfolio to_configure = new ConfiguredPortfolio(max);
//		for(int i=0; i< consideredplannerslist.size()-1; i++){
		//			for(int j=0; j < training_instances.size(); j++ ){
		//				Element test = plans.get_plan(i, j);
		//				System.out.println(test.getChild("statistics").getChildText("time"));
		//			}
		//		}
		String technique=allocation+selection+target;
		System.out.println(technique);
		if(technique.equals("sametimeIPC-scorequality")){
			to_configure = IPC_same_q(min,max);
		}else{
			System.out.println("Allocation and/or Selection strategy not supported (yet..)");
		}
		return to_configure;
	}
	
	private ConfiguredPortfolio IPC_same_q(int min, int max){
//		ConfiguredPortfolio to_configure = new ConfiguredPortfolio(max);
		Vector<ConfiguredPortfolio> portfolios; 
		int position_selected=-1;
		portfolios = GeneratePortfolios(min,max);
		for(int i=0; i < portfolios.size(); i++)
			System.out.println(portfolios.elementAt(i).toString());
		for(int j=0; j < Plans.size(); j++){
			//WE HAVE TO WORK HERE. WE HAVE GENERATED ALL THE POSSIBLE PORTFOLIO AND NOW WE SHOULD
			//COMPARE THEM
		}

		return portfolios.elementAt(position_selected);
	}
	
	private Vector<ConfiguredPortfolio> GeneratePortfolios(int min, int max){
		Vector<ConfiguredPortfolio> to_return = new Vector<ConfiguredPortfolio> ();
		int number_of_planners = Plans.size();
		//int total_portfolios = 0;
		//int fact_planners= MathHelp.factorial(number_of_planners);
		if(max > number_of_planners)
			max=number_of_planners;
			//total_portfolios += ( fact_planners/MathHelp.factorial(number_of_planners - i) );
		Vector<Vector<String>> prova = MathHelp.Dispositions(number_of_planners, max);
		for(int j=min-1; j < prova.size(); j++){
			for(int h=0; h< prova.elementAt(j).size(); h++){
				ConfiguredPortfolio insert = new ConfiguredPortfolio(max);
				System.out.println("J: "+j+" H: "+h+" "+prova.elementAt(j).elementAt(h));
				//CONFIGURE THE PORTFOLIO
				String[] varie = prova.elementAt(j).elementAt(h).split(",");
				for(int w=0; w < varie.length; w++)
					insert.addPlanner(Integer.parseInt(varie[w]));
				to_return.add(insert);
			}
		}
		
		//System.out.println(total_portfolios);
		// D() = number_planner! / (number_planner - min) + ..... + number_planner! / (number_planner - max)
		return to_return;
	}
	

	
	
}
