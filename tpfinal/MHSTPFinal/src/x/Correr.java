package x;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import x.Solucion.Resultado;

public class Correr {
	
	public static void main(String[] args) {
		
		int[] pesos1 = {5,4,3,2,1};
		int[] pesos2 = {1,2,3,4,5};
		int[] pesos3 = {1,4,1,7,1};
		int[] pesos4 = {1,1,6,1,1};
		int[] pesos5 = {1,1,1,8,1};
		
		
		
		List<Individuo> x = leerDatos();
		
		for (Individuo t: x) {
			System.out.print(t.getId() + " > ");
			for (int i= 0 ; i < t.getCantidadMaquinas() ; i++ ) {
				System.out.printf( " %2d", t.getPeso(i) );
			}
			System.out.println();
		}
		
		
		
		Solucion sol = new Solucion(x);

		int generaciones = 10;
		int bestVal = Integer.MAX_VALUE;
		int bestGen = -1;
		Resultado best =  null;
		
		for (int gen = 1; gen <= generaciones; gen ++) {
			
			sol.shuffle();
			
			//System.out.println("===========================");
			//System.out.println("Evaluando generacion " + gen);
			
			Resultado r = sol.evaluar();
			//System.out.println("valor obtenido " + r.getTiempoTotal() );
			
			if(r.getTiempoTotal() <bestVal) {
				bestVal = r.getTiempoTotal();
				bestGen = gen;
				best = r;
			}
			
			
		}
		
		System.out.printf("Mejor solucion %d en la generacion %d", bestVal, bestGen);
		best.imprimir();
		
	}
	
	
	public static List<Individuo> leerDatos() {
		
		List<Individuo> tareas = new LinkedList<>();
		List<List<Integer>> valores = new LinkedList<>();
		
		
		String linea ;
		try {
			BufferedReader br = new BufferedReader(new FileReader("data.txt"));
			while ((linea = br.readLine())!= null) {
				
				if ( linea.trim().equals("") ) 
					continue;
				
				List<Integer> lineaProceso = new LinkedList<>();
				valores.add(lineaProceso);
				String [] tokens = linea.split(" ");
				
				for (String token: tokens) {
					lineaProceso.add(new Integer(token));
				}
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int cantidadTareas = valores.get(0).size();
		int cantidadMaquinas = valores.size();
		
		for (int i = 0; i < cantidadTareas ; i++) {
			tareas.add(new Individuo("Tarea " + (i + 1), cantidadMaquinas));
		}
		
		for (int maquina = 0; maquina < cantidadMaquinas; maquina++) {
			if (cantidadTareas != valores.get(maquina).size()) {
				throw new RuntimeException("Todas las líneas del archivo de datos deben tener la misma cantidad de valores. Error en la linea " + (maquina + 1));
			}
			for (int tarea = 0; tarea < cantidadTareas ; tarea++) {
				tareas.get(tarea).setPeso(maquina, valores.get(maquina).get(tarea));
			}
		}
		return tareas;
		
	}

}
