// The University of Newcastle
// School of Electrical Engineering and Computing
// COMP3290 Compiler Design
// Semester 2, 2019
// Project Part 1 A Scanner for CD19 (15%) 
// Due: August 30th
// Binbin.Wang C3214157

// while not scanner.eof( ) do {
	// token = scanner . gettoken( );
	// scanner . printToken(token);
// }



public class A1{
	
	public static void main(String[] args)  {
		//check file name

		if (args[0]!=null){
			Scanner scanner=new Scanner(args[0]);
//			scanner.getToken();

			Token tempToken;
			while (!scanner.eof()){
				// int i=0;
			// while (i<30){
				tempToken = scanner.getToken();
				scanner.printToken(tempToken);
				//i++;
			}
			//System.out.println("eof: "+scanner.eof());

			

		}else{
			//erro of file name
			System.out.println("Empty File name args[] is Null");
		}
	}



}
