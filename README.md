### **What I did:**

- added the "type" field in QUESTION table (TRIVIA, POLL, CHECKBOX, MATRIX, I added an enum class handle this.)
- added a "isRowOption" in QUESTION_ANSWER table to distinguish the column options from the row options of matrix questions so that matrix  question could be stored into that table as well.
- added SITECP(=USER) table and ANSWER_HISTORY table to store answer record and user info 
- created entities&dto
- created two endpoints for generating unique question and recording answers(/unique && /useranswer)
- validated  a few parameters
- reorganized the code  
- set a few tests



### **Assumption:**

- All types of question has a column of options (is_row_option = false), and for matrix question, there is an extract row of options.



### Note:

- "SITECP" actually stands for "USER". My user table didn't work for unknow reason, so I copied the "SITE" table and built my "USER" based on it.
- "SITECP" stores user info including the answer round the user is currently in.
- "ANSWER_HISTORY" stores all answer history for a user and the round that user is in when answering.





### **My thoughts on security concerns and salability is :**

**SQL injection.** An Attacker can inject SQL scripts from the client api request. We need to sanitize the request parameters before executing database query. e.g. ban the key word "drop"
**Authorization and authentication**: If we do not do authorization, anyone can read the network request from the inspector and use our API. We can use **Json Web Token.** We  generate a token using a secret key and a timestamp. We also set the  expiration time of the token. Then we hand in the token to user and the  user stores the token in the cookie. Every time we validate the token when we receive a request.

**Database Encryption and Data exposure:** to make sure the database is secure. We need to encrypt sensitive data like passwords (maybe in the user table in the future).  We should not expose database configuration. Instead we should set  environment variables in the production or testing environment. A robust test, build and deploy workflow is needed to eliminate bugs or security concerns.

**More:** We can bring up multiple backend servers to serve the clients and add read write lock on database so that user won't get wrong data. Also, we can partition the database by question type. 



### Database

No change for basic setting. 

The application is currently using H2 DB for an in memory database, this is for testing purposes only and is not a production ready solution.  The Database is initialized by using the Hibernate auto update, again this is not a production ready upgrade solution.

#### Access the Database

> localhost:8080/h2-console

Set the JDBC URL to *jdbc:h2:mem:testdb*, and press connect



#### Creating a Sample Matrix Question

I used postman.

##### Matrix Question

> Please tell us a bit about yourself? 

| Age/Gender | Male | Female |
| :--------- | :--: | :----: |
| < 18       | [ ]  |  [ ]   |
| 18 to 35   | [ ]  |  [ ]   |
| 35 to 55   | [ ]  |  [ ]   |
| \> 55      | [X]  |  [ ]   |



Create a Site

> post http://localhost:8080/sites
>
> requestbody:
>
> {"url": "www.bob.com"}
>
> response:
>
> {
>
>   "siteId": 1,
>
>   "siteUUID": "00c69607-3cb2-457d-a7fc-d6090e2988e7",
>
>   "url": "www.bob.com",
>
>   "createdAt": "2020-07-28T03:34:05.082+0000",
>
>   "updatedAt": "2020-07-28T03:34:05.082+0000"
>
> }



Create the Trivia Question for the Site

> post http://localhost:8080/questions
>
> requestbody:
>
> {"siteId":1, "question": "Please tell us a bit about yourself? ", "type": "Matrix"}
>
> response:
>
> {
>
>   "questionId": 2,
>
>   "siteId": 1,
>
>   "type": "MATRIX",
>
>   "question": "Please tell us a bit about yourself? "
>
> }



Create some responses for the question

>post http://localhost:8080/questions/2/answers
>
>requestbody:
>
>{"answer": "< 18", "isCorrectAnswer": false, "isRowOption": false}
>
>response:
>
>{
>
>  "id": 3,
>
>  "answer": "< 18",
>
>  "isCorrectAnswer": false,
>
>  "isRowOption": false
>
>}



> post http://localhost:8080/questions/2/answers
>
> requestbody:
>
> {"answer": "18 to 35", "isCorrectAnswer": false, "isRowOption": false}
>
> response:
>
> {
>
>   "id": 4,
>
>   "answer": "18 to 35",
>
>   "isCorrectAnswer": false,
>
>   "isRowOption": false
>
> }



