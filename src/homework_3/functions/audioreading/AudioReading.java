package homework_3.functions.audioreading;


import java.io.*;

public class AudioReading {
    WaveFileReader reader =null;
    double doubleData[][]=null;
    public  AudioReading(String fileName){
        reader=new WaveFileReader(fileName);
        DataToDouble();
    }
    private void DataToDouble(){
        int data[][]=reader.getData();
        doubleData = new double[data.length][data[0].length];
        for(int i =0;i<=data.length-1;i++){
            for(int j=0;j<=data[i].length-1;j++){
                doubleData[i][j]=data[i][j];
            }
        }
    }
    public void ReadToTxt(String filePath){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(new File(filePath)));
            for (int i=0; i < doubleData.length; ++i) {
                for(int j=0;j<doubleData[0].length;j++){
                    out.printf("%.2f,\n", doubleData[i][j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double[][] GetDoubleData(){
        return doubleData;
    }

    public  double[] GetSingleData(){
        return doubleData[0];
    }
}
