# To-Do Web App

This is a simple To-Do Web App created using HTML, CSS, and JavaScript. The main purpose of this project is to help users add and manage their daily tasks in one place.

Users can add new tasks, edit them, mark them as completed, and delete them when they are no longer needed. Pending and completed tasks are shown separately.

## Features

* Add a new task
* View all pending tasks
* Mark a task as completed
* View completed tasks separately
* Edit an existing task
* Delete a task
* See the number of pending and completed tasks
* Display the time when a task was added
* Display the time when a task was completed
* Save tasks in the browser using localStorage
* Tasks remain saved after refreshing the page
* Show a message when there are no tasks
* Responsive design for different screen sizes

## Technologies Used

* HTML5
* CSS3
* JavaScript
* localStorage

## Project Files


### index.html

This file contains the basic structure of the To-Do application. It includes the input box, Add Task button, Pending Tasks section, and Completed Tasks section.

### style.css

This file is used to design the application. It contains the layout, buttons, task styles, spacing, and responsive design for smaller screens.

### script.js

This file contains the main functionality of the application. It handles adding, editing, completing, deleting, displaying, and saving tasks.

### README.md

This file contains information about the project and explains how the application works.

## How to Run

1. Open the project folder in Visual Studio Code.
2. Make sure `index.html`, `style.css`, and `script.js` are in the same folder.
3. Open `index.html` in a web browser.
4. You can also use the Live Server extension in VS Code.
5. Enter a task in the input box.
6. Click the **Add Task** button.

## How to Use

### Add Task

Enter the task in the input field and click **Add Task**. The new task will appear under Pending Tasks.

### Complete Task

Click the **Complete** button on a pending task. The task will be moved to the Completed Tasks section.

### Edit Task

Click the **Edit** button to change the task name. After making the changes, press Enter or click outside the input field to save it.

### Delete Task

Click the **Delete** button if you want to remove a task. A confirmation message will appear before the task is deleted.

## Local Storage

I used JavaScript `localStorage` to save the tasks in the browser. Because of this, the tasks will not disappear when the page is refreshed.

The saved data is stored locally in the user's browser.

## Task Counters

The application shows the current number of tasks in both sections.

For example:

```text
Pending Tasks       2 pending
Completed Tasks     3 completed
```

The numbers are automatically updated when tasks are added, completed, or deleted.

## Empty Task Lists

If there are no pending tasks, the application displays a friendly message instead of showing an empty section.

The same type of message is displayed when there are no completed tasks.

## Responsive Design

The application works on both desktop and mobile screens.

On a desktop, the Pending Tasks and Completed Tasks sections are displayed side by side. On smaller screens, they are arranged one below the other.

## What I Learned

While creating this project, I practiced:

* Creating a webpage using HTML
* Styling a webpage using CSS
* Working with JavaScript
* DOM manipulation
* Handling button and keyboard events
* Working with arrays and objects
* Adding and removing HTML elements dynamically
* Using `localStorage`
* Updating data on the webpage
* Creating a responsive layout

## Future Improvements

Some features that can be added later are:

* Task priority
* Due dates
* Search tasks
* Task categories
* Dark mode
* Sorting tasks
* Notifications and reminders


