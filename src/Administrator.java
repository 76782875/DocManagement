
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
public class Administrator extends User implements ActionListener,DocumentListener,ListSelectionListener {
	Dimension dim;
	JFrame AdFrame=new JFrame();
	JButton btn[]={new JButton("�޸��û���Ϣ"),new JButton("ע���û�"),new JButton("�û��б�"
			),new JButton("�����ļ�"),new JButton("�ļ��б�"),new JButton("��������"),new JButton("ע����¼"),
			new JButton("�˳�")};
	JPanel pnl;
	JPanel pnl2;
	Color LightCyan=new Color(225,255,255);//����
	Color Azure=new Color(240,255,255);//ε��
	Color choosed=new Color(135,206,250);//��
	ArrayList nowuserList=new ArrayList();
	ArrayList nowfileList=new ArrayList();
	JTextField textuserName;
	JTextField textFileName;
	JList userList;
	JList fileList;
	JScrollPane scroll;
	Document userdoc;
	Document filedoc;
	User user;
	Doc doc;
Administrator(String name,String password,String role){
		super(name,password,role);
		AdFrame=new JFrame("�𾴵�"+this.getName()+",��ӭ�������Ա����\n");
		AdFrame.setSize(600, 400);
		AdFrame.setResizable(false);//���ô�С���ɸı�
		AdFrame.setLocationRelativeTo(null);
		pnl=new JPanel();
		pnl.setSize(200, 400);
		pnl.setBackground(LightCyan);
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 10, 10);
		pnl.setLayout(fl);
		dim= new Dimension(200,30);
		for(int i=0;i<8;i++) {
			if(i>=6) dim= new Dimension(90,30);
			btn[i].setPreferredSize(dim);
			btn[i].setBackground(Azure);
			btn[i].addActionListener(this);
			pnl.add(btn[i]);
		}
		AdFrame.add(pnl);
		pnl2=new JPanel();
		pnl2.setSize(370, 400);
		pnl2.setBackground(Azure);
		AdFrame.add(pnl2);//pnl2��Ҫ����ػ�
	}
@Override
public void showMenu() {
	
	AdFrame.setVisible(true);
	changePwdUi();
	btn[5].setBackground(choosed);
}

