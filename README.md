# mileio
An android app to track distance on trips to split gas bills.

#### Philip Seger, Bill Du, Danny Wolf

## App Proposal
Main idea is to build an app to track gas usage for people who share a car. This means that User 1 can log in, drive somewhere while the app is tracking where they are going, then stop tracking once they are done. When User 2 logs in and drives somewhere, their trip is tracked. If they stop off at the gas station and fill the tank, they can figure out how much the other users owe and can be reimbursed according to how much gas costs.


### APIs:
The two APIs.
- GPS
- Firebase

### The feature(s) you plan on implementing as MVP:
- Track driving distance
- Compute trip price based on car mpg
- Allow multiple users to split bills when sharing a ride.
- Start tracking your trip, then minimizes to a notification/service so you can use other apps (maps, music)

### Stretch goals
- Compute gas cost based on trip time (integrating with gas price api?)
- Settle up screen if you fill the car with gas (and how much)
- Huge level of depth depending on how much gas costs depending on where you are driving (highways, side streets, etc.)

### A quick write up on how you plan to divide up work and integrate features.
- Interface and service for logging in and starting tracking 
- Algorithm defining gas costs and tank fill cost and
- Firebase integration for multiple users and some easy form of authentication (security isn’t a huge issue)

### Where you plan to be in a week (Thur Oct 20). We will be doing informal check-ins to monitor your progress.
- Layouts and start of service development
- Firebase authentication for users (Philip and Danny have some experience in this)



