# Coding Challenge
Coding brief can be found in [Challenge.MD](Challenge.MD)

Logs are saved to spring.log upon running the application.

The transactions are saved to a file based H2 database (challenge.db). This allows for efficient reading/writing to file, as well as being able to retrieve all the records sorted by date.

# Endpoints
Save transaction(s)

* `POST` - `http://localhost:8080/transactions`

Get all transactions in database

* `GET` - `http://localhost:8080/transactions`

Get individual transaction

* `GET` - `http://localhost:8080/transactions/{date}/{type}`

  * For example: `http://localhost:8080/transactions/22-01-2023/CREDIT`

# Assumptions
* The brief didn't specify any of the possible types beyond "credit". I assumed a total of 2 possible types, `CREDIT` and `DEBIT`.
* Due to only 1 type being specified, for any individual day, I save all types of transactions of each type individually. 
  * Going further, I could have modified the saved transaction based on if a saved credit value went negative due to a large debit value, and then saved the value as debit and still used a positive amount.
* No mention of having to be able to read the file through generic text editor etc, so I made the decision to use a H2 file database, as that allowed me to let Spring deal with the reading and writing to the file.
* Sorting of the data is also done by virtue of it being an SQL database, so queries can order by date etc. 

# Limitations
No validation on amount. Could be negative and still be added.

No validation on date beyond it being an actual valid date. Could be in the future.

# Running locally
To spin up the project locally, run `gradle bootRun` from the root of the repo.

A python script is included for local testing. There are global vars that can be modified to vary the test more.

The python script will, by default:
* Create 150 transactions in a single batch call
  * These transactions are all randomised in terms of date, and amount
    * Date goes back 1000 days
    * Amount is randomised between 0-1000.99
  * Type can be randomised as well from the script's global vars
* Retrieve the current number of records in the database. (This will not always be 150, as duplicates can randomly occur)
* Take the last transaction and do a get call based on that transaction's date, and type
