package com.client;
import java.awt.*;

import javax.swing.*;
public class Info extends JFrame{
    JLabel av;
    JLabel la1,la2,la3,la4;
    JLabel da1,da2,da3,da4;
    JButton b;
    public Info()
    {
    	av=new JLabel(new ImageIcon("c:\\image\\w1.gif"));
    	la1=new JLabel("ID");
    	la2=new JLabel("대화명");
    	la3=new JLabel("성별");
    	la4=new JLabel("위치");
    	da1=new JLabel("");
    	da2=new JLabel("");
    	da3=new JLabel("");
    	da4=new JLabel("");
    	b=new JButton("확인");
    	
    	setLayout(null);
    	av.setBounds(10, 15, 120, 200);
    	la1.setBounds(135, 15, 50, 30);
    	da1.setBounds(190, 15, 150, 30);
    	
    	la2.setBounds(135, 50, 50, 30);
    	da2.setBounds(190, 50, 150, 30);
    	
    	la3.setBounds(135, 85, 50, 30);
    	da3.setBounds(190, 85, 150, 30);
    	
    	la4.setBounds(135, 120, 50, 30);
    	da4.setBounds(190, 120, 150, 30);
    	b.setBounds(200, 165, 100, 30);
    	add(av);
    	add(la1);add(da1);
    	add(la2);add(da2);
    	add(la3);add(da3);
    	add(la4);add(da4);
    	add(b);
    	setSize(365,265);
    	//setVisible(true);
    }
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
        new Info();
	}*/

}
