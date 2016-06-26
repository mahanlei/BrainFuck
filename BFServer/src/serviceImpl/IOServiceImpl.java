package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public void writeFile(String fileAddress,String codes) {
		File f = new File(fileAddress);
		try {
			f.createNewFile();
			FileWriter fw = new FileWriter(f, false);
			fw.write(codes);
			fw.flush();
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public String readFile(String fileAddress) {
		File file=new File(fileAddress);
		String proCodes="";
		try {
			BufferedReader bReader=new BufferedReader(new FileReader(file));
			String line=null;
			try {
				while(( line=bReader.readLine())!=null){
						proCodes+=bReader.readLine();
				}
				bReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return proCodes;
	}

	@Override
	  public File[] readList(String userName){
		    File file=new File(userName);
			File[] files=file.listFiles();
	        return files;
		   }

}
