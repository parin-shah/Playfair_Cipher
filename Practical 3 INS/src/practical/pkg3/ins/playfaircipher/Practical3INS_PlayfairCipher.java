/*
Programme of playfair cipher...
NOTE: Strings in java are immutable so at line no. 175 we've to use a char-array
to replace some specific characters from the string...
Every input must be in lowercase...
*/
package practical.pkg3.ins.playfaircipher;
import java.io.*;

/**
 *
 * @author PS
 */
public class Practical3INS_PlayfairCipher 
{
    
    public String Remove_Duplication(String key)    //will remove the duplication
    {
        String noduplication = "";
        for(int i=0; i<key.length(); i++)  //will remove duplication
        {
            Boolean found = false;
            for(int j = 0; j<noduplication.length(); j++) 
            {
                if(key.charAt(i) == noduplication.charAt(j)) 
                {
                    found = true;
                    break;  //no need to iterate further
                }
            }
            if(found == false) 
            {
                noduplication = noduplication.concat(String.valueOf(key.charAt(i)));
            }
        }
        
        return noduplication;        
    } 
    
    public int Coloumn_Indexof(char x, char[][] chararray)  //will return the coloumn
    {
        int coloumn = 0;
        
        if(x == 'j')    //because we use 5*5 matrix & alphabets are 26 so i is used for i & j
        {
            x = 'i';
        }
        
        for(int i=0; i<5; i++)  //row
        {
            for(int j=0; j<5; j++)  //coloumn
            {
                if(x == chararray[i][j])
                {
                    coloumn = j;
                    break;
                }
            }
        }        
        return coloumn;        
    }
    