@Override
public void actionPerformed(ActionEvent e) {
	// TODO �Զ����ɵķ������
	for(int i=0;i<8;i++) {
		btn[i].setBackground(Azure);
	}
	((JButton)e.getSource()).setBackground(choosed);
	switch(((JButton)e.getSource()).getText()) {
	case "�޸��û���Ϣ":
		 ReNew();
		break;
	case "ע���û�":
		 addUserUi();
		break;
	case "�û��б�":
		GetnowUserList(true);
		DisplayUserList();
		AdFrame.repaint();
		AdFrame.setVisible(true);
		break;
	case "�ļ��б�":
		try {
			nowfileList=GetNowFilelist(true,null);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
		}
		DisplayFileList();
		AdFrame.repaint();
		AdFrame.setVisible(true);
		break;
	case "�����ļ�":
		nowfileList=null;
		try {
			nowfileList=GetNowFilelist(true,null);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
		}
		DisplayFileList();
		textFileName = new JTextField();
	    dim = new Dimension(370,30);
	    textFileName.setPreferredSize(dim);
		pnl2.add(textFileName);
		AdFrame.add(pnl2);
		AdFrame.repaint();
		AdFrame.setVisible(true);
		AdFrame.repaint();
		AdFrame.setVisible(true);
		filedoc=textFileName.getDocument();
		filedoc.addDocumentListener(this);
		fileList.addListSelectionListener(this);
		break;
	case "��������":
		changePwdUi();
			break;
	case "�˳�":
	System.exit(0);
	break;
	case "ע����¼":
		AdFrame.dispose();
		Login returnLogin=new Login();
		break;
}
}
void ReNew(){
	GetnowUserList(true);
	DisplayUserList();
    textuserName = new JTextField();
    dim = new Dimension(370,30);
	textuserName.setPreferredSize(dim);
	pnl2.add(textuserName);
	AdFrame.add(pnl2);
	AdFrame.repaint();
	AdFrame.setVisible(true);
   userdoc=textuserName.getDocument();
	userdoc.addDocumentListener(this);
	userList.addListSelectionListener(this);
}
@Override
public void changedUpdate(DocumentEvent arg0) {
	if(arg0.getDocument().equals(userdoc))
	UpdateuserList();
	if(arg0.getDocument().equals(filedoc))
		UpdateFileList();
}
@Override
public void insertUpdate(DocumentEvent arg0) {
	// TODO �Զ����ɵķ������
	if(arg0.getDocument().equals(userdoc))
		UpdateuserList();
	if(arg0.getDocument().equals(filedoc))
		UpdateFileList();
}
@Override
public void removeUpdate(DocumentEvent arg0) {
	// TODO �Զ����ɵķ������
	if(arg0.getDocument().equals(userdoc))
		UpdateuserList();
	if(arg0.getDocument().equals(filedoc))
		UpdateFileList();
}
@Override
public void valueChanged(ListSelectionEvent arg0) {
	if(arg0.getValueIsAdjusting()) {//һ�ε���¼�
	// TODO �Զ����ɵķ������
		if(arg0.getSource().equals(userList))
		changeUserInfoUi();
		if(arg0.getSource().equals(fileList))
		{doc=(Doc) nowfileList.get(fileList.getSelectedIndex());
			downUi(doc);}
	}
	
}
void UpdateuserList() {
	GetnowUserList(false);
	 DefaultListModel lmodel=(DefaultListModel) userList.getModel();
	 lmodel.removeAllElements();
	 for(int i=0;i<nowuserList.size();i++) {
		    lmodel.addElement(ClasstoString.ToString((User) nowuserList.get(i)));
		    }
}
void UpdateFileList() {
	nowfileList=new ArrayList();
	try {
		nowfileList=GetNowFilelist(false,textFileName.getText());
	} catch (SQLException e) {
	}
	 DefaultListModel lmodel=(DefaultListModel) fileList.getModel();
	 lmodel.removeAllElements();
	 for(int i=0;i<nowfileList.size();i++)
		    lmodel.addElement(ClasstoString.ToString((Doc) nowfileList.get(i)));
}
private void changePwdUi() {
	// TODO �Զ����ɵķ������
	pnl2.removeAll();
	FlowLayout fl = new FlowLayout(FlowLayout.RIGHT, 10, 10);
	pnl2.setLayout(fl);
	ImageIcon icon = new ImageIcon(".\\PersonIcon\\"+this.getName()+".png");
	JLabel	labIcon = new JLabel(icon);
	    dim = new Dimension(370,100);//���һ�㲻Ȼ������
		labIcon.setPreferredSize(dim);
		pnl2.add(labIcon);
	JPanel mUser=new JPanel();
	mUser.setPreferredSize(new Dimension(370,30));
	mUser.setBackground(Azure);
	JLabel labName = new JLabel("�û�����"+this.getName());
	mUser.add(labName);
	JPanel mRole=new JPanel();
	mRole.setPreferredSize(new Dimension(370,30));
	mRole.setBackground(Azure);
	JLabel labrole = new JLabel("ְ�ƣ�"+this.getRole());
	mRole.add(labrole);
	pnl2.add(mUser);
	pnl2.add(mRole);
	JTextField textpword=new JTextField();
	textpword.setPreferredSize(new Dimension(370,30));
	pnl2.add(textpword);
	JPanel pwdpnl=new JPanel();
	pwdpnl.setPreferredSize(new Dimension(370,40));
	pwdpnl.setBackground(Azure);
	JButton pwdbtn=new JButton("�޸�����");
	pwdpnl.add( pwdbtn);
	pnl2.add(pwdpnl);
	JPanel noticepnl=new JPanel();
	noticepnl.setBackground(Azure);
	noticepnl.setPreferredSize(new Dimension(370,40));
	JLabel notice1 = new JLabel();
	noticepnl.add(notice1);
	pnl2.add(noticepnl);
	AdFrame.repaint();
	AdFrame.setVisible(true);
	labIcon.addMouseListener(new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO �Զ����ɵķ������	
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO �Զ����ɵķ������	
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO �Զ����ɵķ������
			ChangeIcon();
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO �Զ����ɵķ������
			
		}});
	pwdbtn.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO �Զ����ɵķ������
			String npwd=textpword.getText();
			if(npwd.equals(""))
			{
				notice1.setForeground(Color.RED);
				notice1.setText("���������룡");
			} else
				try {
					if(changeSelfInfo(npwd)) {
						notice1.setForeground(Color.BLUE);
						notice1.setText("�޸ĳɹ�");
					}
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					notice1.setForeground(Color.RED);
					notice1.setText(e.getMessage());
				}
		}});
}


