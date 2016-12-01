
public class basicFunc {
	
	//set postgresql username, password, url
	protected String usr ="postgres";
	protected String pwd ="8686";
	protected String url ="jdbc:postgresql://localhost:5432/postgres";
	
	
	//set space and justified direction for String
	public static String strFormat(String str, String dir, int n){
		if(str.length() > n)
			return str;
		if(dir.equals("LEFT")){
			for(int i = str.length(); i < n; i++){
				str = str + " ";
			}
			str = str + "  ";
			return str;
		}else{
			for(int i = str.length(); i < n; i++){
				str = " " + str;
			}
			str = str + "  ";
			return str;
		}
	}
}
