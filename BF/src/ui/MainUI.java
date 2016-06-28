package ui;

import java.io.File;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sun.org.apache.regexp.internal.recompile;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import runner.ClientRunner;
public class MainUI extends Scene{
	String account="";//当前登录的用户名
	String newFileName="";//new的新文件名
	BorderPane bp=new BorderPane();
    Label uLabel;
    String openedFilePath;//正打开的文件路径
    TreeView<String>treeView=new TreeView<String>();
    TreeItem<String> rootItem=new TreeItem<String>();
    String cString;//打开历史记录的代码
    TextArea bfCode;
    TextArea input;
    TextArea output;
   				
    private  Node rootIcon = new ImageView(new Image( new File("image\\user.png") .toURI().toString(),16,16,true,true,true));
    private  Node FileIcon ;
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
	  creatTree();
	 
	}
	//创造树视图
	public void creatTree() {
		// TODO Auto-generated method stub
		 File[] fileNames=null;
		 rootItem=new TreeItem<String>(account,rootIcon);
		 try {
		   fileNames = ClientRunner.remoteHelper.getIOService().readList(account);
		   rootItem.setExpanded(true);
		   for(int i=0;i<fileNames.length;i++){
		     TreeItem<String> files=new TreeItem<String>(fileNames[i].getName(),FileIcon = new ImageView(new Image(new File ("image\\folder.png").toURI().toString(),16,16,true,true,true)));
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
		      public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldValue,TreeItem newValue) {
		        if (observable.getValue()!=null&&observable.getValue().getParent() !=null) {
					if (observable.getValue().getParent().getValue() != account) {
						try {
							cString = ClientRunner.remoteHelper.getIOService().readFile(observable.getValue().getParent().getParent().getValue() + "\\"
									+ observable.getValue().getParent().getValue() + "\\"
									+ observable.getValue().getValue());
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						openedFilePath = observable.getValue().getParent().getParent().getValue() + "\\"
									+ observable.getValue().getParent().getValue() + "\\"
									+ observable.getValue().getValue();
					} else {
						openedFilePath = observable.getValue().getParent().getValue() + "\\"
									+ observable.getValue().getValue()+"\\" ;
					  }
				   bfCode.setText(cString);
				}
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
         menuFile.getItems().addAll(itemNew,itemSave,itemExit);
       //Edit下的菜单项
        Menu menuEdit=new Menu("Edit");
         MenuItem itemUndo=new MenuItem("Undo");
         MenuItem itemRedo=new MenuItem("Redo");
         MenuItem itemClear=new MenuItem("Clear");
         menuEdit.getItems().addAll(itemUndo,itemRedo,itemClear);
       //Run菜单项
        Menu menuRun=new Menu("Run");
        MenuItem runMenuItem=new MenuItem("run");
        menuRun.getItems().add(runMenuItem);
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
         
         //代码区注册监听
         RedoUndo ru=new RedoUndo(cString);
         bfCode.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				ru.save(bfCode.getText());
			}
		});
         
         //滚动条
           ScrollBar sBar=new ScrollBar();
           sBar.setMin(0);
           sBar.setOrientation(Orientation.VERTICAL);
           sBar.setMax(150);
      //输入区域
       HBox hb=new HBox();
       input=new TextArea();
       input.setPromptText("Enter integers");
       input.setPrefSize(480, 190);
       //输出区域
       output=new TextArea();
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
		    if(openedFilePath.endsWith("\\")){
		    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
				String time=df.format(new Date());// new Date()为获取当前系统时间
				try {
				ClientRunner.remoteHelper.getIOService().writeFile(openedFilePath+time, codes);
				creatTree();
				} catch (RemoteException e) {
				  e.printStackTrace();
				  }
		    }
		    else{ 
		    	try {
					proCodes = ClientRunner.remoteHelper.getIOService().readFile(openedFilePath);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				 if(codes!=proCodes){
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");//设置日期格式
					String time=df.format(new Date());// new Date()为获取当前系统时间
					try {
					ClientRunner.remoteHelper.getIOService().writeFile(openedFilePath.substring(0,openedFilePath.lastIndexOf("\\"))+"\\"+time, codes);
					creatTree();
					} catch (RemoteException e) {
					  e.printStackTrace();
					  }
				 }
	         }
	      }
		});
      //undo事件
      itemUndo.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			ru.codes.remove(ru.codes.size()-1);
            bfCode.setText(ru.codes.get(ru.codes.size()-1));	
		}
	});
      //redo事件
      itemRedo.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			bfCode.setText(ru.codes.get(1));
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
        //Run事件
         runMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					output.setText(ClientRunner.remoteHelper.getExecuteService().execute(bfCode.getText(), input.getText()));
					System.out.println(ClientRunner.remoteHelper.getExecuteService().execute(bfCode.getText(), input.getText()));
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
} 
