package Window.FriendListWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import SomeComponents.NameLabel;
import Window.MainWindow.MainWindow;
import net.Server.MessageQueue;

public class FriendListWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4366086830293906837L;
	static Socket socket = null;
	private String myName;
	public FriendListWindow(String Name,Socket s){
		this.setIconImage(new ImageIcon(this.getClass().getResource("/res/logo.jpg")).getImage());
		myName = Name;
		socket = s;
		JList<File> list = new JList<File>();
		ImageList listModel = new ImageList();		
		//从服务器读取
		File[] files = new File("D:\\ChatRoom\\room\\bin\\res\\head".trim()).listFiles(new FileFilter(){
				public boolean accept(File file){
					return file.getName().endsWith("jpg");
				}
		});
		for(File file : files){
			listModel.addElement(file);
		}
		list.setModel(listModel);
		//设置JList的渲染器为我们自己构建的渲染器
		list.setCellRenderer(new ImageCellRender());
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String userName = list.getSelectedValue().getName();
				int index = 0;
				for(int i = userName.length() - 1;i>=0;i--) {
					if(userName.charAt(i) == '.') {
						index = i;
						break;
					}
				}
				char[] temp  = new char[index];
				for(int i = 0;i<index;i++) {
					temp[i] = userName.charAt(i);
				}
				userName = String.valueOf(temp);
				if(e.getClickCount() == 2) {	
					new MainWindow(userName,myName, socket).setVisible(true);
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		JScrollPane scrollPane = new JScrollPane(list);
		//从服务器读取
		java.net.URL imageUrl = getClass().getResource("/res/head/" + myName + ".jpg");
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		JLabel Label  = new JLabel();
		ImageIcon icon=new ImageIcon(imageUrl);
		p.setBackground(Color.WHITE);
		icon.setImage(icon.getImage().getScaledInstance(100, 100,Image.SCALE_DEFAULT ));
		Label.setIcon(icon);
		Label.setBackground(Color.blue);
		Label.setForeground(Color.BLACK);
		NameLabel NameLabel = new NameLabel(myName);
		NameLabel.setSize(100, 30);
		NameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		NameLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		JPanel np = new JPanel();
		np.setLayout(null);
		NameLabel.setBounds(50,45, 75, 20);
		np.add(NameLabel);
		p.add(Label,BorderLayout.WEST);
		p.add(np,BorderLayout.CENTER);
		p.setBorder(BorderFactory.createLineBorder(Color.ORANGE,5));
		Label.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
		getContentPane().add(p,BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);	
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				if(socket != null) {
					PrintWriter out;
					try {
						out = new PrintWriter(socket.getOutputStream());
						out.println(Constants.Constant.Quit_Signal + myName);
					    out.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		setBounds(100, 100, 300, 720);
		setResizable(false);
	}
	public static void main(String[] args) {
		new FriendListWindow("king", null).setVisible(true);
	}
}