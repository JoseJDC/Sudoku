package hk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tablero {

	public Random random = new Random();
	public int vacios = 40;
	
	public Tablero() {
		Casilla[][] sudoku = crearSudoku();
		imprimirSudoku(sudoku);
	}
	
    public Casilla[][] crearSudoku() {
    	Casilla[][] sudoku = new Casilla[9][9];
    	
    	for(int i = 0; i < sudoku.length; i++) {
    		for(int j = 0; j < sudoku.length; j++) {
        		sudoku[i][j] = new Casilla();
        	}
    	}

        // Rellenar la diagonal principal con números del 1 al 9
        rellenarDiagonal(sudoku);

        // Resolver el Sudoku mediante backtracking
        rellenarSudoku(sudoku);
        
        // Vacia aleatoriamente los espacios del sudoku
        vaciarSudoku(sudoku);

        return sudoku;
    }

    public void rellenarDiagonal(Casilla[][] sudoku) {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);

        for (int i = 0; i < 9; i++) {
            sudoku[i][i].Numero = numeros.get(i);
        }
    }

    public boolean rellenarSudoku(Casilla[][] sudoku) {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                if (sudoku[fila][columna].Numero == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (esValido(sudoku, fila, columna, num)) {
                            sudoku[fila][columna].Numero = num;
                            if (rellenarSudoku(sudoku)) {
                                return true;
                            }
                            sudoku[fila][columna].Numero = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esValido(Casilla[][] sudoku, int fila, int columna, int num) {
        // Verificar si el número ya existe en la fila
        for (int col = 0; col < 9; col++) {
            if (sudoku[fila][col].Numero == num) {
                return false;
            }
        }

        // Verificar si el número ya existe en la columna
        for (int row = 0; row < 9; row++) {
            if (sudoku[row][columna].Numero == num) {
                return false;
            }
        }

        // Verificar si el número ya existe en la cuadrícula 3x3
        int startRow = fila - fila % 3;
        int startCol = columna - columna % 3;
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (sudoku[row][col].Numero == num) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public void vaciarSudoku(Casilla[][] sudoku) {
    	while(vacios > 0) {
    		for (int fila = 0; fila < 9; fila++) {
                for (int columna = 0; columna < 9; columna++) {
                	if(random.nextBoolean()) {
                		sudoku[fila][columna].Numero = 0;
                		vacios--;
                	}
                }
    		}
    	}
    }

    public void imprimirSudoku(Casilla[][] sudoku) {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                System.out.print(sudoku[fila][columna].Numero + " ");
            }
            System.out.println();
        }
    }
}
