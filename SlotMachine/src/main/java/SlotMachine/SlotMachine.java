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

    public void startMachine(User mainUser, Collection<Column> columns) { //function to start the SlotMachine

        mainUser.haveFreeAttempts(); //Checking if the user has free attempts
        Iterator<Column> iteratorColumns = columns.iterator();
        List<Column> columnList = new ArrayList<>(columns);
        Symbol finalSymbol = null;
        do {
            finalSymbol = findWinningSymbol(columns); //Retrieving the winning symbol
            int numberWinningColumn = 0;

            if(finalSymbol != null) {
                Collection<Column> columnsWithWinningSymbol = new ArrayList<>(); //Create an ArrayList with to store the winning columns

                //Add the 3 winning columns (with findWinningSymbol we know that the first 3 columns are winning)
                for (int i = 0; i < columnList.get(0).getPrintNumberLine() && iteratorColumns.hasNext(); i++) {
                    numberWinningColumn++;
                    columnsWithWinningSymbol.add(iteratorColumns.next());
                }

                //Search for other winning columns
                while (iteratorColumns.hasNext() && isSymbolInColumn(iteratorColumns.next(), finalSymbol)) {
                    numberWinningColumn++;
                    columnsWithWinningSymbol.add(iteratorColumns.next());
                }

                //Search if the winning symbol is a special symbol
                if(finalSymbol.getId() >= 4) { //Not special symbol
                    calculatedMoney(mainUser, finalSymbol.getId(), numberWinningColumn);
                } else  if (finalSymbol.getId() == 2) { //Symbol Free
                    findFreeSymbol(mainUser);
                } else if (finalSymbol.getId() == 3) { //Symbol Bonus

                }

                //Replace winning Symbol
                replaceSymbol(columnsWithWinningSymbol, finalSymbol);

            } else {
                System.out.println("Le joeur a perdu");
            }

        } while (finalSymbol != null); //Repeat as long as there is a winning symbol.

    }

    public Symbol findWinningSymbol(Collection<Column> columns) { //Search the first three columns to find the winning symbol
        Iterator<Column> iterator = columns.iterator();
        Column column1 = iterator.next(); // First columns
        Column column2 = iterator.next(); // Second columns
        Column column3 = iterator.next(); // Third columns

        // Start the position of the first element displayed
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
        }
        return null;
    }


    public void replaceSymbol(Collection<Column> columns, Symbol foundSymbol) { //Replaces the winning symbol in all columns
        for (Column column : columns) {
            List<Symbol> symbolsList = new ArrayList<>(column.getSymbols()); // Create a list of all the symbols in the column
            int foundPosition = -1;
            int linesNumber = column.getLinesNumber(); // Number of elements in the column
            int startPositionFirstElementDisplayed = linesNumber - column.getPrintNumberLine();

            for (int i = startPositionFirstElementDisplayed; i < linesNumber; i++) {
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

    public boolean isSymbolInColumn(Column column, Symbol targetSymbol) { // Check if the symbol winners find is present in the other columns
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

    public void calculatedMoney(User mainUser, int symbolId, int numberWinningColumn) { //Calculates the user's gain
        int moneyWin = 0;
        int multiplier = 1;
        int moneyBet = mainUser.getMoneyBet();

        if(moneyBet == 4000) multiplier = 2;
        else if(moneyBet == 6000) multiplier = 3;
        else if(moneyBet == 10000) multiplier = 5;

        switch (symbolId) {
            case 1:
                if(numberWinningColumn == 3) {
                    moneyWin = 1000;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 2000;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 4000;
                }
                break;
            case 2:
                if(numberWinningColumn == 3) {
                    moneyWin = 750;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 1500;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 3000;
                }
                break;
            case 3:
            case 4:
                if(numberWinningColumn == 3) {
                    moneyWin = 500;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 750;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 1500;
                }
                break;
            default:
                if(numberWinningColumn == 3) {
                    moneyWin = 300;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 500;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 1000;
                }
                break;
        }

        moneyWin *= multiplier;
        mainUser.setMoney(mainUser.getMoney() + moneyWin);
    }

    public void findFreeSymbol(User mainUser) { //Manage the appearance of the Symbol Free
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

    public static Collection<Column> createColumnsCollection(int printNumberLine) { //Create the SlotMachine by default
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
