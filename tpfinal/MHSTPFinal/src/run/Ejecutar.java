package run;

import gui.Salida;
import x.Corrida;
import x.Corrida.MetodoSeleccion;

public class Ejecutar {
	
	public static void main(String[] args) {
		Corrida c = new Corrida(1000, 100, 0.9, MetodoSeleccion.SEL_RULETA);
		c.buscar();
		Salida.dibujar(c.getBest(), "BEST", true);
		System.out.printf("Mejor solucion %d en la generacion %d\n", c.getBest().getTiempoTotal(), c.getBestGen());
		System.out.println(c.getBest().getResultado().toString());

	}
	

}