private void addUserUi() {
	// TODO �Զ����ɵķ������
	pnl2.removeAll();
	FlowLayout fl = new FlowLayout(FlowLayout.RIGHT, 10, 10);
	pnl2.setLayout(fl);
	JPanel newUser=new JPanel();
	JPanel newpwd=new JPanel();
	newUser.setPreferredSize(new Dimension(370,40));
	newUser.setBackground(Azure);
	newpwd.setBackground(Azure);
	newpwd.setPreferredSize(new Dimension(370,40));
	JLabel labName = new JLabel("���û�����");
	newUser.add(labName);
	JTextField textName = new JTextField();
    dim = new Dimension(250,30);
	textName.setPreferredSize(dim);
	newUser.add(textName);
	JLabel labpassword= new JLabel("�������룺");
	JTextField textword=new JTextField();
	textword.setPreferredSize(dim);
	newpwd.add(labpassword);
	newpwd.add(textword);
	pnl2.add(newUser);
	pnl2.add(newpwd);
	JPanel chooseRole=new JPanel();
	chooseRole.setPreferredSize(new Dimension(370,40));
	chooseRole.setBackground(Azure);
	JLabel labchoose = new JLabel("ѡ��ְ�ƣ�");
	chooseRole.add(labchoose);
	JRadioButton box1=new JRadioButton("¼��Ա");//���õ�ѡ
	JRadioButton box2=new JRadioButton("���Ա");
	JRadioButton box3=new JRadioButton("����Ա");
	box1.setBackground(Azure);
	box2.setBackground(Azure);
	box3.setBackground(Azure);
	ButtonGroup bg=new ButtonGroup();
	bg.add(box1);
	bg.add(box2);
	bg.add(box3);
	box1.setSelected(true);//Ĭ�ϵ�ѡ��״̬
	chooseRole.add(box1);
	chooseRole.add(box2);
	if(getName().equals("С��")) chooseRole.add(box3);
	pnl2.add(chooseRole);
	JPanel surebtn=new JPanel();
	surebtn.setPreferredSize(new Dimension(370,40));
	surebtn.setBackground(Azure);
	JButton sure=new JButton("ȷ��ע��");
	surebtn.add(sure);
	pnl2.add(surebtn);
	JPanel noticepnl=new JPanel();
	noticepnl.setBackground(Azure);
	noticepnl.setPreferredSize(new Dimension(370,40));
	JLabel notice1 = new JLabel();
	noticepnl.add(notice1);
	pnl2.add(noticepnl);
	AdFrame.repaint();
	AdFrame.setVisible(true);
	sure.addActionListener(new ActionListener(){
		@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
			User newuser=null;
			String mname=textName.getText();
			String mpwd=textword.getText();
			String mrole=null;
			if(box1.isSelected()) mrole="operator";
			else if(box2.isSelected()) mrole="browser";
			else mrole="administrator";
			if(mname.equals("")) {
				notice1.setForeground(Color.RED);
				notice1.setText("�������û�����");
			}
			else if(mpwd.equals(""))
			{
				notice1.setForeground(Color.RED);
				notice1.setText("���������룡");
			}
			else {
			try {
				if(DataProcessing.insertUser(mname,mpwd, mrole))
				{ notice1.setForeground(Color.blue);
					notice1.setText("ע��ɹ�");}
				else {
					notice1.setForeground(Color.RED);
					notice1.setText("ע��ʧ��,���û����Ѵ��ڣ�");}
			} catch (SQLException e) {
				notice1.setForeground(Color.RED);
				notice1.setText(e.getMessage());
			}
			}	
			}	
		});
}
private void changeUserInfoUi() {
	// TODO �Զ����ɵķ������
	 String strname=null;
	  user=null;
	 DefaultListModel lmodel=(DefaultListModel) userList.getModel();
	 try {
	 user=(User) nowuserList.get(userList.getSelectedIndex());
}catch(Exception e) {
	 }
	 if(user!=null) {
	 JFrame changePwdFrame=new JFrame();
	 changePwdFrame.setSize(250,280);
	 changePwdFrame.setLocationRelativeTo(null);
	 changePwdFrame.getContentPane().setBackground(Azure);
	 changePwdFrame.setResizable(false);
	 FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 10, 10);//10���ؼ��
	 changePwdFrame.setLayout(fl);
	 JLabel notice=new JLabel("����Ա"+this.getName()+"������Ϊ"+user.getName()+"�޸�����:");
	 changePwdFrame.add(notice);
	JLabel labnewword= new JLabel("�����룺");
	 changePwdFrame.add(labnewword);
	 JTextField textnewword=new JTextField();
	     dim = new Dimension(150,30);
		textnewword.setPreferredSize(dim);
		changePwdFrame.add(textnewword);
		JRadioButton box1=new JRadioButton("¼��Ա");//���õ�ѡ
		JRadioButton box2=new JRadioButton("���Ա");
		JRadioButton box3=new JRadioButton("����Ա");
		box1.setBackground(Azure);
		box2.setBackground(Azure);
		box3.setBackground(Azure);
		ButtonGroup bg=new ButtonGroup();
		bg.add(box1);
		bg.add(box2);
		bg.add(box3);
		box1.setSelected(true);//Ĭ�ϵ�ѡ��״̬
		changePwdFrame.add(box1);
		changePwdFrame.add(box2);
		if(getName().equals("С��"))
			changePwdFrame.add(box3);
		JPanel panbtn=new JPanel();
		panbtn.setPreferredSize(new Dimension(250,40));
		panbtn.setBackground(Azure);
		JButton sure=new JButton("ȷ���޸�");
		JButton exit=new JButton("�˳�");
		panbtn.add(sure);
		panbtn.add(exit);
		JPanel delpan=new JPanel();
		delpan.setPreferredSize(new Dimension(250,40));
		delpan.setBackground(Azure);
		JButton del=new JButton("ע���û�");
		delpan.add(del);
		changePwdFrame.add(panbtn);
		changePwdFrame.add(delpan);
		JLabel notice1 = new JLabel("");
		changePwdFrame.add(notice1);
		changePwdFrame.setVisible(true);
		sure.addActionListener(new ActionListener(){
		@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				String changePwd=textnewword.getText();
				if(changePwd.equals("")) changePwd=user.getPassword();
				String changeRole="";
				if(box1.isSelected()) changeRole="operator";
				else if(box2.isSelected()) changeRole="browser";
				else changeRole="administrator";
			 try {
				 if(user.getRole().equals(getRole())) {
					 notice1.setForeground(Color.RED);
					 notice1.setText("Ȩ�޲���");}
				 else if(DataProcessing.updateUser(user.getName(), changePwd, changeRole)){
					 notice1.setForeground(Color.blue);
					notice1.setText("�޸ĳɹ�");
				 }
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				notice1.setForeground(Color.RED);
				notice1.setText(e.getMessage());
			}
			}		
		});
       exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				changePwdFrame.dispose();
				btn[0].setBackground(choosed);
				 ReNew();
			}});	
       del.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO �Զ����ɵķ������
			try {
				 if(user.getRole().equals(getRole())) {
					 notice1.setForeground(Color.RED);
					 notice1.setText("Ȩ�޲���");}
				 else if(DataProcessing.deleteUser(user.getName()))
					{notice1.setForeground(Color.blue);
				notice1.setText("ע���ɹ�");}
				else {
					notice1.setForeground(Color.RED);
					notice1.setText("ע��ʧ��");}
			} catch (SQLException e) {
				notice1.setForeground(Color.RED);
				notice1.setText(e.getMessage());
			}
		}});
	}
}
private void GetnowUserList(boolean choose) {//1��ʾ���У�0��ʾʵʱ
	// TODO �Զ����ɵķ������
   nowuserList=new ArrayList<User>();
   if(choose) nowuserList=DataProcessing.Alluser();
		else {
			for(int i=0;i<DataProcessing.Alluser().size();i++)
			if(DataProcessing.Alluser().get(i).getName().contains(textuserName.getText()))
				nowuserList.add(DataProcessing.Alluser().get(i));
		}
	}

