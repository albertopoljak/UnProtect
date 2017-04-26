import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.Inflater;
import javax.swing.UIManager;

public class Main_Decypher {

	private JFrame frmDecypherInStyle;
	private JTextField txtC;
	private JTextArea txtrLog;
	private String basePath = new File("").getAbsolutePath(); //F:\Development\eclipse-java-neon-1a-win32-x86_64\Workspace\mordor test
	
	/**
	 * Launch new screen.
	 */
	public static void newScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Decypher window = new Main_Decypher();
					window.frmDecypherInStyle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create screen elements.
	 */
	public Main_Decypher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Set up the window
			frmDecypherInStyle = new JFrame();
			frmDecypherInStyle.getContentPane().setBackground(UIManager.getColor("Button.background"));
			frmDecypherInStyle.setResizable(false);
			frmDecypherInStyle.setTitle("Hackerman Poljak Exclusive");
			frmDecypherInStyle.setBounds(100, 100, 780, 580);
			frmDecypherInStyle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmDecypherInStyle.getContentPane().setLayout(null);
			
		//Add text "File name"
			JTextArea txtrFileName = new JTextArea();
			txtrFileName.setBounds(10, 11, 80, 27);
			txtrFileName.setText("File Path:");
			txtrFileName.setOpaque(false);
			txtrFileName.setFont(new Font("Calibri", Font.PLAIN, 18));
			txtrFileName.setEditable(false);
			frmDecypherInStyle.getContentPane().add(txtrFileName);
			
		//Add JTextField for inputting file path
			txtC = new JTextField();
			txtC.setText("disk:\\path\\filename.extension");
			txtC.setToolTipText("Example: C:\\test.cetrainer");
			txtC.setBounds(100, 15, 514, 20);
			txtC.setColumns(10);
			frmDecypherInStyle.getContentPane().add(txtC);
			
		//Add text log in form of JTextArea
			txtrLog = new JTextArea();
			txtrLog.append("Log:\n");
			txtrLog.setLineWrap(true);
			txtrLog.setEditable(false);
			txtrLog.setFont(new Font("Calibri", Font.PLAIN, 18));
			txtrLog.setBounds(20, 49, 399, 482);
			
		//Add Scroll Pane with text log in it
			JScrollPane sp = new JScrollPane(txtrLog);
			sp.setLocation(14, 45);
			sp.setSize(600, 495);
			frmDecypherInStyle.getContentPane().add(sp, BorderLayout.CENTER);
		
