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
        if(internal==0) sp-=3; 
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
        sp+=3; i=s+1;
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
 
 	public void decompress(ActionEvent a) {
 		FileChooser filechooser = new FileChooser();
		tf.setStyle("-fx-border-color: Yellow;");
		tf_in.setStyle("-fx-border-color: Yellow;");
		File file = filechooser.showOpenDialog(stage);
		
		tf.getChildren().clear();
		tf_in.getChildren().clear();
		filein = true;
		
		String decodedString = Encoder.readAndDecodeFile(file);
		stringbuilder = new StringBuilder(decodedString);
		Text t = new Text(decodedString);
		t.setFill(Color.BLACK);
		tf_in.getChildren().add(t);
 	}

    public void convert_json(ActionEvent a){
    
    if(filein){
    
    
    String s= convert(str_in.toString());
    tf.getChildren().clear();
   tf.setStyle(" -fx-border-color: Yellow;");
    Text t=new Text(s);
    tf.getChildren().add(t);
    
    
    
    
    }
    else {
    
    
     Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("ERROR");
               String s ="Please Enter XML File First";
                      alert.setContentText(s);
                alert.show();

    
    
    
    
    
    
    }
    
    
    
    
    }
	public void compress(ActionEvent a) {
		String stri = Encoder.Encode(str_in.toString());
		if (!filein) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="Please Choose XML File First";
			alert.setContentText(s);
			alert.show();
			return;
		}
		
		FileChooser filechooser = new FileChooser();
		File file = filechooser.showSaveDialog(stage);
		if (file == null || stri == null) {
			return;
		}
		
		if (str_in.toString().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			String s ="There is a Problem in The compression process.";
			alert.setContentText(s);
            alert.show();
            return;
		}
		
		Encoder.writeBytes(file, stri);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Compress Data");
		String s ="Data Successfully Compressed.";
		alert.setContentText(s);
		alert.show();
	}
	
	
	 static String convert(String str){
      Queue<String> stag = new LinkedList<>();
      Queue<String> stag2 = new LinkedList<>();
      Queue<String> sattr = new LinkedList<>();
      Queue<String> space = new LinkedList<>();
      Queue<String> stext = new LinkedList<>();
      
     
      
      String s="";
      String s1="";
      int flagtext=0;
      int flagspace=0;
      int flagzero=0;
      for(int i=0;i<str.length();i++)
      {
          if(str.charAt(i)=='<' && str.charAt(i+1)!='?' && str.charAt(i+1)!='!' )
          {
              flagspace=1;
          }
          if(str.charAt(0)== '<' && flagspace==1 && flagzero==0)
          {
              space.add(" ");
              flagzero=1;
          }
          if(str.charAt(i)=='\n')
          {
            if(str.charAt(i-1)=='>' && flagspace==1)
            {
                int k=i+1;
                if(k<str.length())
                {
                    while(str.charAt(k)==' ')
                    {
                        s=s+' ';
                        k++;
                    }
                    space.add(s);
                    s="";
                    flagspace=0;
                }
            }
         }
      }
      
      
    
      s="";
      int xx=0;
      //System.out.println(space);
      for(int i=0;i<str.length();i++)
      {
          
          if(str.charAt(i)=='<')
          {
              if(str.charAt(i+1)!='?' && str.charAt(i+1)!='!')
              {
                 
                  int j=i;
                  while(str.charAt(j) != '>')
                  {
                    s=s+str.charAt(j);
                    j++;
                  }
                  stag.add(s);
                  
                  s="";
              }
              
              
          }
         
          
      }
      
      
      
      
      String attr="";
      int t;
      int flagatt=0;
      t=stag.size();

     for(int i=0;i<t;i++)
      {
          flagatt=0;
         
          attr=stag.poll();
          if(attr.charAt(1)!='/')
          {
            for(int j=0;j<attr.length();j++)
            {
              if(attr.charAt(j)==' ')
              {
                  attr=attr.substring(j+1);
                  sattr.add(attr);
                  flagatt=1;
                  break;
              }
              
            }
            
            if(flagatt==0)
            {
                sattr.add("");
            }       
         }
         
      }
     
     //System.out.println(sattr);
      
     
     for(int i=0;i<str.length();i++)
      {
          if(str.charAt(i)=='<' && str.charAt(i+1) !='/')
          {
              if(str.charAt(i+1)!='?' && str.charAt(i+1)!='!')
              {
                  int j=i;
                  while(str.charAt(j) != '>')
                  {
                    s=s+str.charAt(j);
                    j++;
                  }
                  stag.add(s);
                  flagtext=1;
                  s="";
              }
          }
          if(str.charAt(i)=='>'&& flagtext==1)
          {
           
             int k=i+1;
             if(k<str.length())
             {
                  while(str.charAt(k) != '<' && str.charAt(k)!= '\n' )
                  {
                    s1=s1+str.charAt(k);
                    k++;
                  }
                  
                  stext.add(s1);
                  flagtext=0;
                  s1="";
                  
             }
           
        }
      }
      
     
     for(int i=0;i<str.length();i++)
      {
          if(str.charAt(i)=='<')
          {
              if(str.charAt(i+1)!='?' && str.charAt(i+1)!='!')
              {
                  int j=i;
                  
                  while(str.charAt(j) != '>')
                  {
                    s=s+str.charAt(j);
                    j++;
                  }
                  if(s.charAt(1)=='/')
                  {
                  stag2.add(s.substring(2));
                  }
                  else if(s.charAt(0)=='<')
                  {
                  stag2.add(s.substring(1));
                  }
                  s="";
              }
          }
      }
    
    
     String s0;
     int j=0;
     int size=stag2.size();
     String []c = new String[size];
     Queue<String> closetags = new LinkedList<>();
     while(!stag2.isEmpty())
     {
         s0=stag2.poll();
         for(int i=0;i<s0.length();i++)
         {
             if(s0.charAt(i)==' ')
             {
                 s0=s0.substring(0,i);
             }
         }
         closetags.add(s0);
         
     }
     
     int n;
     n=closetags.size();
     Stack<String> s22 = new Stack<String>();
    String s23="";
    int flag00=0;
     Queue<String> sclose = new LinkedList<>();
     while(!closetags.isEmpty())
     {
        
            flag00=0;
            s0=closetags.poll();
            if(!(s22.empty()))
            {
            s23=s22.peek();
           
            }
            if(s0.equals(s23) && flag00==0)
            { 
                s22.pop();  
                sclose.add("}");
                
                
                flag00=1;
                
            }
          
            else if(!s0.equals(closetags.peek()) && flag00==0)
            {
                sclose.add("");
                s22.push(s0);
               
                flag00=1;
            }
            else if(s0.equals(closetags.peek()) && flag00==0)
            {
                sclose.add("}");
                closetags.poll();
                
                flag00=1;
            }
            
            
           
     }
  
           
         
         
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
      ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     ////////////// sattr ////////// stag ////////////// stext
     
      
     
     
     
     //System.out.println(stext);
     //System.out.println(sattr);
     
     
     
     
     
     String out="";
     out=out+'{'+'\n';
     String s2="";///////////////tag
     String s3="";//////////////print
     String s4="";//////////////////attribute
     String s5="";////////////////////print attr
     int flagattr=0;
     int yy=0;
     
     while(!stag.isEmpty())
     {
         s2=stag.poll();
         s4=sattr.poll();
        
          
         ////////////////////////////////opening tag ( has attr )
         if(s2.charAt(1)!='/')
         {
              s3="";
              s5="";
              s2=s2.substring(1);
              
              
              for(int i=0;i<s2.length();i++)
              {
                  if(s2.charAt(i)!=' ')
                  {
                    s3=s3+s2.charAt(i);
                  }
                  else if(s2.charAt(i)==' ')
                  {
                      s2=s2.substring(i+1);
                      break;
                  }
              }
              out=out+space.peek()+'"'+s3+'"'+':'+'{'+'\n';
            
             flagattr=0;
              ///////////////////////////////////////attrribute
              if(s4!="")
              {
                  flagattr=1;
                  int equalcount=0;
                  for(int i=0;i<s4.length();i++)
                  {
                      if(s4.charAt(i)=='=')
                      {
                          equalcount++;
                      }
                  }
                 
                 for(int kk=0;kk<equalcount;kk++)
                 {
                    for(int i=0;i<s4.length();i++)
                    {
                        if(s4.charAt(i)=='=')
                        {
                          
                          s5='"'+s4.substring(0,i)+'"'+':';
                          s4=s4.substring(i+2);
                          break;
                          
                         }
                    }
                    if(s5.charAt(1)==' ')
                    {
                        s5='"'+s5.substring(2);
                    }
                    out=out+space.peek()+s5; 
                    s5="";
                   
                    for(int i=0;i<s4.length();i++)
                    {
                        if(s4.charAt(i)!='"')
                        {
                          s5=s5+s4.charAt(i);
                        }
                        if(s4.charAt(i)=='"')
                        {
                            s4=s4.substring(i+1);
                            break;
                        }
                    }
                    
                    out=out+'"'+s5+'"'+','+'\n';
                    s5="";
                     
                 } 
              }
              
              
              
              
              ////////////////////////////////////////////////////////////////////////////////////////////
              int flag11=0;
             if(stext.peek()!="" && flagattr==1 && flag11==0)
             {
               out=out+space.peek()+"#text:"+'"'+stext.poll()+'"'+'\n';
               flagattr=0;
               flag11=1;
             } 
             else if(stext.peek()!="" && flagattr==0 && flag11==0)
             {
                  out=out+space.peek()+'"'+stext.poll()+'"'+'\n';
                  
                  flag11=1;
             }
             else if (stext.peek()=="" && flag11==0)
             {
                 stext.poll();
                 flag11=1;
             }
           out=out+space.peek()+sclose.poll()+'\n';
          
              
              
         }
         
       space.poll();  
       
     }
     
     
     
      if(stag.isEmpty() && !sclose.isEmpty() && !space.isEmpty())
      {
               while(!sclose.isEmpty() && !space.isEmpty())
               {
                   if(space.size()==1)
                   {
                       out=out+' '+sclose.poll()+'\n';
                       space.poll();
                       break;
                   }
                   out=out+space.poll()+sclose.poll()+'\n';
                   
                   
               }
      }
     
     
     
     
     
     out=out+'}';
     
    
     //System.out.println(out);
     
    
      return out;
     } 
        
	
	
	
}
