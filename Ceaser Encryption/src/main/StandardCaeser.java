package main;

public final class StandardCaeser{

	private final char[] Alphabet= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
			'Z', 'a','b','c','d','e','f', 'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y'};

	StandardCaeser() {}

	private int GetArrayLength()
	{
		return Alphabet.length;
	}	
	public String Encrypt(String sentence, String direction, int moves) //Standard encryption with shifted letters
	{
		char[] ArrSentence=sentence.toCharArray();
		char[] ShiftedAlphaArray=Shift(direction, moves);
		for(int i=0; i<ArrSentence.length;i++) 
		{
			for(int j=0; j<ShiftedAlphaArray.length; j++) 
			{
				if(GetChar(j)== ArrSentence[i]) 
				{
					ArrSentence[i]=ShiftedAlphaArray[j];
					break;
				}
			}
		}
		return new String(ArrSentence);
	}	
	
	private char GetChar(int index) 
	{
		return Alphabet[index];
	}
	private char[] Shift(String direction, int moves) //Shifting letters depending on direction and number of shifts
	{
		if(moves>GetArrayLength()) 
		{
			int overflow=moves/GetArrayLength();
			moves=moves%overflow;
		}
		direction=direction.toUpperCase();
		char[] Shifted_alphabet= new char[GetArrayLength()];
		if(direction.contains("L")) 
		{
			moves=GetArrayLength()-moves;
		}
		for(int i=0; i<GetArrayLength();i++) 
		{
			if(i+moves>=GetArrayLength()) 
			{
				Shifted_alphabet[(i+moves)-GetArrayLength()]=GetChar(i);
				continue;
			}
			Shifted_alphabet[i+moves]=GetChar(i);
		}
		return Shifted_alphabet;
	}
	
	public String Decrypt(String sentence, String direction, int moves) //Standard Decryption with shifted letters
	{
		char[] ArrSentence=sentence.toCharArray();
		char[] ShiftedAlphaArray=Shift(direction, moves); 
		for(int i=0; i<ArrSentence.length;i++) 
		{
			for(int j=0; j<ShiftedAlphaArray.length; j++) 
			{
				if(ShiftedAlphaArray[j]== ArrSentence[i]) 
				{
					ArrSentence[i]=GetChar(j);
					break;
				}
			}
		}
		return new String(ArrSentence);
	}

}