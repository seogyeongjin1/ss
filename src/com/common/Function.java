package com.common;
// 기능 설정 
/*
 *    내부 프로토콜 (client,server만 사용)
 *    웹 
 *     200 : 정상 수행
 *     403 : 접근 거부
 *     404 : 파일이나 경로명이 틀린경우
 *     500 : 소스에러 
 *     415 : 한글변환 코드가 틀린경우
 *     
 *     예) login , makerook ,roomin ,roomout
 */
public class Function {
    // 로그인 
	public static final int LOGIN=100;
	public static final int MYLOG=110;
	// 방 관련
	public static final int MAKEROOM=200;
	public static final int MYROOMIN=210;
	public static final int MYROOMOUT=220;
	public static final int ROOMADD=230;// 방에 존재하는 
	public static final int ROOMOUT=240;// 남아 있는 사람 처리
	public static final int WAITUPDATE=250;// 대기실 갱신 
	public static final int ROOMNAMEUPDATE=260;
	public static final int INVATE=270;
	// 대기실
	// 게임 
	public static final int GAMEREADY=500;
	public static final int GAMESTART=510;
	// 기타 (채팅)
	public static final int WAITCHAT=300;
	public static final int ROOMCHAT=310;
	public static final int GAMECHAT=320;
	
	public static final int INFO=400;
	// 나가기
	public static final int CHATEND=900;
	public static final int MYCHATEND=910;
}








