		for (String outStr : strArrayLine){
			lineNum++;
			char[] charS=outStr.toCharArray();
			//System.out.println("-l_"+lineNum+"-");
			int charNum=0;
			int preType=this.getType(charS[0]);
			String lexeme="";
			
			for (int i=0; i<=charS.size();i++){
				
				if (i<charS.size()-1){
					int tempType = this.getType(charS[i+1]);
					
					if (preType == tempType){
						lexeme+=charS[i];
						
					}else if(preType == 3){
						// alphabet
						if (this.isKeyword()){
							Token tempToken=new Token(58, lineNum, charNum, lexeme);
						}
						tokenArrayList.add(tempToken);
						preType=tempType;
					}else if(preType == 2){
						//number
						preType=tempType;
						//Token(int t, lineNum, charNum, lexeme);							
					}else if(preType == 1){
						//particular
						preType=tempType;
						//Token(int t, lineNum, charNum, lexeme);
					}else if(preType == 0){
						//space
						preType=tempType;
						//Token(int t, lineNum, charNum, lexeme);
					}else if(preType == -1){
						//erro
						preType=tempType;
						//Token(int t, lineNum, charNum, lexeme);
					}else{
						
					}
					
					charNum++;
					{
						String tempStr =outStr(startC,endC);
						//Token(int t, lineNum, charNum, lexeme);
						//System.out.print("-c_"+charNum+"-["+c+"]");
					}
				}
				
			}
			
			
			//System.out.println("");
			//System.out.print("*");
			//test output string line content				
			//System.out.println("- out of while strLine: ["+outStr+"] ");

			
		}
	