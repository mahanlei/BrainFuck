//请不要修改本文件�?
package serviceImpl;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {

	public String execute(String code, String param) {
		char []codes=code.toCharArray();//将BF代码分解
		int num1=0;//>的数�?
		for(int i=0;i<code.length();i++){
			if(codes[i]=='>'){
		    num1++;
			}
		}
	  char[] cell=new char[num1+100];
		int index=0;//单元指针
		int i=0;//代码指针
		int j=0;//参数指针
		String  c="";//输出的内�?
			do{
			 switch(codes[i]){
			  case '.':
			  c+=cell[index];
			  break;
			  case ',':
				  if((j+1)<param.length()){
				cell[index]=param.charAt(j);
				j=j+1;
				  }
				  else cell[index]=param.charAt(j);
				break;
			  case '[':
				  int index1=1;//[]的指�?
				if(cell[index]==0){
			      do{
			    	  i++;
			    	if(codes[i]=='['){
			    		index1++;
			    	}
			    	else if(codes[i]==']'){
			    		index1--;
			    	}
			    }while(index1!=0);
				}
				break;
			case']':
				int index11=1;
				if(cell[index]!=0){
					   do{
						  i--;
					    	if(codes[i]==']'){
					    		index11++;
					    	}
					    	else if(codes[i]=='['){
					    		index11--;
					    	}
					    }while(index11!=0);
				}
				break;
			case'+':
			   cell[index]++;
				break;
			case'-':
				 cell[index]--;
				break;
			case '>':
				index++;
				break;
			case'<':
				index--;
				break;
			}
			 i++;
			}while(i<codes.length);
			 return c;
	}

public static void main(String []args){
	ExecuteServiceImpl esi=new ExecuteServiceImpl();
	System.out.println(esi.execute(",>++++++[<-------->-],,[<+>-],<.>.","3+4\n"));
}
}
