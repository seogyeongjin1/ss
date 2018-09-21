package com.client;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class GameRoom extends JPanel{
   private String[] munje={"��ٹ�.jpg","���.jpg","���.jpg",
		   "�ڼ���.jpg","������.jpg","����.jpg",
		   "����.jpg","���߱�.jpg","����.jpg",
		   "�¸�.jpg","�Źξ�.jpg","���̸�.jpg","������.jpg",
		   "�˼���.jpg","�쵵ȯ.jpg","������.jpg","�̱���.jpg","�̵���.jpg",
		   "�Ӽ���.jpg","����.jpg","������.jpg","����ȣ.jpg",
		   "����.jpg","������.jpg","������.jpg","�ǿ�.jpg",
		   "�ϼ���.jpg","������.jpg",
		   "�.jpg","ȫ����.jpg"};
   private String path="Image\\gameImage\\";
   int index=0;
   Image img;
   String filename="";
   public GameRoom()
   {
	   //setBackground(Color.PINK);
	   img=Toolkit.getDefaultToolkit().getImage("Image\\loginback.jpg");
   }
   public void setImage()
   {
	   img=Toolkit.getDefaultToolkit().getImage("Image\\ready.jpg");
       repaint();
   }
   public void setImage(int no)
   {
	   img=Toolkit.getDefaultToolkit().getImage(path+munje[no]);
       filename=munje[no];
	   repaint();
   }
   public void setImage(String file)
   {
	   String temp=file.substring(0,file.lastIndexOf("."));
	   temp=temp+"����"+file.substring(file.lastIndexOf("."));
	   img=Toolkit.getDefaultToolkit().getImage(path+temp);
       
	   repaint();
   }
   public void paint(Graphics g)
   {
	   g.setColor(Color.white);
	   g.fillRect(0, 0, getWidth(), getHeight());
	   g.drawImage(img, 0, 0,getWidth(),getHeight(), this);
   }
}








