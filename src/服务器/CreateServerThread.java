package ������;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import �ͻ���.Doc;
import �ͻ���.User;

class CreateServerThread extends Thread {
    private Socket connection;
    private ObjectOutputStream output; 
    private ObjectInputStream input;
    private FileInputStream fis;
    private ServerSocket server; 

    public CreateServerThread(Socket s)throws IOException {
    	connection = s;
    	output = new ObjectOutputStream( connection.getOutputStream() );
        output.flush(); 
        input = new ObjectInputStream( connection.getInputStream() );
        System.out.println("Client(" + getName() +") come in...");
        System.out.println( "\nIO���������\n" );       
        start();
    }

    public void run() {
    	do 
        { 
           try 
           {
          	String cmd= (String) input.readObject(); 
          	System.out.println(getName() +"���������������������"+cmd);
          	switch (cmd) {
          	case "Search_User":
          		String name=(String) input.readObject();
          		System.out.println("���������û�>>>>>>"+name);
          		sendData(DataProcessing.searchUser(name));
          		System.out.println("������ɣ����ڷ�����Ϣ");
          	    break;
          	case "Search_Doc":
          		String Id=(String) input.readObject();
          		System.out.println("���������ļ�>>>>>>id="+Id);
          		sendData((Doc)DataProcessing.searchDoc(Id));
          		System.out.println("������ɣ����ڷ�����Ϣ");
          		break;
          	case "Insert_Doc":
          		Doc doc=(Doc) input.readObject();
          		System.out.println("����������ļ�>>>>>>");
          		sendData((boolean)DataProcessing.insertDoc(doc.getID(), doc.getCreator(), doc.getTimestamp(), doc.getDescription(), doc.getFilename()));
          		System.out.println("������ɣ����ڷ�����Ϣ");
          		break;
          	case "Search_User with password":
          		String na=(String) input.readObject();
          		String pa=(String) input.readObject();
          		System.out.println("�����������˶��û���Ϣ��>>>>>>name="+na+"\tpassword="+pa);
          		sendData((User)DataProcessing.searchUser(na, pa));
          		System.out.println("������ɣ����ڷ�����Ϣ");
          		break;
          	case "Update_User":
          		String upname=(String) input.readObject();
          		String uppwd=(String) input.readObject();
          		String uprole=(String) input.readObject();
          		System.out.println("���ڸ����û���Ϣ��>>>>>>name="+upname+"\tpassword="+uppwd+"\trole="+uprole);
          		sendData((boolean)DataProcessing.updateUser(upname, uppwd, uprole));
          		System.out.println("������ɣ����ڷ�����Ϣ");
          		break;
          	case "Insert_User":
          		String newname=(String) input.readObject();
          		String newpwd=(String) input.readObject();
          		String newrole=(String) input.readObject();
          		System.out.println("����������û���>>>>>>name="+newname+"\tpassword="+newpwd+"\trole="+newrole);
          		sendData((boolean)DataProcessing.insertUser(newname, newpwd, newrole));
          		System.out.println("�����ɣ����ڷ�����Ϣ");
          		break;
          	case "Delete_User":
          		String delname=(String) input.readObject();
          		System.out.println("����ɾ���û���>>>>>>name="+delname);
          		sendData((boolean)DataProcessing.deleteUser(delname));
          		System.out.println("ɾ����ɣ����ڷ�����Ϣ");
          		break;
          	case "Get_User":
          		System.out.println("���ڷ��������û���Ϣ");
          		sendData((ArrayList<User>)DataProcessing.Alluser());
          		System.out.println("�������");
          		break;
          	case "Get_File":
          		boolean choose=(boolean) input.readObject();
          		String text=(String) input.readObject();
          		ArrayList<Doc> array=new ArrayList<Doc>();
          		System.out.println("���ڷ��������ļ���Ϣ");
          		if(choose) array=DataProcessing.docs.getDoc();
          		else for(int i=0;i<DataProcessing.docs.getDoc().size();i++) {
          			if(DataProcessing.docs.getDoc().get(i).getID().contains(text))
          				array.add(DataProcessing.docs.getDoc().get(i));
          		}
          		sendData((ArrayList<Doc>) array);
          		System.out.println("�������");
          		break;
          	case "Up_File":
          		String newfilename=(String) input.readObject();
          		String fileName = input.readUTF();
                  long fileLength = input.readLong();
                  FileOutputStream  fos =new FileOutputStream(new File("./DocLib./" + newfilename));                
                  byte[] sendBytes =new byte[1024];
                  int transLen =0;
                  System.out.println("----��ʼ�����ļ�<" + fileName +">,�ļ���СΪ<" + fileLength +">----");
                  while(true){
                      int read =0;
                      read = input.read(sendBytes);
                      if(read == -1)
                          break;
                      transLen += read;
                      System.out.println("�����ļ�����" +100 * transLen/fileLength +"%...");
                      fos.write(sendBytes,0, read);
                      fos.flush();
                  }
                  System.out.println("----�����ļ�<" + fileName +">�ɹ�-------");
                  break;
          	case "Down_File":
          		
          		String downfilename=(String) input.readObject();
          		System.out.println("----��ʼ�����ļ�<" + downfilename +">----");
          		try {
          	        try {
      
          	            File file =new File("./DocLib./"+downfilename);
          	            fis =new FileInputStream(file);
          	            output.writeLong(file.length());
          	            output.flush();
          	            //�����ļ�
          	            byte[] receiveBytes =new byte[1024];
          	            int length =0,all=0;
          	            while((length = fis.read(receiveBytes,0, receiveBytes.length)) >0){
          	                all+=length;
          	            	output.write(receiveBytes,0, length);
          	            	output.flush();
          	            	int baifen=(int) (all*1.0*100/file.length());
          	            	System.out.println("�ϴ��ļ�����" +baifen +"%...");
          	            }
          	        }catch (Exception e) {
          	        }finally{
          	            if(fis !=null)
          	                fis.close();
          	        }
          	    }catch (Exception e) {
          	    }
          	}
          	}
           catch ( ClassNotFoundException | IOException | SQLException classNotFoundException ) 
           {
   
           } 

        } while (true );
    }
    public void sendData(Object object)
    {
       try
       {
          output.writeObject(object);
         
          output.flush();
       } 
       catch ( IOException ioException )
       {
     	  System.out.println(ioException.toString());
       }
    } 
}
