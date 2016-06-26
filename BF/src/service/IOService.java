//服务器IOService的Stub，内容相同
package service;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public void writeFile(String fileAddress,String codes)throws RemoteException;
	
	public String readFile(String fileAddress)throws RemoteException;
	
	public File[] readList(String userName) throws RemoteException;
}
