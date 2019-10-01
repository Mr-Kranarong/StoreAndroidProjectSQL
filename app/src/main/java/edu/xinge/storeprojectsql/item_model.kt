package edu.xinge.storeprojectsql

data class itemModel(var itemID: Int, var ItemName: String,var itemDesc: String,var itemCount: Int,var itemPrice: Int,var itemStore: String,var itemPhotoURL: String, var storeDescription: String, var storeImage: String){}
data class userModel(var userID: Int, var FullName: String,var Address: String,var Email: String,var Fund: Int){}
data class recordModel(var buyerID: String, var sellerID: String, var itemName:String,  var priceEA:Int, var count:Int,var timestamp: String){}