/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MBR
 */
public class main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root=fxmlLoader.load();
        MainController control=(MainController)fxmlLoader.getController();
        control.init(stage);
        Scene s=new Scene(root);
        stage.setScene(s);
        stage.setTitle("XML ");
        stage.show();
    }
    public static void main(String[]args){
        launch(args);
        
    }
    
}
