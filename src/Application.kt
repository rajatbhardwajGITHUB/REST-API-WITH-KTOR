package com.example
/** JSON RESTFULL API
 * ALLOWS US TO QUERY ALL THE ELEMENTS THAT ARE CUSTOMERS OF OUR IMAGINARY BUSINESS
 * LIST ALL THE CUSTOMERS AND ORDERS IN A CONVENIENT WAY
 * GET THE INFO ABOUT INDIVIDUAL CUSTOMERS AND ORDERS AND PROVIDE FUNCTIONALITY TO ADD NEW ENTRIES AND REMOVE OLD ONES
 * FOR MORE INFO VISIT -> https://ktor.io/docs/creating-http-apis.html */

import com.example.routes.registerCustomerRoutes
import com.example.routes.registerOrderRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.serialization.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    //contentNegotiation ->
        /*formal def => process of inspecting the structure of an incoming HTTP request to determine the best representation of a resource
        * from amongst multiple available representation of the same resource.
        * understandable def => concept that allows the same url to serve same content in various formats.
        * eg => list of customers can be represented in 'json' as well as in 'HOCON' as well as in 'xml'*/

    install(ContentNegotiation){
        json()
    }

    //registering our customer routes
    registerCustomerRoutes()

    //registering our order routes
    registerOrderRoutes()
}

