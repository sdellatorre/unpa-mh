# Makefile to get the PSO

DEBUG = -DDEBUG
#DEBUG = 
CC = gcc
OBJ = 
PSO: pso.o 
	 $(CC) -o PSO $(DEBUG) pso.o -lm 


pso.o: pso.c
	$(CC) -c $(DEBUG) pso.c


