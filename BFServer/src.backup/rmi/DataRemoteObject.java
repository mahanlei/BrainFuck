package rmi;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService,ExecuteService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;
		
	
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService=new ExecuteServiceImpl();
	}

	@Override
	public void writeFile(String fileAddress, String codes) throws RemoteException{
		// TODO Auto-generated method stub
		iOService.writeFile(fileAddress, codes);;
	}
	@Override
	public String readFile(String fileAddress) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.readFile(fileAddress);
	}
	@Override
	public File[] readList(String userName) throws RemoteException{
		return iOService.readList(userName);
	}

	@Override
	public boolean[] login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout(username);
	}
	@Override
	 public boolean register(String newUserName,String passWord) throws RemoteException{
		 return userService.register(newUserName, passWord);
	 }
	@Override
	public boolean creatFile(String userName,String fileName)throws RemoteException{
			return userService.creatFile(userName, fileName);
		}
		
    public String execute(String code, String param) throws RemoteException{
			return executeService.execute(code, param);
	}
}
