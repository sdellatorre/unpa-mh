# Archivo 'makefile' para construir el programa ejecutable

ga-c: main.o rec_argu.o ga.o eval_fun.o plot.o
	gcc -o ga-c main.o rec_argu.o ga.o eval_fun.o plot.o -lm


main.o: main.c var.h def.h
	gcc -c main.c

ga.o: ga.c var.h def.h
	gcc -c ga.c 

rec_argu.o: rec_argu.c var.h def.h
	gcc -c rec_argu.c

eval_fun.o: eval_fun.c var.h def.h
	gcc -c eval_fun.c

plot.o: plot.c var.h def.h
	gcc -c plot.c
