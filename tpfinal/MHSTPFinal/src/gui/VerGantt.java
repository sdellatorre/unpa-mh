package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import x.Corrida;

public class VerGantt extends JFrame {
	
	public VerGantt(Corrida corrida) {
		
		
		this.setSize(1200, 500);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2,1));
		
		ImageCanvas ic = new ImageCanvas( new ImageIcon(Salida.dibujar(corrida.getBest(), "X", false)));
		JScrollPane sp = new JScrollPane(ic);
		this.getContentPane().setLayout(new BorderLayout());
		
		
		TextArea ta =  new TextArea(corrida.getBest().getResultado().toString());
		ta.setFont(new Font("monospaced", Font.PLAIN, 12));
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.add(sp);
		panel.add(ta);
		
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
	}

}
