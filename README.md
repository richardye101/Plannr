# Plannr

Plannr is an android app meant for students attending the University of Toronto Scarborough Campus.

Plannr helps students plan their timetables by telling them which courses they can take, 
based on courses they have taken, and when those courses are available.

## Contributors
- Richard Ye
- Nathan Lam
- Matthew Xie
- Kristi Dodaj
- Andrew Yang

## User stories

### 1. As a student, I want to sign up and login to my account so that I can securely access my data.

The first thing the user sees is the login page. If they don't have an account, they can goto the register page to create one.

<img src="https://github.com/richardye101/Plannr/blob/main/images/login/Screen%20Shot%202022-12-04%20at%2012.37.38%20PM.png" width=25%>

On this page they are able to register and create an account. This creates a FirebaseAuth account for them, and an object in the Firebase Realtime Database to store user related information.
<img src="https://github.com/richardye101/Plannr/blob/main/images/login/Screen%20Shot%202022-12-04%20at%2012.37.35%20PM.png" width=25%>
<img src="https://github.com/richardye101/Plannr/blob/main/images/login/Screen%20Shot%202022-12-04%20at%2012.37.31%20PM.png" width=25%>

They will be redirected to the login page after successful registration, and they can then login.

<img src="https://github.com/richardye101/Plannr/blob/main/images/login/Screen%20Shot%202022-12-04%20at%2012.37.08%20PM.png" width=25%>

Once login is successful, their user data is pulled from Firebase and stored locally in a `StudentUserModel` singleton object.

### 2. As a student, I want to view the list of courses I have taken and add to this list so that I can keep track of all the courses I have taken.

Once the student has signed in, they are taken to a page displaying all the courses they have taken.
There is a button to add courses to the list of courses they have taken.
<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%2012.42.03%20PM.png" width=25%>

If the Add taken courses button is pressed, they are taken to a page where they can enter the course code of the course they have taken, and on completion it will be added to their user profile locally and in the Realtime Database.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%2012.40.03%20PM.png" width=25%>

### 3. As a student, I want to generate a course timeline by providing courses I want to take so that I can plan my education accordingly.

There is also a button to generate a timeline. The student can input the course codes of courses they would like to take.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.56.51%20PM.png" width=25%>

Once they click generate, they are then able to view a timeline of the courses they need to take by session.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.56.53%20PM.png" width=25%>

### 4. As an admin, I want to login to my account so that I can securely manage course information.

Admins are able to login to the application the same way students login.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%203.00.07%20PM.png" width=25%>

The difference is that in our database, we can set whether a user is an admin using the `isAdmin` boolean.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%2012.56.37%20PM.png" width=25%>

This takes them to a different fragment which displays all the courses being offered.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.59.54%20PM.png" width=25%>

### 5. As an admin, I want to add a course and define its name, course code, offering sessions, and prerequisites so that a student’s timeline could be generated correctly.

At the top of the page, the admin has the ability to add a new course. 

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.21.23%20PM.png" width=25%>

They are then directed back to the courses overview with a toast message indicating the course was added successfully.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.31.31%20PM.png" width=25%>

### 6. As an admin, I want to view the list of all courses and edit or delete any course in the list so that I can keep the course information up to date.

As shown above, the admin will see all the courses being offered when they login.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.59.54%20PM.png" width=25%>

When they click on a course, they are able to edit or remove it.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.31.43%20PM.png" width=25%>

They can change all details that were available during course creation. They also have the option of removing that course entirely.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.32.15%20PM.png" width=25%>
<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.32.07%20PM.png" width=25%>

If they edit the course, they are then taken back to the course overview page with a toast message indicating the course was updated successfully.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.32.08%20PM.png" width=25%>

If they remove the course, they are then taken back to the course overview page with a toast message indicating the course was removed successfully.

<img src="https://github.com/richardye101/Plannr/blob/main/images/Screen%20Shot%202022-12-04%20at%202.32.18%20PM.png" width=25%>
