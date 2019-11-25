package net.Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import Common.BackgroundPanel;
import Window.FriendListWindow.ImageCellRender;
import Window.FriendListWindow.ImageList;
import Window.MainWindow.MainWindow;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;


public class server extends JFrame {

	private JPanel contentPane;
    private Point pressedPoint;     
	static String temp1 = "/ + username + command";
	static String temp2 = "默认为8888";
	static int port = 8888;
	static HashMap<String,Socket> Name_Socket = new HashMap<String,Socket>();
	JTextField ip_text;
	static JTextField port_text;
	static JTextArea user_info;
	JList<File> list;
	JTextArea command_area;
	JTextField command;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server frame = new server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public server() {
		this.setIconImage(new ImageIcon(this.getClass().getResource("/res/logo.jpg")).getImage());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);         
        setBounds(100, 100, 815, 785);     
        contentPane = new JPanel();     
        contentPane.setBorder(null);     
        contentPane.setLayout(new BorderLayout(0, 0));     
        setContentPane(contentPane);     
                    
        BackgroundPanel topPanel = new BackgroundPanel();    
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        topPanel.addMouseMotionListener(new MouseMotionAdapter() {     
            @Override
            public void mouseDragged(MouseEvent e){     
                do_topPanel_mouseDragged(e);     
            }     
        });     
        topPanel.addMouseListener(new MouseAdapter() {     
            @Override
            public void mousePressed(MouseEvent e) {     
                // TODO 自动生成的方法存根     
                do_topPanel_mousePressed(e);     
            }     
        });     
        Image centerImage = new ImageIcon(getClass().getResource("/res/FrameTop.png")).getImage();     
        Dimension dimension = new Dimension(815,100);     
        topPanel.setPreferredSize(dimension);     
        topPanel.setImage(centerImage);     
                    
