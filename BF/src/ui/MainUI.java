package ui;

import java.io.File;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import runner.ClientRunner;
public class MainUI extends Scene{
	String account="";
	String newFileName="";
    BorderPane bp=new BorderPane();
    Label uLabel;
    File openedFile;
    TreeView<String>treeView=new TreeView<String>();
    TreeItem<String> rootItem=new TreeItem<String>();
    String cString;
    TextArea bfCode;
	public MainUI(Parent root, double width, double height) {
		super(root, width, height);
		this.layout();
		this.setRoot(bp);
	
	}
	//获得用户名
    public void getAccount(String userName) {
	  this.account=userName;
	  uLabel.setText(account);
	  rootItem=new TreeItem<>(account);
	  File[] fileNames=null;
   	try {
   		fileNames = ClientRunner.remoteHelper.getIOService().readList(account);
   		rootItem.setExpanded(true);
   		for(int i=0;i<fileNames.length;i++){
   			TreeItem<String> files=new TreeItem<String>(fileNames[i].getName());
   			File[]history = ClientRunner.remoteHelper.getIOService().readList(account+"\\"+fileNames[i].getName());
   			if (history!=null) {
				for (int j = 0; j < history.length; j++) {
					TreeItem<String> hItem = new TreeItem<String>(history[j].getName());
					files.getChildren().add(hItem);
				} 
			}
			rootItem.getChildren().add(files);
   		}
   	} catch (RemoteException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
   	treeView.setRoot(rootItem);
   	treeView.getFocusModel().focusedItemProperty().addListener(new ChangeListener<TreeItem>() {
          @Override
		public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldValue,
				TreeItem newValue) {
           try {
		     cString=ClientRunner.remoteHelper.getIOService().readFile(observable.getValue().getParent().getParent().getValue()+"\\"+observable.getValue().getParent().getValue()+"\\"+observable.getValue().getValue());
		     System.out.println(cString);
           } catch (RemoteException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
	         }
           bfCode.setText(cString);
		}
   		 
	});
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
       //  MenuItem itemOpen=new MenuItem("Open");
         menuFile.getItems().addAll(itemNew,itemSave,itemExit);
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
        uLabel=new Label();
        uLabel.setFont(new Font("Aparajita Bold", 19));
        menuUser.setGraphic(uLabel);
         MenuItem itemLogin=new MenuItem("Login");
         MenuItem itemLogout=new MenuItem("logout");
         menuUser.getItems().addAll(itemLogin,itemLogout);
         mBar.getMenus().addAll(menuFile,menuEdit,menuRun,menuUser);
         bp.setTop(mBar);
     //树视图区
          bp.setLeft(treeView);
          
      //代码区
         bfCode=new TextArea();
         bfCode.setText(cString);
         bfCode.setPrefSize(750,150);
         bp.setRight(bfCode);
          //滚动条
            ScrollBar sBar=new ScrollBar();
            sBar.setMin(0);
            sBar.setOrientation(Orientation.VERTICAL);
            sBar.setMax(150);
        //输入区域
        HBox hb=new HBox();
        TextArea input=new TextArea();
       input.setPromptText("Enter integers");
       input.setPrefSize(480, 190);
       //输出区域
       TextArea output=new TextArea();
       output.setPrefSize(490, 190);
        hb.getChildren().addAll(input,output);
        hb.setSpacing(40);
        bp.setBottom(hb);
      
        //New 事件
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
                 try {
					new NewFileUI(MainUI.this).show();;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
      //save事件
      itemSave.setOnAction(new EventHandler<ActionEvent>() {
	@Override
		public void handle(ActionEvent event) {
			String codes=bfCode.getText();//获得当前代码区代码
		    String proCodes="";
			try {
				if (openedFile!=null) {
				 proCodes = ClientRunner.remoteHelper.getIOService().readFile(openedFile.getPath());
				 if(!codes.equals(proCodes)){
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
					String time=df.format(new Date());// new Date()为获取当前系统时间
					try {
					ClientRunner.remoteHelper.getIOService().writeFile(	account+"\\"+newFileName+"\\"+time, codes);
					} catch (RemoteException e) {
					  e.printStackTrace();
					  }
				 }
				}
				else{
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
					String time=df.format(new Date());// new Date()为获取当前系统时间
					try {
					ClientRunner.remoteHelper.getIOService().writeFile(	account+"\\"+newFileName+"\\"+time, codes);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
				        e.printStackTrace();
					  }
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        //Login 事件
        itemLogin.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		try {
        			new LoginUI(MainUI.this).show();
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
        			new LoginUI(MainUI.this).show();;
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
