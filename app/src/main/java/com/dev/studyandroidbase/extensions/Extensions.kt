package com.dev.studyandroidbase.extensions

data class Item(val name: String, val price: Float)

data class Order(val items: Collection<Item>)

fun Order.maxPricedItemValue(): Float = this.items.maxByOrNull { it.price }?.price ?: 0F
fun Order.maxPricedItemName() = this.items.maxByOrNull { it.price }?.name ?: "No_Products"

val Order.commaDeLimitedItemNames: String
	get() = items.joinToString { it.name }

fun main() {
	val order = Order(listOf(Item("Bread", 25F), Item("Banana", 4F), Item("Water", 1F)))

	println("Max priced item name: ${order.maxPricedItemName()}")
	println("Max priced item value: ${order.maxPricedItemValue()}")
	println("Item: ${order.commaDeLimitedItemNames}")
}