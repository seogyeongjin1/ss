package com.common;
// ��� ���� 
/*
 *    ���� �������� (client,server�� ���)
 *    �� 
 *     200 : ���� ����
 *     403 : ���� �ź�
 *     404 : �����̳� ��θ��� Ʋ�����
 *     500 : �ҽ����� 
 *     415 : �ѱۺ�ȯ �ڵ尡 Ʋ�����
 *     
 *     ��) login , makerook ,roomin ,roomout
 */
public class Function {
    // �α��� 
	public static final int LOGIN=100;
	public static final int MYLOG=110;
	// �� ����
	public static final int MAKEROOM=200;
	public static final int MYROOMIN=210;
	public static final int MYROOMOUT=220;
	public static final int ROOMADD=230;// �濡 �����ϴ� 
	public static final int ROOMOUT=240;// ���� �ִ� ��� ó��
	public static final int WAITUPDATE=250;// ���� ���� 
	public static final int ROOMNAMEUPDATE=260;
	public static final int INVATE=270;
	// ����
	// ���� 
	public static final int GAMEREADY=500;
	public static final int GAMESTART=510;
	// ��Ÿ (ä��)
	public static final int WAITCHAT=300;
	public static final int ROOMCHAT=310;
	public static final int GAMECHAT=320;
	
	public static final int INFO=400;
	// ������
	public static final int CHATEND=900;
	public static final int MYCHATEND=910;
}








