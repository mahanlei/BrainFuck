//需要客户端的Stub
package service;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public void writeFile(String fileAddress,String codes)throws RemoteException;
	
	public String readFile(String fileAddress)throws RemoteException;
	
	public String readFileList(String userId)throws RemoteException;

	
}
