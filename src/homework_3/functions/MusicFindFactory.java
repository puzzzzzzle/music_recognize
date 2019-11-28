package homework_3.functions;

import homework_3.functions.audioreading.AudioReading;
import homework_3.functions.hashaccess.HashAccess;
import homework_3.functions.hashing.HashFactory;
import homework_3.functions.hashmtching.HasgMtching;
import homework_3.functions.hashmtching.Result;

import java.io.File;
import java.util.ArrayList;

public class MusicFindFactory {
    HashAccess hashAccess=new HashAccess();

    public HashAccess getHashAccess(){
        return hashAccess;
    }

    public boolean addSong(String name){
        AudioReading reader = new AudioReading(name);
        HashFactory hashFactory =new HashFactory(reader.GetSingleData());
        hashAccess.QuickWriteSql(name,hashFactory.getHash());
        return true;
    }

    public String addSongFolder(String path,int quantity){
        File file = new File(path);
        String status="";
        int quantityTemp=0;
        if (!file.isDirectory()) {
            status ="不是文件夹!\n";
            return status;
        }
        else {
            File readfile=null;
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                readfile = new File(path + "/" + filelist[i]);
                if (!readfile.isDirectory()) {
                    if(filelist[i].endsWith("wav")||filelist[i].endsWith("WAV")){
                        addSong(readfile.getAbsolutePath());
                        quantityTemp++;
                        readfile=null;
                        System.out.printf("已添加%d首歌\n",quantityTemp);
                        if(quantityTemp>=quantity){
                            status+="已添加完"+quantity+"首歌\n";
                            return status;
                        }
                    }
                } else if (readfile.isDirectory()) {
                    status+="易忽略子文件夹"+readfile.getName()+"\n";
                    readfile=null;
                }
            }
        }
        status+="文件夹下不够数量，已添加"+quantityTemp+"首歌";
        return status;
    }

    public ArrayList<Result> getResult(String path){
        HasgMtching hasgMtching =new HasgMtching(hashAccess.GetTable("song"),
                hashAccess.GetTable("finger"),path);
        return hasgMtching.getResult();
    }

    public String findStstusOfRResult(String path,int quanlity){
        ArrayList<Result> getResult=getResult(path);
        String result="";
        for(int i=0;i<quanlity;i++){
            result+="id:"+getResult.get(i).getId()+"\tscore:"+getResult.get(i).getScore()+
                    "\tpath:"+getResult.get(i).getName()+"\n";
        }
        return result;
    }
}
