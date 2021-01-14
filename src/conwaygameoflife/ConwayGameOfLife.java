/*
 * 
 */
package conwaygameoflife;

/**
 *
 * @author Rokas
 */
public class ConwayGameOfLife {

    
    public static void printField(char[][] field) {
        
        for (int rowInd = 0; rowInd < field.length; rowInd++) {
            for (int colInd = 0; colInd < field[rowInd].length; colInd++) {
                System.out.print(field[rowInd][colInd] + " ");
            }
            System.out.println("");
        }
        
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int generationType = 0; // 0 - choosed populatios, 1 - auto generation
        int endType = 0; // 0 - iterationsNum, 1 - terminate earlier if we found the same population
        int iterationsNum = 50;
        
        int fieldRows = 10;
        int fieldCols = 10;
        
        char lifeElement = '#';
        char emptyElement = '-';
        
        int iteration = 0;
        
        char[][][] fieldsArr = new char[1][1][1];
        char[][] field = new char[fieldRows][fieldCols];
        
        for (int r = 0; r < fieldRows; r++) {
            for (int c = 0; c < fieldCols; c++) {
                if (Math.random() <= 0.25) {
                    field[r][c] = lifeElement;
                } else {
                    field[r][c] = emptyElement;
                }
            }
        }
        
        
        if (generationType == 0) {
        
            // 5X5
            char[][] fieldEg1 = {
                {'-', '-', '#', '-', '#'},
                {'-', '-', '-', '#', '-'},
                {'-', '-', '#', '#', '-'},
                {'-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-'}
            };

            // 5X5
            char[][] fieldBlinker = {
                {'-', '-', '-', '-', '-'},
                {'-', '-', '#', '-', '-'},
                {'-', '-', '#', '-', '-'},
                {'-', '-', '#', '-', '-'},
                {'-', '-', '-', '-', '-'}
            };

            // 18X11
            char[][] fieldPentadecathlon = {
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '#', '-', '#', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '#', '-', '#', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'}
            };

            // 18X11
            char[][] fieldSpaceShipGlinder = {
                {'-', '-', '-', '-', '-'},
                {'-', '#', '-', '-', '-'},
                {'-', '-', '#', '#', '-'},
                {'-', '#', '#', '-', '-'},
                {'-', '-', '-', '-', '-'}
            };

            fieldsArr = new char[iterationsNum+1][fieldRows][fieldCols];
  
            char[][] fieldEg = new char[1][1];
            //fieldEg = fieldEg1;
            //fieldEg = fieldSpaceShipGlinder;
            //fieldEg = fieldBlinker;
            fieldEg = fieldPentadecathlon;

            fieldRows = fieldEg.length;
            fieldCols = fieldEg[0].length;
            
            fieldsArr[0] = fieldEg;
        
        } else if (generationType == 1) {
            fieldsArr = new char[iterationsNum+1][fieldRows][fieldCols];
            fieldsArr[0] = field;
        } else {
            System.out.println("Choose the correct generationType, selected: " + generationType);
        }
        
        printField(fieldsArr[iteration]); 
        
        
        boolean findedPeriod = false; // used if endType = 1
        boolean toContinue = true;
        
        while (toContinue) {
        
          
            char[][] newGenerationField = new char[fieldRows][fieldCols];
            int[] elementComparison = new int[iteration+1]; //dydis priklauso nuo buvusiu kartu (gen) skaiciaus. +1 - nes pradine iteracija = 0
            int[] lifeElNewGen = new int[iteration+1]; // population size of generations
            
            for (int rowInd = 0; rowInd < fieldsArr[iteration].length; rowInd++) {
                for (int colInd = 0; colInd < fieldsArr[iteration][rowInd].length; colInd++) {

                    int neighboursNum = 0;

                    // neighbours elements
                    for (int i = -1; i < 2; i++ ) {
                        for (int j = -1; j < 2; j++) {

                            // nepraleidziam, kai i=0 ir j=0, kur yra tikrinamas elementas
                            if (i != 0 || j != 0) {
                                
                                if ( (rowInd+i)>=0 && (rowInd+i)!=fieldsArr[iteration].length && (colInd+j)>=0 && (colInd+j)!=fieldsArr[iteration][rowInd].length  ) {
    //                                
                                    if (fieldsArr[iteration][rowInd+i][colInd+j] == lifeElement) {
                                        neighboursNum++;
                                    }

                                }
                            }

                        }
                    }
                    
                    if (fieldsArr[iteration][rowInd][colInd] == lifeElement) {
                        
                        if (neighboursNum == 2 || neighboursNum == 3) {
                            newGenerationField[rowInd][colInd] = lifeElement;
                        } else {
                            newGenerationField[rowInd][colInd] = emptyElement;
                        }

                    } else {
                        if (neighboursNum == 3) {
                            newGenerationField[rowInd][colInd] = lifeElement;
                        } else {
                            newGenerationField[rowInd][colInd] = emptyElement;
                        }
                    }

                    // generation comparing
                    for (int it = iteration; it >= 0; it--) {
                        
                        // jei elementai nesutampa, pazymime.
                        if (fieldsArr[it][rowInd][colInd] != newGenerationField[rowInd][colInd] ) {
                            elementComparison[it]++;
                        }
                        
                        // tikrame ar naujoje kartoje yra gyvybe. Tam, kad veliau ivertintume ar nesutapo tiesiog kartos be gyvybes.
                        if (newGenerationField[rowInd][colInd] == lifeElement) {
                            lifeElNewGen[it]++; // su kuria generacija lyginam, ten ir dedame kiek naujoje generacijoje yra populiaciju.
                        }
                    }
                        
   
                }

            }

            iteration++;
            
            System.out.println("-<><><><><><><><><><><>-");
            printField(newGenerationField);
            System.out.println("|^| new gen: " + iteration);
            
            fieldsArr[iteration] = newGenerationField;
            
            int period = 0;
            

            for (int gen = iteration-1; gen >= 0; gen--) {
                // Yra periodas, jei dvieju kartu visi elementai sutapo (elementComparison[gen] == 0), o naujoje kartoje yra gyvybe
                if (elementComparison[gen] == 0 && lifeElNewGen[gen] > 0) {
                    if (period == 0) {
                        period = iteration - gen;
                        System.out.print("|^| repeated gen: ");
                        if (endType == 1) {     
                            findedPeriod = true;
                        }
                    }
                    System.out.print(gen + " ");
                }
            }
            
            if (period > 0) {
                System.out.println("");
            }
            System.out.println("|^| Period: " + period);
            
            if (iteration == iterationsNum || findedPeriod) {
                toContinue = false;
            }
            
            
        }  // <<< while end
        

    }
    
}