        contentPane.add(topPanel,BorderLayout.NORTH);     
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));     
        
        JPanel panel = new JPanel();     
        panel.setPreferredSize(new Dimension(60, 22));     
        panel.setOpaque(false);     
        topPanel.add(panel);     
        panel.setLayout(new GridLayout(1, 0, 0, 0));     
                    
        JButton button = new JButton("");     
        button.setRolloverIcon(new ImageIcon(MainWindow.class.getResource("/res/minBH.jpg")));     
        button.addActionListener(new ActionListener() {     
                        
            @Override
            public void actionPerformed(ActionEvent e) {     
                // TODO 自动生成的方法存根     
                do_button_actionPerformed(e);     
            }     
        });     
        button.setFocusPainted(false);     
        button.setBorderPainted(false);     
        button.setContentAreaFilled(false);     
        button.setIcon(new ImageIcon(MainWindow.class.getResource("/res/minB.jpg")));     
        panel.add(button);     
                    
        JToggleButton button_1 = new JToggleButton("");     
        button_1.addItemListener(new ItemListener() {     
                        
            @Override
            public void itemStateChanged(ItemEvent e) {     
                // TODO 自动生成的方法存根     
                do_button_1_itemStateChanged(e);     
            }     
        });     
        button_1.setRolloverIcon(new ImageIcon(MainWindow.class.getResource("/res/maxBH.jpg")));     
        button_1.setRolloverSelectedIcon(new ImageIcon(MainWindow.class.getResource("/res/maxBH.jpg")));     
        button_1.setIcon(new ImageIcon(getClass().getResource("/res/maxB.jpg")));     
        button_1.setContentAreaFilled(false);     
        button_1.setBorderPainted(false);     
        button_1.setFocusPainted(false);     
        panel.add(button_1);     
                    
        JButton button_2 = new JButton("");     
        button_2.addActionListener(new ActionListener() {     
                        
            @Override
            public void actionPerformed(ActionEvent e) {     
                // TODO 自动生成的方法存根     
                do_button_2_actionPerformed(e);     
            }     
        });     
        ImageIcon c_ii = new ImageIcon(getClass().getResource("/res/closeBH.jpg"));
        button_2.setRolloverIcon(c_ii);     
        button_2.setIcon(new ImageIcon(getClass().getResource("/res/closeB.jpg")));     
        button_2.setFocusPainted(false);     
        button_2.setContentAreaFilled(false);     
        button_2.setBorderPainted(false);     
        panel.add(button_2);     
                    
        BackgroundPanel baclBackgroundPanel_1 = new BackgroundPanel();     
        baclBackgroundPanel_1.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
        Image topImage = new ImageIcon(getClass().getResource("/res/FrameCenter.png")).getImage();     
        baclBackgroundPanel_1.setImage(topImage);       
        contentPane.add(baclBackgroundPanel_1, BorderLayout.CENTER);  
		baclBackgroundPanel_1.setLayout(new BorderLayout(8,8));
		
		JPanel north = new JPanel();
		north.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5, true));
		north.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		JLabel ip_label = new JLabel("ip address:");
		ip_text = new JTextField(20);
		ip_text.setText("127.0.0.1");
		ip_text.setHorizontalAlignment(JTextField.CENTER);
		ip_text.setEditable(false);
		JLabel port_label = new JLabel("port number:");
		port_text = new JTextField(20);
		port_text.setText(temp2);
		port_text.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(port_text.getText().equals((String)temp2)){
					port_text.setText(null);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(port_text.getText().isEmpty()) {
					port_text.setText(temp2);
				}
			}
			
		});
		JButton start = new JButton("Start!");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO Auto-generated method stub
				start s = new start();
				Thread th = new Thread(s);
				th.start();
			}
		});
		north.add(ip_label);
		north.add(ip_text);
		north.add(port_label);
		north.add(port_text);
		north.add(start);
		
		user_info = new JTextArea(25,55);
		user_info.setBorder(BorderFactory.createTitledBorder("用户指令"));
		user_info.setEditable(false);
		list = new JList<File>();
		ImageList listModel = new ImageList();		
		File[] files = new File("D:\\ChatRoom\\room\\bin\\res\\head".trim()).listFiles(new FileFilter(){
				public boolean accept(File file){
					return file.getName().endsWith("jpg");
				}
		});
		for(File file : files){
			listModel.addElement(file);
		}
		list.setModel(listModel);
		list.setCellRenderer(new ImageCellRender());
		JScrollPane left = new JScrollPane();
		left.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		left.setViewportView(user_info);
		JScrollPane right = new JScrollPane(list);
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		command_area = new JTextArea(5,0);
		command_area.setEditable(false);
		command_area.setBorder(BorderFactory.createTitledBorder("操作用户"));
		JScrollPane ca = new JScrollPane(command_area);
		south.add(ca,BorderLayout.NORTH);
		command = new JTextField(30);
		south.add(command,BorderLayout.SOUTH);
		command.setText(temp1);
		command.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(command.getText().equals((String)temp1)){
					command.setText(null);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(command.getText().isEmpty()) {
					command.setText(temp1);
				}
			}
			
		});
		command.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar() == KeyEvent.VK_ENTER) {
					if(!(command.getText().isEmpty())) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						command_area.append(command.getText() + "\t\t\t\t" + df.format(new Date()) + "\r\n");
						command.setText(null);
						//对某用户具体操作
					}
				}
			}
			
		});
		south.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		
		baclBackgroundPanel_1.add(left,BorderLayout.WEST);
		baclBackgroundPanel_1.add(right,BorderLayout.EAST);
		baclBackgroundPanel_1.add(north,BorderLayout.NORTH);
		baclBackgroundPanel_1.add(south,BorderLayout.SOUTH);
	}
	protected void do_button_2_actionPerformed(ActionEvent e) {     
        // TODO 自动生成的方法存根     
        dispose();     
    }     
            
    protected void do_button_1_itemStateChanged(ItemEvent e) {     
        // TODO 自动生成的方法存根     
        if (e.getStateChange() == ItemEvent.SELECTED) {     
            setExtendedState(MAXIMIZED_BOTH);     
        } else {     
            setExtendedState(NORMAL);     
        }     
    }     
            
    protected void do_button_actionPerformed(ActionEvent e) {     
        // TODO 自动生成的方法存根     
        setExtendedState(ICONIFIED);     
    }     
            
    protected void do_topPanel_mousePressed(MouseEvent e) {     
        // TODO 自动生成的方法存根     
        pressedPoint = e.getPoint();     
    }     
            
    protected void do_topPanel_mouseDragged(MouseEvent e) {     
        // TODO 自动生成的方法存根     
        Point point = e.getPoint();     
        Point locationPoint = getLocation();     
                    
        int x = locationPoint.x + point.x - pressedPoint.x;     
        int y = locationPoint.y + point.y - pressedPoint.y;     
                    
        setLocation(x, y);     
    }     
}
class SServer implements Runnable {// 服务端
	  static List<Socket> socketList=new ArrayList<Socket>();
	//读取 In
	  static Socket socket = null;
	  static ServerSocket serverSocket = null;
	  public SServer() {// 构造方法
	      try {
	          String temp = server.port_text.getText();
	    	  if((!temp.isEmpty()) && (!temp.equals((String)server.temp2))) {
	    		  server.port = Integer.parseInt(temp);
	    	  }
	          serverSocket = new ServerSocket(server.port);
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }
	  @Override
	  public void run() {
	      try {
	          Thread.sleep(1000);
	          BufferedReader in = new BufferedReader(new InputStreamReader(socket
	                  .getInputStream()));
	          while (true) {
	              String receive = in.readLine();
	              server.user_info.append(receive + "\r\n");
	              //处理登录信息
	              if(receive.startsWith(Constants.Constant.Login_Signal)) {
	            	  String[] Signal_Name = receive.split("###");
	            	  if(Signal_Name != null && Signal_Name.length != 0) {
	            		  if(!server.Name_Socket.containsKey(Signal_Name[1])){
	            			  server.Name_Socket.put(Signal_Name[1], socket);
	            		  }
	            	  }
	              }else if(receive.startsWith(Constants.Constant.Quit_Signal)) {
	            	  String[] Signal_Name = receive.split("###");
	            	  if(Signal_Name != null && Signal_Name.length != 0) {
	            		  if(server.Name_Socket.containsKey(Signal_Name[1])) {
	            			  server.Name_Socket.remove(Signal_Name[1]);
	            		  }
	            	  }
	              }else {
	            	  //from_msg_to(user/group)
	            	  String[] from_msg_to = receive.split("####");
	            	  if(from_msg_to != null && from_msg_to.length != 0) {
	            		  String[] user_or_group = from_msg_to[2].split("_");
	            		  if(user_or_group != null) {
	            			  if(user_or_group.length == 1) {
	            				  if(server.Name_Socket.containsKey(user_or_group[0])) {
	    	            			  Socket ss = server.Name_Socket.get(user_or_group[0]);
	    	            			  PrintWriter out = new PrintWriter(ss.getOutputStream());
	    	            			  out.println(receive);
	    	            			  out.flush();
	    	            		  }
	            			  }else {
	            				  for(int i = 0;i<user_or_group.length;i++) {
	            					  if(server.Name_Socket.containsKey(user_or_group[i])) {
		    	            			  Socket ss = server.Name_Socket.get(user_or_group[i]);
		    	            			  PrintWriter out = new PrintWriter(ss.getOutputStream());
		    	            			  out.println(receive);
		    	            			  out.flush();
		    	            		  }
	            				  }
	            			  }
	            		  }
	            	  }
	              } 	
	          }
	      } catch (Exception e) {

	          e.printStackTrace();
	      }
	  }
	}
class Print implements Runnable {
	  static List<Socket> socketList=new ArrayList<Socket>();
	  Scanner input = new Scanner(System.in);
	  public Print(Socket s) {
	      try {
	          socketList.add(s);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	  }
	  @Override
	  public void run() {
	      try {
	          Thread.sleep(1000);
	          while (true) {
	              String msg = input.next();
	          for (int i = 0; i < socketList.size(); i++) {
	              Socket socket=socketList.get(i);
	              PrintWriter out = new PrintWriter(socket.getOutputStream());
	              System.out.println("对客户端说：");
	              out.println("服务端说："+msg);
	              out.flush();
	          }
	          }
	      } catch (Exception e) {
	          // TODO: handle exception
	          e.printStackTrace();
	      }
	  }
}
class start implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
	    server.user_info.append("************服务端*************\r\n");
	    SServer t = new SServer();
	    int count = 0;
	    while (true) {          
	        try {
	            SServer.socket = SServer.serverSocket.accept();
	            count++;
	            server.user_info.append("第" + count + "个客户已连接\r\n");
	            SServer.socketList.add(SServer.socket);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        Print p = new Print(SServer.socket);
	        Thread read = new Thread(t);
	        Thread print = new Thread(p);
	        read.start();
	        print.start();
	    }
	}
}



