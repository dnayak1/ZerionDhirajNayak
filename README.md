# ZerionDhirajNayak

The app has a user authentication functionality that call a login api developed in node.js and hosted in aws.
The database for user authenticatication is also in aws so the app is available globally.

The testing userid/password is--
email = dhiraj@gmail.com
password=password

The app contains 4 activities for user login, UserIdList activity, user detail activity and Main Activity.

Material design is used for Login UI and User Detail UI.
Implementation of Async task and Retrofit is demonstrated in the app with the use of other basic android libraries like recyclerView, Piccasso and libraries for generating authorization token.

Steps,
1. user logs in (dhiraj@gmail.com, password)
2. User redirected to User Id list ACtivity where the appropriate api is called with valid authorization token.
3. Clicking on any id will take the user to DeatilRecord Activity where user can see the details for particular id.
