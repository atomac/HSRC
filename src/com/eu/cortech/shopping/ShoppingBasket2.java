/**
 * 
 */
package com.eu.cortech.shopping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author mccorm
 *
 */
public class ShoppingBasket2
{
	private static final int NUM_MISTAKES = 2;
	
	// Establish product items
	private static final String APPLE = "apple";
	private static final String ORANGE = "orange";
	
    // Pattern for the cost.
    private static final String currPattern = "£0.00";
	
	// Hashmap for the produce and associated cost.
    static HashMap<String, Integer> hmCost; 
    
	
	/**
	 * Method to initialise the hashmap produce cost.
	 */
	static void init()
	{
	    hmCost = new HashMap<String, Integer>();  
	    
	    hmCost.put(APPLE, 60);  
	    hmCost.put(ORANGE, 25);  
	}
	
	/**
	 * Main program. User enters items (apples or oranges) in any order.
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int numApples;
		int numOranges;
		int numMistakes;
		
		boolean   bAppleOffer = false;
		boolean   bOrangeOffer = false;
		
		double dTotal; 
		
        Iterator<String> itr; 

		
		// Initialise the all list of items to be entered.
		ArrayList<String> alBasket = new ArrayList<String>();
		
		// Initialise the hashmap for the produce and associated costs.
		init();		
		
    	// Establish the input reader.
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String strItem = "";

    	try 
    	{    		
            System.out.println("Is there a special on apples (Y/N): ");
			String strAOffer = reader.readLine();
			
			// Check if Apples are on offer. If so, set flag to true.
			if (strAOffer.equalsIgnoreCase("Y"))
			{
				bAppleOffer = true;
			}
			
            System.out.println("Is there a special on oranges (Y/N): ");
	    	String strOOffer = reader.readLine();
	    	
			// Check if Orange are on offer. If so, set flag to true.
			if (strOOffer.equalsIgnoreCase("Y"))
			{
				bOrangeOffer = true;
			}
		} 
    	catch (IOException e1)
		{
			e1.printStackTrace();
		}
        
    	numMistakes = 0;
        
        /*
         * Establish and infinite loop to enter the produce. The loop is
         * terminated when anything other than "apples" or "oranges" are
         * entered.
         */
        while (true)
        {
	        System.out.println("Enter item: ");
	        
	        /*
	         *  Read the expression and set to lower case throughout.
	         */
	        try 
	        {
	        	strItem = reader.readLine();
	        	
	        	strItem = strItem.toLowerCase();
	        	
	        	// If the item entered is an apple or an orange, add to the 
	        	// shopping basket list
	        	if ((strItem.equals(APPLE)) || (strItem.equals(ORANGE)))
	        	{
	        		alBasket.add(strItem);
	        		
	            	numMistakes = 0;
	        	}
	        	// Otherwise if anything else is entered, allow configurable 
	        	// number of mistakes to be made before terminating the 
	        	// process
	        	else
	        	{
	        		numMistakes = (numMistakes + 1);
	        		
	        		// If the number of mistakes is less than that permitted
	        		// and a non blank entry has been made, request retry entry.
	        		if ((numMistakes < NUM_MISTAKES) && (StringUtils.isNotBlank(strItem)))
	        		{	        		
	        	        System.out.println("Invalid Entry. Please return or press any other key to terminate.");
	        		}
	        		// Otherwise if blank, terminate loop.
	        		else
	        		{
	        			break;
	        		}
	        	}
	        	
	        } 
	        // Catch exception associated with the "readLine" above.
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        
        }
       
        // Print out the hashmap for the produce and associates costs.
	//    printMap(hmCost);
		
        // Initialise the shopping basket total and establish the iterator for
        // the shopping basket.
        dTotal = 0.0; 
		numApples = 0;
		numOranges = 0;
        
        itr = alBasket.iterator(); 

        /*
         * Iterate over the shopping basket items. Obtain the cost of the item
         * from the hashmap produce-cost. Sum the values for the total.
         */
        while (itr.hasNext())
        {  
        	String strProduct = itr.next();
        	
        	Integer intCost = hmCost.get(strProduct);
        	
            System.out.println(" Product : " +strProduct+ " Cost: " +intCost); 
            
        	// If the item entered is an apple, increment the number of apples.
        	if (strProduct.equals(APPLE))
        	{
        		numApples = (numApples+1);        		
        		
        		intCost = specialOffer(bAppleOffer, numApples, 2, intCost );
        	}
        	
        	// If the item entered is an orange, increment the number of oranges.
        	if (strProduct.equals(ORANGE))
        	{
        		numOranges = (numOranges+1);
        		
        		// Check if oranges are on offer.
        		intCost = specialOffer(bOrangeOffer, numOranges, 3, intCost );
        	}

        	// Accumulate the total item basket cost.
            dTotal = (dTotal + (double)intCost);
        } 
        
        // Since originally integer values, divide by 100 to obtain the decimal
        // value.
        dTotal = (dTotal/100.0);
        
        // Use decimal format to obtain the cost in sterling.
        DecimalFormat myFormatter = new DecimalFormat(currPattern);
        String strOutput = myFormatter.format(dTotal);
        
        System.out.println( " Shopping Basket items: " +alBasket+ " Shopping Basket Total :" +strOutput);
	}
	
	
	/**
	 * Method to determine the discount on special offers. This uses modulo
	 * expression to return value for the remainder.
	 * 
	 * @param bOffer - flag to indicate if item is on offer.
	 * @param numItems - number of items.
	 * @param iMod - modulus.
	 * @param intCost - cost of items so far.
	 * @return - special offer adjustment.
	 */
	private static Integer specialOffer( boolean bOffer, int numItems, int iMod, Integer intCost )
	{
		Integer intReturn;
		
		intReturn = intCost;		
    	
		// Check if there is an offer.
		if (bOffer)
		{
    		// If there is an offer, check the remainder of the total, if modulo
			// iMod set the return cost to 0.      
	    	if ((numItems % iMod) == 0)
	    	{
	    		intReturn = 0;
	    	}
		}
		
    	return intReturn;
	}
	
	
	/**
	 * Sanity check for product cost hashmap.
	 * 
	 * @param mp - produce-cost hashmap.
	 */
	public static void printMap(Map<String, Integer> mp) 
	{
	    Iterator<?> it = mp.entrySet().iterator();
	    
	    // Iterate over the products purchased
	    while (it.hasNext())
	    {
	        Map.Entry<?, ?> hmePair = (Map.Entry<?, ?>)it.next();
	        System.out.println(hmePair.getKey() + " = " + hmePair.getValue());
	    }
	    
	}

}
