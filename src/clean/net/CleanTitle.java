package clean.net;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CleanTitle {

	private int aDate;

	public int getaDate() {
		return aDate;
	}

	public void setaDate(int aDate) {
		this.aDate = aDate;
	}

	/**
	 * Enléve les caractères de ponctuation
	 * @param movie
	 * @return
	 */
	public String cleanPunctuation(String movie){
		//movie.replace(".", " ");
		String[] characters ={".","-","_"," -"};
		String[] emptyWord ={" "," "," ",""};
		movie=StringUtils.replaceEach(movie, characters,emptyWord);
		movie=cleanLanguage(movie);

		return movie;
	}

	/**
	 * Enlève l'extention du titre
	 * @param movie
	 * @return
	 */
	public String cleanExtension(String movie){
		if(movie.lastIndexOf(".") != -1 && movie.lastIndexOf(".") !=0){
			String ext=movie.substring(movie.lastIndexOf(".")+1);
			movie=StringUtils.replace(movie, "."+ext, "");
		}
		movie=cleanPunctuation(movie);
		return movie;
	}

	/**
	 * Parcours le titre pour enlever les mots en rapport avec le langage
	 * @param movie
	 * @return
	 */
	public String cleanLanguage(String movie){
		String[] language ={" french "," spanish "," german "," english "," swiss "," portuguese "," russian "," canadian ",
							" fr "," es "," ge "," en "," sz "," pt "," rs "," ca "};
		String chaine=movie.toLowerCase();
		int atDel;
		for (int i = 0; i < language.length; i++) {
			atDel=chaine.indexOf(language[i]);
			if (atDel>=0) {
				movie=movie.substring(0,atDel);
			}
		}
		movie=cleanWord(movie);
		return movie;
	}

	/**
	 * Enlève les caractères de séparation
	 * @param movie
	 * @return
	 */
	public String cleanWord(String movie){
		String[] language ={"{","[","(","~"};
		String[] languageInverse ={"}","]",")","~"};
		String chaine=movie.toLowerCase();
		int atDel;
		boolean passedInverse= false;

		for (int i = 0; i < language.length; i++) {
			atDel=chaine.indexOf(language[i]);
			if (atDel==0) {
				for (int j = 0; j < languageInverse.length; j++) {
					int atDelInverse;
					atDelInverse=chaine.indexOf(languageInverse[i]);
					if (atDelInverse>0 && passedInverse==false) {
						movie=movie.substring(atDelInverse+1);
						passedInverse=true;
					}
				}
			}else if(atDel >0){
				movie=movie.substring(0,atDel);
			}
		}
		movie=cleanQuality(movie);
		return movie;
	}

	/**
	 * Supprime les mots en rapport avec la qualité de la video
	 * @param movie
	 * @return
	 */
	public String cleanQuality(String movie){
		String[] language ={"vostfr"," vf "," vfq "," vfi "," vff "," vo ","dvdrip","bdrip","web-dl","webdl"," netflix "," ituneshd ","tvrip","dvdscr",
							"ppvrip","hddvd","hdrip","FASTSUB"," r5 "," ts "," cam "," hdtv ","fastsub","webrip","dvd-r",
							" fs ","720p","1080p" ,"truefrench"," ac3 "," md ",
							" ld ","xvid","dvix"," subforced "," proper "," repack "," stv ",
							" vost "};
		String chaine=movie.toLowerCase();
		int atDel;
		for (int i = 0; i < language.length; i++) {
			atDel=chaine.indexOf(language[i]);
			if (atDel>=0) {
				movie=movie.substring(0,atDel);
			}
		}

		movie=cleanDate(movie);
		return movie;
	}

	/**
	 * Retire la date du titre mais elle est conservé pour authentifier
	 *  la date du film dans le cas de plusieurs version (ex: la planête des singes -1968-2001)
	 * @param movie
	 * @return
	 */
	public String cleanDate(String movie){
		Pattern pattern = Pattern.compile("((([1]{1})([9]{1}))|(([2]{1})([0]{1})))([0-9]{2})");
		Matcher matcher = pattern.matcher(movie);

		if(matcher.find()){
			setaDate(Integer.valueOf(matcher.group()));
			int end = movie.indexOf(matcher.group());
			movie=movie.substring(0,end);
		}else{
			setaDate(0);
		}
		movie=movie.trim();
		return movie;
	}

	public String normalize(String input) {
		  return Normalizer.normalize(input, Normalizer.Form.NFD);
	}

}
