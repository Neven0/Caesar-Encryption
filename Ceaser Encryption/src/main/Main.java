package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {	
		int answer;
		Scanner scanner = new Scanner(System.in);
		Scanner sentencescan = new Scanner(System.in);
		sentencescan.useDelimiter("\\n");
		ExpandedCaeser exp=new ExpandedCaeser();
		StandardCaeser std=new StandardCaeser();
		do 
		{
			answer=3;
			
			System.out.println("Please choose one of the following:\n"
					+ "1. Standard Caeser\n"
					+ "2. Expanded Caeser\n"
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
				case 1: StandardCaeser(scanner, sentencescan, std);
						break;
				case 2: ExpandedCaeser(scanner, sentencescan, exp);
						break;
				case 3: System.out.println("Goodbye.");
						break;
				default: System.out.println("Invalid input, please select one of the options.");
						break;
				}
			
		} while(answer!=3);
		
		scanner.close();
		sentencescan.close();;
	}
	
	
	public static void StandardCaeser(Scanner scanner, Scanner sentencescan, StandardCaeser std) 
	{	
	
		int answer=0;
		String sentence;
		do 
		{
			System.out.println("Select one of the options:\n"
					+ "1. Encryption\n"
					+ "2. Decryption\n"
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
			String ShiftDirection;
			int NoOfShifts;
			switch (answer) 
			{
			case 1: 
					
					do
					{
					System.out.print("Shift to left[L] or right[R]:");
					ShiftDirection=scanner.next();
					} while(!ShiftDirection.toUpperCase().equals("L") && !ShiftDirection.toUpperCase().equals("R"));
					
					System.out.print("How many shifts:");
					NoOfShifts=scanner.nextInt();
						
					System.out.print("Enter sentence:");
					sentence=sentencescan.next();
	
					System.out.println("Your cyphertext is:"+std.Encrypt(sentence, ShiftDirection, NoOfShifts));
					break;
					
			case 2: 
					
					do
					{
					System.out.print("Shift to left[L] or right[R]:");
					ShiftDirection=scanner.next();
					} while(!ShiftDirection.toUpperCase().equals("L") && !ShiftDirection.toUpperCase().equals("R"));
					
					System.out.print("How many shifts:");
					NoOfShifts=scanner.nextInt();
					
					System.out.print("Enter sentence:");
					sentence=sentencescan.next();
					
					System.out.println("Your decypted text is:"+std.Decrypt(sentence, ShiftDirection, NoOfShifts));
					break;
					
			case 3: break;
			
			default: System.out.println("Invalid input, please select one of the options.");
					break;
			}
		
		
		} while(answer!=3);
	}


	public static void ExpandedCaeser(Scanner scanner, Scanner sentencescan, ExpandedCaeser exp) 
	{
		int answer=0;
		String sentence;
		do 
		{
			System.out.println("Select one of the options:\n"
					+ "1. Encryption\n"
					+ "2. Decryption\n"
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
				long Key;
				do
				{
				System.out.print("Enter key (0 for new key):");
				Key=scanner.nextLong();
				
				if(Key==0) break;
				
				
				} while(!exp.CheckKey(Key));
				
				System.out.print("Enter word or sentence:");
				sentence=sentencescan.next();
				sentence= Key==0 ? exp.Encrypt(sentence) : exp.Encrypt(Key, sentence);
				System.out.println("Your cyphertext is:"+sentence);
				break;
					
			case 2: 
				long key;
				do
				{
				System.out.print("Enter key:");
				key=scanner.nextLong();
				} while(!exp.CheckKey(key));
				
				System.out.print("Enter word or sentence:");
				sentence=sentencescan.next();
				
				System.out.println("Your decyphered text is:"+exp.Decrypt(key, sentence));
				break;
					
			case 3: break;
			
			default: System.out.println("Invalid input, please select one of the options.");
					break;
			}
		
		
		} while(answer!=3);
	}

}
