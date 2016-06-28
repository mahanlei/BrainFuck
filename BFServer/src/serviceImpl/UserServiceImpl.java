package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Stack;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import service.UserService;

public class UserServiceImpl implements UserService{
    File userList=new File("usersList.txt");
	@Override
	public boolean[] login(String username, String password) throws RemoteException {
		boolean[]result=new boolean[2];
		FileReader fileReader;
		String line=null;
		try {
			fileReader = new FileReader(userList);
			BufferedReader bufferedReader=new BufferedReader(fileReader);
			try {
				line=bufferedReader.readLine();
				fileReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(line!=null){
			String[]lines=line.split(" ");
			if(username.equals(lines[0])){
				result[0]=true;
				if(password.equals(lines[1])){
					result[1]=true;
					break;
				}
				else {
					result[1]=false;
					break;
				}
			}
			else {
				result[0]=false;
				continue;
		}
			}
		return result;
	}
	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}
    public boolean register(String newUserName,String newPassWord){
    	try {
			FileWriter fWriter=new FileWriter(userList,true);
			fWriter.write(newUserName+" "+newPassWord+"\n");
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File newfile=new File(newUserName);
        newfile.mkdir();
        return true;
    }

    public boolean creatFile(String userName,String fileName){
	   File f=new File(userName+"\\"+fileName);
	   f.mkdir();
	   return true;
   }
   
    
}