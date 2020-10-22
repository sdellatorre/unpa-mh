package x;

import x.Corrida.MetodoSeleccion;

public class Ejecutar {
	
	public static void main(String[] args) {
		new Ejecutar();
	}
	
	
	public Ejecutar() {
		
		for (int i = 0 ; i < 1000 ; i++ ) {
			new Corrida(1000, 100, 0.9, MetodoSeleccion.SEL_RULETA).buscar();
		}

	}

}
