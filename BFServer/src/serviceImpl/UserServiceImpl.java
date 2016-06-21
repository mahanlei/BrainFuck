package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import service.UserService;

public class UserServiceImpl implements UserService{
 static  ArrayList<String> userName=new ArrayList<>();
 static ArrayList<String>  passWord=new ArrayList<>();
	@Override
	public boolean[] login(String username, String password) throws RemoteException {
		boolean[]result=new boolean[2];
		for(int i=0;i<username.length();i++){
			if(username.equals(userName.get(i))){
				result[0]=true;
				if(password.equals(passWord.get(i))){
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
    public void register(String newUserName,String newPassWord){
    	userName.add(newUserName);
        passWord.add(newPassWord);
    }
}
