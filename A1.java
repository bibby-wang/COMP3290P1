// The University of Newcastle
// School of Electrical Engineering and Computing
// COMP3290 Compiler Design
// Semester 2, 2019
// Project Part 1 A Scanner for CD19 (15%) 
// Due: August 30th 23:99
// Binbin.Wang C3214157

// while not scanner.eof( ) do {
	// token = scanner . gettoken( );
	// scanner . printToken(token);
// }

public class A1{
	
	public static void main(String[] args)  {
		//check file name

		if (args.length>0){
			Scanner scanner=new Scanner(args[0]);
			Token tempToken;
			int countToken=0;
			while (!scanner.eof()){
				countToken++;
				tempToken = scanner.getToken();
				//System.out.println("");

				scanner.printToken(tempToken);
				// if (tempToken.value()==62){
				
					// System.out.println("");
					// System.out.println("TUNDF ");
					// System.out.println("lexical error "+tempToken.getStr());
					// countToken=0;
					
				// }else{
					// if (countToken>5){
						// System.out.println("");
						// countToken=0;
					// }					
				
				// }
			}
			
			//System.out.println("Tokens count is: "+countToken);

		}else{
			//erro of file name
			System.out.println("Empty File name args[] is Null");
		}
		
	}



}
