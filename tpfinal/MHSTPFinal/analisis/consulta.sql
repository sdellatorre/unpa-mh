SELECT cor, gen,  ''pn, ''pm,  tipo, '' arch, MIN (best) as best, AVG(best)::int, (((AVG(best) - 5770 ) / 5770)*100)::numeric(3,2) rpd, AVG (gbest)::int as gbest, COUNT (*) FROM ejecuciones GROUP BY 1,2,3,4,5,6 ORDER BY 1,2,3,4,5,6;

