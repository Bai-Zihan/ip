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
  *  e.g. todo read a book
* **Deadline**: `deadline [description] /by [YYYY-MM-DD]` - Sets an expiration date for your juice.
  * e.g. deadline submit ip /by 2026-02-20
* **Event**: `event [description] /from [YYYY-MM-DD] /to [YYYY-MM-DD]` - Schedules a fresh event.
  * e.g. event team meeting /from 2026-01-01 /to 2026-01-01

> **💡 Reminder**: Use the `YYYY-MM-DD` format for Deadlines so Lime can sort them perfectly!

### 2. View the Pulp
* **List**: `list` - Shows all tasks currently in your bucket.
* **Sort**: `sort` - Sort all tasks by their ending dates.

### 3. View the Tasks
* **Find**: `find [keyword]` - Find all tasks contains a specific keyword.
  * e.g. find read a (returns the todo task or other tasks with "read")
*  **On**: `on [YYYY-MM-DD]` - Find all tasks to be done at a specific time
  * e.g. on 2026-02-20 (returns the ip deadline task)

### 4. Squeeze Out Tasks
* **Mark**: `mark [index]` - Marks a task as deliciously done.
  * e.g. mark 1 (marks the todo task)
* **UnMark**: `unmark [index]` -Marks a task as undone.
  * e.g. unmark 1 (unmarks the todo task)
* **Delete**: `delete [index]` - Removes a task from the pulp-free list.
  * e.g. delete 2 (deletes the ip deadline task)

### 5. Say bye to Lime
* **Bye**: `bye` - Leaves Lime to grow juicier.

---

## 📜 Command Summary

| Action                           | Format                                                    |
|----------------------------------|-----------------------------------------------------------|
| Add Todo                         | `todo [description]`                                      |
| Add Deadline                     | `deadline [description] /by [YYYY-MM-DD]`                 |
| Add Event                        | `event [description] /from [YYYY-MM-DD] /to [YYYY-MM-DD]` |
| Show all Tasks                   | `list`                                                    |
| Sort all Tasks                   | `sort`                                                    |
| Find specific tasks by name/date | `find [keyword]`/`on [YYYY-MM-DD]`                        |
| Mark/Unmark	                     | `mark [index]`/`unmark [index]`                           |
| Delete                           | `delete [index]`                                          |
| Exit                             | `bye`                                                     |

---

### 💻 Code Example

Here is a snippet of the `main` method:

```java
public static void main(String[] args) {
    new Lime("data/tasks.txt").run();
}
```