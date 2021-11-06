package com.example.routes

import com.example.models.orderStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import jdk.jshell.Snippet

//for listing all the items
fun Route.listOrderRoute(){
    get("/order"){
        if(orderStorage.isNotEmpty()){
            call.respond(orderStorage)
        }
    }
}

//for listing individual item based on id
fun Route.getOrderRoute(){
    get("/order/{id}"){
        val id = call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
        val order = orderStorage.find {
            it.number == id
        } ?: return@get call.respondText("Not Found",status = HttpStatusCode.NotFound)
        call.respond(order)
    }
}

//for totalizing an order
fun Route.totalizeOrderRoute(){
    get("/order/{id}/total"){
        val id = call.parameters["id"] ?: return@get call.respondText("bad request", status = HttpStatusCode.BadRequest)
        val order = orderStorage.find{it.number == id} ?: return@get call.respondText("Not Found",status = HttpStatusCode.NotFound)
        val total = order.contents.sumOf {
            it.amount * it.price
        }
        call.respond(total)
    }
}

//for register the routes

fun Application.registerOrderRoutes(){
    routing {
        listOrderRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}