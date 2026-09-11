const taskInput = document.getElementById("taskInput");
const addTaskBtn = document.getElementById("addTaskBtn");

const pendingList = document.getElementById("pendingList");
const completedList = document.getElementById("completedList");

const pendingCount = document.getElementById("pendingCount");
const completedCount = document.getElementById("completedCount");

const STORAGE_KEY = "myTodoTasks";

let tasks = loadTasks();

displayTasks();


// Add a new task
function addTask() {

    const text = taskInput.value.trim();

    if (text === "") {
        alert("Please enter a task first.");
        taskInput.focus();
        return;
    }

    const newTask = {
        id: Date.now(),
        text: text,
        completed: false,
        createdAt: new Date().toLocaleString(),
        completedAt: null
    };

    tasks.push(newTask);

    saveTasks();
    displayTasks();

    taskInput.value = "";
    taskInput.focus();
}


// Display both task lists
function displayTasks() {

    pendingList.innerHTML = "";
    completedList.innerHTML = "";

    const pendingTasks = tasks.filter(task => !task.completed);
    const completedTasks = tasks.filter(task => task.completed);

    updateCounts(pendingTasks.length, completedTasks.length);

    if (pendingTasks.length === 0) {
        showEmptyMessage(
            pendingList,
            "No pending tasks. You're all caught up!"
        );
    } else {
        pendingTasks.forEach(task => {
            pendingList.appendChild(createTaskElement(task));
        });
    }

    if (completedTasks.length === 0) {
        showEmptyMessage(
            completedList,
            "Completed tasks will appear here."
        );
    } else {
        completedTasks.forEach(task => {
            completedList.appendChild(createTaskElement(task));
        });
    }
}


// Create the HTML element for a task
function createTaskElement(task) {

    const taskItem = document.createElement("div");
    taskItem.className = "task-item";

    const details = document.createElement("div");
    details.className = "task-details";

    const taskText = document.createElement("span");
    taskText.className = "task-text";
    taskText.textContent = task.text;

    const taskTime = document.createElement("small");
    taskTime.className = "task-time";

    if (task.completed && task.completedAt) {
        taskTime.textContent = `Added: ${task.createdAt} | Completed: ${task.completedAt}`;
    } else {
        taskTime.textContent = `Added: ${task.createdAt}`;
    }

    details.appendChild(taskText);
    details.appendChild(taskTime);

    const actions = document.createElement("div");
    actions.className = "task-actions";

    if (!task.completed) {
        const completeButton = document.createElement("button");

        completeButton.className = "complete-btn";
        completeButton.textContent = "Complete";

        completeButton.addEventListener("click", function () {
            completeTask(task.id);
        });

        actions.appendChild(completeButton);
    }

    const editButton = document.createElement("button");
    editButton.className = "edit-btn";
    editButton.textContent = "Edit";

    editButton.addEventListener("click", function () {
        editTask(task.id);
    });

    const deleteButton = document.createElement("button");
    deleteButton.className = "delete-btn";
    deleteButton.textContent = "Delete";

    deleteButton.addEventListener("click", function () {
        deleteTask(task.id);
    });

    actions.appendChild(editButton);
    actions.appendChild(deleteButton);

    taskItem.appendChild(details);
    taskItem.appendChild(actions);

    return taskItem;
}


// Mark task as completed
function completeTask(taskId) {

    const task = tasks.find(item => item.id === taskId);

    if (!task) {
        return;
    }

    task.completed = true;
    task.completedAt = new Date().toLocaleString();

    saveTasks();
    displayTasks();
}


// Edit task text
function editTask(taskId) {

    const task = tasks.find(item => item.id === taskId);

    if (!task) {
        return;
    }

    const taskItems = document.querySelectorAll(".task-item");

    taskItems.forEach(item => {

        const textElement = item.querySelector(".task-text");

        if (textElement && textElement.textContent === task.text) {

            const input = document.createElement("input");

            input.type = "text";
            input.value = task.text;
            input.className = "edit-input";

            textElement.replaceWith(input);

            input.focus();

            function saveEdit() {

                const updatedText = input.value.trim();

                if (updatedText !== "") {
                    task.text = updatedText;
                    saveTasks();
                }

                displayTasks();
            }

            input.addEventListener("keydown", function (event) {

                if (event.key === "Enter") {
                    saveEdit();
                }

                if (event.key === "Escape") {
                    displayTasks();
                }
            });

            input.addEventListener("blur", saveEdit);
        }
    });
}


// Delete a task
function deleteTask(taskId) {

    const shouldDelete = confirm("Are you sure you want to delete this task?");

    if (!shouldDelete) {
        return;
    }

    tasks = tasks.filter(task => task.id !== taskId);

    saveTasks();
    displayTasks();
}


// Update task counters
function updateCounts(pending, completed) {

    pendingCount.textContent =
        `${pending} ${pending === 1 ? "pending" : "pending"}`;

    completedCount.textContent =
        `${completed} ${completed === 1 ? "completed" : "completed"}`;
}


// Show empty list message
function showEmptyMessage(container, message) {

    const emptyText = document.createElement("p");

    emptyText.className = "empty-message";
    emptyText.textContent = message;

    container.appendChild(emptyText);
}


// Save tasks in localStorage
function saveTasks() {

    localStorage.setItem(STORAGE_KEY, JSON.stringify(tasks));
}


// Load tasks from localStorage
function loadTasks() {

    const storedTasks = localStorage.getItem(STORAGE_KEY);

    if (!storedTasks) {
        return [];
    }

    try {
        return JSON.parse(storedTasks);
    } catch (error) {
        console.error("Unable to load saved tasks:", error);
        return [];
    }
}


// Button click
addTaskBtn.addEventListener("click", addTask);


// Allow Enter key to add a task
taskInput.addEventListener("keydown", function (event) {

    if (event.key === "Enter") {
        addTask();
    }
});