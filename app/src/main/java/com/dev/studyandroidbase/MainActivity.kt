package com.dev.studyandroidbase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.dev.studyandroidbase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	val TAG = "MainApp"
	private lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		// loc Collections bang lamda
		fruits.filter { it.startsWith("a") }
			.sortedBy { it }
			.map {it.uppercase()}
			.forEach { Log.d(TAG, "listCollections: ${it}") } // -> APPLE, AVOCADO
		// print a map
		for ((k, v) in map) {
			Log.d(TAG, "key:$k -> $v")
		}
		// vararg Parameters
		printAll("Hello", "Hi", "Rule", "Big")
		printAllWithPrefix("Hello", "Hi", "Rule", "Big", prefix =  "Android")
	}

	/**
	 * Anonymous Func
	 */
	private val stringLengthFunction : (String) -> Int = { input ->
		input.length
	}

	/**
	 * Use Lamda expression to filter and map collections: dung bieu thuc lamda loc va anh xa collections
	 */
	val fruits = listOf("banana", "avocado", "apple", "kiwifruit")

	/**
	 * Create a map
	 */
	val map = mapOf("a" to 1, "b" to 2, "c" to 3)

	/**
	 * vararg Paramerters: cho phép phân tách casc đối t
	 */
	fun printAll(vararg messages: String) {
		for (m in messages) Log.d(TAG, "printAll: $m")
	}

	fun printAllWithPrefix(vararg messages: String, prefix: String) {
		for (m in messages) Log.d(TAG, "printAllWithPrefix: $prefix: $m")
	}
}

fun main() {
	// Higher-order function
	// :: la kí hiệu tham chiếu đén 1 hàm bằng t
	val sumResult = calculate(10, 5, ::sum)
	val mulResult = calculate(10, 5) { a, b -> a * b }
	println("sumResult: $sumResult || mulResult: $mulResult")

	// Lamda Function
	println("UpperCase1: ${upperCase1("Android")}")
	// map
	val doubleNumber = numbers.map { it * 2 }
	println("doubleNumber: $doubleNumber")
	// zip : hợp nhất 2 collections thành 1 collection mới
	val listA = listOf("a", "b", "c")
	val listB = listOf(1, 2, 3)

	val resultPair = listA zip listB
	val resultReduce = listA.zip(listB) { a, b -> "$a$b"}
	println("resultPair: $resultPair")
	println("resultReduce: $resultReduce")
}

// Higher-order function: Hàm bậc cao hơn, nhận 1 hàm khác là tham số hoặc trả về 1 hàm
fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
	return operation(x, y)
}

fun sum(x: Int, y: Int) = x + y

// Lamda function
val upperCase1: (String) -> String = { str ->
	str.uppercase()
}
// Map: là 1 phép chuyển đổi như phép ánh xạ x -> y thông qua phép toán
val numbers = listOf(1, 2, 3)