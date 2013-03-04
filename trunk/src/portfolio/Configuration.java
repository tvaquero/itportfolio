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
//import org.jdom.input.SAXBuilder;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;



import languages.xml.*;

public class Configuration {
	String name;
	String authors;
	String date_creation;
	String description;
	String scope; //this could be domain-specific, domain-independent, instance-specific. It will be constrained in input phase
	String target; //runtime, quality, solved_problems (checked in input)
	int size_min;
	int size_max;
    float planners_timeout;
	String scheduling; //tbd the different accepted values
	Vector<String> training_instances;
	Vector<String> domains; //a domain for each instance, repeated even if it is always the same one
//	Vector<String> planners; //the list of planners. Later we will use a better structure for them.
	List<Element> consideredplannerslist;
	Vector<Metric> metrics;
	GeneratedPlans plans;
	String allocation;
	String selection;
	ConfiguredPortfolio configured;
	
public Configuration(){
	//TO-DO: just load predefined values;
	training_instances=new Vector<String>();
	domains=new Vector<String>();
//	planners=new Vector<String>();
	metrics=new Vector<Metric>();
	plans = new GeneratedPlans();
	consideredplannerslist = new ArrayList<Element>();
	return;
}

public Configuration(String file_name){
	training_instances=new Vector<String>();
	domains=new Vector<String>();
	plans = new GeneratedPlans();
//	planners=new Vector<String>();
	metrics=new Vector<Metric>();
	consideredplannerslist = new ArrayList<Element>();
	
	
	//reading the input xml file
	Document configDoc = null;
	try{
		configDoc = XMLUtilities.readFromFile(file_name);
	}catch (Exception e){
		e.printStackTrace();
		return;
	} 
	Element configData = null;
	if (configDoc != null){
		configData = configDoc.getRootElement();
		Iterator it;
		name = configData.getChildText("name");
		authors = configData.getChildText("authors");
		date_creation = configData.getChildText("date");
		description = configData.getChildText("description");
		scope = configData.getChildText("scope");
		target = configData.getChildText("target");
		size_min = Integer.parseInt((configData.getChild("size")).getAttributeValue("min"));
		size_max = Integer.parseInt((configData.getChild("size")).getAttributeValue("max"));
		scheduling = configData.getChildText("schedulingStrategy");
		allocation = configData.getChildText("allocationStrategies");
		selection = configData.getChildText("selectionTechnique");
		//reading trainingInstances
		Element trainingInstancesNode = configData.getChild("trainingInstances");
		List trainingInstances = trainingInstancesNode.getChildren();
		it = trainingInstances.iterator();
		while(it.hasNext()){
			Element el1 = (Element) it.next();
			domains.add(el1.getChildText("domain_file"));
			training_instances.add(el1.getChildText("instance_file"));
		}

		//getting available planners from itPlanners.xml		
		org.jdom.Document itPlannersDoc = null;
		try{
			itPlannersDoc = XMLUtilities.readFromFile("resources/planners/itPlanners.xml");
		}catch(Exception e){e.printStackTrace();}		
		Element itPlanners = null;
		if (itPlannersDoc != null){
			itPlanners = itPlannersDoc.getRootElement();			
		}
		System.out.println("Planners Available: " + itPlanners.getChild("planners").getChildren().size());
		
		//reading planners
        planners_timeout = Float.parseFloat((configData.getChild("consideredPlanners")).getAttributeValue("timeout"));
		Element plannersNode = configData.getChild("consideredPlanners");
		List consideredPlanners = plannersNode.getChildren();
		it = consideredPlanners.iterator();
		while(it.hasNext()){
			Element current = (Element) it.next();
//			planners.add(current.getAttributeValue("id"));
			//TODO: look for the planner in the itPlanners file 
			// and then add it to the list. Now 'planner' needs to be a list of Element ()
			if (itPlanners != null){
				Element correspondingplanner = null;
			    try {
			    	XPath path = new JDOMXPath("planners/planner[@id='"+ current.getAttributeValue("id") +"']");
			    	correspondingplanner = (Element)path.selectSingleNode(itPlanners);
			    } catch (JaxenException e) {
			    	e.printStackTrace();
			    }
			    if (correspondingplanner != null){
			    	consideredplannerslist.add(correspondingplanner);
			    }
			}	
		}
		//reading metrics
		Element metricsNode = configData.getChild("evaluationMetrics");
		List consideredMetrics = metricsNode.getChildren();
		it = consideredMetrics.iterator();
		while(it.hasNext()){
			Element el1 = (Element) it.next();
			Metric help = new Metric(Integer.parseInt(el1.getAttributeValue("id")), el1.getChildText("description"),el1.getChildText("type") );
			metrics.add(help);
		}
	}
	return;
}

public void print_everything(){
	System.out.println("It was the "+date_creation+" when "+authors+" decided to configure "+name+".");
	System.out.println("They described this portfolio as: \""+description+"\". Its scope is "+scope+", and its target "+target);
	System.out.println("It has a minimum size of "+size_min+" and a max size of "+size_min+". The scheduling is "+scheduling+" and the allocation is "+allocation+"; the selection technique is "+selection+".");
	System.out.print("The portfolio considers the following planners: ");
	for(int i=0; i < consideredplannerslist.size(); i++){
		//System.out.print(planners.elementAt(i)+", ");
		System.out.print(consideredplannerslist.get(i).getChildText("name")+", ");
	}
	System.out.println("and is trained on the following instances: ");
	for(int i=0; i < training_instances.size(); i++)
		System.out.print(training_instances.elementAt(i)+", ");
  System.out.println("with a timeout of "+ planners_timeout +" ");
	System.out.println(" and what about the metrics? here they are: ");
	for(int i=0; i < metrics.size(); i++)
		(metrics.elementAt(i)).print();
	System.out.println();
	return;
}

public void save(String fileName){
	String formattedXml ="<itPortfolioConfiguration>\n" +
		"\t <name>" + name + "</name>\n" +
		"\t <authors>" + authors +"</authors>\n" +
		"\t <date>" + date_creation + "</date>\n"+
		"\t <description>"+ description +"</description>\n" +
		"\t <scope>"+ scope + "</scope>\n"+
		"\t <target>" + target + "</target>\n" +
		"\t <size min='"+size_min+"' max='"+size_max+"' />\n" +
		"\t <schedulingStrategy>"+scheduling+"</schedulingStrategy>\n"+
		"\t <consideredPlanners timeout='"+ planners_timeout +"' >\n";
	for (int i=0; i < consideredplannerslist.size(); i++)
		formattedXml+="\t\t<planner id='"+consideredplannerslist.get(i).getAttributeValue("id")  +"' />\n";
	formattedXml+="\t </consideredPlanners>\n \t<trainingInstances>\n";
	for (int i=0; i < training_instances.size(); i++)
		formattedXml+="\t\t<instance id='"+i+"'>\n"+
		"\t\t\t<domain_file>"+domains.elementAt(i)+"</domain_file>\n"+
		"\t\t\t<instance_file>"+training_instances.elementAt(i)+"</instance_file>\n"+
		"\t\t</instance>\n";
	formattedXml+="\t</trainingInstances>\n";
	formattedXml+="\t<evaluationMetrics>\n";
	for (int i=0; i<metrics.size(); i++)
		formattedXml+=metrics.elementAt(i).toXml();
	formattedXml+="\t</evaluationMetrics>\n";
	formattedXml+="\t<selectionTechnique>"+selection+"</selectionTechnique>\n"+
		"\t<allocationStrategies>"+allocation+"</allocationStrategies>\n";
	formattedXml+="</itPortfolioConfiguration>";	
	try{
		  // Create file 
		  FileWriter fstream = new FileWriter(fileName);
		  BufferedWriter out = new BufferedWriter(fstream);
		  out.write(formattedXml);
		  //Close the output stream
		  out.close();
	}catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
	}
	return;
}

public void solve_problems(){
	System.out.println("We are now running the planners on the training instances.. Be patient");
	for(int i=0; i< consideredplannerslist.size(); i++){
		for(int j=0; j < training_instances.size(); j++ ){
			System.out.println("Planner "+ consideredplannerslist.get(i).getChildText("name") + " on problem "+ training_instances.elementAt(j) );
	    	ExecPlanner s = new ExecPlanner(consideredplannerslist.get(i) , domains.elementAt(j), training_instances.elementAt(j) ,false);
	    	//TODO: set the project, domain and problem names
	    	s.setProblemName("problem"+ Integer.toString(j));
	        s.setDomainName("domain1"+ Integer.toString(j));
	        s.setProjectName("project");
	    	
	    	Element xmlPlan = s.solveProblem();
	    	plans.save_plan(i, j, xmlPlan);
	    	//XMLUtilities.printXML(xmlPlan);
		}
	}
	//plans.print_all_plans();
	System.out.println();
	return;
}

public void configure(){
	plans.setAllocation(allocation);
	plans.setSelection(selection);
	plans.setTarget(target);
	configured=plans.portfolio_configuration(size_min,size_max, planners_timeout);
	return;
}

}
