
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;
public class Login implements ActionListener{
	Color LightYellow=new Color(255,255,224);//ǳ��ɫ
	Color LightGreen=new Color(144,238,144);//ǳ��ɫ
	JFrame frame;
	User user;
	Dimension dim;//���ÿؼ���С
	ImageIcon icon;
	JLabel labIcon;
	JLabel labName;
	JTextField textName;
	JLabel labpassword;
	JPasswordField textword;
	JPanel pnl;
	JButton btn[]= { new JButton("��¼"),new JButton("�˳�")};
	JLabel notice;
	String name,pwd;
	Login() {
	    frame = new JFrame();
		frame.setTitle("С������ϵͳ");
		frame.setSize(400, 320);
		Toolkit tool=frame.getToolkit();
		Image im=tool.getImage(".\\drawable\\bg.png");//�޸Ĵ���ͼ��
		frame.setIconImage(im);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(LightYellow);//�����Դ���������ɫ
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 10, 10);//10���ؼ��
		// ��ʽ����
		frame.setLayout(fl);
	    icon = new ImageIcon(".\\drawable\\bg.png");
		labIcon = new JLabel(icon);
	    dim = new Dimension(400,100);//���һ�㲻Ȼ������
		labIcon.setPreferredSize(dim);
		frame.add(labIcon);
	    labName = new JLabel("�˺ţ�");
		frame.add(labName);
        textName = new JTextField();
	    dim = new Dimension(300,30);
		textName.setPreferredSize(dim);
		frame.add(textName);
	    labpassword= new JLabel("���룺");
		frame.add(labpassword);
		textword=new JPasswordField();
		textword.setPreferredSize(dim);
		frame.add(textword);
	    pnl=new JPanel();
		FlowLayout f2 = new FlowLayout(FlowLayout.CENTER, 100, 0);
		pnl.setLayout(f2);
		pnl.setBackground(LightYellow);
		for(int i=0;i<2;i++) {
            dim= new Dimension(60,30);
			btn[i].setPreferredSize(dim);
			btn[i].addActionListener(this);
			btn[i].setBackground(LightGreen);
			pnl.add(btn[i]);
		}
		frame.add(pnl);
		notice = new JLabel("");
		dim = new Dimension(500,30);
		notice.setSize(dim);
		frame.add(notice);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		switch(((JButton)e.getSource()).getText()) {
		case "�˳�":
		System.exit(0);
		break;
		case "��¼":
	      login();
			break;
	}
	}
	private void login() {
		// TODO �Զ����ɵķ������
		name=textName.getText();
	      pwd=textword.getText();
			try {
				user=null;
			user=DataProcessing.searchUser(name);
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				notice.setForeground(Color.RED);
				notice.setText(e1.getMessage());
			}
			if(user==null) {
				notice.setForeground(Color.RED);
				notice.setText("�û�������");
			}
			else if(!pwd.equals(user.getPassword())) {
				notice.setForeground(Color.RED);//������ɫ
				notice.setText("�˺ź����벻ƥ��");		
			}
			else {
				notice.setForeground(Color.BLUE);//������ɫ
				notice.setText("��¼�ɹ�");
				frame.dispose();
				user.showMenu();
			}
			}
	}


