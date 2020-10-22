package x;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import gui.Salida;


public class Corrida {
	
	private Random rnd = new Random();
	
	private int generaciones;
	private int np;
	private double pm;

	private Solucion [] poblacion;
	private Solucion best =  null;
	private int bestGen =  0;
	private int generacionActual;

	//private Resultado [] resultados;
	private int sumaPoblacion;         //Para ruleta
	
	public static void main(String[] args) {
		Corrida c = new Corrida(1000, 100, 0.9);
		c.buscar();
		Salida.dibujar(c.getBest(), "BEST", true);
		
		System.out.printf("Mejor solucion %d en la generacion %d\n", c.getBest().getTiempoTotal(), c.getBestGen());
		System.out.println(c.getBest().getResultado().toString());

	}
	
	public Solucion getBest() {
		return best;
	}
	
	public int getBestGen() {
		return bestGen;
	}
	
	public int getGeneracionActual() {
		return generacionActual;
	}
	
	public int getGeneraciones() {
		return generaciones;
	}
	
	public Solucion[] getPoblacion() {
		return poblacion;
	}
	
	public double getPm() {
		return pm;
	}
	
	public Corrida(int generaciones, int np, double pm) {
		this.generaciones = generaciones;
		this.np = np;
		this.pm = pm;		
	}
	
	public void buscar() {
		
		int bestVal = Integer.MAX_VALUE;
		this.generacionActual = 0;

		List<Individuo> x = leerDatos();
		
		// Se crea una poblacion de np elementos 
		poblacion = new Solucion[np];

		for (int i = 0 ; i < np ; i++) {
			poblacion[i] = new Solucion(x);
			poblacion[i].shuffle();
			poblacion[i].evaluar();
			sumaPoblacion += poblacion[i].getTiempoTotal(); 
			if(poblacion[i].getTiempoTotal() <bestVal) {
				best = poblacion[i];
				bestVal = poblacion[i].getTiempoTotal();
				bestGen = 0;
				
			}			
		}
		
			
		for (generacionActual = 1; generacionActual <= this.generaciones; generacionActual ++) {
			Solucion [] poblacionPN = new Solucion[np];

			//System.out.printf("Gen: %d: ", gen);
			

			
			for (int i = 0; i < np ; i++) {			
				// Selección
				Solucion padre1 = poblacion[seleccion()].clone();
				Solucion padre2 = poblacion[seleccion()].clone();
				
				
				// Reproducción (Mutación y cruce)

				if (rnd.nextDouble() < this.pm) {
					padre1.permutar(rnd.nextInt(padre1.getCantidadIndividuos()), rnd.nextInt(padre1.getCantidadIndividuos()));

				}				
				
				if (rnd.nextDouble() < this.pm) {
					padre2.permutar(rnd.nextInt(padre2.getCantidadIndividuos()), rnd.nextInt(padre2.getCantidadIndividuos()));

				}
				
				Solucion hijo = padre1.clone();
				int corte1 = rnd.nextInt((int) (hijo.getCantidadIndividuos() * (double)2 / 3));
				int corte2 = Math.min( corte1 + rnd.nextInt((int) (hijo.getCantidadIndividuos()  / (double) 3)), hijo.getCantidadIndividuos() -1);
				
				int desde = 0;
				int hasta = 0;
				
				switch (rnd.nextInt(3)) {
					case 0: desde = 0; hasta = corte1; break;
					case 1: desde = corte1; hasta = corte2; break;
					case 2: desde = corte2; hasta = hijo.getCantidadIndividuos(); break;
				}
				
				for (int g = desde ; g < hasta; g++) {
					int indiceTareaEnHijo = hijo.getPosicion(padre2.getTarea(g));
					hijo.permutar(g, indiceTareaEnHijo);
				}
				
				// Evaluar p2
				hijo.evaluar();

				// Reemplazo
				if (hijo.getTiempoTotal() < poblacion[i].getTiempoTotal() && !best.equals(poblacion[i]) ) {
					poblacionPN[i] = hijo;
				} else {
					poblacionPN[i] = poblacion[i];
				}
				
			}
			poblacion = poblacionPN;
			sumaPoblacion = 0;
			int bestValGen = Integer.MAX_VALUE;
			//int bestValGenIndex = 0;
			
			for (int i = 0 ; i < np ; i++) {
				// Se evaluan los resultados y se establece el mejor   
				sumaPoblacion += poblacion[i].getTiempoTotal(); 
				if(poblacion[i].getTiempoTotal() <bestVal) {
					bestVal = poblacion[i].getTiempoTotal();
					bestGen = generacionActual;
					best = poblacion[i];
					this.informarNuevoMinimo();
				}
				if(poblacion[i].getTiempoTotal() <bestValGen) {
					bestValGen = poblacion[i].getTiempoTotal();
					//bestValGenIndex = i;
				}
			}
			
			if (generacionActual%(this.generaciones/100)==0) {
				this.informarAvance();
			}
			
			
		}

		
		if (best.getTiempoTotal() < 5800) {
			Salida.dibujar(best, "MENOR_" + best.getTiempoTotal(), true);
			Salida.guardar(this, "MENOR_" + best.getTiempoTotal());
		}
		
		this.informarFinished();
		
	}
	
