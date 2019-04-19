package ph.aranzazushrine.aranzazuph.Models;

public class Maps {
    private String image, title, desc, directions, contacts;

    public Maps() {
        this("",
                "Placeholder Data",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus imperdiet malesuada sem sit amet mollis. Morbi egestas urna pretium vulputate faucibus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "@14.7060037,121.1284896,14.5z",
                "+639451494339");
    }

    public Maps(String image, String title, String desc, String directions, String contacts) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.directions = directions;
        this.contacts = contacts;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
