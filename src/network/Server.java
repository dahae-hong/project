package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
public static void main(String[] args) throws IOException {
	System.out.println("서버");
	ServerSocket server=new ServerSocket(55000);
	System.out.println("접속대기중...");
	Socket client=server.accept();
	BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
	BufferedWriter out=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
	while(true) {
	String data=in.readLine();
	String rs=data.replace("{","");
	rs=rs.replace("}","");
	String[] array=rs.split(",");
	String[] key=new String[2];
	String[] value=new String[2];
	int i=0;
	for(String s:array) {
		//System.out.println(s);
		String[] as=s.split(":");
		key[i]=as[0];
		value[i]=as[1];
		i++;
	}
	System.out.println(value[0]);//id
	System.out.println(value[1]);//password
	//로그인 여부 확인
	BufferedReader filein=new BufferedReader(new FileReader("user.txt"));
	while(true) {
		String filedata=filein.readLine();
		if(filedata==null) { 
			System.out.println("로그인 실패");
			out.write("{command:0,login:0}\n");
			out.flush();
			break;
		}
		System.out.println(filedata);
		String[] filearray=filedata.split(" ");
		if(filearray[0].equals(value[0]) && filearray[1].equals(value[1])){ 
			System.out.println("로그인 되었습니다."); 
			out.write("{command:0,login:1}\n");
			break;
			}
	}
	
	//로그인/로그인 실패 후 위치
	
	if(client.isInputShutdown()) break;
	}
	client.shutdownInput();
	client.close();
	server.close();
}
}
