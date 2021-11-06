 package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

//routes with the corresponding http method

//ext function
fun Route.customerRouting(){
    //everything we want to do under our customer endPoint
    //defining our endpoint
    route("/customer"){
        get {

            /*for listing all the all customers we can return customerStorage list
            which can a kotlin object and return it serialized in a specified format i.e. json in this case
            Now this will require "contentNegotiation"
            enabled it in the application module
            Now with the help of this Ktor knows how to serialize the Customer*/
            if(customerStorage.isNotEmpty()){
                call.respond(customerStorage)

            }else{
                call.respondText("No customer found", status = HttpStatusCode.NotFound)
            }
        }

        /*In this route we  want a specific  customer to be returned with their specific ID
        * ID will be the parameter , we want to access its value using "call.parameter["param name"]" its a indexed access operator */

        get("{id}"){
            //if the value is null it will return a Bad request Error else onto the next code
                val id = call.parameters["id"] ?: return@get call.respondText(
                    "missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            //now to return a customer with a specific id it should first find that customer
            //if the customer is in the list it will return it else return a Not found status

            val customer =
                    customerStorage.find {
                        it.id == id
                    } ?: return@get call.respondText(
                        "No Customer with id $id",
                        status = HttpStatusCode.NotFound
                    )
                call.respond(customer)
        }
        //Now we want to post a route
        post{
            //we want the data our json request of creating a customer get stored in the list as kotlin customer object
            //we will use call.receive it integrates with contentNegotiation plugin and calling it with generic parameter customer
            //automatically deserializes the json request
            val customer = call.receive<Customer>()
            customerStorage.add(customer) //it is mutable list function
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        //for deleting a customer
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if(customerStorage.removeIf{it.id == id}){
                call.respondText("Customer removed Successfully",status = HttpStatusCode.Accepted)

            }
            else{
                call.respondText("Not Found",status = HttpStatusCode.NotFound)
            }
        }
    }
}
//Now we have to register our routes
//to makesure our Application.module knows about it
//we can also describe each route in our application.module file but its more convenient this way
fun Application.registerCustomerRoutes(){
    routing {
        customerRouting()
        //invoke this function our Application.module function
    }
}
