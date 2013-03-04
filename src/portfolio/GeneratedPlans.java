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
	private Vector<Vector<Element>> Plans;
	//Plans is structured in 2 level. The outer list represents the planners, the inner the training instances
	//e.g. Plans.at(1).at(2) indicates the plan generated by the 2nd planner for the 3rd problem.
	
	//The following 2 lists are used for storing, for each training problem, the best solution and/or 
	//the minimum CPU time across all the considered planners.
	private Vector<Double> Best_quality;
	private Vector<Double> Best_CPU_time;
	
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
		Plans = new Vector<Vector<Element>>();
		Plans.add(new Vector<Element>());
		Best_quality = new Vector<Double>();
		Best_CPU_time = new Vector<Double>();
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
		if(Plans.size() == planner)
			Plans.add(new Vector<Element>());
		Plans.elementAt(planner).add(xmlPlan);
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
	
	public ConfiguredPortfolio portfolio_configuration(int min, int max, float timeout){
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
			to_configure = IPC_same_q(min,max,timeout);
		}else{
			System.out.println("Allocation and/or Selection strategy not supported (yet..)");
		}
		return to_configure;
	}
	
	private ConfiguredPortfolio IPC_same_q(int min, int max, float timeout){
//		ConfiguredPortfolio to_configure = new ConfiguredPortfolio(max);
		Vector<ConfiguredPortfolio> portfolios; 
		int position_selected=-1;
		portfolios = GeneratePortfolios(min,max);
		for(int i=0; i < portfolios.size(); i++){
			//THIS IS THE CYCLE FOR ALLOTTING TIME TO THE PLANNERS IN THE PORTFOLIOS
			Vector<Vector<Float>> CPU_t = new Vector<Vector<Float>> ();
			int size=portfolios.elementAt(i).numberPlanners();
			float allotted=timeout/size;
			for(int h=0; h < size; h++){
				CPU_t.add(new Vector<Float> ());
				CPU_t.elementAt(h).add(allotted);
			}
			portfolios.elementAt(i).setCPU_time(CPU_t);
		}
		calculateBestQuality();
		for (int j=0; j < portfolios.size(); j++){
			// QUA DEVO METTERE POI LA SIMULAZIONE PORTFOLIO PER PORTFOLIO
			SimulateSequentialExecution(portfolios.elementAt(j));
		}
		
		double max_quality=0;
		System.out.println("I configured and tested the following portfolios.");
		for(int i=0; i < portfolios.size(); i++ ){
			System.out.println("["+ i + "] " + portfolios.elementAt(i).toString());
			if(portfolios.elementAt(i).getScore() > max_quality){
				max_quality=portfolios.elementAt(i).getScore();
				position_selected=i;
			}
		}
		System.out.println(">> I selected portfolio ["+position_selected+"] (in case of same score, I prefer the portfolio composed by less planners ;) )");
		return portfolios.elementAt(position_selected);
	}
	
	private void SimulateSequentialExecution(ConfiguredPortfolio to_simulate){
		double quality=java.lang.Double.MAX_VALUE;
		for(int i=0; i < Plans.get(0).size(); i++ ){
			for(int j=0; j < to_simulate.numberPlanners(); j++){
				if (Plans.get(to_simulate.getPlanner(j) ).get(i).getChild("plan").getChildren().size() >= 1){
					if(Float.parseFloat(Plans.get(to_simulate.getPlanner(j) ).get(i).getChild("statistics").getChildText("toolTime")) <= to_simulate.getCPU_time_specific(j).elementAt(0))
						if(quality > Plans.get(to_simulate.getPlanner(j) ).get(i).getChild("plan").getChildren().size())
							quality= Plans.get(to_simulate.getPlanner(j) ).get(i).getChild("plan").getChildren().size();
				}
			}
			if(quality != java.lang.Double.MAX_VALUE)
				to_simulate.addScore( this.Best_quality.elementAt(i) / quality );
		}
		return;
	}
	
	private void calculateBestQuality(){//fills the best_quality list
		//print_all_plans();
		double min;
		for(int i=0; i < Plans.get(0).size(); i++ ){
			min=-1;
			for(int j=0; j < Plans.size(); j++ ){
				if (Plans.get(j).get(i).getChild("plan").getChildren().size() >= 1){
					if(min==-1)
						min=Plans.get(j).get(i).getChild("plan").getChildren().size();
					else
						if(min > Plans.get(j).get(i).getChild("plan").getChildren().size())
							min=Plans.get(j).get(i).getChild("plan").getChildren().size();
				}
			}
			Best_quality.add(min);
		}
		return;
	}
	
	private void calculateBestCPU(){ //fills the best_CPU list
		return;
	}
	
	private Vector<ConfiguredPortfolio> GeneratePortfolios(int min, int max){
		Vector<ConfiguredPortfolio> to_return = new Vector<ConfiguredPortfolio> ();
		int number_of_planners = Plans.size();
		if(max > number_of_planners)
			max=number_of_planners;
		Vector<Vector<String>> prova = MathHelp.Permutations(number_of_planners, max);
		for(int j=min-1; j < prova.size(); j++){
			for(int h=0; h< prova.elementAt(j).size(); h++){
				ConfiguredPortfolio insert = new ConfiguredPortfolio(max);
				//System.out.println("J: "+j+" H: "+h+" "+prova.elementAt(j).elementAt(h));
				//CONFIGURE THE PORTFOLIO
				String[] varie = prova.elementAt(j).elementAt(h).split(",");
				for(int w=0; w < varie.length; w++)
					insert.addPlanner(Integer.parseInt(varie[w]));
				to_return.add(insert);
			}
		}
		return to_return;
	}
	

	
	
}
