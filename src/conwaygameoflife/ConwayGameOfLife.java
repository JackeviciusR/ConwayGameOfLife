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
        System.out.println("|^| gen: 0");
        
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
        
        String[] lifeFiguresTypes = {"Still Life", "Oscillator", "Spacehip"};
        
        int iteration = 0;
        
        // square corners coordinates: LRTB - left, right, top, bottom
        int[][] sqLRTB = new int[iterationsNum][4];

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
            
            // start oscillate after 8 generation, period: 2
            char[][] fieldMy01 = {
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '#', '#', '-', '#', '#', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '#', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-'}
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
                {'-', '-', '-', '-', '-', '-'},
                {'-', '#', '-', '-', '-', '-'},
                {'-', '-', '#', '#', '-', '-'},
                {'-', '#', '#', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-'},
                {'-', '-', '-', '-', '-', '-'}
            };

            fieldsArr = new char[iterationsNum+1][fieldRows][fieldCols];
  
            char[][] fieldEg = new char[1][1];
            //fieldEg = fieldEg1;
            
            fieldEg = fieldSpaceShipGlinder;
            //fieldEg = fieldBlinker;
            //fieldEg = fieldMy01;
            //fieldEg = fieldPentadecathlon;

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
        
        
        boolean findedPeriod = false; // used if endType = 1, nutraukiam, kai suradam pasikartojima
        boolean toContinue = true;
        
        while (toContinue) {
        
          
            char[][] newGenerationField = new char[fieldRows][fieldCols];
            // jei nesutampa kartos, kintamojo reiksme > 0
            int[] elementComparison = new int[iteration+1]; //eilutes dydis priklauso nuo buvusiu kartu (gen) skaiciaus. [ +1 - nes pradine iteracija = 0 ]

            int lifeElNewGen = 0; // population size of new generation
            int lifeFigureNum = 0; // 0 - still life, 1 - oscilator, 2 - spaceship
            
            // new gen.
            int sqLeft = fieldCols; // Left0
            int sqRight = 0; // Right0
            int sqTop = fieldRows; // Top0
            int sqBottom = 0; //Bottom0
            
            // old gen
            sqLRTB[iteration][0] = fieldCols; // Left0
            sqLRTB[iteration][1] = 0; // Right0
            sqLRTB[iteration][2] = fieldRows; // Top0
            sqLRTB[iteration][3] = 0; // Bottom0
            
            
            for (int rowInd = 0; rowInd < fieldsArr[iteration].length; rowInd++) {
                for (int colInd = 0; colInd < fieldsArr[iteration][rowInd].length; colInd++) {

                    int neighboursNum = 0;

                    // neighbours elements
                    for (int i = -1; i < 2; i++ ) {
                        for (int j = -1; j < 2; j++) {

                            // nepraleidziam, kai i=0 ir j=0, kur yra tikrinamas elementas
                            if (i != 0 || j != 0) {
                                
                                if ( (rowInd+i)>=0 && (rowInd+i)!=fieldsArr[iteration].length && (colInd+j)>=0 && (colInd+j)!=fieldsArr[iteration][rowInd].length  ) {
                               
                                    if (fieldsArr[iteration][rowInd+i][colInd+j] == lifeElement) {
                                        neighboursNum++;
                                    }

                                }
                            }

                        }
                    }
                    
                    if (fieldsArr[iteration][rowInd][colInd] == lifeElement) {
                        // eiluciu didejimo kryptis nukreipta zemyn. LRTB - left, right, top, bottom   
                        if (sqLRTB[iteration][0] > colInd) {sqLRTB[iteration][0] = colInd;}
                        if (sqLRTB[iteration][1] < colInd) {sqLRTB[iteration][1] = colInd;}
                        if (sqLRTB[iteration][2] > rowInd) {sqLRTB[iteration][2] = rowInd;}
                        if (sqLRTB[iteration][3] < rowInd) {sqLRTB[iteration][3] = rowInd;}
                        
                        // naujos kartos skaiciavimas
                        if (neighboursNum == 2 || neighboursNum == 3) {
                            newGenerationField[rowInd][colInd] = lifeElement;
                        } else {
                            newGenerationField[rowInd][colInd] = emptyElement;
                        }

                    } else {
                        // naujos kartos skaiciavimas
                        if (neighboursNum == 3) {
                            newGenerationField[rowInd][colInd] = lifeElement;
                        } else {
                            newGenerationField[rowInd][colInd] = emptyElement;
                        }
                    }
                    
                    // for spaceship. Figure frame. Nustatome max figuros lyginimo ribas su kita karta
                    if (newGenerationField[rowInd][colInd] == lifeElement) {
                        // eiluciu didejimo kryptis nukreipta zemyn
                        if (sqLeft > colInd) {sqLeft = colInd;}
                        if (sqRight < colInd) {sqRight = colInd;}
                        if (sqTop > rowInd) {sqTop = rowInd;}
                        if (sqBottom < rowInd) {sqBottom = rowInd;}
                        
                    }
                                        
                    // tikrame ar naujoje kartoje yra gyvybe. Tam, kad veliau ivertintume ar nesutapo tiesiog kartos be gyvybes.
                    if (newGenerationField[rowInd][colInd] == lifeElement) {
                        lifeElNewGen++;
                    }
                        
                }
            }
            
            // System.out.println("*****************");
            // new generation
            int sqDeltaCol = sqRight-sqLeft;
            int sqDeltaRow = sqBottom - sqTop; // virsutines eilutes indeksas 0
            
            // Differet generations squares comparison
            for (int it = iteration; it >= 0; it--) {
                // square corners coordinates: LRTB - left, right, top, bottom
                if ( (sqDeltaCol == sqLRTB[it][1]-sqLRTB[it][0]) && (sqDeltaRow == sqLRTB[it][3]-sqLRTB[it][2]) && lifeElNewGen > 0 ) {
                    
                    for (int rowInd = 0; rowInd <= sqDeltaRow; rowInd++) {
                        for (int colInd = 0; colInd <= sqDeltaCol; colInd++) {
                              // generation comparing
                              // jei elementai nesutampa, pazymime, t.y: +1. Reiksme priskiriame tam elemetui (t.y. jei nauja karta sutampa su 0 karta, tai prie nulinio indekso ir irasome)
                              if (fieldsArr[it][rowInd+sqLRTB[it][2]][colInd+sqLRTB[it][0]] != newGenerationField[rowInd+sqTop][colInd+sqLeft]){
                                elementComparison[it]++;
                                break;
                            }
                        }
                        if (elementComparison[it] > 0) {
                            break;
                        }
                    }
                        
                } else {
                    elementComparison[it]++;
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
                if (elementComparison[gen] == 0 && lifeElNewGen > 0) {
                    if (period == 0) {
                        period = iteration - gen;
                        System.out.print("|^| repeated gen: ");
                        
                        // different gen coordinations system positions gen(y0,x0) && gen1(y0,x0)
                        if (sqLRTB[gen][2] == sqTop && sqLRTB[gen][0] == sqLeft) {
                            // 0 - still life, 1 - oscilator, 2 - spaceship
                            if (period > 1) {lifeFigureNum = 1;}
                        } else {
                            lifeFigureNum = 2;
                        }
                        
                        if (endType == 1) {     
                            findedPeriod = true;
                        }
                    }
                    System.out.print(gen + " ");
                }
            }
            
            if (period > 0) {
                System.out.println("");
                System.out.println("|^| Life type: " + lifeFiguresTypes[lifeFigureNum]);
            }
            System.out.println("|^| Period: " + period);
            
            if (iteration == iterationsNum || findedPeriod) {
                toContinue = false;
            }
            
            
        }  // <<< while end
        

    }
    
}

