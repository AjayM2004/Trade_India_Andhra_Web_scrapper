package com.iadv.main;

import com.iadv.extractor.*;



import com.iadv.data.Andhra_writer2;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import java.util.*;

public class Andhra_trade_details_main1 {
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        HashMap<String, HashMap<String, String>> outerMap = new HashMap<>();
        String[] municipalities = {
        	    // Municipal Corporations (17)
        	    "visakhapatnam", "vijayawada", "guntur", "nellore", "kurnool", "tirupati",
        	    "rajahmundry", "kakinada", "anantapur", "eluru", "kadapa", "ongole",
        	    "chittoor", "machilipatnam", "srikakulam", "vizianagaram", "bhimavaram",

        	    // Municipalities (80)
        	    "adoni", "amalapuram", "anakapalle", "bapatla", "bhimavaram", "bobbili",
        	    "chilakaluripet", "dharmavaram", "gudivada", "hindupur", "ichchapuram",
        	    "jaggayyapeta", "kadiri", "kandukur", "kavali", "kovvur", "mandapeta",
        	    "markapur", "nagari", "nandyal", "narasapuram", "narasaraopet", "narsipatnam",
        	    "nuzvid", "palacole", "palasa", "parvathipuram", "peddapuram", "pithapuram",
        	    "proddatur", "pulivendula", "punganur", "puttur", "rajampet", "rayachoti",
        	    "rayadurg", "salur", "samalkot", "sattenapalle", "siddipet", "srikalahasti",
        	    "tadepalligudem", "tadpatri", "tanuku", "tenali", "tuni", "uravakonda",
        	    "venkatagiri", "vinukonda", "yemmiganur", "yerraguntla", "kuppam", "addanki",
        	    "atmakur", "banaganapalle", "bestavaripeta", "chebrole", "chintalapudi",
        	    "darsi", "dhone", "giddalur", "gokavaram", "gorantla", "gudur", "jammalamadugu",
        	    "kakinada rural", "kanigiri", "korukonda", "mylavaram", "nellimarla", "paderu",
        	    "palamaner", "pamarru", "pedana", "podili", "rampachodavaram", "sullurpet",
        	    "tekkali", "vuyyuru", "vepagunta", "mandasa", "malikipuram", "manubolu"
        	};


        System.out.println("0-16 will be the municipal corporation so provide id between 1-9999");
        ArrayList<String> municipalitiesList = new ArrayList<>(Arrays.asList(municipalities));
        
        
        int start = scan.nextInt();
        int end = scan.nextInt();
        scan.nextLine(); // Clear the newline character

        System.out.println("Enter the range for id in the URL (format: start-end):");
        String input = scan.nextLine(); // Use nextLine to read the entire line
        String[] arr = input.split("-");

        // Check if arr has exactly two elements
        if (arr.length != 2) {
            System.err.println("Invalid input. Please enter a valid range in the format 'start-end'.");
            return; // Exit if the input is not valid
        }

        int idStart, idEnd;
        try {
            idStart = Integer.parseInt(arr[0]);
            idEnd = Integer.parseInt(arr[1]);
        } catch (NumberFormatException e) {
            System.err.println("Please enter valid integers for the ID range.");
            return; // Exit if parsing fails
        }
      
        for (int m = start; m <= end; m++) {
            for (int i = idStart; i <= idEnd; i++) {
            	HashMap<String,String> hmap = new HashMap<>();
            	String muncipalname =municipalitiesList.get(m);
                String urlid = "https://" + municipalitiesList.get(m) + ".emunicipal.ap.gov.in/tl/viewtradelicense/viewTradeLicense-view.action?id=" + i;
                String filePathidpage = args[0];
                // Specify your file path here
                String filepathviewpage =args[0];
                String filepathshowpage =args[0];
                HashMap<String,String> innermap=Andhra_Exctractor.SecondStep(hmap,urlid, filePathidpage, filepathviewpage, filepathshowpage,muncipalname);
                outerMap.put(String.valueOf(m)+muncipalname+String.valueOf(i), innermap);
               
            }
        }
        Andhra_writer2.writeToCSV(args[1], outerMap);
        scan.close();
    }
}
