BEGIN;

CREATE TEMP TABLE tmp_ga (
	funcion text,
	pc text,
	pm text,
	elitismo text,
	sel text,
	reempl text,
	sem int,
	val numeric,
	x text,
	y text,
	z text
);

\copy tmp_ga from detalle_f1.txt 
	
	
UPDATE tmp_ga SET elitismo = 'CON ELITISMO' WHERE elitismo = '1';
UPDATE tmp_ga SET elitismo = 'SIN ELITISMO' WHERE elitismo = '0';

UPDATE tmp_ga SET reempl = 'REEMPLAZO GENERACIONAL' WHERE reempl = '1';
UPDATE tmp_ga SET reempl = 'REEMPLAZO SS' WHERE reempl = '2';


UPDATE tmp_ga SET sel = 'RULETA' WHERE sel = '1';
UPDATE tmp_ga SET sel = 'TORNEO' WHERE sel = '2';


UPDATE tmp_ga SET pm = 'PM = 0.01' WHERE pm = '01';
UPDATE tmp_ga SET pm = 'PM = 0.0'  WHERE pm = '0';
UPDATE tmp_ga SET pm = 'PM = 0.1'  WHERE pm = '1';

UPDATE tmp_ga SET pc = 'PC = 0.' || pc;
END;
