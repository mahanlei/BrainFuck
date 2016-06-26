package ui;

import java.io.File;
import java.rmi.RemoteException;

import com.sun.javafx.sg.prism.web.NGWebView;
import com.sun.org.apache.bcel.internal.generic.LAND;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import rmi.RemoteHelper;
import runner.ClientRunner;;

public class RegisterUI extends Stage{
	GridPane grid= new GridPane();
	Scene scene;
	public RegisterUI() {
		super();
		layout();
		this.setScene(scene);
	}
	private void layout() {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		 Text scenetitle = new Text("New account");
	       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL,35));                
	       grid.add(scenetitle, 0, 0, 2, 1);
		
		  //创建userName Label对象，放到第0列，�?0�?
		Label userName=new Label("UserName:");
		userName.setFont(new Font(20));
		grid.add(userName, 0, 1);
		  //创建文本输入框，放到�?1列，�?0�?
		TextField userTextField=new TextField();
		userTextField.setPromptText("Enter new name");
		grid.add(userTextField, 1, 1);
		  //创建passWord Label对象，放到第0列，�?1�?
		Label passWord=new Label("PassWord:");
		passWord.setFont(new Font(20));
		grid.add(passWord,0,2);
		  //创建文本框，放到�?1列，�?1�?
		TextField passWordField=new TextField();
		passWordField.setPromptText("Enter your passWord");
		grid.add(passWordField, 1,2);
		
		Button button=new Button("OK");
		button.setPrefSize(100, 30);
		grid.add(button, 1, 6);
		scene=new Scene(grid,400,260);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String newUserName=userTextField.getText();//新注册用户的名字
					String newPassWord=passWordField.getText();//新用户的密码
					System.out.println(ClientRunner.remoteHelper.getUserService().register(newUserName, newPassWord));
					
//					File newfile=new File("D:\\软工大作业\\"+newUserName);
//					newfile.mkdirs();
				
					RegisterUI.this.close();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	}
       
}
