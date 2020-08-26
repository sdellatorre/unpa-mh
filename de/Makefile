# Makefile to get the DE

DEBUG = -DDEBUG
#DEBUG = 
CC = gcc
OBJ = 
DifferentialEvolution: de36.o dj9.o
	 $(CC) -o DifferentialEvolution $(DEBUG) de36.o dj9.o -lm 


de36.o: de36.c de.h
	$(CC) -c $(DEBUG) de36.c

dj9.o: dj9.c de.h
	$(CC) -c $(DEBUG) dj9.c

