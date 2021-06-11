**Notes**
----
* You can use `docs/2-docker-compose.yml` compose file to start the application. When you start the compose file
wait a few minutes before accessing to endpoints. It can take some time to start all the services. (It takes on my pc:)

* After you have started the application and waited a few minutes, you can import `3-Example Scenario.postman_collection.json` into Postman and start testing endpoints. Requests are named and ordered. Please remember to add auth token to the requests after login request.

* `1-Architecture.png` shows initial architectural design of the application. Note that the logging service is not implemented.

* Application is designed as a microservice based application with "eventually consistent" database pattern. Spring Boot is the base framework of application. Services run on default Tomcat server. Every service is designed to have it's own database, but in the docker file i've used same database container for all services. Services use their own table(s) and never access others'.
* Choreography based saga pattern used to accomplish database consistency. Gateway pattern is used for authentication and request routing.
* Some controller methods and tests are not implemented because of time constraints.
* Application is developed using Java 11.
* PostgreSql database system is used. Database access is achieved using spring-data-jpa lib.
* RabbitMq used to implement messaging between different services. 
* Lombok library used to make create boilerplate code easier.


Below is the usage information about endpoints.

**Register Customer**
----
  Registers a customer.
* **Method:**
  `POST` /customer
* **Payload**
```javascript
    {
        "username": "yusuf",
        "password": "123asd"
    }
  ```
* **Success Response:**
```javascript
    {
        "username": "yusuf",
        "password": "123asd"
    }
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:41:40.929+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/customer"
    }
```

**Login Customer**
----
  Logins a customer.
* **Method:**
  `POST` /customer/login
* **Payload**
```javascript
    {
        "username": "yusuf",
        "password": "123asd"
    }
  ```
* **Success Response:**
```javascript
    {
        "username": "yusuf",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5dXN1ZiIsImlhdCI6MTYyMzQxNTQ3OSwiZXhwIjoxNjIzNDE5MDc5fQ.RikA2tTFT9KpSHH5qMtlYiO4YLf_H4UMX2_D2Gwa3OE"
    }
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:45:32.722+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/customer/login"
    }
```


**Save Book**
----
  Saves a book.
* **Method:**
  `POST` /book
* **Payload**
```javascript
    {
        "name": "The Hitchhiker's Guide to the Galaxy",
        "author": "Douglas Adams",
        "stock": 4,
        "price": 3.4
    }
  ```
* **Success Response:**
```javascript
    {
        "id": 1,
        "name": "The Hitchhiker's Guide to the Galaxy",
        "author": "Douglas Adams",
        "stock": 4,
        "price": 3.4
    }
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:48:33.375+00:00",
        "status": 400,
        "error": "Bad Request",
        "path": "/book"
    }
```

**Update Book Stock**
----
  Updates a book's stock.
* **Method:**
  `PATCH` /book/updateStock/:bookId/:stockValue
* **URL Params**
`bookId=[integer]`
`stockValue=[integer]`
* **Success Response:**
```javascript
    {
        "id": 1,
        "name": "The Hitchhiker's Guide to the Galaxy",
        "author": "Douglas Adams",
        "stock": 42,
        "price": 3.4
    }
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:52:36.916+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/book/updateStock/1/0"
    }
```



**Create Order**
----
  Creates an order.
* **Method:**
  `POST` /order
* **Payload**
```javascript
    {
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "bookIds": [1, 2, 3]
    }
  ```
* **Success Response:**
```javascript
    {
        "orderId": 1,
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "bookIds": [
            1,
            2,
            3
        ],
        "status": "PENDING_APPROVAL",
        "created": "2021-06-11T12:54:46.256439",
        "totalPurchasedAmount": 0.0
    }
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:55:22.665+00:00",
        "status": 400,
        "error": "Bad Request",
        "path": "/order"
    }
```



**Get Order**
----
  Gets details of an order.
* **Method:**
  `GET` /order/:orderId
* **URL Params**
`orderId=[integer]`
* **Success Response:**
```javascript
    {
        "orderId": 1,
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "books": [
            {
                "name": "The Hitchhiker's Guide to the Galaxy",
                "author": "Douglas Adams",
                "price": 3.4
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Jurassic Park",
                "author": "Michael Crichton",
                "price": 4.5
            }
        ],
        "status": "APPROVED",
        "created": "2021-06-11T12:54:46.256439",
        "totalPurchasedAmount": 13.65
    }
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:58:08.357+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/order/45"
    }
```




**Get Orders Between Dates**
----
  Gets details of an order.
* **Method:**
  `GET` /order/betweenDates/:startDate/:endDate
* **URL Params**
`startDate=[date]`
`endDate=[date]`
* **Success Response:**
```javascript
    [
    {
        "orderId": 1,
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "books": [
            {
                "name": "The Hitchhiker's Guide to the Galaxy",
                "author": "Douglas Adams",
                "price": 3.4
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Jurassic Park",
                "author": "Michael Crichton",
                "price": 4.5
            }
        ],
        "status": "APPROVED",
        "created": "2021-06-11T12:54:46.256439",
        "totalPurchasedAmount": 13.65
    },
    {
        "orderId": 2,
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "books": [],
        "status": "REJECTED",
        "created": "2021-06-11T12:55:07.092721",
        "totalPurchasedAmount": 0.0
    },
    {
        "orderId": 3,
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "books": [],
        "status": "PENDING_APPROVAL",
        "created": "2021-06-11T13:00:50.706427",
        "totalPurchasedAmount": 0.0
    },
    {
        "orderId": 4,
        "customerId": 1,
        "customerName": "Yusuf Kayan",
        "books": [
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            },
            {
                "name": "Kujo",
                "author": "Stephen King",
                "price": 5.75
            }
        ],
        "status": "REJECTED",
        "created": "2021-06-11T13:00:55.893452",
        "totalPurchasedAmount": 74.75
    }
]
```
  
* **Error Response:**
```javascript
    {
        "timestamp": "2021-06-11T12:58:08.357+00:00",
        "status": 500,
        "error": "Internal Server Error",
        "path": "/order/45"
    }
```




**Get Monthly Statistics**
----
  Gets monthly statistics of a customer.
* **Method:**
  `GET` /statistics/:customerId
* **URL Params**
`customerId=[integer]`
* **Success Response:**
```javascript
    {
        "0": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "1": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "2": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "3": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "4": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "5": {
            "totalOrderCount": 1,
            "totalBookCount": 3,
            "totalPurchasedAmount": 13.65
        },
        "6": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "7": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "8": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "9": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "10": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "11": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        }
    }
```
  
* **Error Response:**
```javascript
    {
        "0": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "1": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "2": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "3": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "4": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "5": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "6": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "7": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "8": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "9": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "10": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        },
        "11": {
            "totalOrderCount": 0,
            "totalBookCount": 0,
            "totalPurchasedAmount": 0.0
        }
    }
```