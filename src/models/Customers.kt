package com.example.models

import kotlinx.serialization.Serializable

/** CREATE MODEL WHICH DEFINES THE DATA ASSOCIATED WITH A CUSTOMER
 * CREATE SERIES OF END POINTS TO ALLOW CUSTOMERS TO BE ADDED, LISTED AND DELETED */

/* THINGS A CUSTOMER NEED TO HAVE
*  AN ID, A FIRST AND LAST NAME AND AN EMAIL ADDRESS*/

//serialization of this data class means it will allow us to generate a json representation of the
//of this data class for our API response automatically
@Serializable
data class Customer(
    val id : String,
    val firstName : String,
    val lastName : String,
    val email : String,
)

//for storing the data we will use an in-memory storage a mutable list of customers

val customerStorage = mutableListOf<Customer>()
