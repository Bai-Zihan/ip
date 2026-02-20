# 🤖Lime🍋 - Your Zesty Task Assistant
> _"Lime is the best!"_ -- Myself

Refreshing to see you! Lime is a desktop chatbot that helps you squeeze more productivity out of your day by recording and tracking your tasks.

---

## 📍 Table of Contents
* [🎨 UI Preview](#-ui-preview)
* [🚀 Deployment](#-deployment)
* [📖 Command Guide](#-command-guide)
    * [1. Add Tasks](#1-add-tasks-with-a-zest)
    * [2. Manage Pulp](#2-view-the-pulp)
    * [3. Search & View](#3-view-the-tasks)
    * [4. Squeeze Out Tasks](#4-squeeze-out-tasks)

---

## 🎨 UI Preview
![Lime UI Preview](Ui.png)

---

### Here are what 🤖Lime🍋 stands out

- Easy commands
- Clear user interface
- No monetary cost

---

## 🚀 Deployment
1. **Download**: Get the latest `Lime.jar` from [Releases](https://github.com/Bai-Zihan/ip/releases).
2. **Run**: Ensure you have **Java 17**, then run:
   `java -jar Lime.jar`
3. **Enjoy**: Give your work to **Lime!**

> **💡 Reminder**: Lime automatically creates a `data/tasks.txt` file in your directory to keep your tasks fresh even after a restart!

---

## 📖 Command Guide

### 1. Add Tasks with a Zest
* **Todo**: `todo [description]` - Adds a task to the pulp.
* **Deadline**: `deadline [description] /by [YYYY-MM-DD]` - Sets a expiration date for your juice.
* **Event**: `event [description] /from [time] /to [time]` - Schedules a fresh event.

> **💡 Reminder**: Use the `YYYY-MM-DD` format for Deadlines so Lime can sort them perfectly!

### 2. View the Pulp
* **List**: `list` - Shows all tasks currently in your bucket.
* **Sort**: `sort` - Sort all tasks by their ending dates.

### 3. View the Tasks
* **Find**: `find [keyword]` - Find a task contains a specific keyword.
*  **On**: `on [index]` - Find a task to be done at a specific time

### 4. Squeeze Out Tasks
* **Mark**: `mark [index]` - Marks a task as deliciously done.
* **UnMark**: `unmark [index]` -Marks a task as undone.
* **Delete**: `delete [index]` - Removes a task from the pulp-free list.

---

### 💻 Code Example

Here is a snippet of the `main` method:

```java
public static void main(String[] args) {
    new Lime("data/tasks.txt").run();
}
```
