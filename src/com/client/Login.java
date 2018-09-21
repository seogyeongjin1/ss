package com.client;
import javax.swing.*;

import sun.awt.image.ToolkitImage;

import java.awt.*;
import java.awt.event.*;//interface
public class Login extends JPanel implements ItemListener{
     JTextField tf1,tf2;
     JComboBox box;
     JLabel av1,av2,av3;
     JRadioButton rb1,rb2,rb3;
     JButton b1,b2;
     Image back;
     
     public Login()
     {
    	 back=Toolkit.getDefaultToolkit().getImage("Image\\login.png");
    	 /*
    	  *    생성자 
    	  *     => 클래스명과 동일 , 리턴형이 없다
    	  *     => 오버로딩을 지원한다
    	  *     => 객체 메모리 할당시에 호출 
    	  *     => 멤버변수,멤버객체의 초기화 
    	  *     => 초기화 블럭 {}  ==> static{}
    	  */
    	 tf1=new JTextField();
    	 tf2=new JTextField();
    	 
    	 box=new JComboBox();
    	 box.addItem("남자");
    	 box.addItem("여자");
    	 
    	 av1=new JLabel(new ImageIcon("image\\av1.gif"));
    	 av2=new JLabel(new ImageIcon("image\\av2.gif"));
    	 av3=new JLabel(new ImageIcon("image\\av3.gif"));
    	 
    	 rb1=new JRadioButton("");
    	 rb1.setOpaque(false);
    	 rb2=new JRadioButton("");
    	 rb2.setOpaque(false);
    	 rb3=new JRadioButton("");
    	 rb3.setOpaque(false);
    	 
    	 b1 = new JButton(new ImageIcon("Image\\btLogin_hud.png"));
    	 
    	 ButtonGroup bg=new ButtonGroup();
    	 bg.add(rb1);
    	 bg.add(rb2);
    	 bg.add(rb3); 
    	 
    	 rb1.setSelected(true);
    	 
    	 b2=new JButton("Cancel");
    	 
    	 //배치
    	 setLayout(null);
    	 tf1.setBounds(731, 390, 280, 30);
    	 tf1.setOpaque(false);
         tf1.setForeground(Color.green);
         tf1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    	 
    	 tf2.setBounds(731, 510, 280, 30);
    	 tf2.setOpaque(false);
         tf2.setForeground(Color.green);
         tf2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    	 
    	 box.setBounds(731, 605, 150, 30);
    	 
    	 av1.setBounds(1100, 120, 200, 220);
    	 av2.setBounds(1100, 350, 220, 220);
         av3.setBounds(1100, 580, 200, 220);
    	 
         rb1.setBounds(1350, 250, 30, 30);
         rb2.setBounds(1350, 500, 30, 30);
         rb3.setBounds(1350, 700, 30, 30);
    	 
    	 b1.setBounds(745, 689, 104, 48);
         b1.setBorderPainted(false);
         b1.setFocusPainted(false);
         b1.setContentAreaFilled(false);
    	 
    	 add(b1);
    	 add(b2);
    	 
    	 
    	 // 추가
    	 add(tf1);
    	 add(tf2);
    	 add(box);
    	 //add(av1);add(av2);add(av3);
    	 //add(rb1);add(rb2);add(rb3);
    	
    	 box.addItemListener(this);
    	 box.setOpaque(false);
     }

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		// ComboBox,List
		if(e.getSource()==box)
		{
			int index=box.getSelectedIndex();
			String sex="";
			if(index==0)
			{
				sex="m";
			}
			else
			{
				sex="w";
			}
			av1.setIcon(new ImageIcon("image\\"+sex+"av1.gif"));
			av2.setIcon(new ImageIcon("image\\"+sex+"av2.gif"));
			av3.setIcon(new ImageIcon("image\\"+sex+"av3.gif"));
		}
	}
	  // 스킨입힐때
	   @Override
	   protected void paintComponent(Graphics g) {
	      g.drawImage(back, 0, 0, getWidth(),getHeight(),this);
	   }
}






