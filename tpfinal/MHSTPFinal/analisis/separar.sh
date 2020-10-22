grep Mejor ../C1000_G1000_np*.txt| sed 's/:Mej.*lucion /|/' | sed 's/_/|/g' | sed 's/^..\///'| sed 's/ .*on /|/' > separado.txt
