package homework_3.functions.hashing;

import java.util.ArrayList;

public class HashFactory {
    double[] data=null;
    public HashFactory(double[] data){
        this.data=data;
    }
    public ArrayList<ShazamHash>  getHash(){
        Finger finger = new Finger();
        double[] temp=new double[Fft.WINDOW_SIZE];
        for(int i=0;i<(int)(data.length/Fft.WINDOW_SIZE);i++){
            for(int j=0;j<Fft.WINDOW_SIZE;j++){
                temp[j]=data[i*Fft.WINDOW_SIZE+j];
            }
            finger.append(Fft.fft(temp));
        }
        return finger.combineHash();
    }


}
