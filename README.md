# onlinebanking
POST - Login  
http://localhost:1010/users/login  
Body :  
{
	"username":"mahfooz",
	"password":"Password@1"
}

POST - Create New User  
http://localhost:1010/users  
Body :  
{  
	"username":"mahfooz",  
	"password":"Password@1",  
	"firstName":"Mahfooz",  
	"lastName":"Ahmad",  
	"email":"mahfooz.lakhani@gmail.com",  
	"phone":"9810218422",  
	"ssn":"123-45-6789",  
	"address":"Noida"  
}

GET - Savings Account Details  
http://localhost:1010/accounts/savingsAccount  

GET - Checking Account Details  
http://localhost:1010/accounts/checkingAccount  

GET - MoneyMarket Account Details  
http://localhost:1010/accounts/moneyMarketAccount  


POST - Deposit  
http://localhost:1010/accounts/deposit  
Body :  
{  
	"accountType":"Savings",  
	"amount":10000.00  
}

POST - Withdraw  
http://localhost:1010/accounts/withdraw  
Body :  
{  
	"accountType":"Savings",  
	"amount":10000.00  
}

GET - Saving Transaction List  
http://localhost:1010/accounts/savingAccountTransactionList  

GET - Money Market Transaction List  
http://localhost:1010/accounts/moneyMarketAccountTransactionList  

POST - Transfer Between Accounts  
http://localhost:1010/transfer/betweenAccounts  
Body :  
{  
	"fromAccount":"Savings",  
	"toAccount":"Checking",  
	"amount":50  
}
