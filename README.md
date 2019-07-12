# TaskMaster Application
This is an application that allows user to create, update, or view tasks. Routes available are:



## Link to site
[Frontend](http://taskmanagerfrontend.s3-website-us-west-2.amazonaws.com/)
* Current working feature: 
    * Status Update of user
    * Upload image and redirect to see image in description

[Backend](http://taskmanager.us-east-2.elasticbeanstalk.com)
* /tasks - View all tasks
* /tasks with title and desc parameters - Create new Task
* /tasks/{id}/status - Change user's task status
* /tasks/{name}/tasks - View assigned user's tasks
* /tasks/{id}/images - add image to user

## Lambda
Warmup Ping
![Lambda](https://github.com/idothestamping/taskmaster/blob/imgFeature/src/main/resources/img/Screen%20Shot%202019-07-12%20at%2011.40.18%20AM.png)