	// Ruleta
	public int seleccionRuleta() {
		int v = rnd.nextInt(sumaPoblacion);
		int acum = 0;
		for (int i = 0; i < this.np ; i++) {
			acum += poblacion[i].getTiempoTotal();
			if (acum > v)
				return i;
		}
		return 0;
	}
	
	// Torneo
	public int seleccion() {
		int menorValor = Integer.MAX_VALUE;
		int retval = 0;
		for (int i = 0; i < 3; i++) {
			int v = rnd.nextInt(this.np);
			int aux = this.poblacion[v].getTiempoTotal();
			if (aux < menorValor) {
				menorValor = aux;
				retval = v;
			}
		}
		return retval;
	}
	
	
	
	private static List<Individuo> tareas;
	
	
	public static List<Individuo> leerDatos() {
		if(tareas != null) {
			return new LinkedList<>(tareas);
		}
		
		tareas = new LinkedList<>();
		List<List<Integer>> valores = new LinkedList<>();
		
		
		String linea ;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("dataoriginal.txt"));
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
		
		int cantidadTareas = valores.get(0).size();
		int cantidadMaquinas = valores.size();
		
		for (int i = 0; i < cantidadTareas ; i++) {
			String nombre = String.format("Tarea %3d", i+1);
			tareas.add(new Individuo(nombre, cantidadMaquinas));
		}
		
		for (int maquina = 0; maquina < cantidadMaquinas; maquina++) {
			if (cantidadTareas != valores.get(maquina).size()) {
				throw new RuntimeException("Todas las líneas del archivo de datos deben tener la misma cantidad de valores. Error en la linea " + (maquina + 1));
			}
			for (int tarea = 0; tarea < cantidadTareas ; tarea++) {
				tareas.get(tarea).setPeso(maquina, valores.get(maquina).get(tarea));
			}
		}
		return new LinkedList<>(tareas);
	}
	
	
	//-- Notificación vía patron Observer
	
	private List <CorridaListener> listeners = new LinkedList<>();
	
	public void addListener(CorridaListener cl) {
		this.listeners.add(cl);
	}
	
	private void informarAvance() {
		for (CorridaListener l: this.listeners) {
			Runnable r = new Runnable() {
				public void run() {
					l.avance(Corrida.this);
				}
			};
			new Thread(r).start();
		}
	}
	
	private void informarNuevoMinimo() {
		for (CorridaListener l: this.listeners) {
			Runnable r = new Runnable() {
				public void run() {
					l.nuevoMinimo(Corrida.this);
				}
			};
			new Thread(r).start();
		}
	}
	private void informarFinished() {
		for (CorridaListener l: this.listeners) {
			Runnable r = new Runnable() {
				public void run() {
					l.finished(Corrida.this);
				}
			};
			new Thread(r).start();
		}
	}
	
	
}
