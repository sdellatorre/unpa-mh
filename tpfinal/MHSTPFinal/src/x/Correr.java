package x;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import x.Solucion.Resultado;

public class Correr {
	
	private Random rnd = new Random(); 
	
	private Solucion [] poblacion;
	private Resultado [] resultados;
	private int sumaPoblacion;         //Para ruleta
	private int np = 15;
	private double pm = 0.5;
	
	
	public static void main(String[] args) {
		new Correr();
	}
	
	public Correr() {
		
		
		int generaciones = 100;
		int bestVal = Integer.MAX_VALUE;
		int bestGen = -1;
		Resultado best =  null;

		
		List<Individuo> x = leerDatos();
		
		// Se crea una poblacion de np elementos 
		poblacion = new Solucion[np];
		resultados = new Resultado[np];
		for (int i = 0 ; i < np ; i++) {
			poblacion[i] = new Solucion(x);
			poblacion[i].shuffle();
			
			// Se evaluan los resultados y se establece el mejor   
			resultados[i] = poblacion[i].evaluar();
			sumaPoblacion += resultados[i].getTiempoTotal(); 
			if(resultados[i].getTiempoTotal() <bestVal) {
				bestVal = resultados[i].getTiempoTotal();
				bestGen = 0;
				best = resultados[i];
			}			
		}
		
		
		for (int gen = 1; gen <= generaciones; gen ++) {
			Solucion [] poblacionPN = new Solucion[np];
			Resultado [] resultadosPN = new Resultado[np];
			
			for (int i = 0; i < np ; i++) {			
				// Selección
				Solucion padre1 = poblacion[seleccion()].clone();
				Solucion padre2 = poblacion[seleccion()].clone();
				
				
				// Reproducción (Mutación y cruce)
				
				if (rnd.nextDouble() < pm) {
					padre1.permutar(rnd.nextInt(padre1.getCantidadIndividuos()), rnd.nextInt(padre1.getCantidadIndividuos()));
				}
				
				if (rnd.nextDouble() < pm) {
					padre2.permutar(rnd.nextInt(padre2.getCantidadIndividuos()), rnd.nextInt(padre2.getCantidadIndividuos()));
				}
				
				Solucion hijo = padre1.clone();
				int corte1 = rnd.nextInt((int) (hijo.getCantidadIndividuos() * (double)2 / 3));
				int corte2 = Math.min( corte1 + rnd.nextInt((int) (hijo.getCantidadIndividuos()  / (double) 3)), hijo.getCantidadIndividuos() -1);
				
				for (int g = corte1 ; g <= corte2 ; g++) {
					int indiceTareaEnHijo = hijo.getPosicion(padre2.getTarea(g));
					hijo.permutar(g, indiceTareaEnHijo);
				}
				
				// Evaluar p2
				Resultado r= hijo.evaluar();

				// Reemplazo
				if (r.getTiempoTotal() < resultados[i].getTiempoTotal() && !best.equals(poblacion[i]) ) {
					poblacionPN[i] = hijo;
					resultadosPN[i] = r;
				} else {
					poblacionPN[i] = poblacion[i];
					resultadosPN[i] = resultados[i];
				}
				
			}
			poblacion = poblacionPN;
			resultados = resultadosPN;
			sumaPoblacion = 0;
			int bestValGen = Integer.MAX_VALUE; 
			
			for (int i = 0 ; i < np ; i++) {
				// Se evaluan los resultados y se establece el mejor   
				sumaPoblacion += resultados[i].getTiempoTotal(); 
				if(resultados[i].getTiempoTotal() <bestVal) {
					bestVal = resultados[i].getTiempoTotal();
					bestGen = gen;
					best = resultados[i];
				}
				if(resultados[i].getTiempoTotal() <bestValGen) {
					bestValGen = resultados[i].getTiempoTotal();
				}
			}
			
			System.out.printf("Gen: %d: %4d\n", gen, bestValGen);
			
			
		}
		
		System.out.printf("Mejor solucion %d en la generacion %d\n", bestVal, bestGen);
		best.imprimir();
		
	}
	
	// Ruleta
	public int seleccion() {
		int v = rnd.nextInt(sumaPoblacion);
		int acum = 0;
		for (int i = 0; i < np ; i++) {
			acum += resultados[i].getTiempoTotal();
			if (acum > v)
				return i;
		}
		return 0;
		
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
