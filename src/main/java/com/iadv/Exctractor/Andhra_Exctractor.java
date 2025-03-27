package com.iadv.extractor;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.iadv.data.*;
import java.io.IOException;
import java.util.HashMap;

public class Andhra_Exctractor {

    public static HashMap<String,String> SecondStep(HashMap<String, String> hmap, String urlid, String filePathidpage, String filepathviewpage, String filepathshowpage, String munname) {
        
    	try {
            // Fetch the HTML document
            Document document = Jsoup.connect(urlid).get();
            String pageSourceid = document.outerHtml();
          
            
            // Extract the required string from the anchor tag
            String viewKeyofLink = Andhra_Small_link_key_Exctractor.extractViewKey(pageSourceid);
            String urlview = "https://"+munname+".emunicipal.ap.gov.in/tl/dcb/view/"+viewKeyofLink+"#no-back1";
            String urlshow = "https://"+munname+".emunicipal.ap.gov.in/tl/license/show/"+viewKeyofLink+"#no-back";
            Document document1 = Jsoup.connect(urlview).get();
            String pageSourceview = document1.outerHtml();
           
            Document document2 = Jsoup.connect(urlshow).get();
            
            String pageSourceshow = document2.outerHtml();
          
           
            
            // Write the page source to the file (optional but as per your original requirement)
          
           

            // Traverse the elements using the provided CSS selector
            
            for (int j = 2; j <= 6; j++) {
                Elements elements = document.select("#tradedetails > div:nth-child(" + j + ") > div > div");
                // Loop through the elements and apply the odd-even key-value mapping
                for (int i = 0; i < elements.size(); i += 2) {
                    if (i + 1 < elements.size()) {
                        String key = elements.get(i).text(); // Odd position element (Key)
                        String value = elements.get(i + 1).text(); // Even position element (Value)
                        if (value.isEmpty()) {
                            value = "N/A";
                        }

                        hmap.put(key, value);
                    }
                }
            }
          
                // Use the correct CSS selector for the table cells
                Elements elements = document1.select("body > div.page-container > div > div:nth-child(2) > table > tbody > tr > td");

                // Loop through the elements and apply the odd-even key-value mapping
                for (int i = 0; i < elements.size(); i += 2) {
                    // Ensure there's a value for the key
                	 

                    if (i + 1 < elements.size()) {
                        String key = elements.get(i).text();  // Odd position element (Key)
                        String value = elements.get(i + 1).text();  // Even position element (Value)

                        // Only replace the value with "N/A" if it's empty
                        if (value.isEmpty()) {
                            value = "N/A";
                        }

                        // Add the key-value pair to the HashMap
                        hmap.put(key, value);
                       
                    }
                }
                for(int j=1;j<=9;j+=2)
                {
                	 Elements elements1 = document2.select("#tradedetails > div:nth-child("+j+") > div > div");
                for (int i = 0; i < elements1.size(); i += 2) {
                    // Ensure there's a value for the key
                	 

                    if (i + 1 < elements1.size()) {
                        String key = elements1.get(i).text();  // Odd position element (Key)
                        String value = elements1.get(i + 1).text();  // Even position element (Value)

                        // Only replace the value with "N/A" if it's empty
                        if (value.isEmpty()) {
                            value = "N/A";
                        }

                        // Add the key-value pair to the HashMap
                        hmap.put(key, value);
                    }
                }
                }

              hmap.put("ViewpageLink",urlview);
              hmap.put("ShowPageLink", urlshow );
               hmap.put("muncipalty",munname);
                Andhra_writer_1.writeToFile(pageSourceview,filepathviewpage+"\\"+hmap.get("Application No.")+" "+hmap.get("License No.")+"pageSourceView.html");
                Andhra_writer_1.writeToFile(pageSourceshow,filepathshowpage+"\\"+hmap.get("Application No.")+" "+hmap.get("License No.")+"pageSourceShow.html");
                Andhra_writer_1.writeToFile(pageSourceid, filePathidpage+"\\"+hmap.get("Application No.")+" "+hmap.get("License No.")+"pageSourceid.html");
            // Print the HashMap to the console
            System.out.println("Trade Details Map: " + hmap);
           
        } catch (IOException e) {
            System.err.println("Error fetching the page source: " + e.getMessage());
        } 
    	return hmap;
       
    }
         
    // Method to extract the string between specific substrings
  

    // Helper method to write content to a file
    
}
