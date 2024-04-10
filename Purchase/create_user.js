db.createUser(
   {
     user: "mongo",
     pwd: "password",
     roles: [ "readWrite", "dbAdmin" ]
   }
)