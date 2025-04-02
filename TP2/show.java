import java.util.Date;

public class show {
    private String showId;    
    private String type;
    private String title;
    private String diretor;
    private String[] cast;
    private String contry;
    private Date date_added;
    private int release_year;
    private String duration;
    private String rating;
    private String[] listed_in;

    public show(String showId, String type, String title, String diretor, String[] cast, String contry, Date date_added, int release_year, String rating, String duration, String[] lusted_in){
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.diretor = diretor;
        this.cast = cast;
        this.contry = contry;
        this.date_added = date_added;
        this.release_year = release_year;
        this.rating = rating;
        this. duration = duration;
        this.listed_in = listed_in;
    }
    public show(){
        this.showId = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.diretor =package Tp2; "NaN";
        this.cast = new String[]{"NaN"};
        this.contry = "NaN";
        this.date_added = null;
        this.release_year = -1;
        this.rating = "NaN";
        this. duration = "NaN";
        this.listed_in = new String[]{"NaN"};

    }

    public String getShowId(){
        return showId;
    }
    public void setShowId(String showId){
        this.showId = showId;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDiretor(){
        return diretor;
    }
    public void setDiretor(String diretor){
        this.diretor = diretor;
    }
    public String[] getCast(){
        return cast;
    }
    public void setCast(String[] cast){
        this.cast = cast;
    }
    public String getContry(){
        return contry;
    }
    public void setContry(String contry){
        this.contry = contry;
    }
    public Date getDate_added(){
        return date_added;
    }
    public void setDate_added(Date date_added){
        this.date_added = date_added;
    }
    public int getRelease_year(){
        return release_year;
    }
    public void seteRelease_year(int release_year){
        this.release_year = release_year;
    }
    public String getRating(){
        return rating;
    }
    public void seteRating(String rating){
        this.rating = rating;
    }
    public String getDuration(){
        return duration;
    }
    public void setDuration(String duration){
        this.duration = duration;
    }
    public String[] getListed_in(){
        return listed_in;
    }
    public void setListed_in(String[] listed_in){
        this.listed_in = listed_in;
    }
    
    public show clone(){
        show cloneShow = new show();
        cloneShow.showId = this.showId; 
        cloneShow.type = this.type;
        cloneShow.title = this.title;
        cloneShow.diretor = this.diretor;
        cloneShow.cast = this.cast;
        cloneShow.contry = this.contry;
        cloneShow.date_added = this.date_added;
        cloneShow.release_year = this.release_year;
        cloneShow.rating = this.rating;
        cloneShow.duration = this.duration;
        cloneShow.listed_in = this.listed_in;
        return cloneShow;
    }
    public void print(){
        System.out.println(this.showId + "##" + this.type + "##" + this.title + "##" + this.diretor + "##"
        + this.cast + "##" + this.contry + "##" + this.date_added + "##" + this.release_year + "##" + this.rating + "##"+ this.duration + "##"
        + this.listed_in);
    }
    public void ler(String imput){
        String[] parts = imput.split(" ## ");
        this.showId = parts[0];
        this.type = parts[1];
        this.title = parts[2];
        this.diretor = parts[3];
        this.cast = parts[4].split (", ");
        this.contry = parts[5];
        this.date_added = new Date();
        this.release_year = Integer.parseInt(parts[7]);
        this.rating = parts[8];
        this.duration = parts[9];
        this.listed_in = parts[10].split(", ");

    }
    
}