package simplex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.*;

public class InterfaceSimplexe extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private JLabel lab, lab2, fonctionEco, contraintes;
	private String fonctionEconomique ="";
	private int taille = 0;
	private JTextField tf,  tf2;
	private String vide ="";
	private JButton b1, b2, b3, aide;
	private JPanel mainPanel, p1, p2, p3, labelPanel, centerPanel;
	private String[] contenu = new String[100];
	private String[] contenuFinal;
	private int focustf, focustf2 = 0;
	private String res="";
	private int testEco1 = 0, testEco2 = 0;
	
	
	public InterfaceSimplexe(){
		this.setTitle("Methode du simplexe");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2));
		
		//Panel fonction économique
		p1 = new JPanel(new GridLayout(3,1));
		fonctionEco = new JLabel("");
		lab = new JLabel("<html>Entrer la fonction economique et <br>appuyer sur Entrer</html>");
		tf = new JTextField("Exemple : 2x1 +2x2 -3x3", 10);
		tf.setForeground(Color.gray);
		tf.addFocusListener(new FocusListener() {
			
			
			@Override
			public void focusGained(FocusEvent e) {
				if (focustf == 0){
					focustf = 1;
					tf.setText("");
					tf.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent e) {}
				
		});
		p1.add(fonctionEco);
		p1.add(tf);
		p1.add(lab);
		
		//ajout des boutons
		b2 = new JButton("Reset");
		b2.addActionListener(this);
		b2.setActionCommand("reset");
		b1 = new JButton("Resoudre");
		b1.addActionListener(this);
		b1.setActionCommand("resoudre");
		/*b3 = new JButton("redo");
		b3.addActionListener(this);
		b3.setActionCommand("redo");*/
		aide= new JButton("Exemple");
		aide.addActionListener(this);
		aide.setActionCommand("aide");
		
		
		p3 = new JPanel();
		p3.add(aide);
		p3.add(b2);
		p3.add(b1);
		
		//p3.add(b3);
		
		//panel Contrainte
		p2 = new JPanel(new GridLayout(3,1));
		tf2 = new JTextField("Exemple : 2x1 -4x2 <= 5", 10);
		tf2.setForeground(Color.gray);
		tf2.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if (focustf2 == 0){
					focustf2 = 1;
					tf2.setText("");
					tf2.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent e) {}		
		});
		
		
		lab2 = new JLabel("<html>Taper la contrainte et <br>appuyer sur entrer pour la valider</html>");
		contraintes = new JLabel("");
	
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3,1));
		p2.add(contraintes);
		p2.add(tf2);
		p2.add(lab2);
		
		centerPanel.add(p1);
		centerPanel.add(p2);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(p3, BorderLayout.SOUTH);
		this.add(mainPanel);
		
		tf.addKeyListener(this);
		tf2.addKeyListener(this);
		
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setVisible(true);
		this.setSize(500,500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
	}

	@Override
	public void actionPerformed(ActionEvent event){
		if (event.getActionCommand().equals("resoudre")){
			//Si les champs ne sont pas vide alors...
			if (testEco1 == 1 && testEco2 == 1){
				int a = 0;
				contenuFinal = new String[taille];
				
				//Stockage dans un nouveau tableau
				for(String i : contenu){
					if(i!=null){
						contenuFinal[a]= i;
						a++;
					}	
				}
				//appel au parser
				System.out.println(fonctionEconomique);
				/**String[] yo = Principal.sep2(fonctionEconomique);
				for (String i : yo ){
					System.out.println(i);
				}*/
			}
			//Si les champs sont vide ...
			else{
				if (testEco1 == 0){
					tf.setText("Veuillez remplir ce champ");
					tf.setForeground(Color.black);
				}
				if (testEco2 == 0){
					tf2.setText("Veuillez remplir ce champ");
					tf2.setForeground(Color.black);
				}
				if (testEco1 == 0 && testEco2 == 0){
					tf.setText("Veuillez remplir ce champ");
					tf.setForeground(Color.black);
					tf2.setText("Veuillez remplir ce champ");
					tf2.setForeground(Color.black);
				}
				
			}
		}
		
		if(event.getActionCommand().equals("aide")){
			tf.setText("15x1 +30x2 -30x3");
			tf.setForeground(Color.gray);
			focustf = 0;
			tf2.setText("15x1 -2x3 <= 5");
			tf2.setForeground(Color.gray);
			focustf2 = 0;			
		}
		
		if(event.getActionCommand().equals("reset")){
			fonctionEconomique ="";
			taille = 0;
			tf.setText("Fonction economique ");
			tf.setForeground(Color.gray);
			tf.setEnabled(true);
			tf2.setText("Contraintes ");
			tf2.setForeground(Color.gray);
			focustf = 0; focustf2 = 0;
			testEco1 = 0; testEco2 = 0;
			res="";
			contraintes.setText(res);
			fonctionEco.setText(vide);
			fonctionEconomique = vide;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			if(tf.hasFocus()){
				this.requestFocus();
				fonctionEco.setText(tf.getText());
				fonctionEconomique = tf.getText();
				tf.setText("Fonction economique saisi");
				tf.setForeground(Color.red);
				tf.setEnabled(false);
				testEco1 = 1;
				
			}
			if(tf2.hasFocus()){
				this.requestFocus();
				res = "<html>" +  contraintes.getText() + "<br/>" + tf2.getText();
				contraintes.setText(res);
				this.ajoutContrainte();
				tf2.setText("");
				testEco2 = 1;
			}
		}
	}
		
	public void ajoutContrainte(){
		int i = 0;
		System.out.println(i);
		while(contenu[i] != null){
			i++;
		}
		taille++;
		contenu[i] = tf2.getText();	
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	

}
