import java.util.ArrayList;

class Utils {

	public static String serializarArrayList(ArrayList<Integer> al) {
		if (al == null)
			return "";
			
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < al.size(); i++) {
			sb.append(al.get(i));
			if (i < al.size()-1)
				sb.append(", ");
		}
		return sb.toString();
	}

	public static final ArrayList<Integer[]> paresHorariosNaoPermitidos = new ArrayList<Integer>(
		{21,02}, {23,04}, {25,06}, {27,08},
		{51,32}, {53,34}, {55,36), {57,38},
		{81,62}, {83,64}, {85,66}, {87,68},
		{111,92}, {113,94}, {115,96}, {117,98},
		{141,122}, {143,124}, {145,126}, {147,128}
	);
	
}