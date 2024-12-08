package Cosas;

public class Cositas {

	public static void main(String[] args) {
		 String input = "Hloa-que-estas_haciento_tu";
	      System.out.println("input: "+input);  
		
		System.out.printf(toCamelCase(input));

	}
	  static String toCamelCase(String s){
		    
		   StringBuilder result = new StringBuilder();
		        boolean capitalizeNext = false;

		        for (int i = 0; i < s.length(); i++) {
		            char currentChar = s.charAt(i);

		            if (currentChar == '-' || currentChar == '_') {
		                capitalizeNext = true;
		            } else {
		                if (capitalizeNext) {
		                    result.append(Character.toUpperCase(currentChar)); 
		                    capitalizeNext = false;
		                } else {
		                    result.append(currentChar); 
		                }
		            }
		        }

		        return result.toString();
		    }

}
