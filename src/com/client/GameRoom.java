package com.client;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class GameRoom extends JPanel{
   private String[] munje={"��ٹ�","���","���",
		   "�ڼ���","������","����",
		   "����","���߱�","����",
		   "�¸�","�Źξ�","���̸�","������",
		   "�˼���","�쵵ȯ","������","�̱���","�̵���",
		   "�Ӽ���","����","������","����ȣ",
		   "����","������","������","�ǿ�","�ϼ���","������",
		   "�","ȫ����"};
   private String path="Image\\gameImage\\";
   int index=0;
   Image img;
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
	   img=Toolkit.getDefaultToolkit().getImage(path+munje[no]+".jpg");
       repaint();
   }
   public void paint(Graphics g)
   {
	   g.drawImage(img, 0, 0,getWidth(),getHeight(), this);
   }
}








