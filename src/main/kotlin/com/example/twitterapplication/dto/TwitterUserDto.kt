package com.example.twitterapplication.dto

class TwitterUserDto {
     constructor(firstName: String, lastName: String, email: String, password: String) {
          this.firstName = firstName
          this.lastName = lastName
          this.email = email
          this.password = password
     }

     constructor()

     var firstName: String = ""
     var lastName: String = ""
     var email: String = ""
     var password: String = ""
}