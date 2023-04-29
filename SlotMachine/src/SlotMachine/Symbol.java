package slotmachine;

public class Symbol {
    private int id;

    private String name;
    private String emoji;
    private Boolean alreadyWinned;

    // Constructor
    public Symbol(int id, String image) {
        this.id = id;
        this.name = name;
        this.emoji = image;
        this.alreadyWinned = false;
    }
    public Symbol() {
        this.id = -1;
        this.emoji = "";
        this.alreadyWinned = false;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getImage() {
        return emoji;
    }
    public Boolean getAlreadyWinned() {
        return alreadyWinned;
    }

    // Setters
    public void setAlreadyWinned(Boolean alreadyWinned) {
        this.alreadyWinned = alreadyWinned;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setImage(String image) {
        this.emoji = image;
    }

    //Methods

    // Check if the symbol has not already been won.
    //This avoids using the same symbol twice
    public Boolean isWinned() {
        if(!this.alreadyWinned) {
            this.alreadyWinned = true;
            return true;
        } else {
            return false;
        }
    }

    // Check if the symbol is a specific one and return the value
    // This allows you to know what action to take
    public int isSpecificSymbol() {
        if(this.id == 1) {
            return 1;
        }else if(this.id == 2) {
            return 2;
        }else if(this.id == 3) {
            return 2;
        }else{
            return -1;
        }
    }

}