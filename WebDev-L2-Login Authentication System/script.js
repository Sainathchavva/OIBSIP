const USERS_KEY = "registeredUsers";
const SESSION_KEY = "loggedInUser";


// Get users saved in localStorage
function getUsers() {

    const savedUsers = localStorage.getItem(USERS_KEY);

    if (!savedUsers) {
        return [];
    }

    try {
        return JSON.parse(savedUsers);
    } catch (error) {
        return [];
    }
}


// Save users to localStorage
function saveUsers(users) {

    localStorage.setItem(
        USERS_KEY,
        JSON.stringify(users)
    );
}


// Create SHA-256 password hash
async function hashPassword(password) {

    const data = new TextEncoder().encode(password);

    const hashBuffer = await crypto.subtle.digest(
        "SHA-256",
        data
    );

    const hashArray = Array.from(
        new Uint8Array(hashBuffer)
    );

    return hashArray
        .map(byte => byte.toString(16).padStart(2, "0"))
        .join("");
}


// Show a message on the page
function showMessage(element, text, type) {

    element.textContent = text;
    element.className = `message ${type}`;
}


// Registration
const registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const username = document
            .getElementById("registerUsername")
            .value
            .trim();

        const email = document
            .getElementById("registerEmail")
            .value
            .trim()
            .toLowerCase();

        const password = document
            .getElementById("registerPassword")
            .value;

        const message = document.getElementById("registerMessage");


        // Empty field validation
        if (!username || !email || !password) {

            showMessage(
                message,
                "Please fill in all the fields.",
                "error"
            );

            return;
        }


        // Basic email validation
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!emailPattern.test(email)) {

            showMessage(
                message,
                "Please enter a valid email address.",
                "error"
            );

            return;
        }


        // Password validation
        const passwordPattern = /^(?=.*\d).{8,}$/;

        if (!passwordPattern.test(password)) {

            showMessage(
                message,
                "Password must be at least 8 characters long and contain at least one number.",
                "error"
            );

            return;
        }


        const users = getUsers();


        // Check duplicate username or email
        const existingUser = users.find(user =>
            user.username.toLowerCase() === username.toLowerCase() ||
            user.email.toLowerCase() === email
        );

        if (existingUser) {

            showMessage(
                message,
                "Username or email is already registered.",
                "error"
            );

            return;
        }


        // Hash password before saving
        const passwordHash = await hashPassword(password);


        const newUser = {
            username: username,
            email: email,
            passwordHash: passwordHash,
            registeredAt: new Date().toLocaleString()
        };


        users.push(newUser);

        saveUsers(users);


        showMessage(
            message,
            "Registration successful. Redirecting to login...",
            "success"
        );


        setTimeout(function () {
            window.location.href = "index.html";
        }, 1200);

    });
}


// Login
const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const loginUser = document
            .getElementById("loginUser")
            .value
            .trim();

        const loginPassword = document
            .getElementById("loginPassword")
            .value;

        const message = document.getElementById("loginMessage");


        // Empty field validation
        if (!loginUser || !loginPassword) {

            showMessage(
                message,
                "Please enter your username/email and password.",
                "error"
            );

            return;
        }


        const users = getUsers();

        const user = users.find(item =>
            item.username.toLowerCase() === loginUser.toLowerCase() ||
            item.email.toLowerCase() === loginUser.toLowerCase()
        );


        // Hash entered password
        const enteredPasswordHash =
            await hashPassword(loginPassword);


        // Check credentials
        if (!user || user.passwordHash !== enteredPasswordHash) {

            showMessage(
                message,
                "Invalid username/email or password.",
                "error"
            );

            return;
        }


        // Create login session
        const session = {
            username: user.username,
            email: user.email,
            loginTime: new Date().toLocaleString()
        };

        localStorage.setItem(
            SESSION_KEY,
            JSON.stringify(session)
        );


        window.location.href = "dashboard.html";

    });
}


// Protect dashboard
if (window.location.pathname.endsWith("dashboard.html")) {

    const savedSession =
        localStorage.getItem(SESSION_KEY);

    if (!savedSession) {

        window.location.href = "index.html";

    } else {

        try {

            const session = JSON.parse(savedSession);

            const usernameElement =
                document.getElementById("dashboardUsername");

            if (usernameElement) {
                usernameElement.textContent =
                    session.username;
            }

        } catch (error) {

            localStorage.removeItem(SESSION_KEY);
            window.location.href = "index.html";
        }
    }
}


// Logout
const logoutBtn = document.getElementById("logoutBtn");

if (logoutBtn) {

    logoutBtn.addEventListener("click", function () {

        localStorage.removeItem(SESSION_KEY);

        window.location.href = "index.html";
    });
}