> post http://localhost:8080/questions/2/answers
>
> requestbody:
>
> {"answer": "35 to 55", "isCorrectAnswer": false, "isRowOption": false}
>
> response:
>
> {
>
>   "id": 5,
>
>   "answer": "35 to 55",
>
>   "isCorrectAnswer": false,
>
>   "isRowOption": false
>
> }



> post http://localhost:8080/questions/2/answers
>
> requestbody:
>
> {"answer": "> 55", "isCorrectAnswer": false, "isRowOption": false}
>
> response:
>
> {
>
>   "id": 6,
>
>   "answer": "> 55",
>
>   "isCorrectAnswer": false,
>
>   "isRowOption": false
>
> }



> post http://localhost:8080/questions/2/answers
>
> requestbody:
>
> {"answer": "Male", "isCorrectAnswer": false, "isRowOption": true}
>
> response:
>
> {
>
>   "id": 7,
>
>   "answer": "Male",
>
>   "isCorrectAnswer": false,
>
>   "isRowOption": true
>
> }



> post http://localhost:8080/questions/2/answers
>
> requestbody:
>
> {"answer": "Female", "isCorrectAnswer": false, "isRowOption": true}
>
> response:
>
> {
>
>   "id": 8,
>
>   "answer": "Female",
>
>   "isCorrectAnswer": false,
>
>   "isRowOption": true
>
> }



get unique question for a user(we only have one question in db, so we definitely get it)

> post http://localhost:8080/questions/unique
>
> requestbody:
>
> {
>
>   "sitecpUUID":"7a177c02-085b-45cc-ab46-509b110a548a",
>
>   "siteUUID":"00c69607-3cb2-457d-a7fc-d6090e2988e7"
>
> }
>
> response:
>
> {
>
>   "questionDTO": {
>
> ​    "questionId": 2,
>
> ​    "siteId": 1,
>
> ​    "type": "MATRIX",
>
> ​    "question": "Please tell us a bit about yourself? "
>
>   },
>
>   "options": [
>
> ​    {
>
> ​      "id": 3,
>
> ​      "answer": "< 18",
>
> ​      "isCorrectAnswer": false,
>
> ​      "isRowOption": false
>
> ​    },
>
> ​    {
>
> ​      "id": 4,
>
> ​      "answer": "18 to 35",
>
> ​      "isCorrectAnswer": false,
>
> ​      "isRowOption": false
>
> ​    },
>
> ​    {
>
> ​      "id": 5,
>
> ​      "answer": "35 to 55",
>
> ​      "isCorrectAnswer": false,
>
> ​      "isRowOption": false
>
> ​    },
>
> ​    {
>
> ​      "id": 6,
>
> ​      "answer": "> 55",
>
> ​      "isCorrectAnswer": false,
>
> ​      "isRowOption": false
>
> ​    },
>
> ​    {
>
> ​      "id": 7,
>
> ​      "answer": "Male",
>
> ​      "isCorrectAnswer": false,
>
> ​      "isRowOption": true
>
> ​    },
>
> ​    {
>
> ​      "id": 8,
>
> ​      "answer": "Female",
>
> ​      "isCorrectAnswer": false,
>
> ​      "isRowOption": true
>
> ​    }
>
>   ]
>
> }



record user answer(You are allow to submit several answers. In "userAnswers", include more {      "answerColId" :6,"answerRowId" :7})

> post http://localhost:8080/questions/useranswer
>
> {
>
>   "userUUID":"7a177c02-085b-45cc-ab46-509b110a548a",
>
>   "questionId":2,
>
>   "userAnswers":[
>
> ​    {
>
> ​      "answerColId" :6,
>
> ​      "answerRowId" :7
>
> ​    }
>
>   ]
>
> }
>
> response:
>
> {
>
>   "userUUID": "7a177c02-085b-45cc-ab46-509b110a548a",
>
>   "questionId": 2,
>
>   "userAnswers": [
>
> ​    {
>
> ​      "answerColId": 6,
>
> ​      "answerRowId": 7
>
> ​    }
>
>   ]
>
> }