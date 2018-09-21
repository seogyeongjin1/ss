package com.client;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class MakeRoom extends JFrame implements ActionListener{
    JLabel la1,la2,la3,la4;
    JTextField tf;
    JPasswordField pf;
    JRadioButton rb1,rb2;
    JComboBox box;
    JButton b1,b2;
    public MakeRoom()
    {
    	 la1=new JLabel("���̸�");
    	 la2=new JLabel("����");
    	 la3=new JLabel("��й�ȣ");
    	 la4=new JLabel("�ο�");
    	 
    	 tf=new JTextField();
    	 pf=new JPasswordField();
    	 
    	 rb1=new JRadioButton("����");
    	 rb2=new JRadioButton("�����");
    	 
    	 ButtonGroup bg=new ButtonGroup();
    	 bg.add(rb1);
    	 bg.add(rb2);
    	 
    	 rb1.setSelected(true);
    	 
    	 box=new JComboBox();
    	 for(int i=2;i<=6;i++)
    	 {
    		 box.addItem(i+"��");
    		 /*
    		  *    2��    ==>0
    		  *    3       ==> 1
    		  *    4     2
    		  *    5     3
    		  *    6     4
    		  */
    	 }
    	 
    	 b1=new JButton("Ȯ��");
    	 b2=new JButton("���");
    	 
    	 la3.setVisible(false);
    	 pf.setVisible(false);
    	 //��ġ
    	 setLayout(null);
    	 la1.setBounds(100, 120, 50, 20);
    	 tf.setBounds(200, 120, 150, 20);
    	 
    	 la2.setBounds(100, 160, 50, 20);
    	 rb1.setBounds(200, 160, 70, 20);
    	 rb2.setBounds(275, 160, 70, 20);
    	 
    	 la3.setBounds(150, 200, 60, 20);
    	 pf.setBounds(220, 200, 80, 20);
    	 
    	 la4.setBounds(100, 240, 50, 20);
    	 box.setBounds(200, 240, 100, 20);
    	
    	 JPanel p=new JPanel();
    	 p.add(b1);
    	 p.add(b2);
    	 
    	 p.setBounds(150, 280, 195, 35);
    	 
    	 //�߰�
    	 add(la1);add(tf);
    	 add(la2);add(rb1);add(rb2);
    	 add(la4);add(pf);
    	 add(la3);add(box);
    	 add(p);
    	 
    	 setSize(500, 450);
    	 //setVisible(true);
    	 
    	 rb1.addActionListener(this);
    	 rb2.addActionListener(this);
    }
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
       new MakeRoom().setLocationRelativeTo(null);
	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==rb1)
		{
			la3.setVisible(false);
			pf.setVisible(false);
			pf.setText("");
		}
		if(e.getSource()==rb2)
		{
			la3.setVisible(true);
			pf.setVisible(true);
			pf.setText("");
			pf.requestFocus();
		}
	}

}
