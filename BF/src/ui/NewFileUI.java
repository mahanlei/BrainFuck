package ui;

import java.io.File;
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import runner.ClientRunner;

public class NewFileUI extends Stage {
	GridPane grid=new GridPane();
	Scene scene;
	MainUI mainUI;
	public  NewFileUI(MainUI mainUI) {
		super();
		this.mainUI=mainUI;
		layout();
		this.setScene(scene);
	}
	private  void layout() {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	       Text scenetitle = new Text("New file");
	       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL,35));                
	       grid.add(scenetitle, 0, 0, 2, 1);
	       //创建userName Label对象，放到第0列，第1行
	       Label fileName = new Label("fileName:");
	      fileName.setFont(new Font(20));
	       grid.add(fileName, 0, 1);
	       //创建文本输入框，放到第1列，第1行
	       TextField textField = new TextField();
	       textField.setPromptText("Enter your file name");
	       grid.add(textField, 1, 1);
	       //OK
			Button button=new Button("OK");
			button.setPrefSize(100, 30);
			grid.add(button, 1, 6);
			scene= new Scene(grid,400, 260);
			// OKbutton事件,创建 用户名\\文件名 文件夹
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String newFileName= textField.getText();//用户输入的文件名
					String userName=mainUI.account;
					mainUI.getNewFileName(newFileName);
					try {
						ClientRunner.remoteHelper.getUserService().creatFile(userName, newFileName);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					NewFileUI.this.close();
				}
			});
	}
}
