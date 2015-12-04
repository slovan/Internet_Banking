package com.vpbank.services;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.vpbank.models.Account;
import com.vpbank.models.Client;

/**
 * Session Bean implementation class AccountService
 */
@Stateless
@LocalBean
public class AccountService {
	
	@PersistenceContext(unitName = "VPBank")
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public AccountService() {
        // TODO Auto-generated constructor stub
    }
    
    public void addAccount(Account acc) {
    	em.persist(acc);
    }
    
    // generate individual account number (basic)
    public long genAccountNumberBasic(Client c) {
    	int firstNameLength = c.getFirstName().length(); //take first name length
    	int lastNameLength = c.getLastName().length(); // take last name length
    	int id = c.getId(); // take id number
    	long birthdayNum = c.getDob().getTime(); // take date of birth in number
    	long todayNum = new Date().getTime(); // take current date in number
    	
    	long accountNumberBasic = ((todayNum - birthdayNum)/id)%10000000000L; // initialization
    	accountNumberBasic = (accountNumberBasic*firstNameLength*lastNameLength)%10000000000L;
    	
    	// According to the National Bank of Slovakia, to validate the basic part 
    	// of the account number, the digits are multiplied by 
    	// their respective weights (6,3,7,9,10,5,8,4,2,1), added up and divided by 11. 
    	// The remainder must be zero.
    	
    	byte[] accountNumArray = new byte[10]; // because 10 digits of account
    	accountNumArray[0] = (byte)(accountNumberBasic / 1000000000); // get 1st digit from the number
    	for(int i = 1; i < accountNumArray.length; i++) {
    		// get 2nd, 3rd, ...., 10th digits from the number
    		accountNumArray[i] = (byte)((accountNumberBasic - accountNumArray[i-1]*Math.pow(10, (10-i)))/Math.pow(10, (10-i-1)));
    	}
    	byte[] weights = {6,3,7,9,10,5,8,4,2,1};
    	
    	// check remainder
    	int remainder = 0;
    	for(int i = 0; i < accountNumArray.length; i++) {
    		remainder += accountNumArray[i]*weights[i];
    	}
    	remainder %= 11;
    	if ((remainder == 0)&&(accountNumberBasic != 0))
    		return accountNumberBasic;
    	else {
    		// we have to get reminder = 0, use loop for this
    		do {
    			switch (remainder){
    				// increase with 10, because of value of weight
    				case 1: accountNumArray[4] = (byte)(++accountNumArray[4]%10); break;
    				case 2: accountNumArray[3] = (byte)(++accountNumArray[3]%10); break;// increase with 9
    				case 3: accountNumArray[6] = (byte)(++accountNumArray[6]%10); break;// increase with 8
    				case 4: accountNumArray[2] = (byte)(++accountNumArray[2]%10); break;// increase with 7
    				case 5: accountNumArray[0] = (byte)(++accountNumArray[0]%10); break;// increase with 6
    				case 6: accountNumArray[5] = (byte)(++accountNumArray[5]%10); break;// increase with 5
    				case 7: accountNumArray[7] = (byte)(++accountNumArray[7]%10); break;// increase with 4
    				case 8: accountNumArray[1] = (byte)(++accountNumArray[1]%10); break;// increase with 3
    				case 9: accountNumArray[8] = (byte)(++accountNumArray[8]%10); break;// increase with 2
    				case 10: accountNumArray[9] = (byte)(++accountNumArray[9]%10); break;// increase with 1
    				default: accountNumArray[0] = 1;// in case of (accountNumberBasic == 0)
    			}
    			remainder = 0;
    	    	for(int i = 0; i < accountNumArray.length; i++) {
    	    		remainder += accountNumArray[i]*weights[i];
    	    	}
    	    	remainder %= 11;
    		} while(remainder != 0);
    		
    		// take correct accountNumberBasic from array of byte
    		accountNumberBasic = 0;
    		for(int i = 0; i < accountNumArray.length; i++) {
    			accountNumberBasic += accountNumArray[i] * Math.pow(10, (9-i));
        	}
    		return accountNumberBasic;
    	}
    }

    // generate individual account number (prefix)
    public int genAccountNumberPrefix(Client c, int start) {
    	// According to the National Bank of Slovakia, to validate the prefix part 
    	// of the account number, the digits are multiplied by 
    	// their respective weights (10,5,8,4,2,1), added up and divided by 11. 
    	// The remainder must be zero.
    	int accountNumberPrefix = start + 1;
    	
    	byte[] accountNumArray = new byte[6]; // because 6 digits of account
    	accountNumArray[0] = (byte)(accountNumberPrefix / 100000); // get 1st digit from the number
    	for(int i = 1; i < accountNumArray.length; i++) {
    		// get 2nd, 3rd, ...., 10th digits from the number
    		accountNumArray[i] = (byte)((accountNumberPrefix - accountNumArray[i-1]*Math.pow(10, (6-i)))/Math.pow(10, (6-i-1)));
    	}
    	byte[] weights = {10,5,8,4,2,1};
    	
    	// check remainder
    	int remainder = 0;
    	for(int i = 0; i < accountNumArray.length; i++) {
    		remainder += accountNumArray[i]*weights[i];
    	}
    	remainder %= 11;
    	if (remainder == 0)
    		return accountNumberPrefix;
    	else {
    		// we have to get reminder = 0, use loop for this
    		do {
    			switch (remainder){
    				case 1: accountNumArray[0] = (byte)(++accountNumArray[0]%10); break;// increase with 10
    				case 2: {
    					// increase with 9
    					accountNumArray[1] = (byte)(++accountNumArray[1]%10);// with 5
    					accountNumArray[3] = (byte)(++accountNumArray[3]%10);// with 4
    					break;
    				}
    				case 3: accountNumArray[2] = (byte)(++accountNumArray[2]%10); break;// increase with 8
    				case 4: {
    					// increase with 7
    					accountNumArray[1] = (byte)(++accountNumArray[1]%10);// with 5
    					accountNumArray[4] = (byte)(++accountNumArray[4]%10);// with 2
    					break;
    				}
    				case 5: {
    					// increase with 6
    					accountNumArray[3] = (byte)(++accountNumArray[3]%10);// with 4
    					accountNumArray[4] = (byte)(++accountNumArray[4]%10);// with 2
    					break;
    				}
    				case 6: accountNumArray[1] = (byte)(++accountNumArray[1]%10); break;// increase with 5
    				case 7: accountNumArray[3] = (byte)(++accountNumArray[3]%10); break;// increase with 4
    				case 8: {
    					// increase with 3
    					accountNumArray[5] = (byte)(++accountNumArray[5]%10);// with 1
    					accountNumArray[4] = (byte)(++accountNumArray[4]%10);// with 2
    					break;
    				}
    				case 9: accountNumArray[4] = (byte)(++accountNumArray[4]%10); break;// increase with 2
    				case 10: accountNumArray[5] = (byte)(++accountNumArray[5]%10); break;// increase with 1
    			}
    			remainder = 0;
    	    	for(int i = 0; i < accountNumArray.length; i++) {
    	    		remainder += accountNumArray[i]*weights[i];
    	    	}
    	    	remainder %= 11;
    		} while(remainder != 0);
    		
    		// take correct accountNumberPrefix from array of byte
    		accountNumberPrefix = 0;
    		for(int i = 0; i < accountNumArray.length; i++) {
    			accountNumberPrefix += accountNumArray[i] * Math.pow(10, (6-i-1));
        	}
    		return accountNumberPrefix;
    	}
    }
}
