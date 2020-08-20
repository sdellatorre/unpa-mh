CREATE TEMP TABLE tmp_ga (
	funcion text,
	pc text,
	pm text,
	e text,
	sel text,
	r text,
	sem int,
	val numeric,
	x text,
	y text,
	z text
);

\copy tmp_ga from detalle_f1.txt 
	
	
