package com.client;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class GameRoom extends JPanel{
   private String[] munje={"김다미","디오","모모",
		   "박서준","박진주","버논",
		   "설리","송중기","슬기",
		   "승리","신민아","아이린","오연서",
		   "옹성우","우도환","유병재","이규형","이동휘",
		   "임수향","제니","조보아","조세호",
		   "지민","차은우","케이윌","피오","하성운","한지민",
		   "헨리","홍진영"};
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








