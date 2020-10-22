CREATE TABLE ejecuciones (
	cor text,
	gen text,
	pn text,
	pm text,
	arch text,
	best int,
	gbest int
) ;

\copy ejecuciones from separado.txt with delimiter '|'
