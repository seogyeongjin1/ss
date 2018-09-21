package com.server;
import java.net.*;//ServerSocket(대기 소켓),Socket(통신소켓)
// 소켓 : 연결 기계==>소프트웨어로 제작 
import java.io.*; // 입출력 (BufferedReader,OutputStream)
import java.util.*; // 접속자 저장 공간 , 방정보 저장 => Vector

import com.common.Function;
// StringTokonizer : 단어별로 분리 
// 200|id|name|sex|avata
/*
 *    1) 대기소켓 생성 
 *    2) 대기 상태(접속처리)
 *    3) 접속시 => Vector에 저장후에 쓰레드 생성(통신)
 *    
 *    4) 쓰레드와 클라이언트의 통신 
 */
public class Server implements Runnable{
    // Server ==> 접속만 받는다 
	ServerSocket ss;
	// 접속자 저장 
	Vector<Client> waitVc=new Vector<Client>();// table2
	// 방정보 저장
	Vector<Room> roomVc=new Vector<Room>(); // table1 
	// 서버 가동 
	public Server()
	{
		try
		{
			ss=new ServerSocket(7788); //ServerSocket(100,3355); => 서버에 100명 수용가능 생략 시 50명
			// port : 내선 번호 (0~65535) : 0~1023
			// 소켓에 IP,PORT를 내장 (bind())
			// listen() ==> 접속시까지 대기상태 
			// 클라이언트 접속은 50명까지
			System.out.println("Server Start...");
		}catch(Exception ex){}
	}
	public void run()
	{
		// 접속시에 처리
		try
		{
			while(true)
			{
				Socket s=ss.accept(); // 클라이언트 접속시 호출(자동호출)
				// 접속을 했을 경우에는 반드시 클라이언트 정보 확인
				// 클라이언트 정보 : IP,PORT ==> Socket
				// Socket을 쓰레드로 전송  => 쓰레드와 클라이언트의 통신
				// 메소드를 이용해서 전송 
				Client client=new Client(s);
				// 통신을 시작해라 
				client.start();// run()
			}
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Server server=new Server();
        new Thread(server).start();
	}
    // 통신을 담당하는 클래스 
	class Client extends Thread
	{
		Socket s;
		// 입출력 위치 확인 ==> 스트림 
		// 입력
		BufferedReader in;// 요청값 읽기 
		// 출력 
		OutputStream out; // 결과값 응답 
		// in,out ==> Socket에 내장 
		
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
		// 통신 방법
		public void run()
		{
			try
			{
				while(true)
				{
					// 요청값을 받는다
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
							// 데이터 받기 
							id=st.nextToken();
							name=st.nextToken();
							sex=st.nextToken();
							avata=Integer.parseInt(st.nextToken());
							pos="대기실";
							
							// 대기실에 있는 모든 사람에게 정보 전송 
							messageAll(Function.LOGIN+"|"
							        +id+"|"+name+"|"+sex+"|"+pos);
							
							waitVc.addElement(this);
							// 창을 변경한다 (로그인=> 대기실)
							messageTo(Function.MYLOG+"|"+id);
							// 접속자 명단을 받아서 출력 
							for(Client user:waitVc)
							{
								messageTo(Function.LOGIN+"|"
								        +user.id+"|"
										+user.name+"|"
								        +user.sex+"|"
										+user.pos);
							}
							
							// 방정보 전송
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
							// 데이터 받기
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
							// 명령(방들어가기)
							messageTo(Function.MYROOMIN+"|"
									+id+"|"+name+"|"
									+sex+"|"+avata+"|"+room.roomName);
							rn = room.roomName;
							
							// 출력 ==> client
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
							 *   방찾는다
							 *   현재인원 증가
							 *   위치 변경
							 *   ==========
							 *   방에 있는 사람 
							 *     => 방에 들어가는 사람의 정보 전송
							 *     => 입장메세지 
							 *   방에 들어가는 사람 처리
							 *     => 방으로 변경
							 *     => 방에 있는 사람의 모든 정보를 받는다 
							 *   대기실 처리
							 *     => 1) 인원 (table1)
							 *        2) 위치 (table2)
							 *        
							 *   강퇴 , 초대 , 게임 
							 */
							rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									room.current++;
									pos=room.roomName;
									// 방에 있는 사람 처리
									for(int j=0;j<room.userVc.size();j++)
									{
										Client user=room.userVc.elementAt(j);
										user.messageTo(Function.ROOMADD+"|"
											+id+"|"+name+"|"+sex+"|"+avata);
										user.messageTo(Function.ROOMCHAT
												+"|[알림 ☞]"+name+"님이 입장하셨습니다");
									}
									// 방에 들어가는 사람 처리
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
									// 대기실 
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
							 *   방찾는다
							 *   현재인원 증가
							 *   위치 변경
							 *   ==========
							 *   방에 있는 사람 
							 *     => 방에 들어가는 사람의 정보 전송
							 *     => 입장메세지 
							 *   방에 들어가는 사람 처리
							 *     => 방으로 변경
							 *     => 방에 있는 사람의 모든 정보를 받는다 
							 *   대기실 처리
							 *     => 1) 인원 (table1)
							 *        2) 위치 (table2)
							 *        
							 *   강퇴 , 초대 , 게임 
							 */
							rn=st.nextToken();
							for(int i=0;i<roomVc.size();i++)
							{
								Room room=roomVc.elementAt(i);
								if(rn.equals(room.roomName))
								{
									room.current--;
									pos="대기실";
									// 방에 있는 사람 처리
									for(int j=0;j<room.userVc.size();j++)
									{
										Client user=room.userVc.elementAt(j);
										user.messageTo(Function.ROOMOUT+"|"+id+"|"+name);
										user.messageTo(Function.ROOMCHAT
												+"|[알림 ☞]"+name+"님이 퇴장하셨습니다");
									}
									// 방에 들어가는 사람 처리
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
									// 대기실 
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
		// 접속 전체에 전송 
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
		// 접속자 한명에게 전송
		public synchronized void messageTo(String msg)
		{
			try
			 {
				 out.write((msg+"\n").getBytes());
			 }catch(Exception ex){}
		}
	}
}




