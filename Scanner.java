// The University of Newcastle
// School of Electrical Engineering and Computing
// COMP3290 Compiler Design
// Semester 2, 2019
// Project Part 1 A Scanner for CD19 (15%) 
// Due: August 30th
// Binbin.Wang C3214157




import java.util.ArrayList;

	

import java.io.*;

public class Scanner{
	private ArrayList<String> strArrayLine;
//	private ArrayList<char> charArrayLine;

	private boolean fileEOF;
	private String fileName;
	private int lineNum;
	private int columnNum;

	private char nextChar;
	private int nextType;	
	private char[] tempLineChar;
	private String tempLineStr;
	public Scanner(){
		
	}

	//Constructor Initialization input String
	public Scanner(String name){
		fileEOF=false;
		fileName=name;
		strArrayLine= new ArrayList<String>();
		lineNum=0;
		columnNum=0;

		
		
		try {				
			BufferedReader sourceFile = new BufferedReader(new FileReader(fileName));
			String strLine;

		
			while ((strLine = sourceFile.readLine()) != null) {
				
				strArrayLine.add(this.delComment(strLine));
				//test output string line content
				//System.out.println("strLine: ["+strLine+"] ");
			}
			sourceFile.close();
			
			//Separated character by strArrayLine
			//doScanner();
			//Token(int t, int ln, int p, String s) {  //Constructor takes in token number, line, column & lexeme
			
			
		} catch (IOException e) {
			//erro of read file
			System.out.print("Reading File erro");
		}
	}
	
	public boolean eof(){
		return this.fileEOF;
	}
	
	private void nextLexemeColumn(){
		//find next lexeme starting line and column number
// System.out.println("");
// System.out.println("ss== l["+lineNum+"]c["+columnNum+"] [s] "+strArrayLine.size());
		while(lineNum < strArrayLine.size()){

			tempLineStr=strArrayLine.get(lineNum);
			int columnL=tempLineStr.length();
			tempLineChar=tempLineStr.toCharArray();
	//System.out.println("=="+columnNum+", "+columnL);
			while(columnNum<columnL){
				int currentType=getType(tempLineChar[columnNum]);
//System.out.println("ss== l["+lineNum+"]c["+columnNum+"] [t] "+currentType);				
				
				if (currentType!=0) return;
				columnNum++;
			}
			columnNum=0;
			lineNum++;
		}
		if (lineNum>strArrayLine.size()){
			//System.out.println("eof");
			fileEOF=true;
		}
	}


	public Token getToken(){
		

		int tokenMark=0;
		
		nextLexemeColumn();	
		
		// tempLineStr=strArrayLine.get(lineNum);
		// tempLineChar=tempLineStr.toCharArray();
		
		char currentChar=tempLineChar[columnNum];
		int currentType=getType(currentChar);
		
		nextChar=tempLineChar[columnNum+1];
		nextType=getType(nextChar);		

		int tempColumnNum=columnNum;			
		
		String lexeme="";
	
		currentType=getType(currentChar);
		nextType=getType(nextChar);




//System.out.println("AA== l["+lineNum+"]c["+columnNum+"] [t] "+currentType+"char "+(int)currentChar);		
		if (!fileEOF){
			// -1 Undefined
			// 0 (space)
			// 1 Delimiters or Operators
			// 2 number
			// 3 alphabet

			if (currentType != 0){
				if (currentType == 3){
					tokenMark=extractIdentWords();
					//columnNum++;
				}else if(currentType == 2){
					tokenMark=extractNumber();
					//columnNum++;
				}else if(currentType == 1){
					if (tempLineChar[columnNum]=='"'){
						tokenMark=extractString();
					}else{
						tokenMark=extractOperators();
					}
					//columnNum++;
				}else if(currentType == -1){
					tokenMark=extractUndefined();
					//columnNum++;
				}
				
			}
			
			for(int i=tempColumnNum;i<columnNum;i++){
				// System.out.println("");			
				// System.out.println("==="+tempColumnNum+": "+tempLineChar[i]+" col ["+columnNum+"]");			
				lexeme += tempLineChar[i];

			}

		}
		
	
		//columnNum++;
		Token token=new Token(tokenMark, lineNum, tempColumnNum, lexeme);
		
		if (tokenMark==0) fileEOF=true;

		return token;
	}
	
