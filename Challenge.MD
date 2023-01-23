Programming Assignment:
Design a web service that accepts transactions data in JSON format and saves them to file.
A transaction is uniquely identified by date and type. If such a transaction already exists
in a file, then sum the amounts of 2 transactions but save only one of those.
When this Event happens (transaction with date and type already exists and we sum the amounts), we need to send a message
to external system. For this assignment, external system can simply mean writing event log to another file.
Data in file can be saved in sorted ordered by dates.
Sample Input Request:
[
   {
       "date": "11-12-2018",
       "type": "credit",
       "amount": "9898.36"
   },
   {
       "date": "11-12-2019",
       "type": "credit",
       "amount": "98.36"
   }
]

We also need to get transaction based on date and type.
Sample Get Request
                {
       "date": "11-12-2019",
       "type": "credit"
                }
Sample Get Response
                {
       "date": "11-12-2019",
       "type": "credit",
       "amount": "98.36"
   }

Since number of transactions can grow, retrieving data needs to be efficient.
You only need to design one GET and one POST Api.
It is desirable, but not required, if you can also deploy code to public cloud of your choice and
share the deployment scripts if any. 
If assignment taking long, please submit what is completed and rest of
the steps can be explained via email.
If possible please use one of the following choices for solution:
Spring Boot or Grails, And Java or Groovy.