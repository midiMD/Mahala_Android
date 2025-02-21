# Mahala - The Neighbourhood Lending App

The Romanian word 'mahala' originates from the Arabic محلة (‘mähallä’), brought to the Balkans by the Ottoman Empire (‘mahalle’). It means 'neighborhood'.

-----

## What is it?

Mahala is an app to facilitate the sharing of things between neighbours.

The ladder you're looking for may just be 2 doors down.

### Features
#### Inventory
![[images/Inventory.png|250]]

Add the items you're willing to lend, for free or a price. Only your **nearby** neighbors will be able to see them. 


### Login/Registration
![[Pasted image 20250113061639.png|250]] ![[Login_Reg.gif|250]]

Every user must register with their **home address** and have it **verified** before they can access the content. This is crucial to ensure that everyone can only see their "neighbours" and no other users.
Currently, home address verification has not been implemented.
**Potential solutions include**:
* Decentralised, community verification: A random subset of verified users in the area is selected to verify a new user's Proof of Address. If there aren't enough verified users in the area, then perhaps a larger group could be selected from anywhere in the same country. A high consensus threshold must be reached to successfully register a new user.
* Explore methods used by government entities to verify address using Proof of Address documents.
* Multiple sources of evidence required e.g. call + Proof of Address document
#### Progress
* UI and backend for Login and Registration
#### To Do:
* **Should**: Federated registration (Sign-in with Google)
* **Must**:  Home Address verification (see above)
* **Must**: Forgot Password 
* **Must**: Email Address verification
### Market
![[Market.gif|250]]
See if your neighbors
#### Progress
* UI prototype is largely complete
* Integration with backend and S3 image storage 
#### To Do
* **Must**: Search logic on the backend. Currently it retrieves all items within a 1000 miles X|
* **Should** : Implement Category Filtering on the backend
* **Should**: Sort By
* **Should**: Visual Design and Layout improvements
* **Maybe** "Nearby" criteria improvements
### Chat
![[Chat.gif|250]]
Chat in app then *take it outside*


## 🚀 Roadmap

### ✅ Core Features
Essential features required for the project's main functionality.
- **Authentication**
    - [x] Login and Registration UI and backend
    - [x] Password Reset
    - [ ] Address Verification - manually performed by devs ⏳
- **Market Screen**
    - [x] Detail - detail screen when item clicked 
    - [x] Search
- **Inventory Screen**
    - View user inventory
        - [x] Detail screen
        - [ ] Edit item ⏳
        - [x] Delete item
    - [x] Add a new item to the inventory
- [ ] **Chat** 🚧
- **Settings**
    - [x] Change Password
    - [ ] Change Email ⏳
### ✨ Additional Features
- [ ] Cross platform to IOS
- [ ] Cross platform to Web
- [ ] Address verification automation
- [ ] Indicator of whether an item is currently available. Currently, they have to chat.
- [ ] User reporting system
- [ ] Authenticate with Google

🔹 **Legend:**  
🚧 In Progress | ⏳ Planned 

### 🌀 Future Ambitions

"By the community, For the community" 

---


I envision an app to facilitate democratic decision making within communities.
Local decision-making has the unrivaled advantages of being very specific and highly representative of the stakeholders of that space.
When there aren't countless bureoucratic hoops to go through, works gets done quickly and efficiently, as the progress is transparent to the scrutiny of every member of the community.

For matters like:
* Voting on, raising funds and resources for a local project like a public facility, volunteering work, cleaning, tree etc




 

The ladder you're looking for may just be 2 doors down. 
An app to facilitate the sharing/lending of items between **nearby** neighbours. 




# Architecture
![[Pasted image 20250113075830.png]]