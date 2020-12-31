package SC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorNorth extends JFrame {
    JTextField t ;
    String n = "";
    String preFuction = "";
   	ArrayList<String> formula = new ArrayList<String>();
   
   CalculatorNorth(){
      setTitle("계산기");
      
      setLayout(new BorderLayout(10,10));
      showNorth();
      showSouth();
      
      setVisible(true);//화면에 보이게 하는거
      setPreferredSize(new Dimension(250,300));//선호 사이즈 결정
      setSize(new Dimension(250,300));//사이즈 설정
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 종료버튼과 함께 프로그램 종료
   }
   
   void showNorth() {
	  
	  JPanel p = new JPanel();
	  JLabel l = new JLabel("수식을 입력하시오");
      
	  t = new JTextField(10);
      t.setEditable(false);
      t.setHorizontalAlignment(JTextField.RIGHT);
      t.setBackground(Color.WHITE);
      
      p.add(l);
      p.add(t);
      
      add(p,BorderLayout.NORTH);
      
      
   }
 
   
   void showSouth() {
      
	  JPanel p1 = new JPanel (new GridLayout(4,4,10,10));
      
      String strButtons[] = { "7", "8", "9", "+","4", "5", "6", "-", "1", "2", "3", "*","0","C","=","/" };
      JButton buttons[] = new JButton[strButtons.length];
      
      for (int i = 0; i < strButtons.length; i++) {
    	  
    	  buttons[i] = new JButton(strButtons[i]);
    	  buttons[i].addActionListener(new MyActionListener());
    	  p1.add(buttons[i]);
		
      }
     
      add(p1,BorderLayout.SOUTH);
      
   }
   
   class MyActionListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
    	  
    	  String fuction = e.getActionCommand();
         
    	  if (fuction.equals("C")) {
    		  t.setText("");
    		  } else if (fuction.equals("=")) {
    			  String result = Double.toString(calculate(t.getText()));
    			  t.setText("" + result);
    			  n = "";
    		  } else if (fuction.equals("+") || fuction.equals("-") || fuction.equals("×") || fuction.equals("÷")) {
    			  if (t.getText().equals("") && fuction.equals("-")) {
    				  t.setText(t.getText() + e.getActionCommand());
    			  }else if (!t.getText().equals("") && !preFuction.equals("+") && !preFuction.equals("-") && !preFuction.equals("*") && !preFuction.equals("/")) {
    				  t.setText(t.getText() + e.getActionCommand());
    			  }
    		  } else {
    			  t.setText(t.getText() + e.getActionCommand());
    		  }
    	  
    	  preFuction = e.getActionCommand();
    	  }
   }

	public void strParsing(String inputText) {
		formula.clear();
		
		for (int i = 0; i < inputText.length(); i++) {
			char operSign = inputText.charAt(i);
			
			if (operSign == '-' || operSign == '+' || operSign == '*' || operSign == '/') {
				formula.add(n);
				n = "";
				formula.add(operSign + "");
			} else {
				n += operSign;
			}
		}
		
		formula.add(n);
		formula.remove("");
	}
   public double calculate(String inputText) {
	   
	   double before = 0;
	   double present = 0;
	   String m = "";
	  
	   strParsing(inputText);
   
      
	   for (int i = 0; i < formula.size(); i++) {
         String x = formula.get(i);
         
         if (x.equals("+")) {
            m = "add";
         } else if (x.equals("-")) {
            m = "sub";
         } else if (x.equals("*")) {
            m = "mul";
         } else if (x.equals("/")) {
            m = "div";
         } else {
            if ((m.equals("mul") || m.equals("div")) && !x.equals("+") && !x.equals("-") && !x.equals("*") && !x.equals("/")) {
               Double one = Double.parseDouble(formula.get(i - 2));
               Double two = Double.parseDouble(formula.get(i));
               Double result = 0.0;
               
               if (m.equals("mul")) {
                  result = one * two;
               } else if (m.equals("div")) {
                  result = one / two;
               }
               
               formula.add(i + 1, Double.toString(result));
               
               for (int j = 0; j < 3; j++) {
            	   formula.remove(i - 2);
               }
               
               i -= 2;  
            }
         }
      }   
      
      for (String s : formula) {
         if (s.equals("+")) {
            m = "add";
         } else if (s.equals("-")) {
            m = "sub";
         }  else {
        	 present = Double.parseDouble(s);
            if (m.equals("add")) {
            	before += present;
            } else if (m.equals("sub")) {
            	before -= present;
            } else {
            	before = present;
            }
         }
         before = Math.round(before * 100000) / 100000.0;
      }
      
      return before;
   }
   public static void main(String[] args) {
      new CalculatorNorth();
   }
   
   
}