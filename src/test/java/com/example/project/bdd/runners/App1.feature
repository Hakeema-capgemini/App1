
Feature: App1 User Management
 
Scenario: Create a new User with Name:<UserName> Age:<UserAge>
          When Create a new User  	
             | name      |    age    |
             | <UserName>| <UserAge> |
          Then the response should confirm user creation
          And the user is created
          Examples: 
            | UserName|  UserAge |
            | Rizwana |   24     |
            | Hakeema |   23     |


Scenario: Retrieve a user by ID
          Given the user with ID 1 exists in the system
          When Retrieve User with ID 1
          Then the response should contain the user's details
          | id  | name    | age |
          | 1   | Rizwana | 24  |
         

