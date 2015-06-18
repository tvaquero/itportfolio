The right way for configuring eclipse for the itporffolio project

Check out itportfolio project (Repository folder: https://itportfolio.googlecode.com/svn/trunk/ ) to a local folder in your computer, e.g. in a folder called ‘Portfoliosrc’ (not in the workspace folder of Eclipse). Wait for checkout completion.

  * Create a new project (New-> Java Project). Name the project 'itportfolio'. In the 'Project layout' section, select "Use project folder as root for sources and class files'. Press Next and on the Source tab press "Link Additional source to the project". Chose the 'PortfolioSource/src' folder were the checkout was done.
  * Add the JAR/ZIP libraries to the project (right click over the project -> Properties -> Java Build Path and on the tab press Add external Jars). Selected all .jar libraries. They will be in the folder '.../PortfolioSource/src/lib'
  * Copy and paste the folder ‘input’ from the PortfolioSource folder into the project folder created by Eclipse (e.g., workspace/itportfolio). This is necessary for running the project inside the Eclipse environment.
  * Run the class Main.java.

With the above settings you can play with the input (xml) files in the eclipse enviroment (workspace/itportfolio/input) without changing the source files. When you have something (a change) that you want to commit/share, you just copy and paste the file to the corresponding input folder in the PortfolioSource folder (i.e., PortfolioSource/input). Then you use your svn client to commit.  This is only necessary for the input files,not the src codes.