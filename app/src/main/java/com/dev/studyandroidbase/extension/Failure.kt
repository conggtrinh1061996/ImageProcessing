package com.dev.studyandroidbase.extension

sealed class Failure {
	class DefaultFailure(val e: Exception? = null): Failure()
}
class CustomFailure(e: Exception): Failure()