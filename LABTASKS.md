# Lab Tasks <!-- omit in toc -->

- [Notes on notation and structure](#notes-on-notation-and-structure)
- [Exploring the project](#exploring-the-project)
- [Exploring the server](#exploring-the-server)
  - [Use Thunder Client to explore API output](#use-thunder-client-to-explore-api-output)
- [Exploring the client](#exploring-the-client)
- [Use GitHub Projects to support Agile development](#use-github-projects-to-support-agile-development)
  - [Setting up the GitHub Projects board](#setting-up-the-github-projects-board)
  - [Using the board](#using-the-board)
- [The epics/features](#the-epicsfeatures)
- [Questions](#questions)

## Notes on notation and structure

* Questions that you need to answer (as a group!) are indicated with question mark symbols (:question:).
* The [Questions](./LABTASKS.md#questions) section is at the end of this document.
* Tasks that specify work to do without a written response will be bulleted.

Responses to questions should be submitted as specified below (in the [QUESTIONS](./LABTASKS.md#questions)).

If you're ever confused about what you need to do for a given task, ask.
Similarly, if you're just not sure what's going on or what something does, or
how it does it, please ask! There's a _lot_ going on here, and if you're not
confused now and then you're probably not paying attention. :smile:

Before completing these lab tasks, make sure you have read through [`README.md`](./README.md) and completed the following:

* Set up your project [`README.md`](./README.md#setup)
* Run your server: [run configuration](./README.md#running-your-project)
* Run your server tests: [testing your server](./README.md#testing-your-project)

## Exploring the project

Look over the directory structure of the project before you startmobile devices, d
either by working on a different part of the lab, or by
doing something unrelated to Software Design. Then come back
back to that _as a team_ and review the requirements
described in the issue and compare them to the functionality
you implemented. Is the issue _done done_? Are there solid
and complete tests that back up the work? Can you break it?
Have you tried? Would you bet your career (or at least your
next raise) on this working in a customer demo or out in the
field?

If you find bugs, document them, either in the existing issue, or through new issues. Then go back to working in
the feature branch for that issue, and repeat the whole
process.

Once the issue passes review, you should

* Merge the associated feature branch into master by accepting the (perhaps modified) pull request
* Move the issue to the `Done` track (or, fee free to create more tracks as you see fit)
* There are ways to automate the moves through the tracks based on what's happening in GitHub, but we won't look at that in detail for this lab.

## The epics/features

The initial server (Java) code demonstrates reading in a
collection of (randomly generated) user data, and making it
available (with filtering) via the simple API you explored above.
The client (JavaScript/HTML/CSS) demonstrates using simple forms
that allow the user to make requests of the server and see the results.

Your goal in this lab is to use test-driven development (TDD) to
extend the server's API to support serving 'to-do' data in such a way that it works with the provided client.
We **do** want you to write JUnit tests for the server functionality you
add, but you don't need to worry about the JavaScript code or how one
might test it. (We'll end up using some nice testing
tools that integrate with Angular.)

There is a file `data/todos.json` that has several hundred randomly
generated "to-do"s, each of which has:

* A unique `_id`
* An `owner`
* A `status` (which is a boolean - is the task completed or not)
* A `body` that describes the task
* A `category`

Below are the various features we'd like to see you implement in this lab. You should
create an epic for each of the features listed below, adding at issues as appropriate.

At the very least (necessary to get 85% of this part of the lab)
you should implement (and create meaningful server-side tests for) the following features:

* List all the todos
  * Implement an `api/todos` server-side endpoint, which returns all the to-dos
* List a single todo by ID
  * Implement an `api/todos/58895985c1849992336c219b` server-side endpoint, which
    returns the single todo with the given `_id`. It should return a 404
    (use the Javalin `NotFoundResponse` class) if there is no todo with the
    specified `_id`.
* Support limiting the number of todos that are displayed
  * Implement an `api/todos?limit=7` API endpoint, which lets you specify the maximum
    number of todos that the server returns.
* Support filtering todos by their status (either complete or incomplete)
  * Implement an `api/todos?status=complete` (or `incomplete`) endpoint which lets you
    filter the todos and only return the complete (or incomplete) ones
  * Note that the "database" stores the status as a boolean, but the endpoint uses
    "complete" and "incomplete". You'll have to implement the (simple) logic that
    transforms the endpoint "language" into the database terminology.
* Support searching for todos whose _bodies_ contain a given string
  * Implement an `api/todos?contains=banana` endpoint which lets you search for to-dos
    whose _bodies_ contain (anywhere) the given string (in this case "banana").

To get full (100%) credit on this part of the lab you should
implement (and create meaningful tests for) these additional features:

* Filter todos by owner
  * Implement the endpoint `api/todos?owner=Blanche` which returns just the to-dos
owned by Blanche
* Filter todos by category
  * Implement the endpoint `api/todos?category=groceries` which returns just the to-dos
in the `groceries` category
* Allow for ordering/sorting of todos by a particular attribute
  * Implement the endpoint `api/todos?orderBy=owner` (or `body`, `status`, or `category`)
    which sorts the returned to-dos alphabetically by the specified field

For full credit you also need to support arbitrary combinations
of these filters, e.g.,

```http
api/todos?owner=Blanche&status=complete&limit=12&orderBy=category
```

which would return the first 12 completed to-dos owned by
Blanche ordered by category. Make sure you do the limiting step last so you don't miss any items.

Each of these if implemented properly should work in the provided client.

---

## Questions

Write up your answers to these questions in a Google Doc and turn that in via
Canvas on the assignment for this lab.

:bangbang:

* [ ] __Make sure that everyone in your group has edit privileges on the document.__
* [ ] __Make sure that the link you turn in gives us at least comment privileges.__
* [ ] __Include the URL of the GitHub repository for your group at the top of the
   GDoc. This will make it easier for us to figure out which team is "Snoozing Llamas".__
  
:bangbang: Make sure that your answers address the _purpose_ of
these tools. Don't just tell us _what_ something does, indicate
_why_ we'd want to have it.

:question: *1* What is the purpose of `.gitignore`?
([Maybe search for `.gitignore`?](https://www.google.com/search?q=.gitignore))

:question: *2* What role is Gradle playing in the
project, and what is the purpose of `build.gradle`?

:question: *3* What is the purpose of Github Actions?

:question: *4* Explain what an _endpoint_ is (also often called a _route_). (You might look at the
[Javalin](https://javalin.io/documentation#endpoint-handlers)
documentation for some help here.)

:question: *5* What is the purpose of `umm3601.Server` class?
What is the purpose of the `umm3601.user.UserController` class?
Explain what happens when a user accesses each of the
following URLs:

* :question: The page `users`
  * <http://localhost:4567/users.html>
* :question: The page `api/users`
  * <http://localhost:4567/api/users>
* :question: The page `api/users?age=25`
  * <http://localhost:4567/api/users?age=25>
* :question: The page `api/users/588935f5de613130e931ffd5`
  * <http://localhost:4567/api/users/588935f5de613130e931ffd5>

:bangbang: If you have your project running (see the README), these links should
actually work and generate results from your server.

:question: *6* What are the contents of the `client` folder? What is the purpose of each of the HTML files there?

:question: *7* Describe what happens when you filter users by
age in the client?

* What information is read from the web page, and where is it read from?
* What request is sent to the server?
* What reply does the server send back to the client? How is that constructed?
* What is received by the client, and how/where is it displayed?

:question: *8* Where is the client-side JavaScript defined? Name the HTML file(s) that
load and use it.
