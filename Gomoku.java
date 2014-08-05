import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;

public class Gomuku extends JPanel {
   public static final int gsize = 225;
   public static final int csize = 15;
   public static int player = 0;
   public static int markers = 0;
   public static int[][] board = new int[csize][csize];
   private JButton[] jButton = new JButton[gsize];
   private ImageIcon X;
   private ImageIcon O;
   private ImageIcon B;
   
   public static final int cnt = 5; // counter for 5 set block
    
   public Gomuku() {
      X = new ImageIcon(getURL("whitestone.jpg"));
      O = new ImageIcon(getURL("blackstone.jpg"));
      B = new ImageIcon(getURL("blank.jpg"));
      
      // initialize board value to -1
      for ( int i=0; i<csize; i++ ) {
         for ( int k=0; k<csize; k++ ) {
            board[i][k] = -1;
         }
      }

      	// create an array of 225 buttons
      for ( int i=0; i<gsize; i++) {
         jButton[i] = new JButton();
      }
        
      setLayout(new GridLayout(csize, csize));
         
      // setting default image as "blank"
      for ( int i=0; i<gsize; i++) {
        // starts top left
    	  jButton[i].setIcon(B);
     
      }

      for ( int i=0; i<gsize; i++) {
         final int j = i;
         jButton[j].addActionListener( new ActionListener() {
               // when a jButton is clicked, this method is called
            public void actionPerformed(ActionEvent evt) {
               if ( player == 0 ) {     // Player O - blackstone
                  if (jButton[j].getIcon().equals(B)) {
                     jButton[j].setIcon(O); // value of current button read left to right and then top to bottom
                     
                     board[j/csize][j%csize] = 0;
                     int win = 1;
                     win = checkrow(board,j/csize,0);
                     if ( win == 0 ) {
                        win = checkcol(board,j%csize,0);
                     }
                     if ( win == 0 ) {
                        win = checkdiag(board,0 , j/csize, j%csize);
                     }
                     player = 1; // switches player
                     markers++;

                     if ( win == 1 ) {
                        exitAction("Blackstone wins!\n");
                     }
                     if ( markers == gsize ) { // if all blocks have been taken
                        exitAction("Draw!\n");
                     }
                  } 
               } else {      // Player X - whitestone = 1
                  if (jButton[j].getIcon().equals(B)) {
                     jButton[j].setIcon(X);
                     board[j/csize][j%csize] = 1;
                     int win = 1;
                     win = checkrow(board,j/csize,1);
                     if ( win == 0 ) {
                        win = checkcol(board,j%csize,1);
                     }
                     if ( win == 0 ) {
                        win = checkdiag(board, 1, j/csize, j%csize );
                     }
                     player = 0;
                     markers++;

                     if ( win == 1 ) {
                        exitAction("Whitestone wins!\n");
                     }
                     if ( markers == gsize ) {
                        exitAction("Draw!\n");
                     }
                  } 
               }
            }
         });
      } 

      for ( int i=0; i<gsize; i++) {
         add(jButton[i]);
      }
       
   }

   private void exitAction(String congrats) {
      int answer = JOptionPane.showConfirmDialog( null, congrats + "Do you want to play again?", "", JOptionPane.YES_NO_OPTION);

      if (answer == JOptionPane.NO_OPTION) {
         System.exit(0);
      }
      if (answer == JOptionPane.YES_OPTION) {
         for ( int i=0; i<csize; i++ ) {
            for ( int k=0; k<csize; k++ ) {
               board[i][k] = -1;
            }
         }

         for ( int i=0; i<gsize; i++) {
            jButton[i].setIcon(B);
         }

         player = 0;
         markers = 0;
      }

   }

   private static int checkrow(int board[][], int row, int p) {
      int w = 1;
      int cnt = 0;
      
      for ( int b = 0 ; b < csize ; b++ ) {
    	  if ( board[row][b] == p )
    	  {
    		  cnt++;
    		  if (cnt == 5)
    			  break;
          }
    	  else
    		  cnt=0;
      }  
      if (cnt != 5) // if cnt reaches 5 then winner w at 0 means lose
    	  w = 0;
      return w;
   }
    
   private static int checkcol(int board[][], int col, int p) {
      int w = 1;
      int cnt = 0;
      
      for ( int a = 0 ; a < csize ; a++ ) {
    	  if ( board[a][col] == p ) {
    	      cnt++;
    	      if (cnt == 5)
  			  	 break; //w = 0  means win is not true
         }
    	  else
    		  cnt=0;
      } 
      if (cnt != 5) // if cnt reaches 5 then winner w at 0 means lose
    	  w = 0;
      return w; // winner is 1
   }
 

   private static int checkdiag(int board[][], int p, int row, int col){

	   int k = 0, l = 0, cnt = 0;
	   // top left quadrant
	   for(int i = 0 ; i < csize ; i++){
		   for(l = i ; l > -1 ; l--){
			   
			   if(board[l][k] == p){
				   cnt++;
				   if(cnt == 5)
					   return 1;
			   }
			   else
				   cnt = 0;
			   k++;
		   }
		   k = 0;        
	   }
	 
	   //Top right quadrant
	   
	   k = 14;
	   for(int i = 0; i < csize ; i++){
		   for(l = i; l > -1 ; l--){
			 if(board[l][k] == p){
				   cnt++;  
				   if(cnt == 5)
					   return 1;
			 }
		     else
				 cnt =0;
			 k--;
		   }
		   k =14;
	   }
	   
	   //Bottom left quadrant
	   k = 0;
	   for(int i = 14; i > -1 ; i--){
		   for(l = i; l < csize ; l++){
			 if(board[l][k] == p){
				   cnt++;  
				   if(cnt == 5)
					   return 1;
			 }
		     else
				 cnt =0;
			 k++;
		   }
		   k = 0;
	   }
	   
	   //Bottom right quadrant
	   k = 14;
	   for(int i = 14; i > -1 ; i--){
		   for(l = i; l < csize ; l++){
			 if(board[l][k] == p){
				   cnt++;  
				   if(cnt == 5)
					   return 1;
			 }
		     else
				 cnt =0;
			 k--;
		   }
		   k = 14;
	   }
	   return 0;
   }
   
   private static URL getURL(String fileName) {
      return  Gomuku.class.getResource(fileName);
   }
    
   public static void main(String[] args) {
      JFrame mainWindow = new JFrame("Tic Tac Toe");
      Gomuku ttt = new Gomuku();
      mainWindow.setContentPane(ttt);
      mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainWindow.pack();
      mainWindow.setVisible(true);
   }
}











/*
 * 
 *    if((board[i][j] == p && board[i+1][j+1] == p))		//downward left to right ; i < csize -1 and j <cize - 1
		   	{ 
			   if(i<csize-1 && j< csize-1)
			  cnt++;
			  System.out.println("A");
			  
		   }
		   else if((board[i][j] == p && board[i-1][j-1] == p)) // upward right to left ; i > 0 than zero and j also
		   {
			   if(i>0 && j>0)
			   cnt++;
			   System.out.println("B");
			   
		   }
			   
		   else if(board[i][j] == p && board[i-1][j+1] == p) // upward left to right ; i > 0 AND J LEsS THAN csize -1
		   {
			   if(i>0 && j<csize-1)
			   cnt2++;
			   System.out.println("C");
		   }
		   
		   else if((board[i][j] == p && board[i-1][j-1] == p)) // downard right to left ; j > 0 and i less csize -1
		   {
			   if(i< csize-1 && j>0)
			   cnt2++;
			   System.out.println("D");
				  
		   }
			   */
