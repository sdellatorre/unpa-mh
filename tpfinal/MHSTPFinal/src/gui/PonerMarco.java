package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import sun.security.action.GetBooleanAction;
import x.Solucion.Resultado;

public class PonerMarco {

	public static void dibujar (Resultado r, String nombre) {
		
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
		
		
	try {
		BufferedImage image = new BufferedImage(150 + r.getTiempoTotal(), 25 + (r.getCantidadMaquinas() * 15), BufferedImage.TYPE_INT_RGB);
		int h = image.getHeight();
		int w = image.getWidth();
		

		Graphics gr = image.getGraphics();
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 0, w, h);
		
		int i = 0;
		gr.setColor(Color.DARK_GRAY);
		while (i < r.getTiempoTotal()) {
			gr.drawLine(50 + i, 0, 50 + i, 200);
			gr.drawString(i+"", 52+i, 12);
			i+=250;
		}
		
		gr.drawLine(50 + r.getTiempoTotal(), 0, 50 + r.getTiempoTotal(), 200);
		gr.drawString(r.getTiempoTotal() +"", 52+r.getTiempoTotal(), 15 * (r.getCantidadMaquinas()) + 10 );
		
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

		
		


		// Se copia el achivo
		ImageIO.write(image, "jpg", new File(nombre+".jpg"));
		System.out.println("Archivo de salida copiado en " + new File(nombre+".jpg").getAbsolutePath());
		
	} catch (IOException ex) {
		ex.printStackTrace();
	}
	}

}
