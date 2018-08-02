import kotlinx.coroutines.experimental.*
import org.w3c.fetch.*
import kotlin.browser.*
import org.w3c.dom.parsing.DOMParser

suspend fun search(args: Array<String>): ResponseObject {
	println("You search for " + args.joinToString(" "))
	val searchTerms = args.reversedArray().joinToString("/")
	val response = window.fetch("https://dog.ceo/api/breed/${searchTerms}/images").await()
	val responseObject = response.json().await().asDynamic()
	if (responseObject.status == "success") {
		return ResponseObject(responseObject.status, responseObject.message)
	} else {
		return ResponseObject(responseObject.status, arrayOf(responseObject.message))
	}	
}

class ResponseObject(var status: String, var message: Array<String>) {}