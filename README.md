# 🏪 Store House Management System [BableshAAzad.com](https://www.bableshaazad.com)
- It is RESTful web service using spring boot
- It provided to store products in Warehouses which is used to store products using e-commerce-applications for demo visit on [ecommerce.BableshAAzad.com](https://ecommerce.bableshaazad.com)
- Here, I am used basic fundamental of Warehouse principal and working.
- You can use that project for other e-commerce applications it makes easy to maintain and calculate your inventory records.
- For Seen API Request and Response [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe)

**🏠 Features:**
> Store inventories

> It generates purchase order

>Seller Buy space to store inventories

>Record and space auto maintain

>Search warehouse based on nearby your location

**🧑‍💻 Technologies used here:**
`spring boot` `Spring-Security` `RESTful-API` `MySql` `Opern-AI-Documantaion` `Validation` `Openpdf` `openpdf` `hateoas`
---
**💻 How To Use:**
#### 🚗 <span style="color: green">Method-1 Using online </span>
- You can directly hit the Url https://store-house-management-system.onrender.com based on requirement 
- Take guid of how to send request and response [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe)

#### 🚐 <span style="color: green">Method-2 Using Docker </span>
- you can pull the docker file `bableshaazad/storehousemanagementsystem`
- Take guid of how to send request and response [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe)
- Set Environment variables see below 👇

#### 🚒 <span style="color: green">Method-3 Set your own server </span>
- Download master branch zip file
- Import in you IDE and needed JDK 21
- Set Environment variables like 
- 1. `DB_HOST_NAME`= localhost
- 2. `DB_NAME`= store-house-management-system
- 3. `DB_PASSWORD`= root
- 4. `DB_PORT`= 3306
- 5. `DB_USERNAME`= root
- Take guid of how to send request and response [store-house-management-system](https://documenter.getpostman.com/view/32067662/2sAXjJ7DUe)
---
### Examples 
- for find products http://localhost:8081/inventories?page=0&size=1
````java
{
    "status": 200,
    "message": "Inventories are Found",
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
                "materialTypes": [
                    "WOOD"
                ],
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
````
- For Client Registration http://localhost:8081/clients/register
#### Request:
````java
{
    "businessName" : "Bableshaazad.com",
    "email" : "bableshaazad@bableshaazad.com",
    "contactNumber" : 7898300815
}
````
#### Response:
````java
{
        "status": 201,
        "message": "Client Created",
        "data": {
           "apiKey": "29e420c3-7a6a-47d7-9342-3b3ebdcbe394",
           "username": "bableshaazad@bableshaazad.com",
           "clientId": 2
        }
}
````