    public int Row_Indexof(char x, char[][] chararray)  //will return the row
    {
        int row = 0;
        
        if(x == 'j')    //because we use 5*5 matrix & alphabets are 26 so i is used for i & j
        {
            x = 'i';
        }
                
        for(int i=0; i<5; i++)  //row
        {
            for(int j=0; j<5; j++)  //coloumn
            {
                if(x == chararray[i][j])
                {
                    row = i;
                    break;
                }
            }
        }        
        return row;
    }
       
    
    public static void main(String[] args) throws IOException 
    {
        char[][] keyarray = new char[5][5]; 
        String alphabet = "abcdefghiklmnopqrstuvwxyz";
        String key = "";   //given key
        String key_noduplicate = "";    //to remove duplication
        String alphabet_XOR_key = "";   //will remove the chars which is present in both alphabet and key_noduplicate
        String plaintxt_first = "";        
        String ciphertxt = "";         
        int i, j, k = 0, coloumn_odd = 0, row_odd = 0, coloumn_even = 0, row_even = 0;
        char evenchar = 0, oddchar = 0;
        
        Practical3INS_PlayfairCipher prac3 = new Practical3INS_PlayfairCipher(); //oject of class
        DataInputStream input = new DataInputStream(System.in);  //object of DIS 
        
        System.out.println("Enter the KEY:");
        key = input.readLine();
        System.out.println("\n");
        
        key_noduplicate = prac3.Remove_Duplication(key);    //will remove redudancy from key
        
        /* module_1: The next part of code will do an XOR operation between key_noduplicate & alphabet */
        
        for(i=0; i<alphabet.length(); i++)  //"abdfghiklmpqstvwxyz" here
        {
            Boolean flag = true;
            for(j=0; j<key_noduplicate.length(); j++)
            {
                if(alphabet.charAt(i) == key_noduplicate.charAt(j))
                {
                    flag = false;
                }
            }            
            if(flag == true)
            {
                alphabet_XOR_key = alphabet_XOR_key.concat(String.valueOf(alphabet.charAt(i)));
            }
        }
        /* module_1: end */
                
        System.out.println("Enter the Plain Text");
        plaintxt_first = input.readLine();
        System.out.println("\n");
                
        plaintxt_first = plaintxt_first.replaceAll("\\s", ""); //will delete the White space        
        
        /* module_2: will insert characters to the keyarray[] */  
        
        for(i=0; i<5; i++)  //row
        {
            for(j=0; j<5; j++)  //coloumn
            {
                if(((5*i) + j) < key_noduplicate.length())   //6 here
                {
                    keyarray[i][j] = key_noduplicate.charAt((5*i) + j);   //because j's value can't be more than 4, 5 is there because we are using 5*5 matrix
                }
                
                else if(((5*i) + j) >= key_noduplicate.length())
                {
                    keyarray[i][j] = alphabet_XOR_key.charAt(k);    //k is there because we've to start from 0 in alphabet_XOR_key 
                    k++;
                }                       
            }
        }        
        /* module_2: end */ 
        
        System.out.println("MATRIX:");        
        for(i=0; i<5; i++)  //row
        {
            for(j=0; j<5; j++)  //coloumn
            {
                System.out.print("\t"+keyarray[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\n");
                
        //if total no. is odd we've to add "x" its a rule so that we can make TWO-TWO pair
        
        if(plaintxt_first.length()%2 != 0)
        {
            plaintxt_first = plaintxt_first.concat(String.valueOf("x"));    
        }
        
/*--->*/System.out.println("For odd no. of characters:\n"+plaintxt_first);
        System.out.println("\n");
        
        //one of the rule which states if in an even-odd(because it starts from 0) pair both chars are same than the odd one will become x
        char[] nn_to_nx = plaintxt_first.toCharArray();
        
        for(i=0; i<plaintxt_first.length(); i++)  
        {            
            if(i%2 == 0)    //even (because it starts from i=0)
            {
                evenchar = nn_to_nx[i];
            }
            else    //odd
            {                
                oddchar = nn_to_nx[i];
            }
            
            if(i%2 != 0)
            {
                if(evenchar == oddchar)
                {
                     nn_to_nx[i] = 'x';
                }
            }
        }        
        String plaintxt = new String(nn_to_nx);
        
/*--->*/System.out.println("For same characters in Even-Odd pair:\n"+plaintxt);
        System.out.println("\n");  

        /* module_3: encryption starts */        
        
        for(i=0; i<plaintxt.length(); i++)
        {
            if(i%2 == 0)    //even (because it starts from i=0)
            {
                row_even = prac3.Row_Indexof(plaintxt.charAt(i), keyarray);           
                coloumn_even = prac3.Coloumn_Indexof(plaintxt.charAt(i), keyarray);
            }
            else    //odd
            {                
                row_odd = prac3.Row_Indexof(plaintxt.charAt(i), keyarray);
                coloumn_odd = prac3.Coloumn_Indexof(plaintxt.charAt(i), keyarray);
            }
            
            if(i%2 != 0)    //odd (after 1 even and 1 odd no.(TWO-TWO Pair))
            {
                if(row_even != row_odd && coloumn_even != coloumn_odd)
                {    
                    ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_even][coloumn_odd])); //use of function valueof will convert char to string
                    ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_odd][coloumn_even]));
                }
                
                else if(row_even == row_odd && coloumn_even != coloumn_odd)
                {
                    if(coloumn_even == 4)   //max no. of coloumn as its 5*5 (0-4)
                    {
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_even][5 - (coloumn_even + 1)]));
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_odd][coloumn_odd + 1]));
                    }
                    else if(coloumn_odd == 4)
                    {
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_even][coloumn_even + 1]));
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_odd][5 - (coloumn_odd + 1)]));
                    }
                    else if(coloumn_even < 4 && coloumn_odd < 4)
                    {
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_even][coloumn_even + 1]));
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_odd][coloumn_odd + 1]));
                    }
                }
                
                else if(row_even != row_odd && coloumn_even == coloumn_odd)
                {
                    if(row_even == 4)   //max no. of coloumn as its 5*5 (0-4)
                    {
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[5 - (row_even + 1)][coloumn_even]));
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_odd + 1][coloumn_odd]));
                    }
                    else if(row_odd == 4)
                    {
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_even + 1][coloumn_even]));
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[5 - (row_odd + 1)][coloumn_odd]));
                    }
                    else if(row_even < 4 && row_odd < 4)
                    {
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_even + 1][coloumn_even]));
                        ciphertxt = ciphertxt.concat(String.valueOf(keyarray[row_odd + 1][coloumn_odd]));
                    }
                }
            }
        }
        /* module_3: end */ 
        
        System.out.println("Cipher-Text:\n"+ciphertxt);   
    }    
}       
             
             
               
    
