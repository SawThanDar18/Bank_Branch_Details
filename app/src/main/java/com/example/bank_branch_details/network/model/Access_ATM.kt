package com.example.bank_branch_details.network.model

data class Access_ATM (val Terminal_ID : String, val ATM_Type : String, val Location_Name : String,
                       val Latitude : Double, val Longitude : Double, val Address : String,
                       val TouchPointType : String, val Distance : Double)