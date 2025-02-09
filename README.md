<h1>API Testing Using Rest-Assured on Notes API</h1>

<p>This project demonstrates API testing using <strong>Rest-Assured</strong> for the <strong>Notes API</strong>. The API documentation is available via Swagger, and the tests are designed to cover various testing levels, including <strong>Component Testing</strong>, <strong>Integration Testing</strong>, and <strong>End-to-End Testing</strong>. The following features are covered in this project:</p>

<ul>
  <li><strong>Users Endpoints</strong></li>
  <li><strong>Notes Endpoints</strong></li>
  <li><strong>Login Endpoint</strong></li>
</ul>

<h2>Documentation Link</h2>
<p>The Swagger documentation for the Notes API can be found here:  
  <a href="https://example.com/swagger-docs" target="_blank">Swagger Documentation</a> <em>(Replace with the actual link)</em>
</p>

<hr>

<h2>Testing Levels</h2>

<h3>1. Component Testing Level</h3>
<p>Component testing focuses on testing individual components or endpoints in isolation. This ensures that each endpoint behaves as expected under various conditions.</p>

<h4>Users Endpoints:</h4>
<ul>
  <li><code>GET /users</code> - Retrieve all users.</li>
  <li><code>POST /users</code> - Create a new user.</li>
  <li><code>GET /users/{id}</code> - Retrieve a specific user by ID.</li>
  <li><code>PUT /users/{id}</code> - Update a specific user by ID.</li>
  <li><code>DELETE /users/{id}</code> - Delete a specific user by ID.</li>
</ul>

<h4>Notes Endpoints:</h4>
<ul>
  <li><code>GET /notes</code> - Retrieve all notes.</li>
  <li><code>POST /notes</code> - Create a new note.</li>
  <li><code>GET /notes/{id}</code> - Retrieve a specific note by ID.</li>
  <li><code>PUT /notes/{id}</code> - Update a specific note by ID.</li>
  <li><code>DELETE /notes/{id}</code> - Delete a specific note by ID.</li>
</ul>

<h4>Login Endpoint:</h4>
<ul>
  <li><code>POST /login</code> - Authenticate a user and retrieve an access token.</li>
</ul>

<h3>2. Integration Testing Level</h3>
<p>Integration testing ensures that multiple components or endpoints work together as expected. This includes testing interactions between the Users, Notes, and Login endpoints.</p>

<h4>Test Scenarios:</h4>
<ul>
  <li>Create a user → Log in with the user credentials → Create a note associated with that user.</li>
  <li>Log in with invalid credentials and verify the error response.</li>
  <li>Retrieve a user and verify their associated notes after logging in.</li>
  <li>Update a user and verify the impact on their notes after logging in.</li>
  <li>Delete a user and ensure all associated notes are also deleted after logging in.</li>
</ul>

<h3>3. End-to-End Testing Level</h3>
<p>End-to-End testing validates the entire flow of the application, from user creation to note management, ensuring the system works as a whole.</p>

<h4>Test Scenarios:</h4>
<ul>
  <li>Create a user → Log in → Create a note → Retrieve the note → Update the note → Delete the note → Verify the user and note are deleted.</li>
</ul>

<hr>

<h2>Features Covered</h2>

<h3>Users Endpoints</h3>
<ul>
  <li><strong>Create User</strong>: Validate user creation with valid and invalid inputs.</li>
  <li><strong>Retrieve User</strong>: Verify user details are returned correctly.</li>
  <li><strong>Update User</strong>: Ensure user details can be updated successfully.</li>
  <li><strong>Delete User</strong>: Confirm user deletion and its impact on associated notes.</li>
</ul>

<h3>Notes Endpoints</h3>
<ul>
  <li><strong>Create Note</strong>: Validate note creation with valid and invalid inputs.</li>
  <li><strong>Retrieve Note</strong>: Verify note details are returned correctly.</li>
  <li><strong>Update Note</strong>: Ensure note details can be updated successfully.</li>
  <li><strong>Delete Note</strong>: Confirm note deletion and its impact on associated users.</li>
</ul>

<h3>Login Endpoint</h3>
<ul>
  <li><strong>Authenticate User</strong>: Validate login functionality with valid and invalid credentials.</li>
  <li><strong>Access Token</strong>: Verify the retrieval of an access token upon successful login.</li>
</ul>

<hr>

<h2>Prerequisites</h2>
<p>To run the tests, ensure you have the following installed:</p>
<ul>
  <li><strong>Java JDK 8 or higher</strong></li>
  <li><strong>Maven</strong> (for dependency management)</li>
  <li><strong>Rest-Assured</strong> (included in <code>pom.xml</code>)</li>
  <li><strong>TestNG</strong> or <strong>JUnit</strong> (for test execution)</li>
</ul>

<hr>

<h2>Setup and Execution</h2>

<h3>1. Clone the Repository</h3>
<pre><code>git clone https://github.com/your-username/notes-api-testing.git
cd notes-api-testing
</code></pre>

<h3>2. Install Dependencies</h3>
<pre><code>mvn clean install
</code></pre>

<h3>3. Run Tests</h3>
<ul>
  <li>To run all tests:
    <pre><code>mvn test</code></pre>
  </li>
  <li>To run specific test levels:
    <pre><code>mvn test -Dtest=ComponentTest
mvn test -Dtest=IntegrationTest
mvn test -Dtest=EndToEndTest</code></pre>
  </li>
</ul>

<hr>

<h2>Test Reports</h2>
<p>Test reports are generated in the <code>target/surefire-reports</code> directory. You can view the detailed results there.</p>

<hr>

<h2>Contributing</h2>
<p>Contributions are welcome! If you find any issues or want to add new test cases, feel free to open a pull request.</p>
<ol>
  <li>Fork the repository.</li>
  <li>Create a new branch for your feature or bugfix.</li>
  <li>Commit your changes.</li>
  <li>Push to the branch.</li>
  <li>Open a pull request.</li>
</ol>

<hr>

<h2>License</h2>
<p>This project is licensed under the MIT License. See the <a href="LICENSE">LICENSE</a> file for details.</p>




<hr>

<p>Happy Testing! 🚀</p>
