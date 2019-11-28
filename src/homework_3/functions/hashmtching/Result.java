package homework_3.functions.hashmtching;

public class Result{
    private int id=0;
    private String path="";
    private double score=0;
    public int getId(){
        return  id;
    }
    public String getName(){
        return path;
    }
    public double getScore(){
        return score;
    }
    public void setId(int id){
        this.id=id;
    }
    public  void setName(String name){
        this.path=name;
    }
    public void setScore(double score){
        this.score=score;
    }
}
