package homework_3.functions;


public class TestMain {
    public static void main(String[] args) {
        MusicFindFactory musicFindFactory=new MusicFindFactory();
        System.out.println("测试音乐为：\t"+"Avril Lavigne - How You Remind Me.wav");
        System.out.print(musicFindFactory.findStstusOfRResult("D:/test/"+"Avril Lavigne - How You Remind Me.wav",10));


        //下面注释掉的是用来测试的方法，因为可能要debug，所以不做删除
//        System.out.print(musicFindFactory.addSongFolder("D:\\MusicDatabase",30));
//        AudioReading reader = new AudioReading("outputFiles/test.wav");
//        reader.ReadToTxt("outputFiles/audioreading/data.csv");
//        HashAccess hashAccess = new HashAccess();
//        hashAccess.AddSong("11");
//        hashAccess.AddFinger("11","88","878");
//        hashAccess.AddSongFinger(1,1);
//        hashAccess.DeleteTable("song");
//        hashAccess.FormatAdd("11","45","78","重中之重",22.55);
//        hashAccess.UpdateOffset(1,555);
//        hashAccess.DeleteSongAndFinger(1);
//        hashAccess.UpdateSong(1,"55555");
//        hashAccess.UpdateFinger(1,"54","7878","77");
//        hashAccess.PrintTableToConsole("song");
//        hashAccess.PrintTableToConsole("finger");
//        hashAccess.PrintResultSongToConsole(hashAccess.FindSong(1));
//        hashAccess.PrintTableToConsole("songFinger");
//        hashAccess.PrintResultSongToConsole(hashAccess.FindSong("55555"));
//        hashAccess.PrintResultFingerToConsole(hashAccess.findFinger(1));
//        hashAccess.DeleteDatabase(MySqlValues.GetDatabase());
//
//        HashAccessFactory hashAccessFactory=new HashAccessFactory();
//        ShazamHash shazamHash=new ShazamHash();
//        shazamHash.SetF1(123456);
//        shazamHash.SetF2(456);
//        shazamHash.SetOffset(8888);
//        shazamHash.SetDt(456);
//        shazamHash.setId(1);
//        ArrayList<ShazamHash> hashes=new ArrayList<>();
//        for(int i=0;i<20;i++)
//        hashes.add(shazamHash);
//        Fingerprint fingerprint =new Fingerprint();
//        double[] test = {};
//        fingerprint.append(fingerprint.fft(reader.GetSingleData()));
//        HashFactory hashFactory = new HashFactory(reader.GetSingleData());
//        hashAccessFactory.FormatAddToSql("test",hashFactory.getHash());
//        musicFindFactory.getHashAccess().PrintTableToConsole("song");
//        musicFindFactory.getHashAccess().PrintTableToConsole("finger");
//        musicFindFactory.getHashAccess().PrintTableToConsole("songFinger");
//        hashAccessFactory.getHashAccess().DeleteDatabase(MySqlValues.GetDatabase());
//        HashAccess hashAccess=new HashAccess();
//        hashAccess.DeleteDatabase(MySqlValues.GetDatabase());
//        HashFactory hashFactory = new HashFactory(reader.GetSingleData());
//        hashAccess.QuickWriteSql("test",hashFactory.getHash());
//        hashAccess.PrintTableToConsole("song");
//        hashAccess.PrintTableToConsole("finger");
//        hashAccess.PrintTableToConsole("songFinger");
//        HasgMtching hasgMtching =new HasgMtching(hashAccess.GetTable("song"),
//                hashAccess.GetTable("finger"),"outputFiles/test.wav");
//        ArrayList<Result> results= hasgMtching.getResult();
//        for(int i=0;i<results.size();i++){
//            System.out.println("id: "+results.get(i).getId()+"  score:  "+results.get(i).getScore()+
//                    "  path:  "+results.get(i).getName());
//        }
//        hashAccess.PrintTableToConsole("finger");
    }
}
