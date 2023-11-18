# tuition-tracker-kmm
Tuition Tracker(Kotlin Multiplatform Mobile) - The Tracking app of tuitions for teachers available in both Android and iOS..

### Purpose  

To have a dedicated tracking application for tuition teachers about their student details and tracking their work/activities in organised way.

### Usecases

* Showing List of Students

* Add student details like name, age, gender, standard.

* We can add activities in four forms,

  * Gender wise
    * Male -> Automatically assign the activity to All male students.
    * Female -> Automatically assign the activity to All female students.
  
  * Standard Wise
    * 1 -> Automatically assign the activity to All 1st standard students
    * 2 -> Automatically assign the activity to All 2nd standard students
    * 3 -> Automatically assign the activity to All 3rd standard students
    * 4 -> Automatically assign the activity to All 4th standard students
    * 5 -> Automatically assign the activity to All 5th standard students
    * 6 -> Automatically assign the activity to All 6th standard students
    * 7 -> Automatically assign the activity to All 7th standard students
    * 8 -> Automatically assign the activity to All 8th standard students
    * 9 -> Automatically assign the activity to All 9th standard students
    * 10 -> Automatically assign the activity to All 10th standard students
    * 11 -> Automatically assign the activity to All 11th standard students
    * 12 -> Automatically assign the activity to All 12th standard students
  
  * ALL 
    * All -> Automatically assign the activity to all the students
  
  * Individual
    * {student_id} -> Automatically assign the activity to the student who is having the id as studentId

* Student Details by selecting student in students list.
  * Showing List of assigned activities for this particular student.
  * Changing activity completion status as complete/incomplete.
  * Adding Individual activity for this particular student.

* Showing list of all activities.

* Add Gender-based, Standard-based, All-based activities

* Activity details by selecting activity in activities list.
  * Showing list of assigned students for this particular activity.
  * Changing activity completion status as complete/incomplete.

### Screens  

* Bottom navigation with two tabs
  * Students
  * Activities

* Students has start destination as **StudentsList** Screen

* StudentsList Screen
  * Student Rows -> Navigating to **StudentDetails** Screen
  * Navigation icon to **AddStudent** screen.

* StudentDetails Screen
  * Showing student full details.
  * Showing list of assigned activities for this student.
  * Navigation button to **AddWorkActivity** Screen to add Individual Activity for this student by passing student_id as argument

* Activities has start destination as **ActivitiesList** Screen

* ActivitiesList Screen
  * Activity Rows -> Navigating to **ActivityDetails** Screen.
  * Navigation icon to **AddWorkActivity** Screen to add **(Gender, Standard, All)** based activities.  


### Technologies  

#### Android
* **Kotlin**
* **Jetpack Compose**
* **Jetpack Navigation Compose**
* **Material3 Theme for dark/light**
* **Kotlinx Coroutines**
* **Enter/Exit Navigation Animations in Jetpack Compose**
* **MVVM Architecture**

#### Shared
* **SQLDelight** for both Android and iOS SQLite database.
* **Kotlin**
* **Kotlinx Coroutines**
* **Expect/Actual Implementations**

#### IOS
* **SwiftUI**
* **Asynchronous Programming in Swift**
* **MVVM Architecture**

