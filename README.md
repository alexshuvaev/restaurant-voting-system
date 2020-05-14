# Restaurant voting app #
> https://topjava.ru graduation project.
>
> Software requirements in REQUIREMENTS.md file.

### Technology stack:
* Java 11
* Spring Boot 2.5
* Spring Security
* Spring DATA JPA
* Hibernate
* H2 Database
* EhCache 3
* JUnit Jupiter/Mockito
* Maven 3
* REST (Swagger)


## Steps to Setup

**1. Go to the "master" branch and clone the application**

```bash
git clone https://github.com/alexshuvaev/restaurant-voting-system.git
```

**2. Build and run the app using maven**

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

**3. Explore Rest APIs**

Go to <http://localhost:8080/swagger-ui.html>

Or use API documentation below.
____

## API documentation ##
Requests to the API are made via the HTTP protocol with basic authorization. 
POST and PUT request's content must be transferred in JSON format, the Content-Type header must be set to "application/json".

### API sections ##
-   [**_Guest/User/Admin API_**](#Guest/User/Admin-API)  
    Allowed without authorization.
    -   [get all restaurants](#get-all-restaurants)  
    -   [get all menus](#get-all-menus)  
    -   [get single restaurant menu](#get-single-restaurant-menu)  
    
-   [**_User API_**](#user-api)  
    Allowed only for authenticated users.
    -   [get user votes history](#get-user-votes-history)  
    -   [vote for restaurant](#vote-for-restaurant)  

-   [**_Admin API_**](#user-api)  
    Allowed only for authenticated admin.
    -   [create restaurant](#create-restaurant)  
    -   [create menu](#create-menu)  
    -   [update restaurant](#update-restaurant)  
    -   [update menu](#update-menu)  
    -   [delete restaurant](#delete-restaurant)  
    -   [delete menu](#delete-menu)  
 
### Guest/User/Admin API ###
#### get all restaurants ####

method: `GET`  
path: `/api/restaurants`  

    curl -X GET "http://localhost:8080/api/restaurants" -H "accept: application/json"
   
Response example:
   
    [
       {
          "id":1,
          "restaurant_name":"La Maisie",
          "restaurant_telephone":"(416) 901-1050",
          "restaurant_address":"211 King St W, Toronto"
       },
       {
          "id":2,
          "restaurant_name":"Courage Couture",
          "restaurant_telephone":"(416) 203-1121",
          "restaurant_address":"2281 Lake Shore Blvd W, Etobicoke, ON. M8V1C5"
       },
       {
          "id":3,
          "restaurant_name":"Royal Palace",
          "restaurant_telephone":"+14169162099",
          "restaurant_address":"150 Eglinton Avenue East, Toronto M5H 3H1"
       }
    ]     
    
#### get all menus ####
  
method: `GET`  
path: `/api/restaurants/menus`  

Get all menus for today.
     
    curl -X GET "http://localhost:8080/api/restaurants/menus" -H "accept: application/json"
     
Response example: 

    
    {
       "2020-05-06":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Ginger Chicken",
                   "dish_price":22.99
                },
                {
                   "dish_name":"Green Salad",
                   "dish_price":4.99
                }
             ]
          },
          {
             "restaurant_id":2,
             "restaurant_name":"Courage Couture",
             "restaurant_menu":[
                {
                   "dish_name":"Coleslaw Salad",
                   "dish_price":5.99
                },
                {
                   "dish_name":"Rib-eye",
                   "dish_price":49.99
                }
             ]
          }
       ]
    }
    
Get all menus for period: 2020-05-01 – 2020-05-03

    curl -X GET "http://localhost:8080/api/restaurants/menus?endDate=2020-05-03&startDate=2020-05-01" -H "accept: application/json" -H "accept: application/json"
    
Response example: 

    {
       "2020-05-03":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Green Salad",
                   "dish_price":4.99
                },
                {
                   "dish_name":"Falafel plate",
                   "dish_price":11.99
                }
             ]
          },
          {
             "restaurant_id":2,
             "restaurant_name":"Courage Couture",
             "restaurant_menu":[
                {
                   "dish_name":"Chicken Shawarma Plate",
                   "dish_price":12.99
                },
                {
                   "dish_name":"Coleslaw Salad",
                   "dish_price":6.99
                }
             ]
          },
          {
             "restaurant_id":3,
             "restaurant_name":"Royal Palace",
             "restaurant_menu":[
                {
                   "dish_name":"Terrapura, Pinot Noir, Itata Valley, Chile  2018",
                   "dish_price":14.0
                },
                {
                   "dish_name":"Tartar de Carne",
                   "dish_price":22.0
                },
                {
                   "dish_name":"Vieira Cruda",
                   "dish_price":23.0
                }
             ]
          }
       ]
    }

#### get single restaurant menu ####
  
method: `GET`  
path:  `/api/restaurants/{id}`  

Get restaurant menu for today.

    curl -X GET "http://localhost:8080/api/restaurants/1" -H "accept: application/json" 

Response example:  
    
    {
       "2020-05-06":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Ginger Chicken",
                   "dish_price":22.99
                },
                {
                   "dish_name":"Green Salad",
                   "dish_price":4.99
                }
             ]
          }
       ]
    } 
  
Get restaurant menu for period: 2020-05-01 – 2020-05-06 (Change period dates on actual)

    curl -X GET "http://localhost:8080/api/restaurants/1?endDate=2020-05-06&startDate=2020-05-01" -H "accept: application/json"
    
Response example: 

    {
       "2020-05-06":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Ginger Chicken",
                   "dish_price":22.99
                },
                {
                   "dish_name":"Green Salad",
                   "dish_price":4.99
                }
             ]
          }
       ],
       "2020-05-05":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Stuffed Grape Leaves",
                   "dish_price":7.99
                },
                {
                   "dish_name":"Flank Steak",
                   "dish_price":22.99
                }
             ]
          }
       ],
       "2020-05-04":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Enchiladas",
                   "dish_price":16.0
                },
                {
                   "dish_name":"Green Salad",
                   "dish_price":4.99
                },
                {
                   "dish_name":"tea",
                   "dish_price":2.99
                }
             ]
          }
       ],
       "2020-05-03":[
          {
             "restaurant_id":1,
             "restaurant_name":"La Maisie",
             "restaurant_menu":[
                {
                   "dish_name":"Green Salad",
                   "dish_price":4.99
                },
                {
                   "dish_name":"Falafel plate",
                   "dish_price":11.99
                }
             ]
          }
       ]
    }
    
