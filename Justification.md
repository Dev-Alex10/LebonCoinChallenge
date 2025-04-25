# Used libraries / architecture / design pattern

* MVVM
* Layered Architecture 
* Jetpack Compose
* Coil
* Dagger Hilt
* Retrofit
* Room
* Flow
* JUnit
* Mockk

# Brief Eplanation of the usage

* Use of MVVM as a design pattern because it's easier to understand what's responsible for what, so we have a unidirectional data flow where ViewModels expose UI state and receive actions from the UI through method calls.

* As for the architecture, having a "remote", "local", and "domain" layers makes it so each one are independent for each other, so if one changes, and the change is not needed in another layer, we just change the one needed. Complying with single source of truth principle.

* Jetpack Compose for the UI because it's intuitive and, in my opinion, the best way to make UI.

* Used Coil because it's something I'm used, and makes the handling of images simpler.

* Dagger Hilt because it makes dependency injection simpler, and for that reason we save time coding.

* As for the use of Retrofit and ROOM, it makes things clear and simple. Also does the management of coroutines, so we don't need to worry about it.
With a few lines of code we have our logic done.

* Used Kotlin Flows because of the reactivity, meaning that every time we receive a new value, the UI is updated. Flow's are also good because of being asynchronous. 

* JUnit for the tests because it's one of the native ones, mockk is just a preference, I use it because it's intuitive in what's being done.
