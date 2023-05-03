package SlotMachine;

public class Symbol {
    private int id;

    private String name;
    private String emoji;
    private Boolean alreadyWon;

    // Constructor
    public Symbol(int id, String image, String name) {
        this.id = id;
        this.name = name;
        this.emoji = image;
        this.alreadyWon = false;
    }
    public Symbol() {
        this.id = -1;
        this.emoji = "";
        this.alreadyWon = false;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getImage() {
        return emoji;
    }
    public Boolean getAlreadyWon() {
        return alreadyWon;
    }

    // Setters
    public void setAlreadyWon(Boolean alreadyWon) {
        this.alreadyWon = alreadyWon;
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
    public Boolean isWon() {
        if(!this.alreadyWon) {
            this.alreadyWon = true;
            return true;
        } else {
            return false;
        }
    }



}