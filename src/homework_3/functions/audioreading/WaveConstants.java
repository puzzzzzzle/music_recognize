package homework_3.functions.audioreading;

/**
 * 设置wav文件各个字段长度的静态变量。
 * 由于数字为长度为2或4，所以可能用不到这些变量。
 * 但为了完整性也再次进行定义。
 */
public final class WaveConstants {
	static public int LENCHUNKDESCRIPTOR = 4;
	static public int LENCHUNKSIZE = 4;
	static public int LENWAVEFLAG = 4;
	static public int LENFMTSUBCHUNK = 4;
	static public int LENSUBCHUNK1SIZE = 4;
	static public int LENAUDIOFORMAT = 2;
	static public int LENNUMCHANNELS = 2;
	static public int LENSAMPLERATE = 2;
	static public int LENBYTERATE = 4;
	static public int LENBLOCKLING = 2;
	static public int LENBITSPERSAMPLE = 2;
	static public int LENDATASUBCHUNK = 4;
	static public int LENSUBCHUNK2SIZE = 4;
	static  public int EXTRAPARAMS=2;


	public static String CHUNKDESCRIPTOR = "RIFF";
	public static String WAVEFLAG = "WAVE";
	public static String FMTSUBCHUNK = "fmt ";
	public static String DATASUBCHUNK = "data";
}
