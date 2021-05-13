package network;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
class LoginFrame extends Frame{
	Label idlabel,passlabel;
	TextField id, password;
	Button confirm;
	private BufferedWriter out;

	public void setOut(BufferedWriter out) {
		this.out = out;
	}

	public LoginFrame(BufferedWriter out){
		this.out = out;
		setTitle("login client");
		setBounds(100,100, 500, 200);
		setVisible(true);

		idlabel=new Label("ID:");
		passlabel=new Label("PASS:");
		id=new TextField(20); //���� 8~10�ڸ� �Է�
		password=new TextField(20);//���� 8~10�ڸ� �Է�
		confirm=new Button("LOGIN");
		setLayout(new FlowLayout());
		add(idlabel);
		add(id);
		add(passlabel);
		add(password);
		add(confirm);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
		
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//System.out.println(id.getText());
					StringBuffer data=new StringBuffer();
					data.append("{");
					data.append("id:");
					data.append(id.getText());
					data.append(",");
					data.append("password:");
					data.append(password.getText());
					data.append("}");
					data.append("\n");
					out.write(data.substring(0));
					System.out.println(data.substring(0));
					//out.write("{id:"+id.getText()+",password:"+password.getText()+"}\n");
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
	Date time = new Date();
	String time1 = format1.format(time);
	
	void loginSuccess() {
		removeAll();
		add(new Label("login sucess."+time1));		
		revalidate();
	}
	void loginFail() {
		removeAll();
		add(new Label("login fail."));
		revalidate();
		//�⺻ �α��� ȭ���� �������� ó��
	}
}
public class Client {
	static BufferedWriter out=null;
	
public static void main(String[] args) throws UnknownHostException, IOException {
	System.out.println("Ŭ���̾�Ʈ");
	Socket socket=new Socket("192.168.0.13",55000);
	BufferedWriter out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
	BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	LoginFrame loginf = new LoginFrame(out);
	
	while(true) {
		String data=in.readLine();
		String state = LoginConfirm(data);
		if(state.equals("1")) {
			loginf.loginSuccess();
		}else {
			loginf.loginFail();
		}
		loginf.revalidate();
		
		if(socket.isOutputShutdown()) break;
	}
	socket.shutdownOutput();
	socket.close();
}
//����: ���ڿ� 1, ����: ���ڿ� 0
static String LoginConfirm(String data) {
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
	System.out.println(value[0]);//command 0:�α��λ���
	System.out.println(value[1]);//login 1:����, 0:����
	
	return value[i];
	
}

}
