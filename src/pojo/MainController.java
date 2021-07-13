/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MBR
 */
public class MainController{
private Stage stage;
    @FXML
    private TextFlow tf=new TextFlow();
    @FXML
    private TextFlow tf_in=new TextFlow();
   
    private boolean flag=false;
    private boolean filein=false;
     StringBuilder str_in=new StringBuilder();
     StringBuilder stringbuilder=new StringBuilder();
     StringBuilder str=new StringBuilder();
    public void choosingXML(ActionEvent actionevent){
        filein=true;
        FileChooser filechooser=new FileChooser();
        File file=filechooser.showOpenDialog(stage);
        tf.getChildren().clear();
        tf_in.getChildren().clear();
        str_in=new StringBuilder();
        stringbuilder=new StringBuilder();
        str=new StringBuilder();
     tf.setStyle(" -fx-border-color: Yellow;");
     tf_in.setStyle(" -fx-border-color: Yellow;");


        if(file!=null){
            String filename=file.getName();
           
           boolean checkxmlfile= filename.contains(".xml")||filename.contains(".txt");
           if(checkxmlfile){

             try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                        String line;
                     while ((line = reader.readLine()) != null)
                     { 
                         str_in.append(line).append("\n");
                         if(line.isEmpty()){
                     continue;
                     }
                           line = line.replaceAll("  ","");
                         stringbuilder.append(line).append("\n");
                     }
                    

                 } 
             catch (IOException e) {
                      Alert alert = new Alert(AlertType.ERROR);
                      alert.setTitle("ERROR");
                      String s =e.getMessage();
                      alert.setContentText(s);
                      alert.show();
                 }
                       
             Text text=new Text(str_in.toString());
             tf_in.getChildren().add(text);
                       
                          
                       
           }
           else {
           Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Enter XML File";
                      alert.setContentText(s);
                alert.show();

               
           }
           

        }
        
    }
    public void minify(ActionEvent action){
         if(filein){
        tf.getChildren().clear();
         tf.setStyle(" -fx-border-color: Yellow;");
    StringBuilder s=new StringBuilder();
StringBuilder s1=new StringBuilder();
    s.append(stringbuilder);
   int f=0;
    for(int i=0;i<s.length();i++){
        if(s.charAt(i)=='<')f=1;
        else if(s.charAt(i)=='>')f=0;
        
        if(i+1<s.length() && s.charAt(i)=='<'&& s.charAt(i+1)=='!'){
        int index=s.toString().indexOf('>',i+1);
        i=index+1;
        
        }
    if(i+1<s.length() && s.charAt(i)=='\n' && s.charAt(i+1)!='<')
    {
           s1.append(s.charAt(i));
         
    }
    if(s.charAt(i)!='\n')
   s1.append(s.charAt(i));
    }
    //System.out.println(s1);
    Text t=new Text(s1.toString());
    tf.getChildren().add(t);
         str=s1;
         }
         else{
    
    Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Enter XML File First";
                      alert.setContentText(s);
                alert.show();

    }
    }
    public void addXMLFormat(ActionEvent action){
    if(filein){
   String s=format(stringbuilder.toString());
   str.delete(0, str.length());
   str.append(s);
   tf.getChildren().clear();
        tf.setStyle(" -fx-border-color: Yellow;");
   
    Text text=new Text(str.toString());
   tf.getChildren().add(text);}
    else{
    
    Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Enter XML File First";
                      alert.setContentText(s);
                alert.show();

    }
    
    }
    
    public void correctXML(ActionEvent action){
        if(filein){
               tf.getChildren().clear();
      str= check_XML(stringbuilder.toString());
                       if(flag==true)
                       {
                       
                          
                       tf.setStyle("-fx-background-color: white; -fx-border-color: red;");
                       flag=false;
                       }
                       else   tf.setStyle("-fx-background-color: white;  -fx-border-color: green;");}
        else {
        
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Choose XML File First";
                      alert.setContentText(s);
                alert.show();

        
        
        }
                       
    }
