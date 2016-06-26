package serviceImpl;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

import service.UserService;

public class UserServiceImpl implements UserService{
    File user=new File("usersList");
    
	@Override
	public boolean[] login(String username, String password) throws RemoteException {
		boolean[]result=new boolean[2];
		for(int i=0;i<username.length();i++){
			if(username.equals(userNameList.get(i))){
				result[0]=true;
				if(password.equals(passWordList.get(i))){
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
    	userNameList.add(newUserName);
        passWordList.add(newPassWord);
        System.out.println(userNameList.size());
        File newfile=new File(newUserName);
//        newfile.exists();
        newfile.mkdir();
        System.out.println(newfile.exists());
        System.out.println(newfile.getAbsolutePath());
        
        return true;
    }
}
