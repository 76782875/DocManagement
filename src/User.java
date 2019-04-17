import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public abstract class User {
	private String name;
	private String password;
	private String role;
	protected ArrayList DocIdList;
	Dimension dim;
	Color LightCyan=new Color(225,255,255);//����
	Color Azure=new Color(240,255,255);//ε��
	Color choosed=new Color(135,206,250);//��
	ArrayList nowfileList=new ArrayList();
	JScrollPane scroll;
	Document userdoc;
	Document filedoc;
	User user;
	Doc doc;
	JList fileList;
	public User(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;	
	}
	
	public boolean changeSelfInfo(String password) throws SQLException{
		//д�û���Ϣ���洢
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			return true;
		}else
			return false;
	}
	
	public boolean downloadFile(String filename) throws IOException{
		/*double ranValue=Math.random();
		if (ranValue>0.5)
			throw new IOException( "Error in accessing file" );*/
		if(new FileOpt().copy("./DocLib/"+filename,"",0))
		return true;
		else return false;
	}
	
	public ArrayList GetNowFilelist(boolean choose,String text) throws SQLException{
		ArrayList<Doc> array=new ArrayList<Doc>();
		if(choose) array=DataProcessing.docs.getDoc();
		else for(int i=0;i<DataProcessing.docs.getDoc().size();i++) {
			if(DataProcessing.docs.getDoc().get(i).getID().contains(text))
				array.add(DataProcessing.docs.getDoc().get(i));
		}
		return array;
	}
	public void ChangeIcon() {
		File icon = null;
		JFileChooser f=new JFileChooser();
		f.setPreferredSize(new Dimension(370,320));
		f.setFileFilter(new FileFilter() {//���ˣ�ֻѡ��.png�ļ�

			@Override
			public boolean accept(File arg0) {
				// TODO �Զ����ɵķ������
				if(arg0.isDirectory()) return true;
				return arg0.getName().endsWith(".png");
			}

			@Override
			public String getDescription() {
				// TODO �Զ����ɵķ������
				return ".png";
			}
			
		});
		f.showOpenDialog(null);
		if(f.isFileSelectionEnabled())
	     icon=f.getSelectedFile();
		if(icon!=null) new FileOpt().copy(icon.getPath(),getName()+".png",1);
	}
	public abstract void showMenu();
	
	public void exitSystem(){
		System.out.println("ϵͳ�˳�, ллʹ�� ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	protected void downUi(Doc doc) {
		JFrame downFrame=new JFrame();
		downFrame.setSize(250,250);
		downFrame.setLocationRelativeTo(null);
		downFrame.getContentPane().setBackground(Azure);
		downFrame.setResizable(false);
		downFrame.setBackground(Azure);
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 0, 0);//10���ؼ��
		downFrame.setLayout(fl);
		JLabel docid=new JLabel("�ļ�id:"+doc.getID());
		docid.setPreferredSize(new Dimension(250,20));
		docid.setBackground(Azure);
		JLabel docer=new JLabel("�ϴ���:"+doc.getCreator());
		docer.setPreferredSize(new Dimension(250,20));
		docer.setBackground(Azure);
		JLabel time=new JLabel("�ϴ�ʱ��:"+doc.getTimestamp());
		time.setPreferredSize(new Dimension(250,20));
		time.setBackground(Azure);
		JLabel describ=new JLabel("�ļ�����:"+doc.getDescription());
		describ.setPreferredSize(new Dimension(250,50));
		describ.setBackground(Azure);
		JLabel fi=new JLabel("�ļ���:"+doc.getFilename());
		fi.setPreferredSize(new Dimension(250,20));
		fi.setBackground(Azure);
		downFrame.add(docid);
		downFrame.add(docer);
		downFrame.add(time);
		downFrame.add(describ);
		downFrame.add(fi);
		JPanel panbtn=new JPanel();
		panbtn.setPreferredSize(new Dimension(250,40));
		panbtn.setBackground(Azure);
		JButton sure=new JButton("����");
		JButton exit=new JButton("�˳�");
		panbtn.add(sure);
		panbtn.add(exit);
		downFrame.add(panbtn);
		JLabel notice1 = new JLabel("");
		downFrame.add(notice1);
		downFrame.setVisible(true);
		sure.addActionListener(new ActionListener(){
			@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO �Զ����ɵķ������
					String fame=doc.getFilename();
					try {
						if(downloadFile(fame)){
							 notice1.setForeground(Color.blue);
							notice1.setText("���سɹ�");
						 }
						else {
							 notice1.setForeground(Color.RED);
								notice1.setText("����ʧ��");
						}
							
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}		
			});
	       exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO �Զ����ɵķ������
					downFrame.dispose();
				}});		
			}
}
