package ui;

import java.io.File;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.xml.internal.messaging.saaj.soap.impl.TreeException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import runner.ClientRunner;
public class MainUI extends Scene{
	String account="";
	String newFileName="";
    BorderPane bp=new BorderPane();
    File openedFile;
	public MainUI(Parent root, double width, double height) {
		super(root, width, height);
		this.layout();
		this.setRoot(bp);
	}
	
	//获得用户名
    public void getAccount(String userName) {
	  this.account=userName;
	}
	//获得新文件名
    public void getNewFileName(String fileName) {
    	this.newFileName=fileName;
	}
	private void layout(){
        //菜单栏
        MenuBar mBar=new MenuBar();
        //File下的菜单项
        Menu menuFile = new Menu("File"); 
         MenuItem itemNew=new MenuItem("New");
         MenuItem itemSave=new MenuItem("Save");
         MenuItem itemExit=new MenuItem("Exit");
         MenuItem itemOpen=new MenuItem("Open");
         menuFile.getItems().addAll(itemNew,itemOpen,itemSave,itemExit);
        //Edit下的菜单项
        Menu menuEdit=new Menu("Edit");
         MenuItem itemUndo=new MenuItem("Undo");
         itemUndo.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));//设置undo的快捷键
         MenuItem itemRedo=new MenuItem("Redo");
         itemRedo.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));//设置redo的快捷键
         MenuItem itemClear=new MenuItem("Clear");
         itemClear.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));//设置clear的快捷键
         menuEdit.getItems().addAll(itemUndo,itemRedo,itemClear);
         //Run菜单项
        Menu menuRun=new Menu("Run");
        //历史记录
       // Menu menuFileName=new Menu("FileName");
        //用户登录，登出口
        Menu menuUser=new Menu("Account");
        Label uLabel=new Label(account);
        uLabel.setFont(new Font("Aparajita Bold", 19));
        menuUser.setGraphic(uLabel);
         MenuItem itemLogin=new MenuItem("Login");
         MenuItem itemLogout=new MenuItem("logout");
         menuUser.getItems().addAll(itemLogin,itemLogout);
         mBar.getMenus().addAll(menuFile,menuEdit,menuRun,menuUser);
         bp.setTop(mBar);
        //代码区
        TextArea bfCode=new TextArea();
        bfCode.setPrefSize(760,150);
        bp.setCenter(bfCode);
        String codes=bfCode.getText();//获得当前代码区代码
          //滚动条
            ScrollBar sBar=new ScrollBar();
            sBar.setMin(0);
            sBar.setOrientation(Orientation.VERTICAL);
            sBar.setMax(150);
        //输入区域
        HBox hb=new HBox();
        TextArea input=new TextArea();
       input.setPromptText("Enter integers");
       input.setPrefSize(280, 190);
       //输出区域
       TextArea output=new TextArea();
       output.setPrefSize(480, 190);
        hb.getChildren().addAll(input,output);
        hb.setSpacing(40);
        bp.setBottom(hb);
      
 
        //New 事件
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
                 try {
					new NewFileUI().show();;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        //open事件
        itemOpen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
                 new TreeUI().show();			
			}
		});
        //save事件
        itemSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			    String proCodes="";
				try {
					proCodes = ClientRunner.remoteHelper.getIOService().readFile(openedFile.getPath());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    if(!codes.equals(proCodes)){
			    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
			        String time=df.format(new Date());// new Date()为获取当前系统时间
			        File newVersion=new File("D:\\软工大作业\\"+account+"\\"+newFileName+"\\"+time);
			       try {
					ClientRunner.remoteHelper.getIOService().writeFile(newVersion.getPath(), codes);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    }
			}
		});
        //Login 事件
        itemLogin.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			new LoginUI().show();
        			
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        });
        //logout事件
        itemLogout.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			new LoginUI().show();;
        		} catch (Exception e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
        });
        //Exit事件
         itemExit.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		ClientRunner.primaryStage.close();
        	}
        });
         
	}
} 
