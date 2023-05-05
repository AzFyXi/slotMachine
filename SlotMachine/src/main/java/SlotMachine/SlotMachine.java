package SlotMachine;


import ressources.Config;
import User.User;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class SlotMachine {
    private Collection<Column> columns;
    private static int numberColumns;
    private static Symbol finalSymbol;

    public SlotMachine(Collection<Column> columns, int numberColumns) {
        this.columns = columns;
        this.numberColumns = numberColumns;
        this.finalSymbol = new Symbol(0);
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

    public static Symbol getFinalSymbol() {
        return finalSymbol;
    }
    public static void setFinalSymbol(Symbol finalSymbol) {
        SlotMachine.finalSymbol = finalSymbol;
    }

    public boolean startMachine(User mainUser, Collection<Column> columns) { //function to start the SlotMachine
        SlotMachineGUI gui = new SlotMachineGUI();
        Iterator<Column> iteratorColumns = columns.iterator();
        List<Column> columnList = new ArrayList<>(columns);
        Symbol finalSymbol = new Symbol(0);
        Collection<Column> columnsWithWinningSymbol = null;
        boolean isWin = false;

        while(finalSymbol != null) {
            finalSymbol = findWinningSymbol(columns); //Retrieving the winning symbol
            int numberWinningColumn = 0;

            if(finalSymbol != null) {
                this.finalSymbol = finalSymbol;
                isWin = true;
                columnsWithWinningSymbol = new ArrayList<>(); //Create an ArrayList with to store the winning columns

                //Add the 3 winning columns (with findWinningSymbol we know that the first 3 columns are winning)
                for (int i = 0; i < columnList.get(0).getPrintNumberLine() && iteratorColumns.hasNext(); i++) {
                    numberWinningColumn++;
                    columnsWithWinningSymbol.add(iteratorColumns.next());
                }

                //Search for other winning columns
                for(int i = 3; i <= columns.size(); i++)  {
                    if(iteratorColumns.hasNext()) {
                        Column nextColumn = iteratorColumns.next();
                        if(isSymbolInColumn(nextColumn, finalSymbol)) {
                            numberWinningColumn++;
                            columnsWithWinningSymbol.add(nextColumn);
                        } else {
                            break;
                        }
                    }
                }

                //System.out.println(mainUser.getCurrentRowRemaining() + "" + mainUser.getCurrentMultimultiplier());
                //Search if the winning symbol is a special symbol
                if(finalSymbol.getId() > 3) { //Not special symbol
                    calculatedMoney(mainUser, finalSymbol.getId(), numberWinningColumn);
                    gui.showWinImage(mainUser);
                } else if (finalSymbol.getId() == 2) { //Symbol Free
                    System.out.println("2 FRREE");
                    System.out.println(mainUser.getFreeAttempts());
                    gui.showFreeAttemptMenu(mainUser, SlotMachineGUI.getMainFrame());
                    break;
                } else if (finalSymbol.getId() == 1) { //Symbol Bonus
                    System.out.println("1 BONUS");
                    break;
                }

                //Replace winning Symbol
                replaceSymbol(numberWinningColumn, finalSymbol, columns);
            } else {
                if(isWin == false) {
                    gui.showLoseImage();
                }
            }
        } //Repeat as long as there is a winning symbol.
        return isWin;
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
            Symbol symbolToFirstColumn = column1.getSymbol(i);

            for (int j = startPositionSecondColumn; j < column2.getLinesNumber(); j++) {
                if (symbolToFirstColumn.equals(column2.getSymbol(j)) || column2.getSymbol(j).getId() == 1 || symbolToFirstColumn.getId() ==1) {
                    foundSymbol = symbolToFirstColumn.getId() != 1 ? symbolToFirstColumn: column2.getSymbol(j); //For the symbol Super

                    for (int k = startPositionThirdColumn; k < column3.getLinesNumber(); k++) {

                        if (foundSymbol.equals(column3.getSymbol(k)) || foundSymbol.getId() == 1) {
                            foundSymbol = foundSymbol.getId() == 1 ? column3.getSymbol(k) : foundSymbol;
                            return foundSymbol;
                        }
                    }
                }
            }
        }
        return null;
    }
    public void replaceSymbol(int numberWinningColumn, Symbol foundSymbol, Collection<Column> columns) {
        for(Column column : columns) {
            if(column.getNumberColumn() <= numberWinningColumn) {
                List<Symbol> symbolsList = new ArrayList<>(column.getSymbol()); // Create a list of all the symbols in the column
                int foundPosition = -1;
                int linesNumber = column.getLinesNumber(); // Number of elements in the column
                int startPositionFirstElementDisplayed = linesNumber - column.getPrintNumberLine();

                for (int i = startPositionFirstElementDisplayed; i < linesNumber; i++) {
                    if (symbolsList.get(i).equals(foundSymbol) || symbolsList.get(i).getId() == 1) {
                        foundPosition = i;
                        break;
                    }
                }
                if (foundPosition != -1) {
                    for (int i = foundPosition; i > 0; i--) {
                        symbolsList.set(i, symbolsList.get(i - 1));
                    }
                    column.setSymbols(symbolsList);
                }
            }
        }
    }

    public boolean isSymbolInColumn(Column column, Symbol targetSymbol) { // Check if the symbol winners find is present in the other columns
        boolean symbolFound = false;

        for (int i = column.getLinesNumber() - column.getPrintNumberLine(); i < column.getLinesNumber(); i++) {
            Symbol currentSymbol = column.getSymbol(i);

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
            case 2:
                //Free Symbol
            case 3:
                //Bonus Symbol
            case 4:
                if(numberWinningColumn == 3) {
                    moneyWin = 1000;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 2000;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 4000;
                } else {
                    moneyWin = 1000;
                }
                break;
            case 5:
                if(numberWinningColumn == 3) {
                    moneyWin = 750;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 1500;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 3000;
                } else {
                    moneyWin = 750;
                }
                break;
            case 6,7:
                if(numberWinningColumn == 3) {
                    moneyWin = 500;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 750;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 1500;
                }else {
                    moneyWin = 500;
                }
                break;
            default:
                if(numberWinningColumn == 3) {
                    moneyWin = 300;
                } else if(numberWinningColumn == 4) {
                    moneyWin = 500;
                } else if(numberWinningColumn == 5) {
                    moneyWin = 1000;
                }else {
                    moneyWin = 300;
                }
                break;
        }
        moneyWin *= multiplier;
        mainUser.setTotalEarn(moneyWin);
        moneyWin +=mainUser.getMoney();
        mainUser.setMoney(moneyWin);
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

    public static Collection<Column> createColumnsCollection(int printNumberLine, Collection<Symbol> symbols) {
        //Create the SlotMachine by default
        Collection<Column> columns = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Column column;

            if (i < 5) {
                column = new Column(generateSymbols(symbols, 30), i, 30, true, printNumberLine);
            } else {
                column = new Column(generateSymbols(symbols, 41), i, 41, true, printNumberLine);
            }

            columns.add(column);
        }

        return columns;
    }

    public static Collection<Symbol> generateSymbols(Collection<Symbol> symbols, int symbolsNumber) {
        List<Symbol> generatedSymbols = new ArrayList<>();
        Random random = new Random();
        int symbolsSize = symbols.size();

        for (int i = 0; i < symbolsNumber; i++) {
            int randomNumber = random.nextInt(symbolsSize);
            Symbol randomSymbol = symbols.stream().skip(randomNumber).findFirst().orElse(null);

            if (randomSymbol != null) {
                generatedSymbols.add(randomSymbol);
            }
        }
        return generatedSymbols;
    }

    public static Collection<Symbol> generateSymbols(Collection<Symbol> symbols, int symbolsNumber, Column column) {
        List<Symbol> generatedSymbols = new ArrayList<>();
        Random random = new Random();
        int symbolsSize = symbols.size();
        if(column.getNumberColumn() == 3) {
            List<Symbol> filteredSymbols = symbols.stream()
                    .filter(symbol -> symbol.getId() != 1)
                    .collect(Collectors.toList());

            int symbolsSizeWithoutOneSymbol = filteredSymbols.size();

            for (int i = 0; i < symbolsNumber; i++) {
                int randomNumber = random.nextInt(symbolsSizeWithoutOneSymbol);
                Symbol randomSymbol = filteredSymbols.get(randomNumber);
                generatedSymbols.add(randomSymbol);
            }
        } else if (column.getNumberColumn() == 2 || column.getNumberColumn() == 4) {
            List<Symbol> filteredSymbols = symbols.stream()
                    .filter(symbol -> symbol.getId() != 2)
                    .collect(Collectors.toList());

            int symbolsSizeWithoutOneSymbol = filteredSymbols.size();

            for (int i = 0; i < symbolsNumber; i++) {
                int randomNumber = random.nextInt(symbolsSizeWithoutOneSymbol);
                Symbol randomSymbol = filteredSymbols.get(randomNumber);
                generatedSymbols.add(randomSymbol);
            }
        } else {
            for (int i = 0; i < symbolsNumber; i++) {
                int randomNumber = random.nextInt(symbolsSize);
                Symbol randomSymbol = symbols.stream().skip(randomNumber).findFirst().orElse(null);

                if (randomSymbol != null) {
                    generatedSymbols.add(randomSymbol);
                }
            }
        }
        return generatedSymbols;
    }
    public static Collection<Symbol> getSymbolsCollection(){
        JSONObject parsedSymbols = Config.parseSymbols();
        assert parsedSymbols != null;

        return Config.createSymbolsCollection(parsedSymbols);
    }

}
