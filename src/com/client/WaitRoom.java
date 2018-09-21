package com.client;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
public class WaitRoom extends JPanel{
       JTable table1,table2;// table�� �ܰ�
       DefaultTableModel model1,model2; // ������ ���
       // MVC
       JTextPane ta;
       JTextField tf;
       JPanel moviePanel;
       JButton b1,b2,b3,b4,b5,b6;
       TableColumn column;
       JComboBox box;
       JScrollBar bar;
       Image back;
       public WaitRoom()
       {
    	   back=Toolkit.getDefaultToolkit().getImage("Image\\2.jpg");
    	   ta=new JTextPane();
    	   JScrollPane js1=new JScrollPane(ta);
    	   bar=js1.getVerticalScrollBar();
    	   box=new JComboBox();
    	   box.addItem("white");
    	   box.addItem("red");
    	   box.addItem("green");
    	   box.addItem("orange");
    	   box.addItem("yellow");
    	   box.addItem("pink");
    	   box.addItem("cyan");
    	   
    	   ta.setEditable(false);
    	   tf=new JTextField();
    	   
    	   moviePanel=new JPanel();
    	   moviePanel.setBackground(Color.BLACK);
    	   // Color.black ==> black�� static
    	   
    	   b1=new JButton(new ImageIcon("Image\\roommake.jpg"));
           b6=new JButton(new ImageIcon("Image\\wait_exit.png"));
    	   b3=new JButton("���� ��û");
    	   b4=new JButton("����������");
    	   b5=new JButton("���� ����");
    	   //b6=new JButton("������");
    	   
    	   String[] col1={"���̸�","����/�����","�ο�"};
    	   String[][] row1=new String[0][3];
    	   model1=new DefaultTableModel(row1,col1)
    	   {
    		   public boolean isCellEditable(int r,int c)
    		   {
    			   return false;
    		   }
    	   };
    	   table1=new JTable(model1);
    	   JScrollPane js2=new JScrollPane(table1);
    	
    	   
    	   table1.setRowHeight(27);
    	   table1.getTableHeader().setReorderingAllowed(false);
    	   table1.getTableHeader().setResizingAllowed(false);
    	   
    	   table2.getTableHeader().setReorderingAllowed(false);
    	   table2.getTableHeader().setResizingAllowed(false);
    	   
    	   String[] col2={"ID","��ȭ��","����","��ġ"};
    	   String[][] row2=new String[0][4];
    	   model2=new DefaultTableModel(row2,col2)
    	   {
    		   public boolean isCellEditable(int r,int c)
    		   {
    			   return false;
    		   }
    	   };
    	   table2=new JTable(model2);
    	   JScrollPane js3=new JScrollPane(table2);
    	   
    	   // ��ġ
    	   setLayout(null);
    	   js2.setBounds(40, 50, 1000, 500);//��
           js3.setBounds(1100, 50, 430, 500);//���̵�
           
           js2.setOpaque(false);
           js2.getViewport().setOpaque(false);
           
           js3.setOpaque(false);
           js3.getViewport().setOpaque(false);
           
           js1.setBounds(40, 575, 1000, 225);//ä��
           tf.setBounds(40, 815, 865, 30);//ä��â
    	   box.setBounds(930, 815, 100, 30);
    	   moviePanel.setBounds(1300, 800, 265, 200);
    	   
    	   JPanel p=new JPanel();
    	   p.setLayout(new GridLayout(3,2,3,3));
    	   /*p.add(b1);p.add(b2);
    	   p.add(b3);p.add(b4);
    	   p.add(b5);p.add(b6);*/
    	   
    	   //p.setBounds(515, 465, 265,95 );
    	   add(b1);
    	   add(b6);
    	   b1.setBounds(1100, 660, 220, 100);
           b6.setBounds(1330, 660, 220, 100);
    	   
    	   add(js2);
    	   add(js3);
    	   add(js1);
    	   add(tf);add(box);
    	   //add(moviePanel);
    	   add(p);
    	   
    	   for(int i=0;i<3;i++)
    	   {
    		   column=table1.getColumnModel().getColumn(i);
    		   DefaultTableCellRenderer rn=
    				   new DefaultTableCellRenderer();
    		   if(i==0)
    		   {
    			   column.setPreferredWidth(450);
    			   rn.setHorizontalAlignment(JLabel.LEFT);
    		   }
    		   else if(i==1)
    		   {
    			   column.setPreferredWidth(100);
    			   rn.setHorizontalAlignment(JLabel.CENTER);
    		   }
    		   else if(i==2)
    		   {
    			   column.setPreferredWidth(60);
    			   rn.setHorizontalAlignment(JLabel.CENTER);
    		   }
    		   column.setCellRenderer(rn);
    	   }
    	   
    	   
       }
       @Override
       protected void paintComponent(Graphics g) {
          g.drawImage(back, 0,0, getWidth(),getHeight(),this);

       }
       
}




