# Login Authentication System

This project is a simple client-side Login Authentication System created using HTML, CSS, and JavaScript.

The application allows a user to create an account, log in using their username or email and password, and access a protected dashboard after successful authentication.

For this project, browser localStorage is used to store registered user information and the login session.

## Features

* User registration
* Username and email fields
* Password validation
* Minimum 8 characters for password
* Password must contain at least one number
* Duplicate username/email checking
* User login using username or email
* Login credential validation
* Clear error message for incorrect login
* Protected dashboard page
* Redirect to login when there is no active session
* Logout functionality
* Login session stored in localStorage
* Password stored as a SHA-256 hash instead of plain text
* Empty field validation
* Basic email validation
* Responsive design for desktop and mobile devices

## Technologies Used

* HTML5
* CSS3
* JavaScript
* Browser localStorage
* Web Crypto API

## Project Structure

```text
login-authentication-system/
│
├── index.html
├── register.html
├── dashboard.html
├── style.css
├── script.js
└── README.md
```

## File Description

### index.html

This is the login page. It contains fields for entering a username or email and password.

### register.html

This page is used to create a new account. It checks the entered information before creating the account.

### dashboard.html

This is the protected page. A user can access it only after successful login.

### style.css

This file contains the complete styling for the login, registration, and dashboard pages.

### script.js

This file contains the authentication logic, validation, password hashing, localStorage handling, login session, dashboard protection, and logout functionality.

### README.md

This file provides information about the project, features, technologies, and how to run the application.

## How to Run

1. Open the project folder in Visual Studio Code.
2. Make sure all the project files are present.
3. Open `index.html` using a browser.
4. You can also use the Live Server extension in VS Code.
5. First, create an account using the Register page.
6. After registration, go back to the Login page.
7. Enter the registered username/email and password.
8. After successful login, the Dashboard page will open.

## Registration Process

The user needs to provide:

* Username
* Email
* Password

The application checks that:

* No field is empty.
* The email format is valid.
* The password contains at least 8 characters.
* The password contains at least one number.
* The username is not already registered.
* The email is not already registered.

After all checks pass, the password is converted into a SHA-256 hash before the user information is saved.

## Login Process

The user can log in using either their username or email.

The application compares the entered credentials with the registered user information.

If the credentials are correct, a login session is created and the user is redirected to the dashboard.

If the credentials are incorrect, the application displays:

```text
Invalid username/email or password.
```

The message does not reveal whether the username/email or password was incorrect.

## Password Security

Passwords are not stored directly as plain text.

Before saving a user's password, the application uses the browser's Web Crypto API to create a SHA-256 hash.

The stored user information contains the password hash instead of the original password.

Example:

```text
Original password:
MyPassword123

Stored value:
SHA-256 hash of the password
```

## Protected Dashboard

The dashboard is protected using a login session stored in localStorage.

When the dashboard page loads, JavaScript checks whether a valid login session exists.

If no session is found, the user is redirected to the login page.

This prevents users from normally accessing the dashboard directly without logging in.

## Logout

When the user clicks the Logout button, the active login session is removed from localStorage.

The user is then redirected back to the login page.

The registered account itself is not deleted when logging out.

## Local Storage

This project uses localStorage for two purposes:

1. Storing registered users
2. Storing the current login session

The browser keeps this information until it is removed or the site's storage is cleared.

## Validation

The registration form checks for:

* Empty fields
* Invalid email format
* Password length
* Password number requirement
* Duplicate username
* Duplicate email

The login form checks for:

* Empty username/email
* Empty password
* Incorrect credentials

## Responsive Design

The application is designed to work on both desktop and mobile devices.

The layout automatically adjusts on smaller screens so that the forms and dashboard remain easy to use.

## Testing

The following cases can be tested:

### Test 1 - Empty Registration

Submit the registration form without entering information.

Expected result:

```text
Please fill in all the fields.
```

### Test 2 - Weak Password

Enter a password with fewer than 8 characters or without a number.

Expected result:

```text
Password must be at least 8 characters long and contain at least one number.
```

### Test 3 - Duplicate Account

Register using an existing username or email.

Expected result:

```text
Username or email is already registered.
```

### Test 4 - Incorrect Login

Enter incorrect login information.

Expected result:

```text
Invalid username/email or password.
```

### Test 5 - Successful Login

Enter the correct registered credentials.

Expected result:

The user is redirected to the Dashboard.

### Test 6 - Direct Dashboard Access

Open `dashboard.html` without logging in.

Expected result:

The user is redirected to the Login page.

### Test 7 - Logout

Click the Logout button on the dashboard.

Expected result:

The session is removed and the user returns to the Login page.

## Learning Outcomes

While creating this project, I practiced:

* HTML form creation
* CSS page styling
* JavaScript DOM manipulation
* Form validation
* Event handling
* Arrays and objects
* localStorage
* Login session handling
* Password hashing
* Page redirection
* Protected page logic
* Responsive web design

## Limitations

This is a front-end authentication project created for learning and demonstration purposes.

Because authentication is handled in the browser using localStorage, it should not be used as the authentication system for a real production application.

A production application should normally use a backend server, secure password hashing such as bcrypt/Argon2, HTTPS, secure cookies, server-side sessions or tokens, and a proper database.

## Future Improvements

Some possible improvements are:

* Backend authentication using Node.js and Express
* Database integration
* Password reset functionality
* Email verification
* User profile page
* Secure server-side sessions
* Account deletion
* Remember-me functionality
* Better password strength checking