	// delete comments 
	private String delComment(String strLine){
		int i=strLine.indexOf("/--");
		if(i>=0){
			strLine=strLine.substring(0,i);
		}
		strLine+=" "; // add an speace at end
		return strLine;
		
	}
	
	//TIDEN = 58 
	// extract the Identifiers and Reserved Keywords
	private int extractIdentWords(){
		int tokenNmu=58;
		
		//check the end of the Identifiers and Reserved Keywords
		while((nextType>=2) || (tempLineChar[columnNum+1]=='_')){
			columnNum++;
			nextType=getType(tempLineChar[columnNum+1]);
		}
		columnNum++;
		return tokenNmu;  // TSTRG 

	}

	// extract the number 
	// Undefined type does not appear, at least one number
	private int extractNumber(){
		int tokenNmu=59;  //TILIT
		while(nextType==2){
			columnNum++;
			nextType=getType(tempLineChar[columnNum+1]);
		}
		
		//Determine if it is a decimal
		if ((tempLineChar[columnNum+1]=='.') && (getType(tempLineChar[columnNum+2])==2)){
			columnNum+=2;
			nextType=getType(tempLineChar[columnNum+1]);
			while(nextType==2){
				columnNum++;
				nextType=getType(tempLineChar[columnNum+1]);
			}
			tokenNmu=60; //TFLIT
		}			
		columnNum++;
		return tokenNmu;
	}
	
	

	// extract the string "..." 
	private int extractString(){
		//check the next " 
		columnNum++;
		char C=tempLineChar[columnNum];
		
		while((int)C != 34 && columnNum < tempLineChar.length -1){
			columnNum++;
			C=tempLineChar[columnNum];
		}
		
		if ((int)C == 34){
			columnNum++;
			return 61;  // TSTRG String
		}else{
			return 62;  // TUNDF Undefined
		}
	}
	// extract the Delimiters and Operators
	private int extractOperators(){
		char C=tempLineChar[columnNum+1];
		String SS="";
		if (C=='='){
			SS=tempLineChar[columnNum]+"=";
		}else{
			SS=String.valueOf(tempLineChar[columnNum]);
		}
		if (getCoupleSymbolMark(SS)<0){
			columnNum++;
			return getSingleSymbolMark(SS);
		}else{
			columnNum+=2;
			return getCoupleSymbolMark(SS);
		}
		// while(nextType == -1){
			// columnNum++;
			// nextType=getType(tempLineChar[columnNum+1]);
		// }
		// return 62;
	}
		
	// extract the undefined // TUNDF Undefined
	private int extractUndefined(){
		
		
		char C=tempLineChar[columnNum];
		while(nextType == -1){
			columnNum++;
			nextType=getType(tempLineChar[columnNum+1]);
		}
		return 62; // TUNDF Undefined
	}
		
	
	public void printToken(Token tempToken){
		System.out.print(tempToken.shortString());
	}

