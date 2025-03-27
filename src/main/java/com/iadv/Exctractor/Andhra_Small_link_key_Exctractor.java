package com.iadv.extractor;

public class Andhra_Small_link_key_Exctractor {
	 public static String extractViewKey(String htmlContent) {
	        String startStr = "<a name=\"viewdcb\" class=\"btn btn-secondary \" id=\"viewdcb-btn\" onclick=\"window.open('/tl/dcb/view/' + '";
	        String endStr = "', '_blank', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');\">Show Details <i class=\"fa fa-eye\"></i></a>";
	        
	        int startIndex = htmlContent.indexOf(startStr);
	        int endIndex = htmlContent.indexOf(endStr, startIndex);

	        if (startIndex != -1 && endIndex != -1) {
	            startIndex += startStr.length();
	            return htmlContent.substring(startIndex, endIndex);
	        }
	        return null; // Return null if the substring is not found
	    }
	 
}
