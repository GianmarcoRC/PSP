package Cosas;

public class PlayPass {
	  public static double findUniq(double arr[]) {
		  double digito = 0;
		  double impostor = 0;
	  for (int i = 0; i < arr.length; i++) {
		digito = arr[i];
		if (i> 0) {
		if(digito != arr[i-1]) {
			impostor = arr[i];
			break;
		}
	}
	}
	      
	      
	      
	      return impostor;
	    }
	public static void main(String[] args) {
		  System.out.println(PlayPass.findUniq(new double[]{ 1, 1, 1, 2, 1, 1 })); // => 2
		  System.out.println( PlayPass.findUniq(new double[]{ 0, 0, 0.55, 0, 0 })); // => 0.55
	}
}