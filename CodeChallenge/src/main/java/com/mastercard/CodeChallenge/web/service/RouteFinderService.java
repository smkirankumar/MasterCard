package com.mastercard.CodeChallenge.web.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.mastercard.CodeChallenge.web.model.Places;

@Component
public class RouteFinderService {

	static File cityFile = new File("./src/main/resources/city.txt");
	static List<Places> finalPlacesList = new ArrayList<Places>();

	static {
		parseAndLoadCityFile();
	}

	// Parse and load the city file data
	public static void parseAndLoadCityFile() {
		System.out.println("================Loading the City File================");
		List<String[]> fileData = new ArrayList<String[]>();
		String origin, destination;
		try {
			fileData = Files.lines(Paths.get(cityFile.getAbsolutePath())).map(line -> line.split(","))
					.collect(Collectors.toList());

			for (String[] place : fileData) {
				origin = place[0].trim();
				destination = place[1].trim();

				System.out.println(origin + "-" + destination);
				Places currentPlace = new Places(origin, destination);
				currentPlace.getConnectingCitySet().add(origin);
				currentPlace.getConnectingCitySet().add(destination);
				if (finalPlacesList.isEmpty()) {
					finalPlacesList.add(currentPlace);
					System.out.println("currentPlace =" + currentPlace);
					System.out.println("finalPlacesList =" + finalPlacesList);
				} else {
					finalPlacesList.stream()
							.filter(finalList -> finalList.getConnectingCitySet().contains(currentPlace.getOrigin())
									|| finalList.getConnectingCitySet().contains(currentPlace.getDestination()))
							.forEach(finalList -> {
								finalList.getConnectingCitySet().addAll(currentPlace.getConnectingCitySet());
								currentPlace.getConnectingCitySet().addAll(finalList.getConnectingCitySet());
							});
					finalPlacesList.add(currentPlace);

					System.out.println("currentPlace =" + currentPlace);
					System.out.println("finalPlacesList =" + finalPlacesList);
				}
			}
			System.out.println("=================Final Cities and it's connection List============================");
			finalPlacesList.stream().forEach(value -> System.out.println(value));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// process the data and print the result 
	public String findRoute(String pOrigin, String pDestination) {
		System.out.println("pOrigin => " + pOrigin + " -" + " pDestination => " + pDestination);

		Set<Set<String>> connectedCities = finalPlacesList.stream()
				.filter(city -> city.getOrigin().equalsIgnoreCase(pOrigin) || city.getDestination().equalsIgnoreCase(pOrigin))
				.map(connect -> connect.getConnectingCitySet())
				.collect(Collectors.toSet());

		connectedCities.forEach(v -> System.out.println(v));
		Set<String> citySet = connectedCities.stream().flatMap(m -> m.stream()).collect(Collectors.toSet());
		
		if (citySet.contains(pDestination)) {
			System.out.println("YES ===> Destination = " + pDestination + " exists " + citySet);
			return "YES, Route exists between ["+pOrigin+ "] and ["+ pDestination+"] !!!    Connecting Cities list are = " + citySet;
		} else {
			System.out.println("NO, Route doesn't exist ");
			return "NO, Route exists between ["+pOrigin+ "] and ["+ pDestination+"]";
		}
	}
}
