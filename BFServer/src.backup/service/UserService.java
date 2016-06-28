//�?要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean[] login(String username, String password) throws RemoteException;

	public boolean logout(String username) throws RemoteException;
	
	public boolean register(String newUserName,String passWord) throws RemoteException;
	public boolean creatFile(String userName,String fileName)throws RemoteException;
}
