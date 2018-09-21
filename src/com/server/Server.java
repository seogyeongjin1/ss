package com.server;
import java.net.*;//ServerSocket(��� ����),Socket(��ż���)
// ���� : ���� ���==>����Ʈ����� ���� 
import java.io.*; // ����� (BufferedReader,OutputStream)
import java.util.*; // ������ ���� ���� , ������ ���� => Vector

import com.common.Function;
// StringTokonizer : �ܾ�� �и� 
// 200|id|name|sex|avata
/*
 *    1) ������ ���� 
 *    2) ��� ����(����ó��)
 *    3) ���ӽ� => Vector�� �����Ŀ� ������ ����(���)
 *    
 *    4) ������� Ŭ���̾�Ʈ�� ��� 
 */
public class Server implements Runnable{
    // Server ==> ���Ӹ� �޴´� 
	ServerSocket ss;
	// ������ ���� 
	Vector<Client> waitVc=new Vector<Client>();// table2
	// ������ ����
	Vector<Room> roomVc=new Vector<Room>(); // table1 
	// ���� ���� 
	public Server()
	{
		try
		{
			ss=new ServerSocket(7788); //ServerSocket(100,3355); => ������ 100�� ���밡�� ���� �� 50��
			// port : ���� ��ȣ (0~65535) : 0~1023
			// ���Ͽ� IP,PORT�� ���� (bind())
			// listen() ==> ���ӽñ��� ������ 
			// Ŭ���̾�Ʈ ������ 50�����
			System.out.println("Server Start...");
		}catch(Exception ex){}
	}
	public void run()
	{
		// ���ӽÿ� ó��
		try
		{
			while(true)
			{
				Socket s=ss.accept(); // Ŭ���̾�Ʈ ���ӽ� ȣ��(�ڵ�ȣ��)
				// ������ ���� ��쿡�� �ݵ�� Ŭ���̾�Ʈ ���� Ȯ��
				// Ŭ���̾�Ʈ ���� : IP,PORT ==> Socket
				// Socket�� ������� ����  => ������� Ŭ���̾�Ʈ�� ���
				// �޼ҵ带 �̿��ؼ� ���� 
				Client client=new Client(s);
				// ����� �����ض� 
				client.start();// run()
			}
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Server server=new Server();
        new Thread(server).start();
	}
    // ����� ����ϴ� Ŭ���� 
	class Client extends Thread
	{
		Socket s;
		// ����� ��ġ Ȯ�� ==> ��Ʈ�� 
		// �Է�
		BufferedReader in;// ��û�� �б� 
		// ��� 
		OutputStream out; // ����� ���� 
		// in,out ==> Socket�� ���� 
		
		String id,name,sex,pos;
		int avata;
		String rn;
		public Client(Socket s)
		{
			try
			{
				this.s=s;
				in=new BufferedReader(
						new InputStreamReader(s.getInputStream()));
				out=s.getOutputStream();
				
			}catch(Exception ex){}
		}
		// ��� ���
		public void run()
		{
			try
			{
				while(true)
				{
					// ��û���� �޴´�
					String msg=in.readLine();
					// readLine() => \n
					System.out.println(msg);
					// 100|id|name|sex|avata
					StringTokenizer st=new StringTokenizer(msg,"|");
					int protocol=Integer.parseInt(st.nextToken());
					switch(protocol)
					{
						case Function.LOGIN:
						{
							// ������ �ޱ� 
							id=st.nextToken();
							name=st.nextToken();
							sex=st.nextToken();
							avata=Integer.parseInt(st.nextToken());
							pos="����";
							
							// ���ǿ� �ִ� ��� ������� ���� ���� 
							messageAll(Function.LOGIN+"|"
							        +id+"|"+name+"|"+sex+"|"+pos);
							
							waitVc.addElement(this);
							// â�� �����Ѵ� (�α���=> ����)
							messageTo(Function.MYLOG+"|"+id);
							// ������ ����� �޾Ƽ� ��� 
							for(Client user:waitVc)
							{
								messageTo(Function.LOGIN+"|"
								        +user.id+"|"
										+user.name+"|"
								        +user.sex+"|"
										+user.pos);
							}
							
							// ������ ����
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								messageTo(Function.MAKEROOM+"|"
								           +room.roomName+"|"
								           +room.roomState+"|"
								           +room.current+"/"+room.maxcount);
							}
						}
						break;
						case Function.WAITCHAT:
						{
							String data=st.nextToken();
							String color=st.nextToken();
							messageAll(Function.WAITCHAT+"|["
									+name+"]"+data+"|"+color);
						}
						break;
						case Function.CHATEND:
						{
							messageAll(Function.CHATEND+"|"+id);
							messageTo(Function.MYCHATEND+"|");
							for(int i=0;i<waitVc.size();i++)
							{
								Client user=waitVc.elementAt(i);
								if(id.equals(user.id))
								{
									waitVc.removeElementAt(i);
									in.close();
									out.close();
									break;
								}
							}
						}
						break;
						case Function.INFO:
						{
							String yid=st.nextToken();
							for(Client user:waitVc)
							{
								if(yid.equals(user.id))
								{
									messageTo(Function.INFO+"|"
											+user.id+"|"
											+user.name+"|"
											+user.sex+"|"
											+user.pos+"|"
											+user.avata);
									break;
								}
							}
						}
						break;
						case Function.MAKEROOM:
						{
							// ������ �ޱ�
							Room room=new Room(
									st.nextToken(),
									st.nextToken(), 
									st.nextToken(), 
									Integer.parseInt(st.nextToken()));
							room.userVc.addElement(this);
							pos=room.roomName;
							roomVc.addElement(room);
							messageAll(Function.MAKEROOM+"|"
							           +room.roomName+"|"
							           +room.roomState+"|"
							           +room.current+"/"+room.maxcount);
							// 2/6
							// ���(�����)
							messageTo(Function.MYROOMIN+"|"
									+id+"|"+name+"|"
									+sex+"|"+avata+"|"+room.roomName);
							rn = room.roomName;
							
							// ��� ==> client
							messageAll(Function.ROOMNAMEUPDATE+"|"
									+id+"|"+pos);
							
						}
						break;
						case Function.GAMESTART:
						{
							rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									for(int k=0;k<room.userVc.size();k++)
									{
										Client user=room.userVc.get(k);
										user.messageTo(Function.GAMESTART+"|");
									}
								}
							}
						}
						break;
						case Function.MYROOMIN:
						{
							/*
							 *   ��ã�´�
							 *   �����ο� ����
							 *   ��ġ ����
							 *   ==========
							 *   �濡 �ִ� ��� 
							 *     => �濡 ���� ����� ���� ����
							 *     => ����޼��� 
							 *   �濡 ���� ��� ó��
							 *     => ������ ����
							 *     => �濡 �ִ� ����� ��� ������ �޴´� 
							 *   ���� ó��
							 *     => 1) �ο� (table1)
							 *        2) ��ġ (table2)
							 *        
							 *   ���� , �ʴ� , ���� 
							 */
							rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									room.current++;
									pos=room.roomName;
									// �濡 �ִ� ��� ó��
									for(int j=0;j<room.userVc.size();j++)
									{
										Client user=room.userVc.elementAt(j);
										user.messageTo(Function.ROOMADD+"|"
											+id+"|"+name+"|"+sex+"|"+avata);
										user.messageTo(Function.ROOMCHAT
												+"|[�˸� ��]"+name+"���� �����ϼ̽��ϴ�");
									}
									// �濡 ���� ��� ó��
									room.userVc.addElement(this);
									messageTo(Function.MYROOMIN+"|"
											+id+"|"+name+"|"
											+sex+"|"+avata+"|"+room.roomName);
									for(int k=0;k<room.userVc.size();k++)
									{
										Client user=room.userVc.elementAt(k);
										if(!id.equals(user.id))
										{
										  messageTo(Function.ROOMADD+"|"
											+user.id+"|"+user.name+"|"
											+user.sex+"|"+user.avata);
										}
									}
									// ���� 
									messageAll(Function.WAITUPDATE+"|"
											+id+"|"+pos+"|"+room.roomName+"|"
											+room.current+"|"+room.maxcount);
									if(room.current==room.maxcount)
									{
										for(int k=0;k<room.userVc.size();k++)
										{
											Client user=room.userVc.elementAt(k);
											user.messageTo(Function.GAMEREADY+"|"
												+room.userVc.get(0).id);
											
										}
									}
								}
							}
						}
						break;
						case Function.ROOMOUT:
						{
							/*
							 *   ��ã�´�
							 *   �����ο� ����
							 *   ��ġ ����
							 *   ==========
							 *   �濡 �ִ� ��� 
							 *     => �濡 ���� ����� ���� ����
							 *     => ����޼��� 
							 *   �濡 ���� ��� ó��
							 *     => ������ ����
							 *     => �濡 �ִ� ����� ��� ������ �޴´� 
							 *   ���� ó��
							 *     => 1) �ο� (table1)
							 *        2) ��ġ (table2)
							 *        
							 *   ���� , �ʴ� , ���� 
							 */
							rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									room.current--;
									pos="����";
									// �濡 �ִ� ��� ó��
									for(int j=0;j<room.userVc.size();j++)
									{
										Client user=room.userVc.elementAt(j);
										user.messageTo(Function.ROOMOUT+"|"+id+"|"+name);
										user.messageTo(Function.ROOMCHAT
												+"|[�˸� ��]"+name+"���� �����ϼ̽��ϴ�");
									}
									// �濡 ���� ��� ó��
									//room.userVc.addElement(this);
									messageTo(Function.MYROOMOUT+"|");
									for(int k=0;k<room.userVc.size();k++)
									{
										Client user=room.userVc.elementAt(k);
										if(id.equals(user.id))
										{
										   room.userVc.removeElementAt(k);
										   break;
										}
									}
									// ���� 
									messageAll(Function.WAITUPDATE+"|"
											+id+"|"+pos+"|"+room.roomName+"|"
											+room.current+"|"+room.maxcount);
									if(room.current<1)
									{
										roomVc.removeElementAt(i);
										break;
									}
								}
							}
						}
						break;
						
						case Function.ROOMCHAT:
						{
							String chat=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									for(int j=0;j<room.userVc.size();j++)
									{
										Client user=room.userVc.elementAt(j);
										user.messageTo(Function.ROOMCHAT
												+"|["+name+"]" + chat);
									}
								}	
							}
						}
						break;				
						
						
						case Function.INVATE:
						{
							String yid=st.nextToken();
							for(Client user:waitVc)
							{
								if(yid.equals(user.id))
								{
									user.messageTo(Function.INVATE+"|"
								       +id+"|"+pos);
									break;
								}
							}
						}
						break;
					}
				}
			}catch(Exception ex){}
		}
		// ���� ��ü�� ���� 
		public synchronized void messageAll(String msg)
		{
			 try
			 {
				 for(Client user:waitVc)
				 {
					 user.messageTo(msg);
				 }
			 }catch(Exception ex){}
		}
		// ������ �Ѹ��� ����
		public synchronized void messageTo(String msg)
		{
			try
			 {
				 out.write((msg+"\n").getBytes());
			 }catch(Exception ex){}
		}
	}
}




