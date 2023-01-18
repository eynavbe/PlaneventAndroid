# Planevent_Android_App
Application for organizing an event in its entirety that includes everything the hall can offer for the customer's choice. <br />
The hall owner will add his hall to the system with all the additional services he can offer, for example: DJ, arrangement of the hall, gifts for guests...
Customers who close an on-site event with the hall owner will get access to the application and will be able to choose what their event will include both in terms of the menu and in terms of the things accompanying the event that the hall offers.

## Who is the system intended for:
- For hall owners - to be able to put their hall and services in the application.
-  For the customers of the halls - who will be able to choose what they want in their event comfortably and in an orderly manner in one place and produce an event that includes everything needed in the application.
-  For small businesses - the services that the hall offers beyond the basic menu and the renting of the hall will be done through collaborations with small businesses and so they too will be able to indirectly benefit from the application.

## Permissions:
- Event hall manager
- The owner of the event / customers of the halls


## Features:
- Management of the registration and login system: to allow the hall owner and the customer to enter the application and manage their event.

- Managing the details of the event: to allow the hall owner to add and edit the details of the event such as date and time, the last name and email of the client and to allow the client to manage the event which is possible only after the hall owner has registered his event.

- Management of services and additions: to allow the hall owner to add and manage the services he offers and allow the customer to choose from them.

- Account management: to allow the owner of the hall to manage the customers' accounts and allow the customer to view their accounts according to the customer's choices which can lead to an increase in the price and allow the customer to view the price increase for the services requested by him.


## branch:
In branch "master" - the code is displayed up to the point when on the date of the update a connection to firebase was made directly from android studio without using the server.
In branch "master_with_functions" - the code is shown where the connection is made between the android studio and then the request goes to the server and then to firebase

## Technological mapping to branch "master_with_functions":
- Android Studio - The development environment for building the applicationץ
- Node.js - The environment where the server's interface is created.
- Firebase - to store data in the cloud.

## Screen Application
### Splash Screen

<img src="https://user-images.githubusercontent.com/93534494/212079934-4840aa42-b391-4fba-aab8-955059514f66.png" width="170" height="350">

### start screen
When the user chooses which interface is relevant to him (customer or hall owner)

<img src="https://user-images.githubusercontent.com/93534494/212080290-5ecdfd93-699e-4e85-830a-4af948d7049a.png" width="170" height="350">

### home screen
On the client's home page there is a short explanation of the application and a countdown timer until the client's event according to the number of days, hours, minutes and seconds (according to the event that the hall owner registered).

<img src="https://user-images.githubusercontent.com/93534494/213214680-2a26f7e1-2fc0-4154-82ed-9f845bddf93a.png" width="170" height="350">

On the home page of the hall owner there is a short explanation of the application and a button to reach his profile value page.

<img src="https://user-images.githubusercontent.com/93534494/213214674-ed8ef587-9305-4bcc-b874-9a4de6c1862a.png" width="170" height="350">

Editing the profile of the hall owner to edit the details of phone number, maximum number of guests who can enter the hall, region/city of the hall.

<img src="https://user-images.githubusercontent.com/93534494/213214682-00e36d67-9b2c-4831-a13e-38e22062be3c.png" width="170" height="350">


### Registration + login

Customer login/registration,
Login/registration is done by: email + password or Gmail
Login is possible only if the user is registered as a "customer".
After registration, the customer is saved in
Cloud Database Firestore, 
Authentication

<img src="https://user-images.githubusercontent.com/93534494/212081054-ab926b95-070e-4db4-b0e6-78dd95e6b93a.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212081065-45628463-ea1f-4e11-bcd7-1e5c3124536a.png" width="170" height="350">

Entry to the owner of the hall is done by: email + password
Entry is only possible if the user is registered as the "hall owner".
Registration for the owner of the hall is carried out by: Mil
After registration, the customer is saved in
Cloud Database Firestore, 
Authentication

<img src="https://user-images.githubusercontent.com/93534494/212081091-b19b731e-399e-478d-95e5-19b97c637f9d.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212081098-8b531baf-78dc-48f4-a628-6ee3edfdc5f8.png" width="170" height="350">


### Event hall owner
This screen describes the list of events that the owner of the hall registered according to the details of the type of event, the date of the event, the last name of the customers.
This screen allows you to edit, delete and add an event.

<img src="https://user-images.githubusercontent.com/93534494/212081452-37acf1ac-7ed0-427f-941f-ee3155b17c3f.png" width="170" height="350">


Adding an event is done by filling in the details (type of event, date of the event, evening or afternoon event, number of guests, basic price that is closed, last name, email of the main customer and an additional email) after clicking on add, the event will be saved in the database and appear in the list of events.

<img src="https://user-images.githubusercontent.com/93534494/212081471-d2f5b09b-9b6e-4abf-8ce6-a4696f6a7779.png" width="170" height="350">

Deletion of an event will be done after the hall owner confirms it in the dialog that opens. After clicking yes, the event was deleted in the database and updated in the list of displayed events.

<img src="https://user-images.githubusercontent.com/93534494/212081488-c7d94106-edab-4009-8bcb-b3e62a3054cd.png" width="170" height="350">


Editing an event is done by entering all the details of the event in the relevant box and the hall owner will decide which information to update, by clicking on update the details will be updated in the database and in the displayed list.

