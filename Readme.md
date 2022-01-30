# TransactionTracker 
### Currently, it is an In-Memory Api Project

- Ability to Add Transactions
- Get a transaction by Id
- Get transactions by type
- get all transactions
- get Sum of all Transitively Related Transactions
- update any transaction by Id
  - on updating any transaction - its parent id changes, so mapping between transactions also need to be changed
  

### Data Structures Used to Build the Inmemory Solution

- List for storing Transactions - equivalent to Rows in SQL Db
- Hashmap for storing transaction by Id, for instant access
- N-Ary Tree for storing Linked transactions.
- Queue for Traversing through the Tree and getting sum api result

#### Technology - Framework Used
- Kotlin-Dropwizard