	// token nark of the Delimiters and Operators
	private int getSingleSymbolMark(String symbol) {
		switch(symbol) {

			case ",": return 32;
			case "[": return 33;
			case "]": return 34;
			case "(": return 35; // TLPAR
			case ")": return 36;
			case "^": return 43;	
			case ":": return 46;
			case ";": return 56;
			case ".": return 57;			
			case "=": return 37;
			case "+": return 38;
			case "-": return 39;
			case "*": return 40;
			case "/": return 41;
			case "%": return 42;
			case "<": return 44;
			case ">": return 45;
			
		}
		return 62;
	}	
	// token nark of the Delimiters and Operators
	private int getCoupleSymbolMark(String symbol) {
		switch(symbol) {

			case "<=": return 47;
			case ">=": return 48;
			case "!=": return 49;
			case "==": return 50;
			case "+=": return 51;
			case "-=": return 52;
			case "*=": return 53;
			case "/=": return 54;
			case "%=": return 55;
			
		}
		return -1;
	}	
	
	
	//check chat type 
	private int getType(char C){

		// -1 Undefined
		// 0 (space)
		// 1 Delimiters or Operators
		// 2 number
		// 3 alphabet

		int num = (int)C;

		// end of text
		if (num==3){
			this.fileEOF=true;
			return -2;
		}
// return -2;
//  System.out.println("==================="+num);
// check the ASCII CODE
		switch(num){
			
			case 9  : return 0;	  // tab
			case 32 : return 0;   // (space)
			case 33 : return 1;   // !
			case 34 : return 1;   // "
			case 35 : return -1;  // #
			case 36 : return -1;  // $
			case 37 : return 1;   // %
			case 38 : return -1;  // &
			case 39 : return -1;  // '
			case 40 : return 1;  // (
			case 41 : return 1;  // )
			case 42 : return 1;  // *
			case 43 : return 1;  // +
			case 44 : return 1;  // ,
			case 45 : return 1;  // -
			case 46 : return 1;  // .
			case 47 : return 1;  // /
			case 48 : return 2;  // 0
			case 49 : return 2;  // 1
			case 50 : return 2;  // 2
			case 51 : return 2;  // 3
			case 52 : return 2;  // 4
			case 53 : return 2;  // 5
			case 54 : return 2;  // 6
			case 55 : return 2;  // 7
			case 56 : return 2;  // 8
			case 57 : return 2;  // 9
			case 58 : return 1;  // :
			case 59 : return 1;  // ;
			case 60 : return 1;  // <
			case 61 : return 1;  // =
			case 62 : return 1;  // >
			case 63 : return -1;  // ?
			case 64 : return -1;  // @
			case 65 : return 3;  // A
			case 66 : return 3;  // B
			case 67 : return 3;  // C
			case 68 : return 3;  // D
			case 69 : return 3;  // E
			case 70 : return 3;  // F
			case 71 : return 3;  // G
			case 72 : return 3;  // H
			case 73 : return 3;  // I
			case 74 : return 3;  // J
			case 75 : return 3;  // K
			case 76 : return 3;  // L
			case 77 : return 3;  // M
			case 78 : return 3;  // N
			case 79 : return 3;  // O
			case 80 : return 3;  // P
			case 81 : return 3;  // Q
			case 82 : return 3;  // R
			case 83 : return 3;  // S
			case 84 : return 3;  // T
			case 85 : return 3;  // U
			case 86 : return 3;  // V
			case 87 : return 3;  // W
			case 88 : return 3;  // X
			case 89 : return 3;  // Y
			case 90 : return 3;  // Z
			case 91 : return 1;  // [
			case 92 : return -1;  // "\" 
			case 93 : return 1;  // ]
			case 94 : return 1;  // ^
			case 95 : return -1;  // _
			case 96 : return -1;  // `
			case 97 : return 3;  // a
			case 98 : return 3;  // b
			case 99 : return 3;  // c
			case 100 : return 3;  // d
			case 101 : return 3;  // e
			case 102 : return 3;  // f
			case 103 : return 3;  // g
			case 104 : return 3;  // h
			case 105 : return 3;  // i
			case 106 : return 3;  // j
			case 107 : return 3;  // k
			case 108 : return 3;  // l
			case 109 : return 3;  // m
			case 110 : return 3;  // n
			case 111 : return 3;  // o
			case 112 : return 3;  // p
			case 113 : return 3;  // q
			case 114 : return 3;  // r
			case 115 : return 3;  // s
			case 116 : return 3;  // t
			case 117 : return 3;  // u
			case 118 : return 3;  // v
			case 119 : return 3;  // w
			case 120 : return 3;  // x
			case 121 : return 3;  // y
			case 122 : return 3;  // z
			case 123 : return -1;  // {
			case 124 : return -1;  // |
			case 125 : return -1;  // }
			case 126 : return -1;  // ~
		default : return 0;
		}
	}
	
	

}