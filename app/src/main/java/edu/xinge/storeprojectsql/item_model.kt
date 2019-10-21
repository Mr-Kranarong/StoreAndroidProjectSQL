package edu.xinge.storeprojectsql

data class itemModel(var itemID: Int, var ItemName: String,var itemDesc: String,var itemCount: Int,var itemPrice: Int,var itemStore: String,var itemPhotoURL: String, var storeDescription: String, var storeImage: String){}
data class recordModel(var buyerID: String, var sellerID: String, var itemName:String,  var priceEA:Int, var count:Int,var timestamp: String){}
data class cartModel(var ItemImage: String, var ItemName: String, var ItemDescription: String, var ItemPrice: Int, var Count: Int, var ItemID: Int){}
data class storeListModel( var ItemID: Int,var ItemImage: String, var ItemName: String, var ItemDescription: String, var ItemPrice: Int, var ItemCount: Int){}