package ui;

import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import runner.ClientRunner;;

public  class LoginUI extends Stage {
	GridPane grid=new GridPane();
	Scene scene;
	public LoginUI() {
		super();
		layout();
		this.setScene(scene);
	}
	private void layout(){
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	       Text scenetitle = new Text("Welcome");
	       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL,35));                
	       grid.add(scenetitle, 0, 0, 2, 1);
	       //创建userName Label对象，放到第0列，�?1�?
	       Label userName = new Label("User Name:");
	       userName.setFont(new Font(20));
	       grid.add(userName, 0, 1);
	       
	       //创建文本输入框，放到�?1列，�?1�?
	       TextField userTextField = new TextField();
	       userTextField.setPromptText("Enter your account");
	       String inID= userTextField.getText();//用户输入的ID
	       grid.add(userTextField, 1, 1);
	       
           //创建passWord Label对象，放在第0列，�?2�?
	       Label pw = new Label("Password:");
	       pw.setFont(new Font(20));
	       grid.add(pw, 0, 2);
	       
           //创建passWordField
	      PasswordField pwBox=new PasswordField();
	       pwBox.setPromptText("Enter your passWord");
	       String inpassWord=pwBox.getText();//用户输入的密�?
	       grid.add(pwBox,1,2);
	       
	       //register 按钮
	       Button btn2=new Button("register");
	       btn2.setFont(new Font(21));
	       HBox hbBtn2 = new HBox();
	       hbBtn2.setAlignment(Pos.BASELINE_LEFT);
	       hbBtn2.getChildren().add(btn2);//将该按钮控件作为子节�?
	       //login 按钮
	       Button btn1 = new Button("Login");
	       btn1.setFont(new Font(21));
	       HBox hbBtn1=new HBox();
	       hbBtn1.setAlignment(Pos.BASELINE_RIGHT);
	       hbBtn1.getChildren().add(btn1);//将按钮控件作为子节点
	       
	       grid.add(hbBtn2, 0, 4);//将HBox2 pane放到grid中的�?0列，�?4�?
           grid.add(hbBtn1, 1, 4);//将hbBtn1 pane 放到grid中的�?1列，�?4�?
	    		   
	       final Text actiontarget=new Text();//增加用于显示信息的文�?
	       grid.add(actiontarget, 1, 6);
	       scene=new Scene(grid,400,260);
	       //login按钮
	       btn1.setOnAction(new EventHandler<ActionEvent>() {//注册事件handler
	           public void handle(ActionEvent e) {
					boolean[] isin;
					try {
						isin = ClientRunner.remoteHelper.getUserService().login(inID, inpassWord);
						if(isin[0]==false){
							actiontarget.setFill(Color.FIREBRICK);//将文字颜色变�? red
							actiontarget.setText("This account does not exit!");
						}
						if(isin[0]==true&&isin[1]==false){
							actiontarget.setFill(Color.FIREBRICK);
							actiontarget.setText("Password is not correct!");
						}
						if(isin[0]==true&&isin[1]==true){
							try {
								MainUI mFrame=new MainUI(new BorderPane(), 1000, 720);//新的main
								mFrame.getAccount(inID);
								LoginUI.this.close();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
	           }
	        });
	       //register按钮
	       btn2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					LoginUI.this.close();
				    RegisterUI registerUI=new RegisterUI();
				    registerUI.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		   
	}
}
