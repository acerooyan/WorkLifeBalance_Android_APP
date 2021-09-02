#App name Rester


Motivation

In today's society, people's work pressure and academic pressure are increasing. People ignore healthy and reasonable rest patterns when working or studying. Especially at this time, everyone is facing severe Covid-19, everyone needs to work or study at home. This also increases the time for everyone to work or study. People tend to continue to work or study without rest before finishing the task. There are three disadvantages to this working model. Firstly, it will make people unable to concentrate. Secondly, people’s work efficiency will be reduced, and thirdly, people will develop sedentary behavior. For students, high concentration is very important, because students are constantly learning new knowledge. For workers, work efficiency is an indispensable part, because higher work efficiency represents higher work ability of workers. Sedentary behavior is something everyone needs to pay attention to and avoid. Because sitting in a chair for a long time will greatly damage people's health, including heart disease and Type 2 diabetes. Sedentary behavior also increases our risk of death.In this special period, all people should avoid the impact of work and study on our health. I hope to be able to create an app to help people match their breaks during work. We hope that users can not only complete their work efficiently but also in a healthy mode.


State of the Art / Current solution

There are many similar mobile apps on the market and most software has a good number of qualities that software must have such as usability or functionality. Most of these apps are designed to help people monitor and improve people's work productivity. This kind of mobile app is fantastic at motivating users to increase productivity, such as through some reward mechanisms or competition rules. Features they had can effectively help improve work productivity, but they don’t seem to be a perfect balance between encouraging people to work and rest. Although they do have the function of advising users to rest properly while working. However, these features are too inflexible, and it's like they focus too much on ways to motivate and track productivity which results in most of the apps not being very concerned about providing users with scientific, effective, and personalized rest cycles to people.




We have built a system which recommends the work time and rest time based on the user’s work time, work location and weather. We use 3 data sources to build a personal model and 4 for the contextual information as follows:

Personal Model Data Sources:
  User’s Work Time Range based on mobile app (manual input)
  User’s Work Intensity based on mobile app (manual choose or recommend)
  User’s model source including user ID, location, work start time, work intensity and work time (store in Firebase)

Context Data Sources:
  Location Information from GPS.
  Weather Information based on weather.com API.
  User steps number from phone.
  Get current time from phone.

•	Developed and design a software to help users balance work and rest. 
•	Involved contextual personalized recommendation systems. 
•	Collect data from automated captured sensor data, such as geographical location and weather
• built in	Walker counter 
• firebase to collect user's data

