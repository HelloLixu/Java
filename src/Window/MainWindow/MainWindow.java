package Window.MainWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
import Common.BackgroundPanel;
import net.Server.MessageQueue;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7960264103967188915L;    
	private Point pressedPoint;  
	static HashMap<String,List<String>> from_msg_s = new HashMap<String,List<String>>();
	////发送消息（文本#时间）
	private JPanel contentPane;
	static String MyName;
	static String to_usr;
	static Socket socket;
	static JTextArea SenText;
	static JTextArea jta;
	static String last = "";
	private int OpenChatFirst = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow(null,"king", null);
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
	public MainWindow(String to,String Name,Socket s) {
		this.setIconImage(new ImageIcon(this.getClass().getResource("/res/logo.jpg")).getImage());
		setResizable(false);
		to_usr = to;
		MyName = Name;
		socket = s;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);         
        setBounds(100, 100, 815, 778);     
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
        
		
		JToolBar QuickOp = new JToolBar();
		QuickOp.setOpaque(false);
		QuickOp.setBounds(10, 527, 592, 33);
		baclBackgroundPanel_1.add(QuickOp);
		QuickOp.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		
		JScrollPane SenJsp = new JScrollPane();
		SenJsp.setBounds(10, 565, 592, 103);
		SenJsp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		baclBackgroundPanel_1.add(SenJsp);
		
		SenText = new JTextArea();
		SenText.setOpaque(false);
		SenJsp.setOpaque(false);
		SenText.setFont(new Font("微软雅黑",Font.PLAIN,20));
		SenJsp.setViewportView(SenText);
		SenJsp.getViewport().setOpaque(false);
		
		
		JPanel Self = new JPanel();
		Self.setBounds(612, 62, 190, 605);
		Self.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		Self.setOpaque(false);
		Self.setLayout(new BorderLayout());
		baclBackgroundPanel_1.add(Self);
		ImageIcon ii23 = new ImageIcon(getClass().getResource("/res/self/" + MyName + ".gif"));
		JLabel l = new JLabel();
		ii23.setImage(ii23.getImage().getScaledInstance(Self.getWidth(), Self.getHeight(), Image.SCALE_DEFAULT));
		l.setIcon(ii23);
		Self.add(l,BorderLayout.CENTER);
		
		
		JLabel user_to = new JLabel(to,JLabel.CENTER);
		user_to.setBounds(10, 10, 790, 43);
		baclBackgroundPanel_1.add(user_to);
		user_to.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		user_to.setOpaque(false);
		
		jta = new JTextArea();
		jta.setOpaque(false);
		JScrollPane RecJsp = new JScrollPane(jta);
		RecJsp.setOpaque(false);
		RecJsp.getViewport().setOpaque(false);
		jta.setEditable(false);
		jta.setFont(new Font("微软雅黑",Font.PLAIN,20));
		RecJsp.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		RecJsp.setBounds(10, 62, 592, 454);
		jta.setOpaque(false);
		baclBackgroundPanel_1.add(RecJsp);
		
		SenText.addKeyListener(new KeyListener() {

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
				if(e.getKeyChar() == KeyEvent.VK_ENTER && e.isControlDown() && !SenText.getText().isEmpty()) {
					jta.append(SenText.getText() + "\r\n");
					start Client = new start();
					Thread t = new Thread(Client);
					t.start();
				}
			}
			
		});
		try {
			loadMsg();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	protected void do_button_2_actionPerformed(ActionEvent e) {     
        // TODO 自动生成的方法存根     
        this.dispose();     
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
    private void loadMsg() throws InterruptedException {
    	if(OpenChatFirst == 0) {
			String[] user_or_group = to_usr.split("_");
			if(user_or_group != null) {
				if(user_or_group.length == 1 && from_msg_s.containsKey(user_or_group[0])) {
					List<String> msgs = from_msg_s.get(user_or_group[0]);
					for(int i = 0;i<msgs.size();i++) {
						String[] msg = msgs.get(i).split("####");
						if(msg != null && msg.length != 0) {
							jta.append(msg[0] + ":" + msg[1] + "\r\n");
							Thread.sleep(50);
						}
					}
				}
				if(user_or_group.length == 3) {
					for(int i = 0;i<3;i++) {
						if(from_msg_s.containsKey(user_or_group[i])) {
							List<String> msgs = from_msg_s.get(user_or_group[i]);
							for(int j = 0;j<msgs.size();j++) {
								String[] msg = msgs.get(i).split("####");
								if(msg != null && msg.length != 0 && msg[2].equals(to_usr)) {
									jta.append(msg[0] + ":" +  msg[1] + "\r\n");
									Thread.sleep(50);
								}
							}
						}
					}
				}
			}
			OpenChatFirst = 1;
		}
    }
}
class start implements Runnable {

	@Override
	public void run() {
		CClient t = new CClient();
	    Read r = new Read(MainWindow.socket);
	    Thread print = new Thread(t);
	    Thread read = new Thread(r);
	    print.start();
	    read.start();
	}
	
}
class CClient implements Runnable {// 客户端
	@Override
	public void run() {
		try {
	        Thread.sleep(1000);         
	        PrintWriter out = new PrintWriter(MainWindow.socket.getOutputStream());
	        while (!MainWindow.SenText.getText().isEmpty() && !MainWindow.SenText.getText().equals((String)MainWindow.last)) {
	            String msg = MainWindow.SenText.getText();
	            out.println(MainWindow.MyName + "####" + msg + "####" + MainWindow.to_usr);
	            out.flush();
	            MainWindow.SenText.setText(null);
	            MainWindow.last = msg;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
class Read implements Runnable {
	static Socket socket = null;
	public Read(Socket socket) {
	    this.socket = socket;
	}
	@Override
	public void run() {
	    try {
	        Thread.sleep(1000);
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket
	                .getInputStream()));
	        while (true) {
	           String str = in.readLine();
	           if(str != null) {
	        	   //from_msg_to(user/group)
	        	   String[] from_msg_to = str.split("####");
	        	   if(from_msg_to != null && from_msg_to.length != 0) {
	        		   String[] user_or_group = from_msg_to[2].split("_");
	        		   if((user_or_group.length == 3 && MainWindow.to_usr.contains(from_msg_to[0])) || (user_or_group.length == 1 && MainWindow.to_usr.equals(from_msg_to[0]))) {
	        			   MainWindow.jta.append(from_msg_to[0] + ":" + from_msg_to[1] + "\r\n");
	        		   }
	        		   else {
	        			   if(MainWindow.from_msg_s.containsKey(from_msg_to[0])) {
	        				   List<String> msgs = MainWindow.from_msg_s.get(from_msg_to[0]);
	        				   msgs.add(str);
	        			   }else {
	        				   List<String> msgs = new ArrayList<String>(); 
	        				   msgs.add(str);
	        				   MainWindow.from_msg_s.put(from_msg_to[0], msgs);
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

