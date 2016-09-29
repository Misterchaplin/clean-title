package clean.net;

public class App {

	public static void main(String[] args) {
		CleanTitle clt = new CleanTitle();
		String data = clt.cleanExtension("[ www.CpasBien.cm ] DCs.Legends.of.Tomorrow.S01E07.FASTSUB.VOSTFR.HDTV.XviD-SDTEAM");
		System.out.println(data);
	}

}