		//Add button that checks if file exists based on inputed file path
			JButton btnProvjeri = new JButton("Check");
			btnProvjeri.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if(new File(txtC.getText()).isFile()){
						txtrLog.append("[INFO]-->File exist!\n");
					}else{
						txtrLog.append("[ERROR]-->File does not exist!\n");
					}
				}
			});
			btnProvjeri.setBounds(624, 14, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnProvjeri);
			
		//Add button that gets relative path and updates input path
			JButton btnGetRelativePath = new JButton("Get relative path");
			btnGetRelativePath.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					txtC.setText(basePath);
				}
			});
			btnGetRelativePath.setBounds(624, 48, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnGetRelativePath);
			
		//Add button that clears inputed path
			JButton btnClearPath = new JButton("Clear Path");
			btnClearPath.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					txtC.setText("");
				}
			});
			btnClearPath.setBounds(624, 82, 140, 23);
			frmDecypherInStyle.getContentPane().add(btnClearPath);	
			
		//Add button that clears log
			JButton btnClearLog = new JButton("Clear Log");
			btnClearLog.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					txtrLog.setText("");
					txtrLog.append("Log:\n");
				}
			});
			btnClearLog.setBounds(624, 416, 140, 27);
			frmDecypherInStyle.getContentPane().add(btnClearLog);
		
		//Add button that opens new window ("Info")
			JButton btnInfo = new JButton("Info");
			btnInfo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					Info.newScreen();
				}
			});
			btnInfo.setBounds(624, 454, 140, 27);
			frmDecypherInStyle.getContentPane().add(btnInfo);
					
		//Add button that deciphers and decompresses the file
			JButton btnHakiraj = new JButton("Hack!");
			btnHakiraj.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try{
						txtrLog.append("[INFO]-->Hack in progress!\n");
						Path path = Paths.get(txtC.getText());
							//Load file as a byte array
								byte[] raw_data = Files.readAllBytes(path);
								int size = raw_data.length;
								
							//Decipher that file using XOR decryption
								txtrLog.append("      --> Starting XOR decryption!\n");
									//First pass : Before-key relationship where the first byte (x) starts at 2 and the first XOR key starts at x-2
										txtrLog.append("      --> First pass!\n");
										for (int i = 2 ; i<size ; i++){
											raw_data[i] = (byte) (raw_data[i] ^ raw_data[i - 2]);
										}
									//Second pass : After-key relationship where the first byte (x) starts at length-2 and the first XOR key starts at x+1
										txtrLog.append("      --> Second pass!\n");
										for (int i = size-2 ; i >= 0 ; i--){
											raw_data[i] = (byte) (raw_data[i] ^ raw_data[i + 1]);
										}
									//Third pass : Static-incrementing key relationship where the key starts at 0xCE and increments each XOR
										txtrLog.append("      --> Third pass!\n");
										byte key = (byte) 0xCE;
										for (int i = 0; i<size ; i++)
										{
											raw_data[i] = (byte) (raw_data[i] ^ key);
											++key;
										}
								txtrLog.append("      --> XOR Decryption done!\n\n");
							
							//Check if XOR successful : Using newer trainer files will show a 5 byte header saying 'CHEAT'. This should be skipped before attempting to decompress the buffer
								txtrLog.append("[Info]--> First 5 characters of encrypted file should be CHEAT: ");
								String prvih5 = new String(raw_data, 0, 5);
								txtrLog.append(prvih5 + "\n");
								if( prvih5.equals("CHEAT") )
									txtrLog.append("      --> XOR encryption sucesfully decrypted!\n");
								else{
									txtrLog.append("[ERROR]--> XOR decryption failed!\n");
									txtrLog.append("[ERROR]--> File is not protected or is made with older/newer CheatEngine version\n");
									return;
								}
							
							//Zlib decompression
								txtrLog.append("\n[Info]--> Starting decompression using Zlib!\n");
								//First delete string "CHEAT" from decrypted file
									txtrLog.append("      --> Deleting string CHEAT!\n");
									byte[] outputFinal = Arrays.copyOfRange(raw_data, 5, raw_data.length);
								//Then set inflater methods
									txtrLog.append("      --> Setting up inflater!\n");
									Inflater decompresser = new Inflater(true);
							        decompresser.setInput(outputFinal, 0, raw_data.length-5);
							        byte[] result = new byte[100000];	//FIX THIS ERROR BY DYNAMICALLY ALLOCATING
							        int resultLength = decompresser.inflate(result);
							        decompresser.end();
							    //Convert decompressed array to string
							        txtrLog.append("      --> Converting byte array to string!\n");
							        String outStr = new String(result, 4, resultLength-4, "UTF-8");
					        //Save that string to file
						        txtrLog.append("      --> Trying to save file output.xml!\n");
						        BufferedWriter writer = null;
						        writer = new BufferedWriter( new FileWriter("output.xml"));
						        writer.write( outStr);
						        writer.close();
						        txtrLog.append("[END]--> File saved! It's located in the same directory as this executable!\n");
							 
					} catch (IOException e1) {
						txtrLog.append("\n[ERROR]-->File location wrong!\n                -->"+e1+"\n");
					}catch (IndexOutOfBoundsException e1) {
						txtrLog.append("\n[ERROR]-->File is not supported! File is too small!\n");
					}catch (InvalidPathException e1) {
						txtrLog.append("[ERROR]-->File does not exist!\n");
					}catch (Exception e1) {
						txtrLog.append("\n[ERROR]-->"+e1+"\n");
					}
				}
			});
			btnHakiraj.setBounds(624, 490, 140, 50);
			frmDecypherInStyle.getContentPane().add(btnHakiraj);
	}
}