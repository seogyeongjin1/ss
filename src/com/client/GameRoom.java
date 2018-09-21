package com.client;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class GameRoom extends JPanel{
   private String[] munje={"김다미.jpg","디오.jpg","모모.jpg",
		   "박서준.jpg","박진주.jpg","버논.jpg",
		   "설리.jpg","송중기.jpg","슬기.jpg",
		   "승리.jpg","신민아.jpg","아이린.jpg","오연서.jpg",
		   "옹성우.jpg","우도환.jpg","유병재.jpg","이규형.jpg","이동휘.jpg",
		   "임수향.jpg","제니.jpg","조보아.jpg","조세호.jpg",
		   "지민.jpg","차은우.jpg","케이윌.jpg","피오.jpg",
		   "하성운.jpg","한지민.jpg",
		   "헨리.jpg","홍진영.jpg"};
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
	   temp=temp+"원본"+file.substring(file.lastIndexOf("."));
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








