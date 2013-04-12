package portfolio;

import java.util.Vector;


/*
 * A set of useful math methods :)
 */

public class MathHelp {
	private MathHelp(){
		throw new AssertionError();
	}
	
	public static int factorial (int number){ //FOR FACTORIAL CALCULUS
	    int ret = 1;
	    for (int i = 1; i <= number; ++i) 
	    	ret *= i;
	    return ret;
	}
	
	public static Vector< Vector<String> > Permutations (int number_planners, int num_k){
		//function enumer2(varn,vark,res){
		    boolean ok; 
			String alfabet= new String();
			String add;
			for(int i=0; i < number_planners; i++){
				alfabet+=Integer.toString(i);
				alfabet+=",";
			}			
			Vector< Vector<String> > pmut= new Vector< Vector<String> >();
			for(int i=0;i < number_planners ;i++){
				Vector <String> ciccio = new Vector<String>();
				pmut.add(ciccio);
			}
			int count[]= new int[num_k];
			//String main=alfabet;
			//System.out.println(alfabet);
			//System.out.println(pmut.size());
			for(int i=0;i<number_planners/*-1*/;i++){
					pmut.elementAt(0).add( alfabet.split(",")[i]+"," );
					//[i]=main.substring(i,i+1);
			}
			count[0]=number_planners;
			if(num_k==1)
				return pmut;
			
			for(int i=1;i<=num_k-1;i++){
				count[i]=0;
				for(int k=0;k<=number_planners-1;k++){
					//add=main.substring(k,k+1);
					add=alfabet.split(",")[k];
					for(int m=0;m<=count[i-1]-1;m++){
						ok=true;
						for(int q=0;q<=i-1;q++){
							//if(add==pmut.elementAt(i-1).elementAt(m).substring(q,q+1)){
							//System.out.println(add+" "+pmut.elementAt(i-1).elementAt(m).split(",")[q]);
							if(add.equals(pmut.elementAt(i-1).elementAt(m).split(",")[q])){
								ok=false;
								break;
							}
						}
						if(ok==true){ //HERE 
							String prova = pmut.elementAt(i-1).elementAt(m)+add+",";
							pmut.elementAt(i).add(prova);
							count[i]++;
						}
					}
				}
			}
			return pmut;
		}
		
	
}
