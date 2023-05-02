package main.java.SlotMachine;

import main.java.Ressources.Config;
import main.java.User.User;
import org.json.simple.JSONObject;

import java.util.*;

public class SlotMachine {
    private Collection<Column> columns;
    private static int numberColumns;

    public SlotMachine(Collection<Column> columns, int numberColumns) {
        this.columns = columns;
        this.numberColumns = numberColumns;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public void setColumns(Collection<Column> columns) {
        this.columns = columns;
    }

    public int getNumberColumns() {
        return numberColumns;
    }

    public void setNumberColumns(int numberColumns) {
        this.numberColumns = numberColumns;
    }

    public void startMachine(User mainUser, Collection<Column> columns) {

        mainUser.haveFreeAttempts();
        Iterator<Column> iteratorColumns = columns.iterator();
        List<Column> columnList = new ArrayList<>(columns);
        Symbol finalSymbol = null;
        do {
            finalSymbol = findWinningSymbol(columns); //Retrieving the winning symbol
            int numberWinningSymbol = 0;

            if(finalSymbol != null) {
                Collection<Column> columnsWithWinningSymbol = new ArrayList<>();

                //Add the 3 winning columns
                for (int i = 0; i < columnList.get(0).getPrintNumberLine() && iteratorColumns.hasNext(); i++) {
                    numberWinningSymbol++;
                    columnsWithWinningSymbol.add(iteratorColumns.next());
                }

                //Search for other winning columns
                while (iteratorColumns.hasNext() && isSymbolInColumn(iteratorColumns.next(), finalSymbol)) {
                    numberWinningSymbol++;
                    columnsWithWinningSymbol.add(iteratorColumns.next());
                }

                //Search if it is a special symbol
                if(finalSymbol.getId() >= 4) {
                    calculatedMoney(mainUser, finalSymbol.getId(), numberWinningSymbol);
                } else  if (finalSymbol.getId() == 2) {
                    findFreeSymbol(mainUser);
                } else if (finalSymbol.getId() == 3) {
                    //Bonus symbol
                }

                //Replace winning Symbol
                replaceSymbol(columnsWithWinningSymbol, finalSymbol);

            } else {
                System.out.println("Le joeur a perdu");
            }

        } while (finalSymbol != null);

    }

    public Symbol findWinningSymbol(Collection<Column> columns) {
        Iterator<Column> iterator = columns.iterator();
        Column column1 = iterator.next(); // First columns
        Column column2 = iterator.next(); // Second columns
        Column column3 = iterator.next(); // Third columns

        int startPositionFirstColumn = column1.getLinesNumber() - column1.getPrintNumberLine();
        int startPositionSecondColumn = column2.getLinesNumber() - column2.getPrintNumberLine();
        int startPositionThirdColumn = column3.getLinesNumber() - column3.getPrintNumberLine();
        Symbol foundSymbol = null;

        for (int i = startPositionFirstColumn; i < column1.getLinesNumber(); i++) {
            Symbol symbolToFirstColumn = column1.getSymbols(i);

            for (int j = startPositionSecondColumn; j < column2.getLinesNumber(); j++) {

                if (symbolToFirstColumn.equals(column2.getSymbols(j)) || column2.getSymbols(j).getId() == 1 || symbolToFirstColumn.getId() ==1) {
                    foundSymbol = symbolToFirstColumn.getId() != 1 ? symbolToFirstColumn: column2.getSymbols(j); //For the symbol Super

                    for (int k = startPositionThirdColumn; k < column3.getLinesNumber(); k++) {
                        if (foundSymbol.equals(column3.getSymbols(k))) {
                            return foundSymbol;
                        }
                    }
                }
            }

            if (foundSymbol != null) {
                break;
            }
        }
        return null;
    }


    public void replaceSymbol(Collection<Column> columns, Symbol foundSymbol) {
        for (Column column : columns) {
            List<Symbol> symbolsList = new ArrayList<>(column.getSymbols());
            int foundPosition = -1;
            int linesNumber = column.getLinesNumber();

            for (int i = linesNumber - column.getPrintNumberLine(); i < linesNumber; i++) {
                if (symbolsList.get(i).equals(foundSymbol)) {
                    foundPosition = i;
                    break;
                }
            }
            if (foundPosition != -1) {
                for (int i = foundPosition; i > 0; i--) {
                    symbolsList.set(i, symbolsList.get(i - 1));
                }
                symbolsList.set(0, null);
                column.setSymbols(symbolsList);
            }
        }
    }

    public boolean isSymbolInColumn(Column column, Symbol targetSymbol) {
        boolean symbolFound = false;

        for (int i = 0; i < column.getLinesNumber(); i++) {
            Symbol currentSymbol = column.getSymbols(i);

            if (currentSymbol.equals(targetSymbol) || currentSymbol.getId() == 1) {
                symbolFound = true;
                break;
            }
        }

        return symbolFound;
    }

    public void calculatedMoney(User mainUser, int symbolId, int numberWinningSymbol) {
        int moneyWin = 0;

        switch (symbolId) {
            case 1:
                if(numberWinningSymbol == 3) {
                    moneyWin = 1000;
                } else if(numberWinningSymbol == 4) {
                    moneyWin = 2000;
                } else if(numberWinningSymbol == 5) {
                    moneyWin = 4000;
                }
                break;
            case 2:
                if(numberWinningSymbol == 3) {
                    moneyWin = 750;
                } else if(numberWinningSymbol == 4) {
                    moneyWin = 1500;
                } else if(numberWinningSymbol == 5) {
                    moneyWin = 3000;
                }
                break;
            case 3:
            case 4:
                if(numberWinningSymbol == 3) {
                    moneyWin = 500;
                } else if(numberWinningSymbol == 4) {
                    moneyWin = 750;
                } else if(numberWinningSymbol == 5) {
                    moneyWin = 1500;
                }
                break;
            default:
                if(numberWinningSymbol == 3) {
                    moneyWin = 300;
                } else if(numberWinningSymbol == 4) {
                    moneyWin = 500;
                } else if(numberWinningSymbol == 5) {
                    moneyWin = 1000;
                }
                break;
        }
        mainUser.setMoney(mainUser.getMoney() + moneyWin);
    }

    public static Collection<Column> createColumnsCollection(int printNumberLine) {
        Collection<Column> columns = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Column column;
            if (i < 5) {
                column = new Column(i, 30, true, printNumberLine);
            } else {
                column = new Column(i, 41, true, printNumberLine);
            }

            columns.add(column);
        }

        return columns;
    }


    public void findFreeSymbol(User mainUser) {
        System.out.println("Le symbole Free est apparu, choissisez entre c'est 3 options ");
        System.out.println("1/ 15 lancers, multiplicateur de gain x 2");
        System.out.println("2/ 10 lancers, x3");
        System.out.println("3/ 5 lancers x6");
        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        switch(userInput) {
            case 1 :
                mainUser.setFreeAttempts(2, 15);
                break;
            case 2 :
                mainUser.setFreeAttempts(3, 10);
                break;
            case 3 :
                mainUser.setFreeAttempts(6, 5);
                break;

        }
    }

    public static Collection<Symbol> getSymbolsCollection(){
        JSONObject parsedSymbols = Config.parseSymbols();
        assert parsedSymbols != null;

        return Config.createSymbolsCollection(parsedSymbols);
    }

    public static Collection<Column> columnsGeneration(){
        new Column(getSymbolsCollection(), numberColumns, 3, false, 3);

        return null;
    }
}
