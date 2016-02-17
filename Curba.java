/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package curbeBezier;

/**
 *
 * @author Irina.B
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.*;

 class Curba extends  JPanel
 {
   
  ArrayList<Point2D.Double> puncte;
  boolean amAdaugatMouseListener=false;
  int k=0;
  boolean adauga=false;
   mouseListener mouse;
   JButton button1;
   JButton button2;
   JButton button3;
   JTextField text;
   JPanel panel2;
boolean creat=false;
   
  public Curba() throws FileNotFoundException
  {
      this.setLayout(new BorderLayout());
      puncte=new ArrayList<>(); 
      panel2=new JPanel(new FlowLayout());
      
      
              button2=new JButton("Create");
       button2.addActionListener(new ButtonListener2(this));
      panel2.add(button2);
      
      
      button1=new JButton("Incrementeaza");
      button1.addActionListener(new ButtonListener(this));
      panel2.add(button1);
      button1.setVisible(false);
      
      
  
           button3=new JButton("Adauga");
     button3.addActionListener(new ButtonListener3(this));
     button3.setVisible(false);
     panel2.add(button3);
      
      
       text=new JTextField(10);
      panel2.add(text);
      text.setVisible(false);
      

      
      this.add(panel2,BorderLayout.SOUTH);
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
       super.paintComponent(g); 
       this.setBackground(Color.BLACK);
        Graphics2D g2=(Graphics2D)g;
        if(amAdaugatMouseListener==false){
            this.addMouseListener(mouse=new mouseListener(this,g));
        amAdaugatMouseListener=true;
        }
       if(creat){
      if(adauga==false){
          this.poligonDeControl(g);
          this.DeseneazaCurba(g);
     }
      if(adauga==true){
          this.NewCurba();
          this.poligonDeControl(g);
          this.DeseneazaCurba(g);
          adauga=false;
          if(k!=0)k--;

      }
      }
         if(!puncte.isEmpty()){
             g.setColor(Color.cyan);
             for(int i=0;i<puncte.size();i++){
            // g2.drawOval((int)puncte.get(i).x,(int)puncte.get(i).y,10,10);
              g.fillRect((int)puncte.get(i).x,(int)puncte.get(i).y,7,7);
     }
     }
      
       if(k!=0){
       adauga= true;
       this.repaint();
       }
  
  }
    int Factorial(int n)
    {
        if(n==0)
            return 1;
        else
            return n*Factorial(n-1);
    }
   int Combinari(int n,int k)
   {
       return Factorial(n)/(Factorial(k)*Factorial(n-k));
   }
   double EvaluarePolinom( float t,int grad,int i)
   {
       return Combinari(grad,i)*Math.pow(t, i)*Math.pow((1-t),(grad-i));
   }
   void DeseneazaCurba(Graphics g)
   {
       // super.paintComponent(g);
      Graphics2D g2=(Graphics2D)g;
      g2.setStroke(new BasicStroke(2));
      g2.setColor(Color.RED);
        double sumax=0;
       double sumay=0;
       double lastx=puncte.get(0).getX();
       double lasty=puncte.get(0).getY();
       for(float t=0.000f;t<=1.000f;t+=0.001f)
       {
           sumax=sumay=0;
       
       for(int i=0; i<puncte.size();i++)
       {
           sumax+=puncte.get(i).x*EvaluarePolinom(t,puncte.size()-1,i);
           sumay+=puncte.get(i).y*EvaluarePolinom(t,puncte.size()-1,i);
       }
       g2.drawLine((int)lastx,(int)lasty,(int)sumax,(int)sumay);
       lastx=sumax;
       lasty=sumay;
   }
    
   }
   void poligonDeControl(Graphics g)
   {
       
      Graphics2D g2=(Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
      g2.setColor(Color.WHITE);
       for(int i=0;i<puncte.size()-1;i++){
           Point2D.Double punct1=puncte.get(i);
           Point2D.Double punct2=puncte.get(i+1);
           g2.drawLine((int)punct1.x,(int)punct1.y,(int)punct2.x,(int)punct2.y);
       }
   }
   void NewCurba()
   {
      ArrayList<Point2D.Double> new_puncte = new ArrayList<>();
      new_puncte.add(new Point2D.Double(puncte.get(0).x,puncte.get(0).y));
      for(double i =1; i < puncte.size(); i++)
      { 
          double new_x = ((i/(puncte.size()+1))*puncte.get( (int) (i-1)).x) + 
                          ((1-(i/(puncte.size()+1)))*puncte.get((int) i).x);
         
          double new_y = (i/(puncte.size()+1))*puncte.get( (int) (i-1)).y + 
                          (1-(i/(puncte.size()+1)))*puncte.get( (int) i).y;

          new_puncte.add(new Point2D.Double(new_x,new_y));
      }
       new_puncte.add(new Point2D.Double(puncte.get(puncte.size()-1).x,
                                         puncte.get(puncte.size()-1).y));
       puncte = new_puncte;
      
   }
  
      public static void main(String[] args) throws FileNotFoundException{
       JFrame frame=new JFrame("curba");
       frame.setPreferredSize(new Dimension(600,600));
       frame.setLocation(300, 100);
Container contentPane=frame.getContentPane();
contentPane.setLayout(new BorderLayout());
     JPanel panel=new Curba();
     panel.setPreferredSize(new Dimension(400,400));
     contentPane.add(panel,BorderLayout.CENTER);
     JPanel panel2=new JPanel();
     panel2.setLayout(new FlowLayout());
     frame.setResizable(false);
     frame.setVisible(true);
     frame.addWindowListener(new windowListener());
       frame.pack();
       
   }
}
class windowListener extends WindowAdapter{

    @Override
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}
class mouseListener extends  MouseAdapter{
  Curba panel;
  Graphics2D g;
  mouseListener(Curba panel,Graphics g){
      this.panel=panel;
      this.g= (Graphics2D)g;
  }
  @Override
   public void mouseClicked(MouseEvent e){
       panel.amAdaugatMouseListener=true;
      // panel.punct=new Point2D.Double(e.getX(),e.getY());
       panel.puncte.add(new Point2D.Double(e.getX(),e.getY()));
     //  System.out.println(panel.punct.x+" "+panel.punct.y);
         panel.repaint();

   }
}
class ButtonListener implements ActionListener{
    Curba panel;
    ButtonListener(Curba panel){
        this.panel=panel;
    }
    @Override
    public void actionPerformed(ActionEvent e){
         panel.k=1;
        panel.adauga=true;
        panel.repaint();
    }
}
class ButtonListener3 implements ActionListener{
    Curba panel;
    ButtonListener3(Curba panel){
        this.panel=panel;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        panel.k=Integer.parseInt(panel.text.getText());
        panel.text.setText("");
        panel.adauga=true;
        panel.repaint();
    }
}
class ButtonListener2 implements ActionListener{
    Curba panel;
    ButtonListener2(Curba panel){
        this.panel=panel;
    }
    @Override
    public void actionPerformed(ActionEvent e){
       panel.creat=true;
       panel.removeMouseListener(panel.mouse);
      panel.button2.setVisible(false);
      panel.button1.setVisible(true);
        panel.button3.setVisible(true);
      panel.text.setVisible(true);
     
        panel.repaint();
        
    }
}