### User API ###
#### get user votes history ####

method: `GET`  
path:  `/api/profile/votes`  

Get users vote for today.

    curl -X GET "http://localhost:8080/api/profile/votes" -H "accept: application/json" --user user@yandex.ru:password

Response example:  

    {
      "vote_id": 7,
      "date_time": "2020-05-03T20:43:01.0327805",
      "restaurant_id": 1,
      "restaurant_name": "La Maisie"
    }
    
Get users vote for period: 2020-05-01 – 2020-05-06 (Change period dates on actual)

    curl -X GET "http://localhost:8080/api/profile/votes?endDate=2020-05-06&startDate=2020-05-01" -H "accept: application/json" --user user@yandex.ru:password

Response example:  

    [
       {
          "vote_id":5,
          "date_time":"2020-05-05T10:00:00",
          "restaurant_id":1,
          "restaurant_name":"La Maisie"
       },
       {
          "vote_id":3,
          "date_time":"2020-05-04T10:00:00",
          "restaurant_id":3,
          "restaurant_name":"Royal Palace"
       },
       {
          "vote_id":1,
          "date_time":"2020-05-03T10:00:00",
          "restaurant_id":1,
          "restaurant_name":"La Maisie"
       }
    ]
    
#### vote for restaurant ####

method: `POST`  
path: `/api/profile/restaurants/1`  
     
    curl -X POST "http://localhost:8080/api/profile/restaurants/1" -H "accept: application/json" -H "accept: application/json" --user user@yandex.ru:password
     