private void DisplayUserList() {
	// TODO �Զ����ɵķ������
	AdFrame.remove(pnl2);
    pnl2=new JPanel();
	pnl2.setBackground(Azure);
	pnl2.setSize(370, 400);
    userList=new JList();
    DefaultListModel model=new  DefaultListModel();
    userList.setModel(model);
   for(int i=0;i<nowuserList.size();i++) {
    model.addElement(ClasstoString.ToString((User) nowuserList.get(i)));
    }
	userList.setPreferredSize(new Dimension(370,300));
    scroll=new JScrollPane(userList);
	pnl2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
		JLabel labName = new JLabel("     �û���                                 ְ��");
		labName.setPreferredSize(new Dimension(370,20));
		scroll.setPreferredSize(new Dimension(370,270));
	pnl2.add(labName);
	pnl2.add(scroll);
	AdFrame.add(pnl2);
}
private void DisplayFileList() {
	// TODO �Զ����ɵķ������
	AdFrame.remove(pnl2);
    pnl2=new JPanel();
	pnl2.setBackground(Azure);
	pnl2.setSize(370, 400);
    fileList=new JList();
    DefaultListModel model=new  DefaultListModel();
    fileList.setModel(model);
    for(int i=0;i<nowfileList.size();i++) {
        model.addElement(ClasstoString.ToString((Doc) nowfileList.get(i)));
        }
	fileList.setPreferredSize(new Dimension(370,300));
    scroll=new JScrollPane(fileList);
	pnl2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 10));
		JLabel labName = new JLabel("�ļ�id       �ϴ���           ����                        �ļ���");
		labName.setPreferredSize(new Dimension(370,20));
		scroll.setPreferredSize(new Dimension(370,270));
	pnl2.add(labName);
	pnl2.add(scroll);
	AdFrame.add(pnl2);
	AdFrame.repaint();
}
}
