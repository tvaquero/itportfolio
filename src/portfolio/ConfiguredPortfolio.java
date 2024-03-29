package portfolio;

import java.util.Arrays;
import java.util.Vector;

/*
 * This class is used for saving the information of a configured portfolio. It is also used for testing 
 * different configurations of the same-structured portfolio.
 * OrderedPlanners is a list of ids of selected planners, CPU_time is a list of the CPU_time that the planner will run.
 */

public class ConfiguredPortfolio {


	int[] orderedPlanners;
	Vector <Vector<Float> > CPU_time;
	double score; // the score achieved by that portfolio on the training problems
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public void addScore(double add){
		this.score+=add;
		return;
	}

	public ConfiguredPortfolio(int size){
		CPU_time = new Vector <Vector<Float>>();
		orderedPlanners = new int[size];
		for(int i=0; i < size; i++){
			orderedPlanners[i]=-1; //-1 means not allocated
			Vector<Float> fake = new Vector<Float>();
			CPU_time.add(fake);
			score=0.0;
		}
	}
	
	public ConfiguredPortfolio() {
		orderedPlanners[0]=-1;
		CPU_time = new Vector <Vector<Float>>();
	}
	
	public int[] getOrderedPlanners() {
		return orderedPlanners;
	}

	public void setOrderedPlanners(int[] orderedPlanners) {
		this.orderedPlanners = orderedPlanners;
	}

	
	//add a new planner to the portfolio. -1 means that no planner is present in that position
	public void addPlanner(int id){
		int i=0;
		while(orderedPlanners[i]!=-1)
			i++;
		orderedPlanners[i]=id;
		return;
	}
	
	public void addPlanner(int id, Vector<Float> time){
		int i=0;
		while(orderedPlanners[i]!=-1)
			i++;
		orderedPlanners[i]=id;
		
		CPU_time.set(id, time);
		
		return;
	}
	
	public void removeLastPlanner(){
		int i=0;
		while(orderedPlanners[i]!=-1)
			i++;
		if(i!=0){
			orderedPlanners[i-1]=-1;
			CPU_time.set(i-1, new Vector<Float>());
		}
		return;
	}
	
	public int getPlanner(int id){
		return orderedPlanners[id];
	}
	
	public boolean presentPlanner (int id){
		int i=0;
		while(i< orderedPlanners.length && orderedPlanners[i]!=-1)
			if(id == orderedPlanners[i])
				return true;
		return false;
	}
	
	// returns the number of planners included in the portfolio
	public int numberPlanners(){
		int i=0;
		while(i< orderedPlanners.length && orderedPlanners[i]!=-1)
			i++;
		return i;
	}
	
	public Vector<Vector<Float>> getCPU_time() {
		return CPU_time;
	}
	
	public Vector<Float> getCPU_time_specific(int id) {
		int i=0;
		for(i=0; i < orderedPlanners.length; i++)
			if(orderedPlanners[i]==id)
				break;
		return CPU_time.elementAt(i);
	}
	
	public void setCPU_time_specific(int id, Vector<Float> change) {
		int i=0;
		for(i=0; i < orderedPlanners.length; i++)
			if(orderedPlanners[i]==id)
				break;
		CPU_time.set(i, change);
		return;
	}
	
	public Float total_CPU_time(){
		float totale=0;
		for(int i=0; i < CPU_time.size(); i++)
			for(int j=0; j < CPU_time.elementAt(i).size();j++)
				totale+=CPU_time.elementAt(i).elementAt(j);
		return totale;
	}

	public void setCPU_time(Vector<Vector<Float>> cPU_time) {
		CPU_time = cPU_time;
	}
	
	@Override
	public String toString() {
		return "ConfiguredPortfolio [orderedPlanners="
				+ Arrays.toString(orderedPlanners) + ", CPU_time=" + CPU_time
				+ ", score=" + score + "]";
	}



}
