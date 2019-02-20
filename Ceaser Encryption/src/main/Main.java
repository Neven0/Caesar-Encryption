package main;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;;

public class Main {
	
	//Procedural custom Caeser Encryption

	public static void main(String[] args) {
		AlphaArray.GenerateArrays();
		
		int answer;
		Scanner scanner = new Scanner(System.in);
		Scanner sentencescan = new Scanner(System.in).useDelimiter("\\n");
		do 
		{
			answer=3;
			
			System.out.println("Please choose one of the following:\n"
					+ "1. Encryption\n"
					+ "2. Decryption\n"
					+ "3. Exit");
			try
			{
			answer=scanner.nextInt();
			}
			catch(java.util.InputMismatchException e) 
			{
				System.out.println("Exception has occured because of wrong input. Program will now close.");
				System.exit(0);
			}
			catch(Exception e) 
			{
				System.out.println("Exception has occured. Program will now close.");
				System.exit(0);
			}
				switch (answer) 
				{
				case 1: EncryptionMenu(scanner, sentencescan);
						break;
				case 2: DecryptionMenu(scanner, sentencescan);
						break;
				case 3: System.out.println("Goodbye.");
						break;
				default: System.out.println("Invalid input, please select one of the options.");
						break;
				}
			
		} while(answer!=3);
		
		scanner.close();
		sentencescan.close();
	}
	
	
	public static void EncryptionMenu(Scanner scanner, Scanner sentencescan) 
	{	

		int answer=3;
		String sentence;
		do 
		{
			System.out.println("Select one of the options:\n"
					+ "1. Caeser encryption\n"
					+ "2. Expanded Caeser encryption\n"
					+ "3. Return to main menu");
			
			try
			{
			answer=scanner.nextInt();
			}
			catch(java.util.InputMismatchException e) 
			{
				System.out.println("Exception has occured because of wrong input. Program will now close.");
				System.exit(0);
			}
			catch(Exception e) 
			{
				System.out.println("Exception has occured. Program will now close.");
				System.exit(0);
			}
			
			switch (answer) 
			{
			case 1: 
					String ShiftDirection;
					do
					{
					System.out.print("Shift to left[L] or right[R]:");
					ShiftDirection=scanner.next();
					} while(!ShiftDirection.toUpperCase().equals("L") && !ShiftDirection.toUpperCase().equals("R"));
					
					System.out.print("How many shifts:");
					int NoOfShifts=scanner.nextInt();
					
					char[] ShiftedAlphaArray=Shift(ShiftDirection, NoOfShifts);
					
					System.out.print("Enter sentence:");
					sentence=sentencescan.next();
					
					sentence=STDEncryption(sentence, ShiftedAlphaArray);
					System.out.println("Your cyphertext is:"+sentence);
					break;
					
			case 2: 
					long Key;
					do
					{
					System.out.print("Enter key (0 for new key):");
					Key=scanner.nextLong();
					
					if(Key!=0) 
					{
						if(NoOfDigits(Key, false)!=12 || NoOfDigits(GetFinalSeed(Key), true)>=3) 
						{
							System.out.println("Invalid key");
						}
					}
					
					else break;
					
					} while(NoOfDigits(Key, false)!=12 || NoOfDigits(GetFinalSeed(Key), true)>=3);
					
					System.out.print("Enter word or sentence:");
					sentence=sentencescan.next();
					
					sentence=ExpandedEncryption(Key, sentence);
					System.out.println("Your cyphertext is:"+sentence);
					break;
					
			case 3: break;
			
			default: System.out.println("Invalid input, please select one of the options.");
					break;
			}
		
		
		} while(answer!=3);
	}
	
	public static void DecryptionMenu(Scanner scanner, Scanner sentencescan) 
	{		
		
		
		int answer=3;
		String sentence;
		do
		{
			System.out.println("Select one of the options:\n"
					+ "1. Caeser decryption\n"
					+ "2. Expanded Caeser decryption\n"
					+ "3. Return to main menu");
			
			try
			{
			answer=scanner.nextInt();
			}
			catch(java.util.InputMismatchException e) 
			{
				System.out.println("Exception has occured because of wrong input. Program will now close.");
			}
			catch(Exception e) 
			{
				System.out.println("Exception has occured. Program will now close.");
			} 
			
			switch(answer) 
			{
			case 1:
				String ShiftDirection;
				do
				{
				System.out.print("Shift to left[L] or right[R]:");
				ShiftDirection=scanner.next();
				} while(!ShiftDirection.toUpperCase().equals("L") && !ShiftDirection.toUpperCase().equals("R"));
				
				System.out.print("How many shifts:");
				int shift=scanner.nextInt();
				
				char[] Shifted=Shift(ShiftDirection, shift);
				
				System.out.print("Enter sentence:");
				sentence=sentencescan.next();
				
				sentence=STDDecryption(sentence, Shifted);
				
				System.out.println("Your decypted text is:"+ShiftDirection);
				
			case 2:
				long Key;
				do
				{
				System.out.print("Enter key:");
				Key=scanner.nextLong();
				
				if(NoOfDigits(Key, false)!=12 || NoOfDigits(GetFinalSeed(Key), true)>=3) 
				{
					System.out.println("Invalid key");
				}
				
				
				} while(NoOfDigits(Key, false)!=12 || NoOfDigits(GetFinalSeed(Key), true)>=3);
				
	
				System.out.print("Enter word or sentence:");
				sentence=sentencescan.next();
				
				sentence=ExpandedDecryption(Key, sentence); 
				System.out.println("Your decyphered text is:"+sentence);
				
			case 3: break;
			
			default: System.out.println("Invalid input, please select one of the options");
			}

		} while(answer!=3);
		
	}
	
