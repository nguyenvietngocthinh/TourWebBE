# Tour Website

## Overview
Tour Website is a responsive web application with an admin panel for managing tours, users, and orders. The platform integrates secure cloud storage and automated email notifications to streamline project management and improve user experience.

## Features
- **Admin Panel**: Manage tours, users, and orders with CRUD functionality.
- **Email Notifications**: Java Mail Sender integration for automated communication.
- **Secure User Authentication**: Passwords are encrypted with BCrypt.
- **Cloud Storage**: AWS S3 and IAM for secure storage of user data and images.
- **Responsive Design**: Optimized for seamless use across devices.

## Tech Stack
- **Backend**: Spring Boot, MongoDB.
- **Frontend**: ReactJS, Ant Design, Bootstrap.
- **Cloud Services**: AWS S3, IAM.
- **Utilities**: Java Mail Sender, BCrypt.

## Deployment
Designed for cloud hosting with scalable backend and responsive frontend.

## Installation
1. Clone the repositories:
   - Backend:  
     ```bash
     git clone https://github.com/nguyenvietngocthinh/TourWebBE.git
     ```
   - Frontend:  
     ```bash
     git clone https://github.com/trngmhuu/FEAdmin.git
     ```
2. Configure database and cloud credentials in the backend.
3. Start the backend server:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Start the frontend:
   ```bash
   npm install && npm start
   ```
5. Open the application in a browser.

## How to Use
1. Admins can log in to manage products, users, and orders.
2. End users can browse tours, place orders, and receive notifications.

## Project Highlights
- **Admin Efficiency**: Intuitive panel for streamlined management.
- **Enhanced Communication**: Automated email notifications using Java Mail Sender.
- **Data Security**: Secure storage and encrypted passwords ensure user data protection.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---

**GitHub Repositories**:
- **Backend**: [TourWebBE](https://github.com/nguyenvietngocthinh/TourWebBE)
- **Frontend**: [FEAdmin](https://github.com/trngmhuu/FEAdmin)
