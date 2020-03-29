Master Card Code Challenge
===========================

***********************************
Running the SpringBoot application
***********************************
1) Download the project from Github link
2) Clone the project into eclipse
3) Run as Java application 
4) After running application in eclipse, open the web browser and type the URL 
	http://localhost:8080/connected?origin=Boston&destination=Philadelphia
5) 	It should show up a message if the Origin and Destination cities are connected.

	
	Try different origin and destination for testing.

 (or)
1) Download the project from Github link
2) Clone the project into eclipse
3) Run -> Run As -> Maven Build... -> Goals = compile  -> Run
4) Run -> Run As -> Maven Build... -> Goals = package  -> Run


Executable Jar file name = CodeChallenge-0.0.1-SNAPSHOT.jar 
Jar file location = "CodeChallenge\target" project directory 
5) Open command prompt and go to project directory
6) Execute the following command 	
	For E.g.:
	C:\Users\<username>\git\MasterCard\CodeChallenge>java -jar target/CodeChallenge-0.0.1-SNAPSHOT.jar
	
	Note: Make sure no other process is running on the same server port 8080. 
		If other process is running make sure to kill the other processes

3) After running the above command open the browser and type
	http://localhost:8080/connected?origin=Boston&destination=Philadelphia

4) 	It should show up a message if the Origin and Destination cities are connected.
	

Note: 
-----
In first commit we have a differnt code which will show direct to ConnectCities.jsp
it will have 2 input fields and submit button.
Code can be referred in 
CodeChallenge/src/main/java/com/mastercard/CodeChallenge/web/controller/ConnectionController.java

Pre-Requisites:
--------------
Java 8
Eclipse Interface for development



Steps:

1) Generate the spring boot maven project structure using Spring Initializr (Web UI).
	- Project : Maven
	- Language : Java
	- Spring boot : 2.2.5
	- Project Meta data : Group = com.mastercard
						  Artifact = CodeChallenge
	- Dependencies : 1) Spring Web - Contains Spring MVC, embedded tomcat and restful services
					 2) Spring Boot DevTools - Fast application restart for rapid application development
					 
	Click on Generate the zip file , extract and import the project into Eclipse				 

2)  Controller  : 
		Create a class "ConnectionController" in package com.mastercard.CodeChallenge.web.controller mark with @Controller annotation.
			- Create a method "Connect" and mark it with annotation 
				@RequestMapping(value = "/connected", method = RequestMethod.GET). 
				This denotes the "/connected" endpoint 
			- Create a method  "Connect" and mark it with annotation 
				@RequestMapping(value = "/connected", method = RequestMethod.POST)
				call the findRoute method present in RouteFinderService to calcuate if the cities are connected
				Return the result in the same page once the "Find Route" button is clicked
			
3)  View :
		1)Create a folder under the path "CodeChallenge\src\main\webapp\WEB-INF\Jsp" to maintain all the JSP files
		
		2) Create 2 differnt JSP Page (ConnectCities.jsp) for two different views
				- Jsp Page will prompt for user input. It prompts for "Origin" and "Destination" for finding the route 
				- Apply the input validation on these field by setting the required property as "Required" 
				- Else prompt the user with a message "Please fill out this field"				
	
			(OR)
			
			Created another Jsp page (CityEndPoint.jsp)
				- This page will be called if user is directly providing the inputs in URL instead of using the form
	
		3) Add the below dependency to redirect the output to a Jsp page
		-------------------------------------------------
				<dependency>
					<groupId>org.apache.tomcat.embed</groupId>
					<artifactId>tomcat-embed-jasper</artifactId>
					<scope>provided</scope>
				</dependency>
			
		   Add the below dependency for more stylesheet options for Jsp page	
		-------------------------------------------------	
				<dependency>
					<groupId>org.webjars</groupId>
					<artifactId>bootstrap</artifactId>
					<version>4.3.1</version>
				</dependency>
		-------------------------------------------------
		4) Add the view resolver in application.properties file
			spring.mvc.view.prefix=/WEB-INF/Jsp/      ==> Prefix denotes the directory to look for the view files (e.g.: Jsp)
			spring.mvc.view.suffix=.jsp				  ==> Look up for file extension
			logging.level.org.springframework = debug ==> Setting this Log level will provide more helpful information for debugging

	
	Note: 
		  By now the a simple web application / form should be avaialble and ready to run the application
		  Run the application by entering the origin as "Boston" and destination as "Newark" and click on "Find route" button
		  It should map correctly to the URI with "connected" that is mentioned in @RequestMapping annotation (ConnectionController class)
		  
		  URI will not show the input values, if we use RequestMethod = POST
			URI will look like http://localhost:8080/connected
		  URI will have input parameters, If we use RequestMethod = GET
			http://localhost:8080/connected?origin=Boston&destination=Newark

4) Test File:
		Create a city.txt file under this "CodeChallenge\src\main\resources\"		  
		Enter the City test data with comma seperated values
		For E.g.: 	Boston, New York
					Philadelphia, Newark
					Newark, Boston
					Trenton, Albany
5) Model :
		Create a POJO class named "Places" in package "com.mastercard.CodeChallenge.web.model"
			-	Create the private data members like origin, destination and ArrayList to hold the connecting cities
			-	Create the parameterized constructor with origin and destination,
			- 	Create getters and setters for all private data members.
			-	Override the toString method for printing the Places Object 
		
