# cs90-final-project serverless API
The cs90-final-project project, created with [`aws-serverless-java-container`](https://github.com/awslabs/aws-serverless-java-container).

Note that this project was built as a demo to investigate AWS Serverless.  It's not intended for true production use, as it skips some potential error modes and doesn't provide the full API that would be desired for an actual To-do application.


## Deployment Instructions
If using this project in Intellij IDEA, select the "shaded-jar" profile and do a "clean package" Maven build.  This will result in several jars:
* cs90-final-project-version-SNAPSHOT.jar
* cs90-final-project-version-SNAPSHOT-shaded.jar
* original-cs90-final-project-version-SNAPSHOT.jar

Upload the first jar above (cs90-final-project-version-SNAPSHOT.jar) to Amazon for use in the AWS Lambda function.  

Note that the jar will be too large to upload directly via the AWS Lambda console.  If using the console, it must be first uploaded to an S3 bucket, and then into the AWS Lambda function. 

### Programmatic Testing
By default, tests will be skipped during the build since the <skipTests> property is set to true in the pom.xml.  This is because the tests require additional setup: environment variables and the availability of a database.

To run the tests:
1. Install MySQL and create a "todos" database.
2. Run the create-todos.sql script in the resources folder (filling in the password for the "todo" user).
3. Set environment variables as follows:  
SPRING_DATASOURCE_URL = jdbc:mysql://_{server name}_:3306/todos  
SPRING_DATASOURCE_PASSWORD = _{todo password}_ 


## REST Endpoints
The REST endpoints below (except /ping) are designed for use with a hypothetical To-Do List web application front end.

### GET /ping
Leftover from the AWS-created Maven archetype auto-generated controller.

I left it as a useful way to check connectivity without involving any logic or database interactions, as it simply returns an immediate response, and can be queried even from a web browser.  It's not intended for use with the To-Do application, it's just left in for testing purposes.

### GET /lists
Use to retrieve all to-do lists.

Returns 200 OK with an array of all saved to-do list objects in JSON format, including all to-do items in each list.  
Returns 200 OK with an empty array if there are no saved lists.

### GET /lists/{listId}
Use to retrieve a single to-do list.

Returns 200 OK with a single JSON object for the to-do list with the specified persistence index, if found.  
Returns 404 Not Found if there’s no matching to-do list.

### POST /lists
Use to create a new list entry.

Expects JSON-formatted to-do list in request body, with minimally a name specified.  

Returns 200 OK with the saved to-do list entry, including persistence index.  

### PUT /lists/{listId}
Use to update a to-do list – this includes adding a new item or modifying an item such as editing the task or marking it complete.

Expects JSON-formatted to-do list in request body, including to-do items (if any).  Note that the data provided will OVERWRITE any previously-saved value for the specified persistence index, including to-do items.  

Returns 200 OK with the saved updated to-do list entry, including to-do items if there’s a saved list with the specified id.  
Returns 404 Not Found if there’s no matching to-do list.

### DELETE /lists/{listId}
Use to delete a to-do list, including any items on that list.

Returns 204 No Content if the list was successfully deleted.  
Returns 404 Not Found if there’s no to-do list with the specified listId to delete.

### DELETE /lists/{listId}/items/{itemId}
Use to delete an item from a to-do list.

Returns 204 No Content if the item was successfully deleted.  
Returns 404 Not Found if there’s no to-do item with the specified itemId to delete.


## REST API Testing
Some sample JSON objects are in the src/json folder.  The text in these files can be used as the request body for the POST and PUT endpoints when testing with an external application such as Postman.
