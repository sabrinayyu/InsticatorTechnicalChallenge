No change for basic setting. 



### **What I did:**

- added the "type" field in QUESTION table (TRIVIA, POLL, CHECKBOX, MATRIX, I added an enum class handle this.)
- added a "isRowOption" in QUESTION_ANSWER table to distinguish the column options from the row options of matrix questions so that matrix  question could be stored into that table as well.
- added SITECP(=USER) table and ANSWER_HISTORY table to store answer record and user info 
- created entities&dto
- validated  a few parameters
- reorganized the code  
- set a few tests



### **Assumption:**

- All types of question has a column of options (is_row_option = false), and for matrix question, there is an extract row of options.



### Note:

- "sitecp" actually stands for "user". My user table didn't work for unknow reason, so I copied the "site" table and built my "user" based on it.





### **My thoughts on assumptions, security concerns and salability is :**

**SQL injection.** An Attacker can inject SQL scripts from the client api request. We need to sanitize the request parameters before executing database query. e.g. ban the key word "drop"
**Authorization and authentication**: If we do not do authorization, anyone can read the network request from the inspector and use our API. We can use **Json Web Token.** We  generate a token using a secret key and a timestamp. We also set the  expiration time of the token. Then we hand in the token to user and the  user stores the token in the cookie. Every time we validate the token when we receive a request.

**Database Encryption and Data exposure:** to make sure the database is secure. We need to encrypt sensitive data like passwords (maybe in the user table in the future).  We should not expose database configuration. Instead we should set  environment variables in the production or testing environment. A robust test, build and deploy workflow is needed to eliminate bugs or security concerns.

**More: **We can bring up multiple backend servers to serve the clients and add read write lock on database so that user won't get wrong data. Also, we can partition the database by question type. 

