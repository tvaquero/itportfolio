package portfolio;

/*
 * A class that is used for saving the metric that are considered while solving training problems
 * A class corresponds to a single Metric, that has a description, an id and a type.
 */

public class Metric {
	int id;
	String description;
	String type;
	
public Metric(){
	id=0;
	description="";
	type="";
	return;
}

public Metric(int number, String one, String two){
	id=number;
	description=one;
	type=two;
	return;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public void print(){
	System.out.println("Metric id: "+id+", description: "+description+", type: "+type+".");
}

public String toXml(){
	String totale="\t\t<metric id='"+id+"'>\n"+
		"\t\t\t<description>"+description+"</description>\n"+
		"\t\t\t<type>"+type+"</type>\n"+
		"\t\t</metric>\n";
		
	return totale;
}

}