Response example: 

    {
       "vote_id":7,
       "date_time":"2020-05-06T21:40:45.5536944",
       "restaurant_id":1,
       "restaurant_name":"La Maisie"
    }
    
### Admin API ###
#### create restaurant ####

method: `POST`  
path: `api/admin/restaurants`  
     
    curl -X POST "http://localhost:8080/api/admin/restaurants" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"restaurant_name\": \"New Restaurant\", \"restaurant_address\": \"Street 1, 1\", \"restaurant_telephone\": \"000 000 000\"}" --user admin@gmail.com:admin
     
Request example: 

    {
      "restaurant_name": "New Restaurant",
      "restaurant_address": "Street 1, 1",
      "restaurant_telephone": "000 000 000"
    }

Response example: 

    {
       "id":4,
       "restaurant_name":"New Restaurant",
       "restaurant_telephone":"000 000 000",
       "restaurant_address":"Street 1, 1"
    }

#### create menu ####

method: `POST`  
path: `/api/admin/restaurants/3/menu`  
     
    curl -X POST "http://localhost:8080/api/admin/restaurants/3/menu" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"restaurant_menu\": [ { \"dish_name\": \"Spaghetti\", \"dish_price\": 17.99 }, { \"dish_name\": \"Coffee cappuccino or cup of tea\", \"dish_price\": 2.99 } ]}" --user admin@gmail.com:admin
     
Request example: 

    {
      "restaurant_menu": [
        {
          "dish_name": "Spaghetti",
          "dish_price": 17.99
        },
        {
          "dish_name": "Coffee cappuccino or cup of tea",
          "dish_price": 2.99
        }
      ]
    }

Response example: 

    [
      {
        "id": 25,
        "dish_name": "Coffee cappuccino or cup of tea",
        "dish_price": 2.99
      },
      {
        "id": 26,
        "dish_name": "Spaghetti",
        "dish_price": 17.99
      }
    ]

#### update restaurant ####

method: `PUT`  
path: `/api/admin/restaurants/1`  
     
    curl -X PUT "http://localhost:8080/api/admin/restaurants/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"restaurant_name\": \"New restaurant name\", \"restaurant_address\": \"New address\", \"restaurant_telephone\": \"000 000 000\"}" --user admin@gmail.com:admin

Request example: 

    {
      "restaurant_name": "New restaurant name",
      "restaurant_address": "New address",
      "restaurant_telephone": "000 000 000"
    }

Response example: 

    {
      "id": 1,
      "restaurant_name": "New restaurant name",
      "restaurant_telephone": "000 000 000",
      "restaurant_address": "New address"
    }

#### update menu ####

method: `PUT`  
path: `/api/admin/restaurants/2/menu`  
     
    curl -X PUT "http://localhost:8080/api/admin/restaurants/2/menu" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"restaurant_menu\": [ { \"dish_name\": \"beef stroganoff\", \"dish_price\": 18.99 }, { \"dish_name\": \"baklava and tea\", \"dish_price\": 9.99 } ]}" --user admin@gmail.com:admin

Request example: 

    {
      "restaurant_menu": [
        {
          "dish_name": "beef stroganoff",
          "dish_price": 18.99
        },
        {
          "dish_name": "baklava and tea",
          "dish_price": 9.99
        }
      ]
    }

Response example: 

    [
      {
        "id": 27,
        "dish_name": "baklava and tea",
        "dish_price": 9.99
      },
      {
        "id": 28,
        "dish_name": "beef stroganoff",
        "dish_price": 18.99
      }
    ]
   
    
#### delete restaurant ####

method: `DELETE`  
path: `/api/admin/restaurants/2/menu`  
     
    curl -X DELETE "http://localhost:8080/api/admin/restaurants/1" -H "accept: application/json" --user admin@gmail.com:admin

#### delete menu ####

method: `DELETE`  
path: `/api/admin/restaurants/2/menu`  
     
    curl -X DELETE "http://localhost:8080/api/admin/restaurants/2/menu" -H "accept: application/json" --user admin@gmail.com:admin