//Formatting
    public String format(String string) {
    String xml=string; String out="";
    String take=""; char c='>';
    int sp=0,line=0,internal=0;
int n=xml.length(); 
for(int i=0;i<n;i++){ 
    if(xml.charAt(i)=='<' && xml.charAt(i+1)=='/'){
        int s=xml.indexOf(c,i);
        if(internal==0) sp-=2; 
         if (line==1) //sameline
         out+=xml.substring(i,s+1)+"\n";
         else{
         for(int j=0;j<sp;j++)
           out+=" " ; 
  out+=xml.substring(i,s+1)+"\n"; 
         }
         line=0;   internal=0; i=s;
    }
  else  if(xml.charAt(i)=='<' && xml.charAt(i+1)!='!' && xml.charAt(i+1)!='?'){
          int s=xml.indexOf(c,i); 
         int slash= xml.indexOf("/", i); internal=0;
         if((slash<s)&& s==slash+1) // self closing tag
         {
             for(int j=0;j<sp;j++)
           out+=" " ; 
        out+=xml.substring(i,s+1);
         out+="\n";  i=s;
         internal=1;
        continue;
         }           
            for(int j=0;j<sp;j++)
           out+=" " ; 
        out+=xml.substring(i,s+1);
        sp+=2; i=s+1;
         s=xml.indexOf('<',i); 
        if((xml.charAt(i)!='\n' && xml.charAt(i)!='<') || (xml.charAt(i)=='\n' &&xml.charAt(i+1)!='<' )&&xml.charAt(i+1)!='/'){ //closing tag in the same line or text
            line=1; 
            if(xml.charAt(i)!='\n')
            out+=xml.substring(i,s); 
            else
               out+=xml.substring(i+1,s);  
            i--;
        }
        else{ out+="\n"; i--;}
    }
    else  if(xml.charAt(i)=='<' &&( xml.charAt(i+1)=='!' || xml.charAt(i+1)=='?')){
          int s=xml.indexOf(c,i); 
        out+=xml.substring(i,s+1); i=s;
        out+="\n";
}}
       return out ; 
}
    void init(Stage s) {
        stage=s;
    }
    StringBuilder check_XML(String line){
         Stack<String> stack=new Stack();
         Stack<Integer> sa=new Stack();
         StringBuilder newline=new StringBuilder();
        StringBuilder errormsg=new StringBuilder();

         Text t2 = new Text();
         Text t1=new Text();
         long lines=0;
          
    for(int i=0;i<line.length();i++){
         
    
   
          if(i+1!=line.length() && line.charAt(i)=='<' && line.charAt(i+1)!='!' && line.charAt(i+1)!='?' && line.charAt(i+1)!='/' ){
              int indexOf=-1;
                                    int indexOf1 = line.indexOf(">",i);
                                   if(line.charAt(indexOf1+1)=='<')sa.add(1);
                                   else sa.add(0);
                                       int  indexOf2=line.indexOf(" ",i+1);
                                       if(indexOf2==-1)indexOf=indexOf1;
                                       else if( indexOf2<indexOf1 )
                                           indexOf=indexOf2;
                                       else if(indexOf2>indexOf1)
                                           indexOf=indexOf1;
                                 if(indexOf!=-1)
                                 {stack.add(line.substring(i+1,indexOf));
                                 //System.out.println(line.substring(i+1,indexOf));
                                 //newline.append("input:").append(line.substring(i+1,indexOf)).append("\n");
                                 }
                                 else 
                                 {//newline.append("ERROR");
                                                                     errormsg.append(" ERROR in Line: ").append(lines).append("\n");
                                                                    
            flag=true;
                                 }
                                } 
    
          else if(i+1!=line.length() && line.charAt(i)=='<' &&  line.charAt(i+1)=='/'){
               if(!sa.isEmpty())sa.pop();
                             if(stack.isEmpty() ) {                        
                                             flag=true;
                                            newline.append('<');
                         errormsg.append(" ERROR in Line: ").append(lines).append(" Missing  Open Tag ");
                 

                        int indexOf=line.indexOf(">",i+1);
                                  if(indexOf!=-1){
                                   
                                     String check=line.substring(i+2,indexOf);
                                            errormsg.append("<").append(check).append(">").append("\n");


                                   }
                              continue;
                               }
                               
                                String check;
                                        int indexOf=line.indexOf(">",i+1);
                                 if(indexOf!=-1)
                                 {check=line.substring(i+2,indexOf);
                                   String s=stack.peek();
                                  
                                 if(!check.equals(stack.pop()) ){
                              // newline.append("output:").append(check).append("\n");
                                     
                                     if(i!=0 && !sa.isEmpty())
                                     {
                                         i=line.indexOf("\n",i+1);
                                  newline.append("</").append(s).append(">");  
                                    flag=true;
                                    errormsg.append(" ERROR in Line: ").append(lines).append("  ").append("Wrong Closing Tag").append("\n");
 
                                     }
                                   else if(sa.isEmpty()){stack.add(s);

                                    errormsg.append(" ERROR in Line: ").append(lines).append("  ").append("Missing open Tag/Tags ").append('<').append(check).append('>').append("\n");
                                     flag=true;
                                     
                                     }
                                     
                                     
                                 }
                           
                                 
                                 
                                 }
                                 
          }
          else if(i!=0 && line.charAt(i)=='>' &&  line.charAt(i-1)=='/'){
              if(!stack.isEmpty())
          stack.pop();
          }
          
          if((i==0 || line.charAt(i)=='\n')&& line.length()!=i+1)
          {lines++;
           
          newline.append("\n").append(" ").append(lines).append(" ");
           
                   }
         if(line.charAt(i)!='\n')
          newline.append(line.charAt(i));
      
    }
    
    while(!stack.isEmpty())
         {lines++;
             newline.append("\n").append(" ").append(lines).append(" ").append("</").append(stack.pop()).append(">");  

           errormsg.append(" ERROR in Line: ").append(lines).append("  Missing Closing Tag/Tags").append("\n");
         
         flag=true;
    }
    
    t1.setText(errormsg.toString());
    t1.setFill(Color.RED);
    tf.getChildren().add(t1); 
    t2.setText(newline.toString());
    if(!flag)
    t2.setFill(Color.GREEN);
    else 
        t2.setFill(Color.BLACK);

    tf.getChildren().add(t2);
    return newline;
    
    }
 
   public void save(ActionEvent action){
        if(filein){   FileChooser filechooser=new FileChooser();

   File file=filechooser.showSaveDialog(stage);
   if(file!=null && str.toString()!=null){
   if(!str.toString().isEmpty())
   savecontent(file,str.toString());
   else {
    Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Make Changes in the XML File First";
                      alert.setContentText(s);
                alert.show();
   
   }
   }
        
        
        }
     else {
        
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Choose XML File First";
                      alert.setContentText(s);
                alert.show();

        
        
        }
   
   }
   
   
    public void doExit(){
        Platform.exit();
    }
 void savecontent(File file,String s){
    try {
        PrintWriter p=new PrintWriter(file);
        p.write(s);
        p.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
 
 
 
 }
 
 public void decompress(ActionEvent a){
      FileChooser filechooser=new FileChooser();
       tf.setStyle(" -fx-border-color: Yellow;");
     tf_in.setStyle(" -fx-border-color: Yellow;");
        File file=filechooser.showOpenDialog(stage);
        str_in=new StringBuilder();
        stringbuilder=new StringBuilder();
        
          try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                        String line;
                     while ((line = reader.readLine()) != null)
                     { 
                         str_in.append(line).append("\n");
                     }
                    
                 } 
             catch (IOException e) {
                      Alert alert = new Alert(AlertType.ERROR);
                      alert.setTitle("ERROR");
                      String s =e.getMessage();
                      alert.setContentText(s);
                      alert.show();
                 }
                       

 tf.getChildren().clear();
 tf_in.getChildren().clear();
 filein=true;
 String stri=Encoder.Decode(str_in.toString());
 str_in.delete(0, str_in.length());
 str_in.append(stri);
 stringbuilder=str_in;
 Text t=new Text(stri);
 t.setFill(Color.BLACK);
 tf_in.getChildren().add(t);
 }
 public void compress(ActionEvent a){
 

 String stri=Encoder.Encode(str_in.toString());
 if(filein){   FileChooser filechooser=new FileChooser();

   File file=filechooser.showSaveDialog(stage);
   if(file!=null && stri!=null){
   if(!str_in.toString().isEmpty())
   {savecontent(file,stri);
   
   
    Alert alert = new Alert(AlertType.INFORMATION);
         alert.setTitle("Compress Data");
               String s ="Data Successfully Compressed.";
                      alert.setContentText(s);
                alert.show(); 
   }
   
   
   else {
    Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="There is a Problem in The compression process.";
                      alert.setContentText(s);
                alert.show();
   
   }
   }
        
        
        }
     else {
        
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Choose XML File First";
                      alert.setContentText(s);
                alert.show();

        
        
        }
 
 }
  
}
