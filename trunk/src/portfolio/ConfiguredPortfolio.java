package portfolio;

import java.util.Arrays;
import java.util.Vector;

public class ConfiguredPortfolio {
    //THE configured portfolio. OrderedPlanners is a list of ids of selected planners,
	// CPU_time is a list of the CPU_time that the planner will run.

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

	public void addPlanner(int id){
		int i=0;
		while(orderedPlanners[i]!=-1)
			i++;
		orderedPlanners[i]=id;
		return;
	}
	
	public int getPlanner(int id){
		return orderedPlanners[id];
	}
	
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
		return CPU_time.elementAt(id);
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
