package run;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gui.Salida;
import x.Corrida;

public class CorrerParaEstadisticas {

	public static void main(String[] args) throws IOException {
		
		int corridas = 1000;
		int generaciones = 1000;
		int tamanioPoblacion = 100;
		double probablidadMutacion = 0.9;

		Corrida best = null;
		
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
		
		
		String nombre = "C" + corridas+"_G" + generaciones + "_np"+tamanioPoblacion +"_pm" + probablidadMutacion + "_" + sdf.format(new Date()) ;  
		BufferedWriter bw = new BufferedWriter(new FileWriter(nombre + ".txt"));
		
		for (int i = 0; i < corridas ; i++  ) {
			Corrida c = new Corrida(1000, 100, 0.9);
			c.buscar();
			
			
			
			
			System.out.print( String.format( "Mejor solucion %d en la generacion %d\n", c.getBest().getTiempoTotal(), c.getBestGen()));
			
			bw.write( String.format( "Mejor solucion %d en la generacion %d\n", c.getBest().getTiempoTotal(), c.getBestGen()));
			bw.write( c.getBest().getResultado().toString());
			
			
			if (best == null || c.getBest().getTiempoTotal() < best.getBest().getTiempoTotal()) {
				best = c;
				
			}
			
		}
		
		Salida.dibujar(best.getBest(), nombre + "_gen" + best.getBestGen(), true);
		
		bw.close();
	}

}
