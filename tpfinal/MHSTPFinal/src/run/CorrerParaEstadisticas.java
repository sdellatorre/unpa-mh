package run;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gui.Salida;
import x.Corrida;
import x.Corrida.MetodoSeleccion;

public class CorrerParaEstadisticas {
	
	

	public static void main(String[] args) throws IOException {
		
		int corridas = 1000;
		int generaciones = 1000;
		
		int[] tamaniosPoblacion = {500, 1000}; 
		double[] probabilidadesMutacion = {0.1, 0.3, 0.6, 0.9};
		
		
		

		
		
		for (MetodoSeleccion metodoSeleccion: MetodoSeleccion.values()) {
			if(metodoSeleccion == MetodoSeleccion.SEL_RULETA)
				continue;
			for (int tamanioPoblacion : tamaniosPoblacion) {
				for (double probablidadMutacion : probabilidadesMutacion) {
				
		
			Corrida best = null;
			SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
			
			
			String nombre = "C" + corridas+"_G" + generaciones + "_np"+tamanioPoblacion +"_pm" + probablidadMutacion + "_" + metodoSeleccion + "_" + sdf.format(new Date()) ;  
			BufferedWriter bw = new BufferedWriter(new FileWriter(nombre + ".txt"));
			
			for (int i = 0; i < corridas ; i++  ) {
				Corrida c = new Corrida(generaciones, tamanioPoblacion, probablidadMutacion, metodoSeleccion);
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
		}
	}

}
