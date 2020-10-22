package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import x.Corrida;
import x.Corrida.MetodoSeleccion;
import x.CorridaListener;

@SuppressWarnings("serial")
public class GUI extends JFrame implements CorridaListener {
	
	
	private Corrida corrida;
	private JLabel lblAvance = new JLabel("");
	private JLabel lblBest = new JLabel("");
	private JLabel lblGeneracionBest = new JLabel("");
	private JButton btnLanzar = new JButton("Lanzar");
	private JButton btnVerGantt    = new JButton("Ver Gantt");
	
	JTextField fldGeneraciones     = new JTextField("1000");
	JTextField fldTamanioPoblacion = new JTextField("100");
	JTextField fldProbMutacion     = new JTextField("0.9");
	
	public GUI() {
		
		
		JPanel panelControles = new JPanel();
		panelControles.setLayout(new GridLayout(4, 2));
		panelControles.add(new JLabel("Generaciones"));
		panelControles.add(fldGeneraciones);
		panelControles.add(new JLabel("Tamaño población"));
		panelControles.add(fldTamanioPoblacion);
		panelControles.add(new JLabel("Probabilidad de mutación"));
		panelControles.add(fldProbMutacion);
		panelControles.add(new JLabel(""));
		panelControles.add(btnLanzar);
		
		
		JPanel panelBest = new JPanel();
		panelBest.setLayout(new GridLayout(4, 2));
		panelBest.add(new JLabel("Avance"));
		panelBest.add(lblAvance);
		panelBest.add(new JLabel("Best"));
		panelBest.add(lblBest);
		panelBest.add(new JLabel("Generación de Best"));
		panelBest.add(lblGeneracionBest);
		panelBest.add(new JLabel(""));
		panelBest.add(btnVerGantt);
		btnVerGantt.setEnabled(false);
		
		
		
		JPanel panelIzquierda = new JPanel();
		JPanel panelArriba = new JPanel();
		
		panelArriba.setLayout(new BorderLayout());
		panelIzquierda.setLayout(new BorderLayout());
		panelArriba.add (panelIzquierda, BorderLayout.NORTH);
		panelIzquierda.add(panelControles, BorderLayout.NORTH);
		panelIzquierda.add(panelBest, BorderLayout.SOUTH);
		
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelArriba, BorderLayout.WEST);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setSize(400, 400);
		
		this.setVisible(true);
		
		
		
		btnLanzar.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						Runnable r = new Runnable() {
							
							@Override
							public void run() {
								
								int generaciones = 1000;
								int poblacion = 100;
								double mutacion = 0.9;
								
								try {generaciones = Integer.parseInt(fldGeneraciones.getText().trim());} catch (Exception ex) {ex.printStackTrace();}
								try {poblacion = Integer.parseInt(fldTamanioPoblacion.getText().trim());} catch (Exception ex) {ex.printStackTrace();}
								try {mutacion = Double.parseDouble(fldProbMutacion.getText().trim());} catch (Exception ex) {ex.printStackTrace();}
								
								fldGeneraciones.setText(""+ generaciones);
								fldTamanioPoblacion.setText(""+poblacion);
								fldProbMutacion.setText("" + mutacion);
								
								
								GUI.this.corrida = new Corrida(generaciones, poblacion, mutacion, MetodoSeleccion.SEL_RULETA);
								GUI.this.corrida.addListener(GUI.this);
								btnLanzar.setEnabled(false);
								btnVerGantt.setEnabled(false);
								GUI.this.corrida.buscar();
								
							}
						};
						
						new Thread(r).start();
												
					}
				});
		
		btnVerGantt.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						new VerGantt(corrida);
						
					}
				}
				);
				
		
				
		
		
	}
	
	@Override
	public void avance(Corrida corrida) {
		this.lblAvance.setText((corrida.getGeneracionActual() * 100 /corrida.getGeneraciones()) +" %");
	}
	
	@Override
	public void finished(Corrida corrida) {
		btnLanzar.setEnabled(true);
		btnVerGantt.setEnabled(true);
	}
	
	@Override
	public void nuevoMinimo(Corrida corrida) {
		this.lblBest.setText("" + corrida.getBest().getTiempoTotal());
		this.lblGeneracionBest.setText("" + corrida.getBestGen());
		
	}
	
	public static void main(String[] args) {
		new GUI();
	}

}
