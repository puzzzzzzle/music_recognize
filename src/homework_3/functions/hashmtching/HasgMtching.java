package homework_3.functions.hashmtching;


import homework_3.functions.audioreading.AudioReading;
import homework_3.functions.hashaccess.HashAccess;
import homework_3.functions.hashing.HashFactory;
import homework_3.functions.hashing.ShazamHash;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HasgMtching {
    private ResultSet song=null;
    private ResultSet finger=null;
    private ArrayList<ShazamHash> hash=null;
    private ArrayList<OffsetSub> offsetSub=new ArrayList<>();
    public HasgMtching(ResultSet song,ResultSet finger,String testPart){
        this.song=song;
        this.finger=finger;
        hash=new HashFactory(new AudioReading(testPart).GetSingleData()).getHash();
        offsetSubNum();
    }
    public ArrayList<OffsetSub> getOffsetSub(){
        return offsetSub;
    }
    private void offsetSubNum(){
        int size = hash.size();
        double miniNum=0.000001;
        int songId =0;
        int fingerId=0;
        double offset=0;
        OffsetSub temp=null;
        ShazamHash hashTemp=null;
        for(int i=0;i<size;i++){
            hashTemp=hash.get(i);
            try {
                finger.first();
                while (finger.next()){
                    if(Double.valueOf(finger.getString("f1"))-hashTemp.GetF1()<miniNum){
                        if(Double.valueOf(finger.getString("f2"))-hashTemp.GetF2()<miniNum){
                            if(Double.valueOf(finger.getString("dt"))-hashTemp.GetDt()<miniNum){
                                songId=finger.getInt("song");
                                offset=Double.valueOf(finger.getString("offset"));
                                if(offset-hashTemp.getOffset()>=0){
                                    temp =new OffsetSub();
                                    temp.setId(songId);
                                    temp.setOffsetSub(offset-hashTemp.getOffset());
                                    offsetSub.add(temp);
                                    temp=null;
                                }
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            System.out.println((i+1)+"/"+size);
        }
    }
    public ArrayList<Result> getResult(){
        HashAccess hashAccess =new HashAccess();
        ArrayList<Result> results = new ArrayList<>();
        int songNum=0;
        int N=1;
        try {
            song.first();
            while (song.next()){
                songNum=song.getInt("id");
            }

            int offsetSubSize=offsetSub.size();
            ArrayList<Double> offsetSubTemp=null;
            int max=0,k=N,temp=0;
             for(int i=0;i<songNum;i++){
                 max=0;k=N;temp=0;
                 offsetSubTemp=new ArrayList<>();
                for(int j=0;j<offsetSubSize;j++){
                    if(offsetSub.get(j).getId()==i+1){
                        offsetSubTemp.add(offsetSub.get(j).getOffsetSub());
                    }
                }
                 Collections.sort(offsetSubTemp);
                 for(int j=0;j<offsetSubTemp.size();j++){
//                     System.out.println(offsetSubTemp.get(j));
                     if(offsetSubTemp.get(j)<k){
                         temp++;
                         continue;
                     }else{
                         k+=N;
                         max=(max>temp)?max:temp;
                         temp=0;
                     }
                 }
                 Result result =  new Result();
                 result.setId(i+1);
                 result.setName(hashAccess.FindSong(i+1));
                 result.setScore(max);
                 results.add(result);
                 offsetSubTemp=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(results,new Compare());
        return results;
    }
}
class Compare implements Comparator{
    public int compare(Object obj1,Object obj2){
        Result result1=(Result) obj1;
        Result result2=(Result)obj2;
        if(result1.getScore()>result2.getScore()){
            return -1;
        }
        else if(result1.getScore()==result2.getScore()){
            return 0;
        }
        else{
            return 1;
        }

    }
}
class OffsetSub{
    private int id=0;
    private double offsetSub =0;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public double getOffsetSub(){
        return offsetSub;
    }
    public void setOffsetSub(double offsetSub){
        this.offsetSub=offsetSub;
    }
}
