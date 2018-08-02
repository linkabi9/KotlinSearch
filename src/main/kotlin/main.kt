import kotlin.browser.*
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.js.*
import kotlinx.coroutines.experimental.*
import org.w3c.dom.*


fun main(args: Array<String>) {
    window.onload = {
        document.body!!.append.div {
            classes = setOf("app")
            form {
                name = "searchForm"
                onSubmitFunction = { event -> 
                    event.preventDefault()
                    val searchInput = document.getElementById("search-input") as HTMLInputElement
                    if (searchInput.value.length > 0) {
                        launch {
                            val dogList = document.getElementById("search-list") as HTMLDivElement
                            console.log(dogList)
                            while (dogList.hasChildNodes()) {
                                dogList.removeChild(dogList.firstChild as Node)
                            }
                            val searchResponse = search(searchInput.value.split(" ").toTypedArray()) as ResponseObject
                            if (searchResponse.status == "success") {
                                val dogs = searchResponse.message
                                for (dog in dogs.take(20)) {
                                    dogList.append.div {
                                        classes = setOf("thumbnail")
                                        img {
                                            src = dog
                                        }
                                    }
                                }
                            } else {
                                val messages = searchResponse.message
                                for (message in messages) {
                                    dogList.append.div { +message }
                                }
                            }
                        }
                    }
                }
                input {
                    id = "search-input"
                    name = "search"
                    classes = setOf("search-input")
                    placeholder = "Search dog breeds"
                }
            }
            div {
                id = "search-list"
                classes = setOf("list")
            }
        }
        val startFocusElement = document.getElementById("search-input") as HTMLInputElement
        startFocusElement.focus()
    }
}
