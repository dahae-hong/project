package network;
import java.io.BufferedOutputStream;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.ObjectInputStream;

import java.io.OutputStreamWriter;

import java.net.ServerSocket;

import java.net.Socket;

 

public class Server1 {

 

public static void main(String[] args) throws IOException, ClassNotFoundException {

 System.out.println("서버");

 ServerSocket server=new ServerSocket(55000);

 
 int count=0;
 while(true) {



 System.out.println("접속 대기중....");

 Socket client=server.accept();

 count++;

 BufferedOutputStream out=new BufferedOutputStream(client.getOutputStream());

 out.write(count);

  out.flush();

  }

 

}

 

}
