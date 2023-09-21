package com.example.twitterapplication.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="ttusers")
class BatchUser {
    @Id
    var id: Long = 0
    @Column(name = "first_name")
    var firstName: String = ""
    @Column(name = "last_name")
    var lastName: String = ""
    @Column(name = "email")
    var email: String = ""
    @Column(name = "password")
    var password: String = ""
}