<img src="https://user-images.githubusercontent.com/93534494/212081503-d2abfcfe-15bc-4e48-a954-61e39c0e9f0b.png" width="170" height="350">


### Hall owner - menu editing
Selecting a details type of editing from the menu

<img src="https://user-images.githubusercontent.com/93534494/212082135-cb4d33db-3f4f-4de6-8f4f-551831c460d5.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212082145-5ab19930-1224-4802-bfb6-b06df4e25414.png" width="170" height="350">


After selecting an item type for editing, a screen will be displayed describing the list of details that the hall owner has recorded that his hall offers, according to the details of the name of the item, whether it is included in the price or not, the price of the product (assuming it is not included in the price or the customer has reached the limit of what is included for free and can include it at an additional price) , the amount of products included in the price of all products.
This screen allows you to edit, add, delete and save items.
Clicking the X will delete the current box.
Editing of the details in the box is possible at any time because they are an edit box.
When you click on saving the items and only when you click on add, all the information displayed on the screen will be saved except for the boxes that do not contain complete information. The information will be saved in the database.

<img src="https://user-images.githubusercontent.com/93534494/212082302-ba66a0b1-8611-4988-955b-ba30bd312f94.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212082295-44570933-6b15-41d9-9f25-3c8a01abf500.png" width="170" height="350">



By clicking on add, a new box is added to fill in the name of the item, if it is included in the price, price, and a photo according to the gallery on the phone or to take a new photo.

<img src="https://user-images.githubusercontent.com/93534494/212082216-4516f952-be54-454e-9d72-62825a0e6356.png" width="170" height="350">


### Customer - choosing a hall
The list of halls registered in the database is described according to the details of hall name, city name, the number of people the hall can accommodate.

<img src="https://user-images.githubusercontent.com/93534494/212082566-d8824873-f7a8-498c-aa7f-bd866d1c92ff.png" width="170" height="350">


Clicking on one of the event boxes will direct the customer to the customer's home screen, assuming that the venue owner has registered the customer's event and recorded in the email information the same email that the customer entered with. If the venue that the customer has chosen does not have an event with the customer's email, then the option will appear for the customer to call the venue.

<img src="https://user-images.githubusercontent.com/93534494/212082616-ef6a45a0-eff7-466f-878a-196277e1f0e8.png" width="170" height="350">



### Customer - menu selection
Selecting a details type of selection from the menu

<img src="https://user-images.githubusercontent.com/93534494/212083199-613619a1-e23f-4a37-99d3-61d53e9d3565.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212083193-f015736a-74ed-4827-9809-516a7b5d7aa6.png" width="170" height="350">


After selecting the type of item to choose, a screen will be displayed that describes the list of details that the owner of the hall has registered as his hall. If the customer's choice has already been made according to the details, then according to the selections it will appear that it is a performance (choose the product will be marked and the price of the product has changed accordingly) offering according to the details product name, price and if you choose the product or not, the amount of products included in the price of all products.

The price of the product is according to the amount of products included in the basic price, if the customer chooses fewer products than the amount included in the price, then the products will be free.

The mark of select the product includes this product in the products that will be at the event (the product will appear with the owner of the hall)

On the screen it will be possible to save the customer's selections in the database by clicking on Save.

<img src="https://user-images.githubusercontent.com/93534494/212083238-5db0bbf4-cb32-4bf7-8836-499da55e38b2.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212083229-fb47e03d-f8e1-470b-88c7-05a4e1502d64.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212083241-77a18ac7-600c-4ae9-a5af-903e79cb80c5.png" width="170" height="350">

### Customer - price summary
The price summary is carried out by including the basic price with a check on each type of service with an additional payment made there and if so then the sum of all the products for which a payment has been added is made and will appear on the bill as an addition to the name of the services it offers. Then a sum will be made of all the additions in the account to the price that the customer has to pay.

<img src="https://user-images.githubusercontent.com/93534494/212083601-d2aef5e4-0ff3-418d-8c0a-a3494236dc22.png" width="170" height="350">


### Hall owner - customer choices + price summary
The account screen will display a summary of the prices that the customer needs to finalize according to the basic price set with the customer and the add-ons that the customer has added.

<img src="https://user-images.githubusercontent.com/93534494/212084002-6dba8ee1-6cf9-41cb-9c8b-035363300d1d.png" width="170" height="350">


By clicking on the customer's choices, the hall owner will have to choose the type in which the customer's choices will be displayed.
After selecting the type of item to choose from, a screen will be displayed describing the list of details that the customer has chosen, plus he has to add money to the product, the details that appear there are according to what the owner of the hall has recorded that his hall offers.
  The information is obtained by reading from the database
  
  <img src="https://user-images.githubusercontent.com/93534494/212084025-75a8cae5-d1a3-44f7-9f59-b454edb0b2ac.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212084044-aabb1283-adb4-41a7-a52a-cafd831571c4.png" width="170" height="350">
<img src="https://user-images.githubusercontent.com/93534494/212084052-e109a9f1-1578-48e0-8e71-26ba75c97c65.png" width="170" height="350">



## Tutorial Video
https://user-images.githubusercontent.com/93534494/213278658-f99e5e93-8d95-4da7-8c2f-8a3606efe16b.mp4