6) Service:
		// All the business logic will be present here
	Create a "RouteFinderService" class  in package "com.mastercard.CodeChallenge.web.service"
		
		- Create a new file object and mention the path of the city file. 
		- Create a ArrayList to hold the "Places" object, this list will be used to hold the final data with all connecting cities
			==========================================================
			static List<Places> finalPlacesList = new ArrayList<Places>();
			==========================================================
		- Load all the city by calling "parseAndLoadCityFile()" method
	
	parseAndLoadCityFile():
	-----------------------
		- Parse the file and lookup for the regular expression "," {Comma} and split the file data based on the regular expression and store it in an ArrayList<String[]>. This ArrayList will have the test file information.
		-------------- Main Looping starts here ----------------
		- Loop the list which contains the file information (fileData)
			- Store the zero th index in "origin" and first index in "destination" variables
			- Create a Places object and pass the origin and destination values to constructor 
					==========================================================
					Places currentPlace = new Places(origin, destination);
					==========================================================
			- Add the currentPlace's origin and destination to the "connectingCitySet" that is present in Places class. 
			This step will provide all the connecting cities for a particular route. 
			Used Set interface in my code to maintain  unique cities (doesn't allow duplicate cities).
			
			- check if the "finalPlacesList" is empty, if yes then add the "currentPlace" object to the "finalPlacesList"
			
			- If the "finalPlacesList" is NOT empty, it means already a Places object is present in the list.
				----------------------------------------------------------------------
				compare "finalPlacesList" object data with "currentPlace" object data
				----------------------------------------------------------------------
			1)Check the below predicate condition and filter the matching records
			
				check if "currentPlace" Object's Origin is present in the "connectingCitySet"
				check if "currentPlace" Object's Destination is present in the "connectingCitySet"
				
				E.g.:
				.filter(finalList -> finalList.getConnectingCitySet().contains(currentPlace.getOrigin())
				   				  || finalList.getConnectingCitySet().contains(currentPlace.getDestination()))
								
			
			2) Loop the filtered records and add the connecting Cities to the final list and current place to get all the connecting cities and it's place of origin and destination.
				E.g.:				
				finalList.getConnectingCitySet().addAll(currentPlace.getConnectingCitySet());
				currentPlace.getConnectingCitySet().addAll(finalList.getConnectingCitySet());
		
********************************************************************************************************		
Printing the currentPlace and finalPlacesList to understand how the data is populated in these list when each line is parsed and added to the list

 - currentPlace
 - finalPlacesList
 
********************************************************************************************************
-------Line 1 in Sample City test file
Boston-New York
currentPlace =Places [origin=Boston, destination=New York, connectingCitySet=[New York, Boston]]

finalPlacesList =[Places [origin=Boston, destination=New York, connectingCitySet=[New York, Boston]]]

-------Line 2 in Sample City test file
Philadelphia-Newark
currentPlace =Places [origin=Philadelphia, destination=Newark, connectingCitySet=[Newark, Philadelphia]]

finalPlacesList =[Places [origin=Boston, destination=New York, connectingCitySet=[New York, Boston]], 
				Places [origin=Philadelphia, destination=Newark, connectingCitySet=[Newark, Philadelphia]]]

-------Line 3 in Sample City test file
Newark-Boston
currentPlace =Places [origin=Newark, destination=Boston, connectingCitySet=[New York, Newark, Philadelphia, Boston]]

finalPlacesList =[Places [origin=Boston, destination=New York, connectingCitySet=[New York, Newark, Boston]],
				Places [origin=Philadelphia, destination=Newark, connectingCitySet=[New York, Newark, Philadelphia, Boston]], Places [origin=Newark, destination=Boston, connectingCitySet=[New York, Newark, Philadelphia, Boston]]]

-------Line 4 in Sample City test file
Trenton-Albany
currentPlace =Places [origin=Trenton, destination=Albany, connectingCitySet=[Trenton, Albany]]

finalPlacesList =[Places [origin=Boston, destination=New York, connectingCitySet=[New York, Newark, Boston]], 
			Places [origin=Philadelphia, destination=Newark, connectingCitySet=[New York, Newark, Philadelphia, Boston]], Places [origin=Newark, destination=Boston, connectingCitySet=[New York, Newark, Philadelphia, Boston]], 
			Places [origin=Trenton, destination=Albany, connectingCitySet=[Trenton, Albany]]]


Now the final list contains the origin, destination and it's connecting cities

=================Printing Final Cities list and it's connectionCity List============================

Places [origin=Boston, destination=New York, connectingCitySet=[New York, Newark, Boston]]
Places [origin=Philadelphia, destination=Newark, connectingCitySet=[New York, Newark, Philadelphia, Boston]]
Places [origin=Newark, destination=Boston, connectingCitySet=[New York, Newark, Philadelphia, Boston]]
Places [origin=Trenton, destination=Albany, connectingCitySet=[Trenton, Albany]]

	========================================================
	Method : findRoute(String pOrigin, String pDestination)
	========================================================
	
	This method will take the user puts origin and destination that will be entered on the screen
	This method helps to validate the user input with the city file.
	
	- Get the constructed "finalPlacesList" as stream 
	- Filter the list to check 
			1) if the origin present in the finalPlacesList is equal to the user input (origin)
					(or)
			2) if the destination present in the finalPlacesList is equal to the user input (origin)
	- collect the result in an set		
	- If the final result set has the destination then return "Yes" else "No"
	
=========================================================================================================	
Note:	
	This code is developed to identify if the cities are connected or not only!	
=========================================================================================================