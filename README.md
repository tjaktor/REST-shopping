# REST-shopping
RESTful version of the shopping application (Spring Boot backend + JWT authentication)
The frontend will be added in the future.

Authentication path "api/authentication/login", accepts fields "username" and "password"

Available commands for the authenticated users ("/api/v1" + path):

GET /admin/users  | Find all the users
GET /admin/users/email?email={address}  | Find a user by email
GET /admin/users/{id}  | Find a user by email address
POST /admin/users  | Create a new user, accepts {CreateUserDTO}
PUT /admin/users/{userId}  | Update a user, accepts {UpdateUserDetailsDTO}
DELETE /admin/users/{userId}  | Delete a user by ID

Cart commands:
GET /carts  | Find all the carts
GET /carts/{cartId}  | Find a cart by ID
POST /carts  | Create a new cart, accepts {CreateCartDTO}
PUT /carts/{cartId}  | Update a cart with ID, accepts {UpdateCartDTO}
DELETE /carts/{cartId}  | Delete a cart by ID

Item commands:
GET /carts/{cartId}/items  | Find all the items in a cart
POST /carts/{cartId}/items  | Create a new item into a cart, accepts {CreateItemDto}
GET /carts/{cartId}/items/{itemId}  | Find an item in a cart
PUT /carts/{cartId}/items/{itemId}  | Update an item, accepts {UpdateItemDTO}
DELETE /carts/{cartId}/items/{itemId}  | Delete an item by ID
