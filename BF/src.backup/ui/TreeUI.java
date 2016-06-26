package ui;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;
 
public class TreeUI extends Stage {
	Scene scene;
//    private final Node rootIcon = 
//        new ImageView(new Image(("image\\root.png")));
//    private final Image depIcon = 
//        new Image(("image\\department.png"));
    List<Employee> employees = Arrays.<Employee>asList(
            new Employee("Jacob Smith", "Accounts Department"),
            new Employee("Isabella Johnson", "Accounts Department"),           
            new Employee("Ethan Williams", "Sales Department"),
            new Employee("Emma Jones", "Sales Department"),
            new Employee("Michael Brown", "Sales Department"),
            new Employee("Anna Black", "Sales Department"),
            new Employee("Rodger York", "Sales Department"),
            new Employee("Susan Collins", "Sales Department"),
            new Employee("Mike Graham", "IT Support"),
            new Employee("Judy Mayer", "IT Support"),
            new Employee("Gregory Smith", "IT Support"));
    TreeItem<String> rootNode;
 
   
    public TreeUI() {
    	super();
    	layout();
    	this.setScene(scene);
        this.rootNode = new TreeItem<>("MyCompany Human Resources");
    }
 
   private void layout() {
      //  rootNode.setExpanded(true);
        for (Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> depNode = new TreeItem<>(
                    employee.getDepartment()); 
                    
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }
        VBox box = new VBox();
        Scene scene = new Scene(box, 400, 300);
        scene.setFill(Color.LIGHTGRAY);
 
        TreeView<String> treeView = new TreeView<>(rootNode);
        
        box.getChildren().add(treeView);
       
    }
 
    public static class Employee {
 
        private final SimpleStringProperty name;
        private final SimpleStringProperty department;
 
        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }
 
        public String getName() {
            return name.get();
        }
 
        public void setName(String fName) {
            name.set(fName);
        }
 
        public String getDepartment() {
            return department.get();
        }
 
        public void setDepartment(String fName) {
            department.set(fName);
        }
    }
}
