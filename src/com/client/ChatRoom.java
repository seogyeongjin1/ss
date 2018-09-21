package com.client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class ChatRoom extends JPanel{
       JPanel p1=new JPanel();
       JPanel p2=new JPanel();
       JPanel p3=new JPanel();
       JPanel p4=new JPanel();
       
       Image back;
       
       JTextField t1=new JTextField();
       JTextField t2=new JTextField();
       JTextField t3=new JTextField();
       JTextField t4=new JTextField();
       
       
       JPanel[] pan={p1,p2,p3,p4};
       JTextField[] idtf={t1,t2,t3,t4};
       boolean[] sw=new boolean[4];
       
       JTextArea ta;
       JTextField tf;
       JTable table;
       DefaultTableModel model;
       JButton b1,b2,b3;
       GameRoom gr=new GameRoom();
       JProgressBar bar=new JProgressBar();
       JTextField daptf=new JTextField(15);
       JScrollBar bar1;
       public ChatRoom()
       {
    	   back=Toolkit.getDefaultToolkit().getImage("Image\\2.jpg");
    	   ta=new JTextArea();
    	   JScrollPane js1=new JScrollPane(ta);
    	   tf=new JTextField();
    	   
    	   ta.setEditable(false);
    	   b1=new JButton("시작");
    	   b2=new JButton("다음");
    	   b3=new JButton("나가기");
    	   b1.setEnabled(false);
    	   b2.setEnabled(false);
    	   String[] col={"아이디","성별"};
    	   String[][] row=new String[0][2];
    	   model=new DefaultTableModel(row,col);
    	   table=new JTable(model);
    	   JScrollPane js2=new JScrollPane(table);
    	   
    	   setLayout(null);
    	   pan[0].setBackground(Color.black);
    	   pan[0].setBounds(10, 15, 200, 200);
    	   idtf[0].setBounds(10, 220, 200, 50);
    	   idtf[0].setEnabled(false);
    	   add(pan[0]);
    	   add(idtf[0]);
    	   
    	   pan[1].setBackground(Color.black);
    	   pan[1].setBounds(1030, 15, 200, 200);
           idtf[1].setBounds(1030, 220, 200, 50);
    	   idtf[1].setEnabled(false);
    	   add(pan[1]);
    	   add(idtf[1]);
    	   
    	   pan[2].setBackground(Color.black);
    	   pan[2].setBounds(10, 350, 200, 200);
           idtf[2].setBounds(10, 555, 200, 50);
    	   idtf[2].setEnabled(false);
    	   add(pan[2]);
    	   add(idtf[2]);
    	   
    	   pan[3].setBackground(Color.black);
    	   pan[3].setBounds(1030, 350, 200, 200);
           idtf[3].setBounds(1030, 555, 200, 50);
    	   idtf[3].setEnabled(false);
    	   add(pan[3]);
    	   add(idtf[3]);
    	   
    	   bar.setMaximum(100);
    	   bar.setMinimum(0);
    	   bar.setStringPainted(true);
    	   bar.setForeground(Color.YELLOW);
    	   bar.setBackground(Color.WHITE);
    	   
    	   bar.setBounds(215, 510, 810, 40); // 퍼센트바
    	   add(bar);
    	   JPanel p1=new JPanel();
    	   daptf.setEnabled(false);
    	   daptf.setFont(new Font("궁서", Font.PLAIN,30));  
    	   
    	   p1.add(daptf);
    	   p1.setBounds(215, 555, 810, 50);
    	   add(p1);
    	   
    	   
    	   table.getTableHeader().setReorderingAllowed(false);
    	   table.getTableHeader().setResizingAllowed(false);
    	   
    	   
    	  /* for(int i=0;i<3;i++)
    	   {
    		   pan[i].setBackground(Color.black);
    		   pan[i].setBounds(10+(i*160), 15, 150, 150);
    		   idtf[i].setBounds(10+(i*160),170 ,150, 30);
    		   add(pan[i]);
    		   add(idtf[i]);
    	   }
    	   int k=0;
    	   for(int i=3;i<6;i++)
    	   {
    		   pan[i].setBackground(Color.black);
    		   pan[i].setBounds(10+(k*160), 210, 150, 150);
    		   idtf[i].setBounds(10+(k*160),365 ,150, 30);
    		   add(pan[i]);
    		   add(idtf[i]);
    		   k++;
    	   }*/
    	   bar1= js1.getVerticalScrollBar();
    	   gr.setBounds(215, 15, 810, 495); // 문제나오는 창
           add(gr);
           js1.setBounds(10, 610, 1220 , 200); // textArea
           add(js1);
           tf.setBounds(10, 815, 1220, 30); // textField
           add(tf);
           
           js2.setBounds(1235, 15, 300, 500); // 성명,아이디
           add(js2);
    	   
    	   JPanel p=new JPanel();
    	   p.add(b1);
    	   p.add(b2);
    	   p.add(b3);
    	   
    	   p.setLayout(new GridLayout(3,1,5,5));
           p.setBounds(1235, 520, 300, 330);

    	   add(p);
    	   
       }
       @Override
       protected void paintComponent(Graphics g) {
          g.drawImage(back, 0,0, getWidth(),getHeight(),this);

       }
       
       
}






