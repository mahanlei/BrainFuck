package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public void writeFile(String fileAddress,String codes) {
		File f = new File(fileAddress);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(codes);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public String readFile(String fileAddress) {
		File file=new File(fileAddress);
		String proCodes="";
		try {
			BufferedReader bReader=new BufferedReader(new FileReader(file));
			do{
			 proCodes+=bReader.readLine();
			}while(bReader.readLine().equals(null));
			bReader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return proCodes;
	}

	@Override
	public String readFileList(String userId) {
		
		
		
		return "OK";
	}
	
}
