package com.client;
import java.awt.*;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.client.MainForm.PrograssThread;
import com.common.Function;

import java.awt.event.*;
// ��Ʈ��ũ ���� 
import java.io.*;
import java.util.*;
import java.net.*;
public class MainForm extends JFrame 
implements ActionListener,Runnable,MouseListener{
    CardLayout card=new CardLayout();
    Login login=new Login();
    WaitRoom wr=new WaitRoom();
    Info info=new Info();
    MakeRoom mr=new MakeRoom();
    ChatRoom cr=new ChatRoom();
    Invate iv=new Invate();
    // ��Ʈ��ũ ���� Ŭ����
    Socket s;
    BufferedReader in;
    OutputStream out;
    String myId,myRoom;
    int selRow=-1;
    PrograssThread pt;
    static boolean bthread;
    int score=0;
    public MainForm()
    {
    	setLayout(card);
    	
    	add("LOGIN",login);
    	add("WR",wr);
    	add("CR",cr);
    	setSize(1600, 900);
    	setVisible(true);
    	
    	login.b1.addActionListener(this);
    	login.b2.addActionListener(this);
    	wr.tf.addActionListener(this);
    	wr.table2.addMouseListener(this);
    	wr.table1.addMouseListener(this);
    	wr.b1.addActionListener(this);// �游��� 
    	//wr.b2.addActionListener(this);
    	wr.b6.addActionListener(this);
    	wr.b5.addActionListener(this);
    	info.b.addActionListener(this);
    	
    	
    	mr.b1.addActionListener(this);
    	mr.b2.addActionListener(this);
    	
    	cr.b3.addActionListener(this);
    	cr.b2.addActionListener(this);
    	cr.b1.addActionListener(this);
    	cr.tf.addActionListener(this);
    	cr.daptf.addActionListener(this);
    	iv.table.addMouseListener(this);
    	/*
    	 *   ActionListener : JButton,JMenu,TextField
    	 *   ComboBox : ItemListener
    	 *   JTable : MouseListener
    	 */
    	/*setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e)
    		{
    			JOptionPane.showMessageDialog(
    					MainForm.this,
    					"������ ��ư�� Ŭ���ϼ���");
    		}
		});*/
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
	    	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
				JFrame.setDefaultLookAndFeelDecorated(true);
			}
			catch(Exception e){}
        new MainForm().setLocationRelativeTo(null);
        	
	}
	public void init()
	{
		Style def=StyleContext.
				getDefaultStyleContext().
				getStyle(StyleContext.DEFAULT_STYLE);
		Style red=wr.ta.addStyle("red", def);
		StyleConstants.setForeground(red, Color.red);
		
		Style cyan=wr.ta.addStyle("cyan", def);
		StyleConstants.setForeground(cyan, Color.cyan);
		
		Style yellow=wr.ta.addStyle("yellow", def);
		StyleConstants.setForeground(yellow, Color.yellow);
		
		Style orange=wr.ta.addStyle("orange", def);
		StyleConstants.setForeground(orange, Color.orange);
		
		Style pink=wr.ta.addStyle("pink", def);
		StyleConstants.setForeground(pink, Color.pink);
		
		Style green=wr.ta.addStyle("green", def);
		StyleConstants.setForeground(green, Color.green);
		
		
		
		
	}
	public void append(String msg,String color)
	{
		try
		{
         Document doc=wr.ta.getDocument();
         doc.insertString(doc.getLength(), msg+"\n", 
        		  wr.ta.getStyle(color));
		}catch(Exception ex){}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==login.b2)
		{
			dispose();// �޸� ȸ��
			System.exit(0);//���α׷��� ���� ���� 
		}
		
		else if(e.getSource()==login.b1)
		{
			String id=login.tf1.getText().trim();
			if(id.length()<1)
			{
				JOptionPane.showMessageDialog(this,
						"ID�� �Է��ϼ���");
				login.tf1.requestFocus();
				return;
			}
			String name=login.tf2.getText().trim();
			if(name.length()<1)
			{
				JOptionPane.showMessageDialog(this,
						"��ȭ���� �Է��ϼ���");
				login.tf2.requestFocus();
				return;
			}
			String sex=login.box.getSelectedItem().toString();
			int avata=0;
			if(login.rb1.isSelected())
				avata=1;
			else if(login.rb2.isSelected())
				avata=2;
			else
			    avata=3;
			
			// ������ ���� 
			connection(id, name, sex, avata);
		}
		else if(e.getSource()==wr.tf)
		{
			String data=wr.tf.getText().trim();
			if(data.length()<1)
			      return;
			String color=wr.box.getSelectedItem().toString();
			try
			{
				out.write((Function.WAITCHAT+"|"+data+"|"
			      +color+"\n").getBytes());
			}catch(Exception ex){}
			wr.tf.setText("");
		}
		else if(e.getSource()==wr.b6)
		{
			try
			{
				out.write((Function.CHATEND+"|\n").getBytes());
			}catch(Exception ex){}
			// client(eventó��) => server (run()) ==> clinet(run())
		}
		else if(e.getSource()==wr.b5)
		{
			if(selRow==-1)
			{
				JOptionPane.showMessageDialog(this,
						"���� �� ����� �����ϼ���");
				return;
			}
			String yid=wr.model2.getValueAt(selRow, 0).toString();
			
			try
			{
				out.write((Function.INFO+"|"+yid+"\n").getBytes());
			}catch(Exception ex){}
			selRow=-1;
		}
		else if(e.getSource()==info.b)
		{
			//dispose();
			//System.exit(0);
			info.setVisible(false);
		}
		else if(e.getSource()==wr.b1)
		{
			// �ʱ�ȭ 
			mr.tf.setText("");
			mr.rb1.setSelected(true);// ���� 
			mr.pf.setText("");
			mr.la3.setVisible(false);
			mr.pf.setVisible(false);
			mr.box.setSelectedIndex(0);
		    mr.setLocation(750, 280);
			mr.setVisible(true);
		}
		else if(e.getSource()==mr.b1)
		{
			// �游��� ��û 
			String rn=mr.tf.getText().trim();
			if(rn.length()<1)
			{
				JOptionPane.showMessageDialog(this,
						"���̸��� �Է��ϼ���");
				mr.tf.requestFocus();
				return;
			}
			// ���̸��� ���� �ߺ�üũ 
			String temp="";
			for(int i=0;i<wr.model1.getRowCount();i++)
			{
				temp=wr.model1.getValueAt(i,0).toString();
				if(rn.equals(temp))
				{
					JOptionPane.showMessageDialog(this,
							"�̹� �����ϴ� ���Դϴ�\n�ٽ� �Է��ϼ���");
					mr.tf.setText("");
					mr.tf.requestFocus();
					return;
				}
			}
			// ���̸� �б� 
			// ���� (����,�����) => ���
			String state="",pwd="";
			if(mr.rb1.isSelected())// ������ư Ŭ����
			{
				state="����";
				pwd=" "; // ���ڿ� => ""(null) , " "
			}
			else
			{
				state="�����";
				pwd=String.valueOf(mr.pf.getPassword());
				// new String(char[]) => ���ڿ� 
			}
			// �ο�
		    int inwon=mr.box.getSelectedIndex()+2;
		    // ������ ����
		    try
		    {
		    	out.write((Function.MAKEROOM+"|"+
		            rn+"|"+state+"|"
		    		+pwd+"|"+inwon+"\n").getBytes());
		    	// ����ó�� 
		    	// out.write(�̺�Ʈ,��ư,���콺) ==> ����ó��(run)
		    	// messageTo,messageAll ==> Ŭ���̾�Ʈ ó�� (run)
		    }catch(Exception ex){}
		    mr.setVisible(false);
		}
		else if(e.getSource()==mr.b2)
		{
			mr.setVisible(false);
		}
		else if(e.getSource()==cr.b1)
		{
			try
			{
			   out.write((Function.GAMESTART+"|"+myRoom+"\n").getBytes());
			   cr.b1.setEnabled(false);
			}catch(Exception ex){}
		}
		else if(e.getSource()==cr.b2)
		{
			cr.gr.index++;
			if(cr.gr.index>9)
			{
				cr.b2.setEnabled(false);
				
				JOptionPane.showMessageDialog(this,"������ �����մϴ�\n�� ����:"+score+"��");
				pt.interrupt();
				bthread=false;
			}
			cr.daptf.setEnabled(true);
			cr.gr.setImage(cr.gr.index);
		}
		else if(e.getSource()==cr.b3)
		{
			try
			{
				out.write((Function.ROOMOUT+"|"+myRoom+"\n").getBytes());
			}catch(Exception ex){}
		}
		
		else if(e.getSource()==cr.tf)
		{
			String data=cr.tf.getText().trim();
			if(data.length()<1)
			      return;
			try
			{
				out.write((Function.ROOMCHAT+"|"+data+"\n").getBytes());
			}catch(Exception ex){}
			cr.tf.setText("");
		}
		
		else if(e.getSource()==cr.daptf)
		{
			String dap=cr.daptf.getText();
			if(dap.length()<1)
			{
				JOptionPane.showMessageDialog(this,"���� �Է��ϼ���");
			    cr.daptf.requestFocus();	
				return;
			}
			String temp=cr.gr.filename;
			//System.out.println(temp);
			String mun=temp.substring(0,temp.lastIndexOf("."));
			if(dap.equals(mun))
			{
				Image img=getImageSizeChange(new ImageIcon("Image\\o.png"), cr.p1.getWidth(), cr.p1.getHeight());
				cr.p1.setLayout(new BorderLayout());
				cr.p1.removeAll();
				cr.p1.add("Center",
		    			new JLabel(new ImageIcon(img)));
		    	cr.p1.validate();
		    	score+=10;
		    	
			}
			else
			{
				Image img=getImageSizeChange(new ImageIcon("Image\\x.png"), cr.p1.getWidth(), cr.p1.getHeight());
				cr.p1.setLayout(new BorderLayout());
				cr.p1.removeAll();
				cr.p1.add("Center",
		    			new JLabel(new ImageIcon(img)));
		    	cr.p1.validate();
		    	cr.gr.setImage(temp);
		    	score-=10;
		    	
			}
			cr.daptf.setText("");
			cr.daptf.setEnabled(false);
		}
	}
	public void connection(String id,String name,
			  String sex,int avata)
	{
		try
		{
			s=new Socket("211.238.142.63",7788); 
			in=new BufferedReader(
					new InputStreamReader(s.getInputStream())); 
			out=s.getOutputStream();
			
			// ������ ����
			out.write((Function.LOGIN+"|"+id+"|"
					+name+"|"+sex+"|"+avata+"\n").getBytes());
		}catch(Exception ex){}
		// �����κ��� ������ ���� �о ���
		new Thread(this).start();
	}
	// �������� ������ ���� �޾Ƽ� ��� 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				String msg=in.readLine();
				System.out.println(msg);
				StringTokenizer st=
						new StringTokenizer(msg,"|");
				int protocol=Integer.parseInt(st.nextToken());
				switch(protocol)
				{
				    case Function.LOGIN:
				    {
				    	String[] data={
				    		st.nextToken(),	
				    		st.nextToken(),	
				    		st.nextToken(),	
				    		st.nextToken(),
				    	};
				    	wr.model2.addRow(data);
				    }
				    break;
				    case Function.MYLOG:
				    {
				    	myId=st.nextToken();
				    	card.show(getContentPane(), "WR");
				    	setTitle(myId);
				    }
				    break;
				    case Function.WAITCHAT:
				    {
				    	String data=st.nextToken();
				    	String color=st.nextToken();
				    	init();
				    	append(data,color);
				    	wr.bar.setValue(wr.bar.getMaximum());
				    }
				    break;
				    case Function.CHATEND:
				    {
				    	String id=st.nextToken();
				    	String temp="";
				    	for(int i=0;i<wr.model2.getRowCount();i++)
				    	{
				    		temp=wr.model2.getValueAt(i, 0).toString();
				    		if(id.equals(temp))
				    		{
				    			wr.model2.removeRow(i);
				    			break;
				    		}
				    	}
				    }
				    break;
				    case Function.MYCHATEND:
				    {
				    	dispose();
				    	System.exit(0);
				    }
				    break;
				    case Function.INFO:
				    {
				    	String id=st.nextToken();
				    	String name=st.nextToken();
				    	String sex=st.nextToken();
				    	String pos=st.nextToken();
				    	int avata=Integer.parseInt(st.nextToken());
				    	info.da1.setText(id);
				    	info.da2.setText(name);
				    	info.da3.setText(sex);
				    	info.da4.setText(pos);
				    	String path="c:\\image\\"
				    	+(sex.equals("����")?"m":"w")
				    	+avata+".gif";
				    	info.av.setIcon(new ImageIcon(path));
				    	info.setVisible(true);
				    }
				    break;
				    case Function.MAKEROOM:
				    {
				    	String[] data={
				    		st.nextToken(),
				    		st.nextToken(),
				    		st.nextToken()
				    	};
				    	wr.model1.addRow(data);
				    }
				    break;
				    case Function.MYROOMIN:
				    {
				    	String id=st.nextToken();
				    	String name=st.nextToken();
				    	String sex=st.nextToken();
				    	String avata=st.nextToken();
				    	myRoom=st.nextToken();
				    	// w1,m1
				    	for(int i=0;i<4;i++)
				    	{
				    		if(!cr.sw[i])
				    		{
				    			cr.sw[i]=true;
				    			cr.idtf[i].setText(id);
				    			cr.pan[i].removeAll();
				    			cr.pan[i].setLayout(new BorderLayout());
				    			cr.pan[i].add("Center",
				    					new JLabel(new ImageIcon("c:\\image\\"+(sex.equals("����")?"m":"w")+avata+".gif")));
				    			cr.pan[i].validate();
				    			break;
				    		}
				    	}
				    	String[] data={name,sex};
				    	cr.model.addRow(data);
				    	card.show(getContentPane(), "CR");
				    }
				    break;
				    case Function.ROOMNAMEUPDATE:
				    {
				    	String id=st.nextToken();
				    	String pos=st.nextToken();
				    	String temp="";
				    	for(int i=0;i<wr.model2.getRowCount();i++)
				    	{
				    		temp=wr.model2.getValueAt(i, 0).toString();
				    		if(id.equals(temp))
				    		{
				    			wr.model2.setValueAt(pos, i, 3);
				    			break;
				    		}
				    	}
				    }
				    break;
				    case Function.ROOMADD:
				    {
				    	String id=st.nextToken();
				    	String name=st.nextToken();
				    	String sex=st.nextToken();
				    	String avata=st.nextToken();
				    	// w1,m1
				    	for(int i=0;i<4;i++)
				    	{
				    		if(!cr.sw[i])
				    		{
				    			cr.sw[i]=true;
				    			cr.idtf[i].setText(id);
				    			cr.pan[i].removeAll();
				    			cr.pan[i].setLayout(new BorderLayout());
				    			cr.pan[i].add("Center",
				    					new JLabel(new ImageIcon("c:\\image\\"+(sex.equals("����")?"m":"w")+avata+".gif")));
				    			cr.pan[i].validate();
				    			break;
				    		}
				    	}
				    	String[] data={name,sex};
				    	cr.model.addRow(data);
				    }
				    break;
				    case Function.ROOMCHAT:
				    {
				    	cr.ta.append(st.nextToken()+"\n");
				    	cr.bar1.setValue(cr.bar1.getMaximum());
				    }
				    break;
				    case Function.WAITUPDATE:
				    {
				    	String id=st.nextToken();
				    	String pos=st.nextToken();
				    	String rn=st.nextToken();
				    	String rc=st.nextToken();
				    	String rm=st.nextToken();
				    	
				    	String temp="";
				    	for(int i=0;i<wr.model1.getRowCount();i++)
				    	{
				    		temp=wr.model1.getValueAt(i, 0).toString();
				    		if(temp.equals(rn))
				    		{
				    			if(Integer.parseInt(rc)<1)
				    			{
				    				wr.model1.removeRow(i);
				    			}
				    			else
				    			{
				    				wr.model1.setValueAt(rc+"/"+rm, i, 2);
				    			}
				    			break;
				    		}
				    	}
				    	for(int i=0;i<wr.model2.getRowCount();i++)
				    	{
				    		temp=wr.model2.getValueAt(i, 0).toString();
				    		if(id.equals(temp))
				    		{
				    			wr.model2.setValueAt(pos, i, 3);
				    			break;
				    		}
				    	}
				    }
				    break;
				    case Function.INVATE:
				    {
				    	String id=st.nextToken();
				    	String pos=st.nextToken();
				    	JOptionPane.showMessageDialog(this,
				    			id+"���� "+pos+"������ �ʴ��ϼ̽��ϴ�");
				    	out.write((Function.MYROOMIN+"|"+pos+"\n").getBytes());
				    }
				    break;
				    case Function.MYROOMOUT:
				    {
				    	for(int i=0;i<4;i++)
				    	{
				    		cr.sw[i]=false;
				    		cr.pan[i].removeAll();
				    		cr.pan[i].setLayout(new BorderLayout());
				    		cr.pan[i].add("Center",
				    			new JLabel(new ImageIcon("c:\\image\\def.png")));
				    		cr.idtf[i].setText("");
				    		cr.pan[i].validate();
				    	}
				    	cr.ta.setText("");
				    	for(int i=cr.model.getRowCount()-1;i>=0;i--)
				    	{
				    		cr.model.removeRow(i);
				    	}
				    	card.show(getContentPane(), "WR");
				    }
				    break;
				    case Function.ROOMOUT:
				    {
				    	String id=st.nextToken();
				    	String name=st.nextToken();
				    	for(int i=0;i<4;i++)
				    	{
				    		String temp=cr.idtf[i].getText();
				    		if(id.equals(temp))
				    		{
				    			cr.idtf[i].setText("");
				    			cr.sw[i]=false;
				    			cr.pan[i].removeAll();
				    			cr.pan[i].setLayout(new BorderLayout());
					    		cr.pan[i].add("Center",
					    			new JLabel(new ImageIcon("c:\\image\\def.png")));
				    			cr.pan[i].validate();
				    			break;
				    		}
				    	}
				    	for(int i=0;i<cr.model.getRowCount();i++)
				    	{
				    		String temp=cr.model.getValueAt(i, 0).toString();
				    		if(name.equals(temp))
				    		{
				    			cr.model.removeRow(i);
				    			break;
				    		}
				    	}
				    }
				    break;
				    case Function.GAMEREADY:
				    {
				    	String mid=st.nextToken();
				    	if(mid.equals(myId))
				    	{
				    		cr.b1.setEnabled(true);
				    	}
				    	cr.gr.setImage();
				    }
				    break;
				    case Function.GAMESTART:
				    {
				    	JOptionPane.showMessageDialog(this, "������ �����մϴ�!!");
				    	cr.gr.index=0;
				    	cr.gr.setImage(0);
				    	cr.b1.setEnabled(false);
				    	cr.b2.setEnabled(true);
				    	cr.daptf.setEnabled(true);
				    	cr.daptf.requestFocus();
				    	bthread=true;
				    	pt=new PrograssThread();
				    	pt.start();
				    }
				    break;
				}
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==wr.table2)
		{
			int row=wr.table2.getSelectedRow();
			selRow=row;
			String id=wr.model2.getValueAt(row, 0).toString();
			if(id.equals(myId))
			{
				wr.b3.setEnabled(false);
				wr.b4.setEnabled(false);
			}
			else
			{
				wr.b3.setEnabled(true);
				wr.b4.setEnabled(true);
			}
		}
		else if(e.getSource()==wr.table1)
		{
			if(e.getClickCount()==2)
			{
				int row=wr.table1.getSelectedRow();
				String rn=wr.model1.getValueAt(row,0).toString();
				String inwon=wr.model1.getValueAt(row,2).toString();
				// 6/6
				StringTokenizer st=new StringTokenizer(inwon,"/");
				int a=Integer.parseInt(st.nextToken());
				int b=Integer.parseInt(st.nextToken());
				if(a==b)
				{
					JOptionPane.showMessageDialog(this,
							"�̹� ���� á���ϴ�\n�ٸ� ���� �����ϼ���");
					return;
				}
				// ����� ��û (����)
				try
				{
					out.write((Function.MYROOMIN+"|"+rn+"\n").getBytes());
				}catch(Exception ex){}
			}
		}
		else if(e.getSource()==iv.table)
		{
			if(e.getClickCount()==2)
			{
				int row=iv.table.getSelectedRow();
				String id=iv.model.getValueAt(row, 0).toString();
				try
				{
					out.write((Function.INVATE+"|"+id+"\n").getBytes());
				}catch(Exception ex){}
				iv.setVisible(false);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	class PrograssThread extends Thread
    {
    	public void run()
    	{
    		int k=0;
    		while(bthread)
    		{
    			try
    			{
    				cr.bar.setValue(k);
    				Thread.sleep(300);
    				k++;
    				if(k==100)
    				{
    					JOptionPane.showMessageDialog(MainForm.this, "�� ����:"+score+"��");
    					interrupt();
    				}
    			}catch(Exception ex){}
    		}
    	}
    }
    public Image getImageSizeChange(ImageIcon icon,int width,int height)
    {
    	Image img=icon.getImage();
    	Image change=img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    	return change;
    }
   
}
