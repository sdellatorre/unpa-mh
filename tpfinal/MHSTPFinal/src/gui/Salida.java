package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

import x.Corrida;
import x.Solucion;
import x.Solucion.Resultado;

public class Salida {
	
	public static void guardar(Corrida c, String nombre) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(nombre + ".txt"));
			bw.write( String.format( "Mejor solucion %d en la generacion %d\n", c.getBest().getTiempoTotal(), c.getBestGen()));
			bw.write( c.getBest().getResultado().toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
			}
		}
		
		
	}
	

	public static Image dibujar (Solucion s, String nombre, boolean bajarArchivo) {
		
		Color[] colores = {
				
				Color.YELLOW,
				Color.CYAN,
				Color.GRAY,
				Color.RED,
				Color.GREEN,
				Color.MAGENTA,
				Color.ORANGE,
				Color.PINK
				
				
		};
		
		Resultado r= s.getResultado();
		BufferedImage image = new BufferedImage(150 + s.getTiempoTotal(), 25 + (r.getCantidadMaquinas() * 15), BufferedImage.TYPE_INT_RGB);
		
		int h = image.getHeight();
		int w = image.getWidth();
		

		Graphics gr = image.getGraphics();
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 0, w, h);
		
		int i = 0;
		gr.setColor(Color.DARK_GRAY);
		while (i < s.getTiempoTotal()) {
			gr.drawLine(50 + i, 0, 50 + i, 200);
			gr.drawString(i+"", 52+i, 12);
			i+=250;
		}
		
		gr.drawLine(50 + s.getTiempoTotal(), 0, 50 + s.getTiempoTotal(), 200);
		gr.drawString(s.getTiempoTotal() +"", 52 + s.getTiempoTotal(), 15 * (s.getCantidadMaquinas()) + 10 );
		
		//Font font = gr.getFont();
		//font.
		
		for (int maquina = 0 ; maquina < r.getCantidadMaquinas() ; maquina ++) {
			for (int tarea = 0 ; tarea < r.getCantidadTareas(); tarea ++) {
				gr.setColor(colores[tarea%colores.length]);
				gr.fillRect(50 + r.getInicio(maquina, tarea), 15 * (maquina + 1), r.getFin(maquina, tarea) - r.getInicio(maquina, tarea), 10);
				gr.setColor(Color.BLACK);
				if (r.getFin(maquina, tarea) - r.getInicio(maquina, tarea) >= 30) {
					gr.drawString(r.getTagTarea(tarea), 50 + r.getInicio(maquina, tarea), 15 * (maquina +1) + 10);
				}
			}
		}

		if (bajarArchivo) {
			try {
		
				// Se copia el achivo
				ImageIO.write(image, "jpg", new File(nombre+".jpg"));
				System.out.println("Archivo de salida copiado en " + new File(nombre+".jpg").getAbsolutePath());
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	
	return image;
	}
	
	
	

}
