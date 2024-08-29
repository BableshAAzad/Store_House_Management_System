## 🏪 Store House Management System API [BableshAAzad.com](https://www.bableshaazad.com)
- This project is a RESTful web service built using Spring Boot.
- It allows storing products in warehouses, which can be used by e-commerce applications. For a demo, visit [ecommerce.BableshAAzad.com](https://ecommerce.bableshaazad.com).
- The project is based on basic warehouse management principles.
- You can integrate this project with other e-commerce applications to simplify inventory management and tracking.

**📜 API Documentation:**
- View API postman requests and responses: [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe)
- View ecommerce shopping app frontEnd documentation [ecommerce-shopping-app-ui](https://github.com/BableshAAzad/ecommerce_shopping_app_ui)
- View ecommerce shopping app api documentation [ecommerce-shopping-app-api](https://github.com/BableshAAzad/Ecommerce_Shopping_App)

**🏠 Features:**
- Store and manage inventories.
- Generate purchase orders.
- Allow sellers to lease space for storing inventories.
- Automatically manage records and space utilization.
- Search for warehouses based on proximity to your location.

**🧑‍💻 Technologies Used:**

`Spring Boot` `Spring Security` `RESTful API` `MySQL` `Spring-data-jpa` `OpenAPI Documentation` `Validation` `OpenPDF` `HATEOAS`

---

**💻 How To Use:**

#### 🚗<u> Method 1: Using Online Service </u>
- You can directly access the service via [https://store-house-management-system.onrender.com](https://store-house-management-system.onrender.com).
- Refer to the API documentation for guidance on sending requests and handling responses: [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe).

#### 🚐 <u>Method 2: Using Docker </u>
- Pull the Docker image: `bableshaazad/storehousemanagementsystem`.
- Refer to the API documentation for guidance on sending requests and handling responses: [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe).
- Set the necessary environment variables as shown below 👇.

#### 🚒 <u>Method 3: Setting Up Your Own Server</u>
- Download the master branch as a zip file.
- Import the project into your IDE and ensure JDK 21 is installed.
- Set the following environment variables: 🔐
    >1. `DB_HOST_NAME`= localhost
    >2. `DB_NAME`= store-house-management-system
    >3. `DB_PASSWORD`= root
    >4. `DB_PORT`= 3306
    >5. `DB_USERNAME`= root
- Refer to the API documentation for guidance on sending requests and handling responses: [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe).
- For swagger documentation `http;//localhost:8080/swagger-ui.html`

---

#### 📝 Examples

- **Find Products:**

  **Endpoint:** `GET http://localhost:8081/inventories?page=0&size=1`


  **Response:**
  ```json
  {
      "status": 200,
      "message": "Inventories found",
      "data": {
          "links": [],
          "content": [
              {
                  "inventoryId": 1,
                  "productTitle": "Chair",
                  "lengthInMeters": 0.7,
                  "breadthInMeters": 0.8,
                  "heightInMeters": 0.8,
                  "weightInKg": 8,
                  "price": 300,
                  "description": "Chair for general use",
                  "productImage": "http://res.cloudinary.com/dpaf0bjfx/image/upload/c_fill,h_500,w_500/85010851-aafe-4d8e-9146-69e6b1b5c516",
                  "materialTypes": ["WOOD"],
                  "restockedAt": "2024-08-27",
                  "updatedInventoryAt": null,
                  "sellerId": 1,
                  "stocks": [
                      {
                          "stockId": 5,
                          "quantity": 10
                      }
                  ],
                  "discount": 2,
                  "discountType": "NEW"
              }
          ],
          "page": {
              "size": 1,
              "totalElements": 12,
              "totalPages": 12,
              "number": 0
          }
      }
  }
  ```

- For Client Registration **Endpoint** `http://localhost:8081/clients/register`
#### Request:
```json
{
    "businessName" : "Bableshaazad.com",
    "email" : "bableshaazad@bableshaazad.com",
    "contactNumber" : 7898300815
}
```
#### Response:
```json
{
        "status": 201,
        "message": "Client Created",
        "data": {
           "apiKey": "29e420c3-7a6a-47d7-9342-3b3ebdcbe394",
           "username": "bableshaazad@bableshaazad.com",
           "clientId": 2
        }
}
```