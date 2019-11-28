package homework_3.functions.hashing;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ShazamHash {

    private double f1;
    public double GetF1(){
        return this.f1;
    }
    public void SetF1(double f1){
        this.f1= f1;
    }

    private double f2;
    public double GetF2(){
        return this.f2;
    }
    public void SetF2(double f2){
        this.f2= f2;
    }

    private int dt;
    public double GetDt(){
        return this.dt;
    }
    public void SetDt(int dt){
        this.dt= dt;
    }

    private int offset;
    public int getOffset(){
        return this.offset;
    }
    public void SetOffset(int offset){
        this.offset=offset;
    }

    private int id;
    public  int getId(){
        return  id;
    }
    public void setId(int id){
        this.id=id;
    }
}
