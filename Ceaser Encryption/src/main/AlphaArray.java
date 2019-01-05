package main;

import java.util.ArrayList;
import java.util.List;

public class AlphaArray {
	private static char[] Alphabet= {' ','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
			'Z', 'a','b','c','d','e','f', 'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','1','2','3','4','5',
			'6','7','8','9','0',',','.',':','"','/','*','-','+','_',';','\'','(',')','%','!','#','$','&','/','=','?','|','€','@','{','}',
			'<','>','[',']'};
	private static List<char[]> ShiftedArraysList = new ArrayList<char[]>();
	
	public static char GetChar(int index) 
	{
		return Alphabet[index];
	}
	
	public static int ArrayLenght() 
	{
		return Alphabet.length;
	}
	
	public static void GenerateArrays() 
	{
		ShiftedArraysList.clear();
		ShiftedArraysList.add(Alphabet);
		int Shift=1;
		while(ShiftedArraysList.size()<Alphabet.length) 
		{
			char[] CharArray=new char[Alphabet.length];
			
			for(int i=0; i<Alphabet.length; i++) 
			{
				if(Shift+i<Alphabet.length)
				{
					CharArray[i]=Alphabet[Shift+i];
				}
				else 
				{
					CharArray[i]=Alphabet[(Shift+i)-Alphabet.length];
				}
			}
			Shift++;
			ShiftedArraysList.add(CharArray);
		}
	}
	
	public static char GetChar(long shift, int index, String direction ) 
	{
		if(shift>=ShiftedArraysList.size()) 
		{
			long overflow=shift/(long)ShiftedArraysList.size();
			shift=shift%overflow;
		}
		if(direction.contains("L")) 
		{
			shift=ShiftedArraysList.size()-1-shift;
		}
		
		return ShiftedArraysList.get((int)shift)[index];
	}
}
