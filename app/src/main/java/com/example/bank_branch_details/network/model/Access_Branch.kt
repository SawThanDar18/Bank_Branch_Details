package com.example.bank_branch_details.network.model

data class Access_Branch (val Branch_Code : String, val Branch_Alias : String, val Branch_Name : String,
                          val Latitude : Double, val Longitude : Double, val Phone : String,
                          val Address : String, val TouchPointType : String, val Distance : Double)