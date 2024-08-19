# CMS: Candidates Management System

## Goal
This project was developed to allow the management of candidates to DEI fellowships (or grants),
and the management of these fellowships. The features are explained below.
Implementation decisions are explained at the end.

## Features
The CMS subsystem allows different functionalities through two main pages that redirect to others: the candidates page and the fellowships page.
Implementation decisions are explained at the end.

### Candidates page
Navigate to the candidates page by clicking on the "Candidates" button on the navigation bar.
#### Register new candidates
This is accomplished by clicking on the "**Add a New Candidate**" button, and correctly filling the form.
#### Update and delete candidates
The new candidates are added to the candidates list in this page. You can click the **pen icon** to **edit** the candidate's information, 
or the **trash icon** to **delete** the candidate.
#### View candidate's details
Click on the candidate's **eye button** to view their details. You will be redirected to a new page with all the candidate's information:
name, email, istID, photo, and a list of all the fellowships they have applied to.

### Fellowships page
Navigate to the fellowships page by clicking on the "Fellowships" button on the navigation bar.
#### Adding new fellowships
This is accomplished by clicking on the "**Add a New Fellowship**" button, and correctly filling the form.
#### Update and delete fellowships
Similarly to the candidates, you can click the **pen icon** to **edit** the fellowship's information,
or the **trash icon** to **delete** the fellowship.
#### View fellowship's details
Click on the fellowship's **eye button** to view its details. You will be redirected to a new page -- **fellowship details page**.
#### Manage fellowship candidates
Click on the fellowship's **account group button** to manage its candidates. You will be redirected to a new page -- **fellowship candidate management page**.

### Fellowship details page
This page shows all the information about a specific fellowship: its name, description, start and end dates, monthly value,
and below, **list of all candidates that have applied to it**. You can also go to the **fellowship candidate management page**. 
Here, you can:
#### Evaluate candidates
For any candidate, click on the "View Evaluation" button to evaluate the candidate; if the evaluation was already made,
you can see the **grade for each category** and the **final grade** (all between 0 and 20).
You can also edit the evaluation by clicking on the "Edit Evaluation" button.
#### Change the evaluation weights
Click on the "Change Evaluation Weights" button to **change the weights** for each evaluation category (value between 0 and 1, total sum of 1).
You can also **add or remove evaluation categories** for this particular fellowship.

### Fellowship candidate management page
This page shows all existing candidates and their basic info, and you can either enroll or unenroll them in the fellowship by clicking on the available
button (either **"Enroll"** or **"Unenroll"**).

## Future changes
These are some changes that I want to eventually implement:
- Since evaluation categories can be added or removed from a fellowship,
guarantee that any evaluation that was previously made is still valid


## Implementation Decisions
Given the described features to be implemented, I decided to create a fellowship and candidate class for evident reasons.

Since a candidate can be enrolled in multiple fellowships, and a fellowship can have multiple candidates, I decided to create an 
enrollment class to simplify each of these relationships. Adding, updating, and deleting candidates and fellowships is done through
the respective classes.

To enroll or unenroll a candidate in a fellowship, I simply create or 
delete an enrollment object, which contains a candidate, a fellowship and an evaluation. To ensure that no duplicate enrollments are created,
the combinate of the fellowship and candidate IDs must be unique.

The evaluation class was created to represent the evaluation of a candidate in a specific fellowship. Since that relationship is unique and
represented by an enrollment, I decided to directly relate the evaluation class to an enrollment, through which I can access anything
necessary - so, the evaluation simply contains an enrollment and the candidate's grades for each category. This class also allowed
a clean separation of concerns, since all requests related to evaluations are done to its controller endpoints.

As the evaluation weights are specific to each fellowship, they are stored in the fellowship class to allow different fellowships to 
manage their evaluation weights accordingly. The final score is calculated in the evaluation class, since it has the candidate's scores
and can access the fellowship weights. Adding, removing or editing the evaluation categories wasn't required, but it is a possible
improvement point - they are currently an Enum, and not very extensible via the API.

I also added an error package where I experimented a lot, and ended up having a GlobalExceptionHandler that works together with a 
specific exception handler class (CMSException) - this was done with a lot of testing in Postman and trying to understand what would
work best and be simple.
