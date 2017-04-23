import java.util.ArrayList;
import java.util.Arrays;

class Utils {

	public String serializarArrayList(ArrayList<Integer> al) { 
		
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

	public final int[] paresHorariosNaoPermitidos = new int[]{
		21,2, 23,4, 25,6, 27, 8,
		51, 32, 53, 34, 55, 36, 57, 38,
		81, 62, 83, 64, 85, 66, 87, 68,
		111, 92, 113, 94, 115, 96, 117, 98,
		141, 122, 143, 124, 145, 126, 147, 128
	};
	
	public int indexOfHorarioParNaoPermitido(int h) {
		for (int i = 0; i < paresHorariosNaoPermitidos.length; i++)
			if (paresHorariosNaoPermitidos[i] == h)
				return i;
		return -1;
	}
	
}