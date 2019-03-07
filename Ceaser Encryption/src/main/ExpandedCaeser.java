package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class ExpandedCaeser {
	
	private final char[] Alphabet= {' ','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
			'Z', 'a','b','c','d','e','f', 'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','1','2','3','4','5',
			'6','7','8','9','0',',','.',':','"','/','*','-','+','_',';','\'','(',')','%','!','#','$','&','/','=','?','|','€','@','{','}',
			'<','>','[',']'};
	
	private List<char[]> ShiftedArraysList = new ArrayList<char[]>();

	ExpandedCaeser()
	{
		GenerateArrays();
	}
	
	
	private void GenerateArrays() //Generating all possible shifted arrays
	{
		ShiftedArraysList.add(Alphabet);
		int Shift=1;
		while(ShiftedArraysList.size()<Alphabet.length) 
		{
			char[] CharArray=new char[Alphabet.length];
			
			for(int i=0; i<Alphabet.length; i++) 
			{
				/*if(Shift+i<Alphabet.length)
				{
					CharArray[i]=Alphabet[Shift+i];
				}
				else 
				{
					CharArray[i]=Alphabet[(Shift+i)-Alphabet.length];
				}*/
				CharArray[i]=Shift+i<Alphabet.length ? Alphabet[Shift+i] : Alphabet[(Shift+i)-Alphabet.length];
			}
			Shift++;
			ShiftedArraysList.add(CharArray);
		}
	}
	
	private int GetArrayLength() 
	{
		return Alphabet.length;
	}
	
	private char GetChar(long shift, int index, boolean direction) //Returns character of specific shifted array
	{
		if(shift>=ShiftedArraysList.size()) 
		{
			long overflow=shift/(long)ShiftedArraysList.size();
			shift=shift%overflow;
		}
		if(!direction) 
		{
			shift=ShiftedArraysList.size()-1-shift;
		}
		
		return ShiftedArraysList.get((int)shift)[index];
	}
	
	private long GetNewKey() //Generates new and correct 12 digit key
	{
		int defaultNo, ShiftNo, AltDefNo; //ShiftNo: Alternative shift direction, AltDefNo: Reverse shift number
		long FinalSeed;
		boolean GoodSeed=false;
		do {
			defaultNo=ThreadLocalRandom.current().nextInt(100000, 999999+1);
			ShiftNo=ThreadLocalRandom.current().nextInt(100,999+1);
			AltDefNo=ThreadLocalRandom.current().nextInt(100,999+1);
			
			FinalSeed=((long)defaultNo*1000+(long)ShiftNo)*1000+(long)AltDefNo;
			if(NoOfZero(GetFinalSeed(FinalSeed))<3) 
			{
				GoodSeed=true;
			}
				
		} while(GoodSeed==false);
		
		return FinalSeed;
	}	
	
	private long GetFinalSeed(long Seed) //Returns seed number from key
	{
		return FinalSeedNo(Math.toIntExact(Seed/1000000), GetShiftNumber(Seed), GetDirectionNumber(Seed));
	}
	
	private int GetShiftNumber(long Seed) //Returns numbers that determine shift direction
	{
		return Math.toIntExact((Seed%1000000)/1000);
	}
	
	private int GetDirectionNumber(long Seed)	//Returns number that determine seed number direction
	{
		return Math.toIntExact(Seed%1000);
	}
	
	private long FinalSeedNo(int defaultNumber, int ShiftNumber, int AltDirection) //Returns seed after final calculations
	{

		return  (long)defaultNumber*((long)FinalNoChange(ShiftNumber)*(long)FinalNoChange(AltDirection));
	}

	private int NoOfDigits(long number) // Returns number of digits or 0 in a number
	{
		int digits=0;
		while (number > 0) 
		{
			digits++;
			number = number / 10;
		}
		return digits;
	}
	
	private int NoOfZero(long number) 
	{
		int digits=0;
		while (number > 0) 
		{
		    if(number%10==0) 
		    {
			    digits++;
		    }
		    number = number / 10;

		}
		return digits;
	}
	
	private int FinalNoChange(int number) //Returns the final change of numbers before calculation
	{
		if(number>=500) 
		{
			number=number-(number/100);
		}
		else 
		{
			number=number+(number/100);
		}
		return number;
	}
	
	public String Encrypt(long SeedNo, String sentence) //Text encryption with given key
	{
		long finalSeedNo;
		boolean ShiftDirRight=false, FirstNoForward=false;
		
		finalSeedNo=GetFinalSeed(SeedNo);
		
		if(GetShiftNumber(SeedNo)%2==0) 
		{
			ShiftDirRight=true;
		}
		if(GetDirectionNumber(SeedNo)%2==0) 
		{
			FirstNoForward=true;
		}
			
		return Encryption(sentence, finalSeedNo, ShiftDirRight, FirstNoForward);
	}

	public String Encrypt(String sentence) // Text encryption with newly generated key
	{
		long SeedNo, finalSeedNo;
		boolean ShiftDirRight=false, FirstNoForward=false;
		
		SeedNo=GetNewKey();
		System.out.println("Your key is:"+SeedNo);
		
		finalSeedNo=GetFinalSeed(SeedNo);
		
		if(GetShiftNumber(SeedNo)%2==0) 
		{
			ShiftDirRight=true;
		}
		if(GetDirectionNumber(SeedNo)%2==0) 
		{
			FirstNoForward=true;
		}
			
		return Encryption(sentence, finalSeedNo, ShiftDirRight, FirstNoForward);
	}
	
	private String Encryption(String sentence, long FinalSeed, boolean FirstRight, boolean FirstForward) //Encryption of string
	{
		
		long ReverseSeed=GetReverseOrder(FinalSeed);
		
		List<Long> ListofSeeds=new ArrayList<Long>();
		
		ListofSeeds=FirstForward==true ? GetListofSeedNo(FinalSeed, ReverseSeed) : GetListofSeedNo(ReverseSeed, FinalSeed);
		
		char[] sentenceChar=sentence.toCharArray();
		int ListIndex=0;
		
		for(int i=0; i<sentenceChar.length;i++) 
		{
			for(int j=0; j<GetArrayLength();j++) 
			{
				if(sentenceChar[i]==GetChar(j)) 
				{
					sentenceChar[i]=GetChar(ListofSeeds.get(ListIndex), j, FirstRight);
					
					FirstRight=FirstRight ? false : true;
					
					ListIndex++;
					if(ListIndex>=ListofSeeds.size()) 
					{
						ListIndex=0;
					}
					break;
				}
			}
		}
		
		return new String(sentenceChar);
	}
	
	private  List<Long> GetListofSeedNo(long FirstNo, long SecondNo) //Returns the list of seed numbers used as array shifts
	{
		List<Long> ListOfSeedNo= new ArrayList<Long>();
		long temp=FirstNo;
		int Digits=NoOfDigits(temp);
		for(int i=0; i<Digits; i++) 
		{	
			if(FirstNo==0) 
			{
				ListOfSeedNo.add((long)0);
				break;
			}
			
			for(int j=0; j<NoOfDigits(FirstNo); j++) 
			{
				ListOfSeedNo.add(FirstNo/(long)Math.pow(10, NoOfDigits(FirstNo)-j-1));
			}
		FirstNo%=Math.pow(10, NoOfDigits(FirstNo)-1); 
			
		}
		
		for(int i=0; i<Digits; i++)
		{	
			if(SecondNo==0) 
			{
				ListOfSeedNo.add((long)0);
				break;
			}
			
			for(int j=0; j<NoOfDigits(SecondNo); j++) 
			{
				ListOfSeedNo.add(SecondNo/(long)Math.pow(10, NoOfDigits(SecondNo)-j-1));
			}
			SecondNo%=Math.pow(10, NoOfDigits(SecondNo)-1); 
			
		}
		return ListOfSeedNo;
	}
	
	private long GetReverseOrder(long number) //Reverse the order of numbers
	{
		long ReverseSeed=0;
		int Digits=NoOfDigits(number);
		for(int i=0;i<Digits;i++) 
		{
			ReverseSeed*=10;
			ReverseSeed+=number%10;
			number/=10;
		}
		return ReverseSeed;
	}
	
	public boolean CheckKey(long Key) //Check if the given key meets the criteria
	{
		if(NoOfDigits(Key)!=12 || NoOfZero(GetFinalSeed(Key))>=3) 
		{
			System.out.println("Invalid key");
			return false;
		}
		else return true;
	}
	
	public String Decrypt(long SeedNo, String sentence) 
	{
		boolean ShiftDirRight=false, FirstNoForward=false;
		
		if(GetShiftNumber(SeedNo)%2==0) 
		{
			ShiftDirRight=true;
		}
		if(GetDirectionNumber(SeedNo)%2==0) 
		{
			FirstNoForward=true;
		}
		SeedNo=GetFinalSeed(SeedNo);
		
		return Decryption(sentence, SeedNo, ShiftDirRight, FirstNoForward);
	}
	
	private String Decryption(String text, long FinalSeed, boolean FirstRight, boolean FirstForward) //Decrypting text
	{
		long ReverseSeed=GetReverseOrder(FinalSeed);

		
		List<Long> ListofSeeds=new ArrayList<Long>();
		
		ListofSeeds=FirstForward==true ? GetListofSeedNo(FinalSeed, ReverseSeed) : GetListofSeedNo(ReverseSeed, FinalSeed);
		
		char[] sentenceChar=text.toCharArray();
		
		int ListIndex=0;
		
		for(int i=0; i<sentenceChar.length;i++) 
		{
			for(int j=0; j<GetArrayLength();j++) 
			{
				
				if(sentenceChar[i]==GetChar((ListofSeeds.get(ListIndex)), j, FirstRight)) 
				{
					sentenceChar[i]=GetChar(j);
					
					FirstRight=FirstRight ? false : true;

					ListIndex++;
					if(ListIndex>=ListofSeeds.size()) 
					{
						ListIndex=0;
					}
					break;
				}
			}
		}
		
		return new String(sentenceChar);
	}
	
	private char GetChar(int index) //Return character from Alphabet array
	{
		return Alphabet[index];
	}
}