	public static String ExpandedDecryption(long SeedNo, String sentence) 
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
		
		return ExpandedDecryptText(sentence, SeedNo, ShiftDirRight, FirstNoForward);
	}
	
	public static String ExpandedDecryptText(String text, long FinalSeed, boolean FirstRight, boolean FirstForward) 
	{
		long ReverseSeed=GetReverseOrder(FinalSeed);

		
		List<Long> ListofSeeds=new ArrayList<Long>();
		
		if(FirstForward==true) 
		{
			ListofSeeds=GetListofSeedNo(FinalSeed, ReverseSeed);
		}
		else 
		{
			ListofSeeds=GetListofSeedNo(ReverseSeed, FinalSeed);
		}
		char[] sentenceChar=text.toCharArray();
		
		int ListIndex=0;
		String direction;
		
		for(int i=0; i<sentenceChar.length;i++) 
		{
			if(FirstRight==true) 
			{
				direction="R";
			}
			else 
			{
				direction="L";
			}
			for(int j=0; j<AlphaArray.ArrayLenght();j++) 
			{
				
				if(sentenceChar[i]==AlphaArray.GetChar((ListofSeeds.get(ListIndex)), j, direction)) 
				{
					sentenceChar[i]=AlphaArray.GetChar(j);
					if(FirstRight==true) 
					{
						FirstRight=false;
					}
					else 
					{
						FirstRight=true;
					}
					ListIndex++;
					if(ListIndex>=ListofSeeds.size()) 
					{
						ListIndex=0;
					}
					break;
				}
			}
		}
		
		return text=new String(sentenceChar);
	}

	public static String ExpandedEncryption(long SeedNo, String sentence)
	{
		long finalSeedNo;
		boolean ShiftDirRight=false, FirstNoForward=false;
		if(SeedNo==0)
		{
			SeedNo=GetNewKey();
			System.out.println("Seed number is:"+SeedNo);
		}
		
		finalSeedNo=GetFinalSeed(SeedNo);
		
		if(GetShiftNumber(SeedNo)%2==0) 
		{
			ShiftDirRight=true;
		}
		if(GetDirectionNumber(SeedNo)%2==0) 
		{
			FirstNoForward=true;
		}
		
		sentence=ExpandedEncryptText(sentence, finalSeedNo, ShiftDirRight, FirstNoForward);
		
		return sentence; 
	}
	
	public static String ExpandedEncryptText(String sentence, long FinalSeed, boolean FirstRight, boolean FirstForward) 
	{
		
		long ReverseSeed=GetReverseOrder(FinalSeed);
		
		List<Long> ListofSeeds=new ArrayList<Long>();
		
		if(FirstForward==true) 
		{
			ListofSeeds=GetListofSeedNo(FinalSeed, ReverseSeed);
		}
		else 
		{
			ListofSeeds=GetListofSeedNo(ReverseSeed, FinalSeed);
		}
		char[] sentenceChar=sentence.toCharArray();
		int ListIndex=0;
		
		for(int i=0; i<sentenceChar.length;i++) 
		{
			for(int j=0; j<AlphaArray.ArrayLenght();j++) 
			{
				if(sentenceChar[i]==AlphaArray.GetChar(j)) 
				{
					if(FirstRight==true) 
					{
						sentenceChar[i]=AlphaArray.GetChar(ListofSeeds.get(ListIndex), j, "R");
						FirstRight=false;
					}
					else 
					{
						sentenceChar[i]=AlphaArray.GetChar(ListofSeeds.get(ListIndex), j, "L");
						FirstRight=true;
					}
					ListIndex++;
					if(ListIndex>=ListofSeeds.size()) 
					{
						ListIndex=0;
					}
					break;
				}
			}
		}
		
		return sentence=new String(sentenceChar);
	}
	
	public static long GetNewKey() 
	{
		int defaultNo, ShiftNo, AltDefNo; //ShiftNo: Alternative shift direction, AltDefNo: Reverse shift number
		long FinalSeed;
		boolean GoodSeed=false;
		do {
			defaultNo=ThreadLocalRandom.current().nextInt(100000, 999999+1);
			ShiftNo=ThreadLocalRandom.current().nextInt(100,999+1);
			AltDefNo=ThreadLocalRandom.current().nextInt(100,999+1);
			
			FinalSeed=((long)defaultNo*1000+(long)ShiftNo)*1000+(long)AltDefNo;
			if(NoOfDigits(GetFinalSeed(FinalSeed), true)<3) 
			{
				GoodSeed=true;
			}
				
		} while(GoodSeed==false);
		
		return FinalSeed;
	}	
	
	public static long GetReverseOrder(long number) 
	{
		long ReverseSeed=0;
		int Digits=NoOfDigits(number, false);
		for(int i=0;i<Digits;i++) 
		{
			ReverseSeed*=10;
			ReverseSeed+=number%10;
			number/=10;
		}
		return ReverseSeed;
	}
	
	public static List<Long> GetListofSeedNo(long FirstNo, long SecondNo)
	{
		List<Long> ListOfSeedNo= new ArrayList<Long>();
		long temp=FirstNo;
		int Digits=NoOfDigits(temp, false);
		for(int i=0; i<Digits; i++) 
		{	
			if(FirstNo==0) 
			{
				ListOfSeedNo.add((long)0);
				break;
			}
			
			for(int j=0; j<NoOfDigits(FirstNo, false); j++) 
			{
				ListOfSeedNo.add(FirstNo/(long)Math.pow(10, NoOfDigits(FirstNo, false)-j-1));
			}
		FirstNo%=Math.pow(10, NoOfDigits(FirstNo, false)-1); 
			
		}
		
		for(int i=0; i<Digits; i++)
		{	
			if(SecondNo==0) 
			{
				ListOfSeedNo.add((long)0);
				break;
			}
			
			for(int j=0; j<NoOfDigits(SecondNo, false); j++) 
			{
				ListOfSeedNo.add(SecondNo/(long)Math.pow(10, NoOfDigits(SecondNo, false)-j-1));
			}
			SecondNo%=Math.pow(10, NoOfDigits(SecondNo, false)-1); 
			
		}
		return ListOfSeedNo;
	}

	public static long GetFinalSeed(long Seed) 
	{
		return FinalSeedNo(Math.toIntExact(Seed/1000000), GetShiftNumber(Seed), GetDirectionNumber(Seed));
	}
	
	public static int GetShiftNumber(long Seed) 
	{
		return Math.toIntExact((Seed%1000000)/1000);
	}
	
	public static int GetDirectionNumber(long Seed)
	{
		return Math.toIntExact(Seed%1000);
	}
	
	
	public static long FinalSeedNo(int defaultNumber, int ShiftNumber, int AltDirection) 
	{

		return  (long)defaultNumber*((long)FinalNoChange(ShiftNumber)*(long)FinalNoChange(AltDirection));
	}
	
	public static int NoOfDigits(long number, boolean CheckZero) 
	{
		int digits=0;
		if(CheckZero==true)
		{
			while (number > 0) 
			{
			    if(number%10==0) 
			    {
				    digits++;
			    }
			    number = number / 10;
	
			}
		}
		else 
		{
			while (number > 0) 
			{
				    digits++;
			    number = number / 10;
			}
		}
		return digits;
	}
	
	public static int FinalNoChange(int number) 
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
	
	public static String STDEncryption(String str, char[] Alpha) //Standard encryption with shifted letters
	{
		//str=str.toUpperCase();
		char[] sentence=str.toCharArray();
		for(int i=0; i<sentence.length;i++) 
		{
			for(int j=0; j<Alpha.length; j++) 
			{
				if(AlphaArray.GetChar(j)== sentence[i]) 
				{
					sentence[i]=Alpha[j];
					break;
				}
			}
		}
		return str=new String(sentence);
	}	
	
	public static String STDDecryption(String str, char[] Alpha) //Standard Decryption with shifted letters
	{
		//str=str.toUpperCase();
		char[] sentence=str.toCharArray();
		for(int i=0; i<sentence.length;i++) 
		{
			for(int j=0; j<Alpha.length; j++) 
			{
				if(Alpha[j]== sentence[i]) 
				{
					sentence[i]=AlphaArray.GetChar(j);
					break;
				}
			}
		}
		return str=new String(sentence);
	}
	
	public static char[] Shift(String direction, int moves) //Shifting letters depending on direction and number of shifts
	{
		if(moves>AlphaArray.ArrayLenght()) 
		{
			int overflow=moves/AlphaArray.ArrayLenght();
			moves=moves%overflow;
		}
		direction=direction.toUpperCase();
		char[] Shifted_alphabet= new char[AlphaArray.ArrayLenght()];
		if(direction.contains("L")) 
		{
			moves=AlphaArray.ArrayLenght()-moves;
		}
		for(int i=0; i<AlphaArray.ArrayLenght();i++) 
		{
			if(i+moves>=AlphaArray.ArrayLenght()) 
			{
				Shifted_alphabet[(i+moves)-AlphaArray.ArrayLenght()]=AlphaArray.GetChar(i);
				continue;
			}
			Shifted_alphabet[i+moves]=AlphaArray.GetChar(i);
		}
		return Shifted_alphabet;
	}

}
