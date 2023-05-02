package main.java.SlotMachine;

public class Symbol {
    private int id;

    private String name;
    private String emoji;
    private Boolean alreadyWinned;

    // Constructor
    public Symbol(int id, String image, String name) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
    //Methods
    public Boolean isWinned() {
        if(!this.alreadyWinned) {
            this.alreadyWinned = true;
            return true;
        } else {
            return false;
        }
    }


}