package ������;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import �ͻ���.Doc;
import �ͻ���.User;

public class Server  
{
  
   private ServerSocket server; 
   private Socket connection; 
   private int counter = 1;
   public void runServer()
   {
      try 
      {
         server = new ServerSocket( 6666, 100 );
         while ( true ) 
         {
            try 
            {
               waitForConnection();
              
            }
            catch ( EOFException eofException ) 
            {
            	System.out.println( "\n�������Ͽ�����......" );
            }
            finally 
            {
               counter++;
            }
         }
      }
      catch ( IOException ioException ) 
      {
         ioException.printStackTrace();
      } 
   } 

   private void waitForConnection() throws IOException
   {
      System.out.println( "�ȴ�������......\n" );
      connection = server.accept();
      new CreateServerThread(connection);
      System.out.println( "������" + counter + " ��ǰ����ip: " +
         connection.getInetAddress().getHostName() );
   } 
   
   

}

