package b;

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/*
 * Tic-Tac-Toe game
 * ----------------
 * CREATOR: Yasin Sharif
 * VERSION: 2.0
 * PACKAGE: b 
 * DATE CREATED: 10.07.2022 
 * DATE FINISHED: ? 
 * ---------
 * NEW FEATURES:
 * winner and draw identifier
 * */

@SuppressWarnings("serial")
class Board extends JFrame{
	final static private Font BIG=new Font("monspaced",Font.PLAIN,20);
	static private String turn="X";
	static JButton[][] barr=new JButton[3][3]; // creating an array of jbuttons
	static JLabel banner=new JLabel(turn+" turn now");	
	static ArrayList<Integer> hor,ver,diag;
	static int halt=0; // initially false
		
	void initiate() {		
		hor=new ArrayList<Integer>(3);
		ver=new ArrayList<Integer>(3);
		diag=new ArrayList<Integer>(2);
		for(int i=0;i<3;i++) {
			hor.add(i);
			ver.add(i);
		}
		diag.add(22);diag.add(20); // diag is bottom representation
	}
	
	/* board creator */
	void create() {
		// advertisement banner
		JLabel ad=new JLabel("Created By:- Yasin Sharif");
		ad.setSize(300,50);
		JPanel adPanel=new JPanel();
		adPanel.add(ad);
		
		// top banner
		banner.setFont(BIG);
		banner.setSize(300,75);
		JPanel bannerPanel=new JPanel();
		bannerPanel.add(banner);
		
		// buttons 
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new GridLayout(3,3));
		ActionListener bl=new ButtonListener();
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				barr[i][j]=new JButton(".");
				barr[i][j].addActionListener(bl);
				barr[i][j].setSize(50,25);
				buttonPanel.add(barr[i][j]);
			}
		} 
		
		// whole panel
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(bannerPanel,BorderLayout.NORTH);
		panel.add(buttonPanel,BorderLayout.CENTER);
		panel.add(adPanel,BorderLayout.SOUTH);
		this.setContentPane(panel);
		this.pack();
		
		// board settings
		setTitle("Tic-Tac-Toe v2.0");
		setSize(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	/* real time board marking */
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton jb=(JButton)e.getSource();
			if(halt!=1 && turn=="X" && jb.getActionCommand()!="X" && jb.getActionCommand()!="O") {
				jb.setBackground(Color.red);
				jb.setText("X");
				jb.setFont(BIG);
				stateCheck_hor();
				stateCheck_ver();
				stateCheck_diag();
				if(halt!=1) {
					turn="O";
					banner.setText(turn+" turn now");
				}
			}
			else if(halt!=1 && turn=="O" && jb.getActionCommand()!="X" && jb.getActionCommand()!="O") {
				jb.setBackground(Color.green);
				jb.setText("O");
				jb.setFont(BIG);
				stateCheck_hor();
				stateCheck_ver();
				stateCheck_diag();
				if (halt!=1) {
					turn="X";
					banner.setText(turn+" turn now");
				}
			}
		}
	}
	
	/* horizonal state check */
	void stateCheck_hor() {
		int i,j,r,count=0;
		String one=null;
		for(i=0;i<hor.size();i++) {	// array traversal
			r=hor.get(i); // getting the row number to check
			for(j=0;j<3;j++) {
				if(barr[r][j].getText()==".") {
					continue; // what happens if all three spaces are null?
							  // or a null space is found
				}
				else if(one==null) { // a letter is present
					one=barr[r][j].getText();
					++count;
				}
				else if(one==barr[r][j].getText()){ // same letter is present again
					++count;
				}	
				else { // another letter is present
					count=0;
					hor.remove(i);
				}
			}
			// checking if the count is 3
			if(count==3) {
				terminate(one);
				//return one;
			}
			one=null;
			count=0;
		}
	}
	
	/* vertical state check */
	void stateCheck_ver() {
		int i,j,c,count=0;
		String one=null;
		// vertical check = ?
		for(i=0;i<ver.size();i++) {	// array traversal
			c=ver.get(i); // getting the column number to check
			for(j=0;j<3;j++) {
				if(barr[j][c].getText()==".") {
					continue; // what happens if all three spaces are null?
							  // or a null space is found
				}
				else if(one==null) { // a letter is present
					one=barr[j][c].getText();
					++count;
				}
				else if(one==barr[j][c].getText()){ // same letter is present again
					++count;
				}	
				else { // another letter is present
					count=0;
					ver.remove(i);
				}
			}
			// checking if the count is 3
			if(count==3) {
				terminate(one);
			}
			one=null;
			count=0; 
		}
	}
	
	void stateCheck_diag() {
		int i,j,r,count=0;
		String one=null;
		
		for(r=0;r<diag.size();r++) {	// array traversal
			if(diag.get(r)==22){
				for(i=0;i<3;i++) {
					if(barr[i][i].getText()==".") {
						continue; // what happens if all three spaces are null?
								  // or a null space is found
					}
					else if(one==null) { // a letter is present
						one=barr[i][i].getText();
						++count;
					}
					else if(one==barr[i][i].getText()){ // same letter is present again
						++count;
					}	
					else { // another letter is present
						count=0;
						diag.remove(r);
					}
				}
			}
			else {
				for(i=0;i<3;i++){
					j=3-i-1;
					if(barr[i][j].getText()==".") {
						continue; // what happens if all three spaces are null?
								  // or a null space is found
					}
					else if(one==null) { // a letter is present
						one=barr[i][j].getText();
						++count;
					}
					else if(one==barr[i][j].getText()){ // same letter is present again
						++count;
					}	
					else { // another letter is present
						count=0;
						diag.remove(r);
					}
				}
			}
			// checking if the count is 3
			if(count==3) {
				terminate(one);
			}
			one=null;
			count=0;
		}
		
	
	}
	
	void terminate(String player) {
		// setting halt to true
		halt=1;
		// setting banner
		banner.setText(player+" is winner");
	}
}

public class GameBoard {
	public static void main(String args[]) {
		Board b=new Board();
		b.initiate();
		b.create();
	}
}
