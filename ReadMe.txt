From Android Dev Submit !! Work Manager Beyond the Basic


Q1 -  How does WorkManager Interact with Android ?
* Here is How Work Flow Goes
   * We Define Worker Class
   * We create Work Request and Add some Constraint
   * We build it and enqueue it
* To know whether our work is running, has finished, has retried 5 times or whatever all the information should store inside the WorkManager Database so it is a single source of truth for everything.
*  And for newer device api level 23 and up work request is sent to job scheduler 


Q2 - How do WorkManager Run our Work Requests ?
* Job scheduler, GCM Network Manager and Alarm Manager these are the things that are outside of our application. So it’s either the OS or Google Play stores 
* Something else now knows about our work.
* And that things can wake up our application if needed and ask WorkManager to run our work
   * Example 👍
   * Suppose we have network connectivity constraints on newer device Job scheduler will wake up our app and tell us hey you have a network you can do this work and then WorkManager will then run it.


– Suggestion – 
* Since WorkManager is something that exists within our application but there are repercussions outside of your application. Because the rest of the OS also knows about it. 
* So you should always cancel all your work requests to clean up after yourself.
* If not Job Scheduler still look for the network connectivity or constraints and calls upon satisfaction of all work request constraints but we won’t have anything to perform that work.
* This simply means using the system resource to do nothing.


Q3 - Why does the work run so often ?
* Example 
   * Here inside onCreate() we enqueue periodic work requests ?
   * WorkManager.getInstance(this).enqueue (
PeriodicWorkRequestBuilder<MyWorker>(duration).build()  )
* This is work because every time it will create a new work request and we periodically start having new requests every time.
   * WorkManager.getInstance(this).enqueueUniquePeriodicWork(“test_work”, ExistingPeriodicWorkPolilcy.KEEP,
PeriodicWorkRequestBuilder<MyWorker>(duration).build() )
* This is the right way to do enqueue the work through enqueUniquePeriodicWork function where we can simply what happen if the work request with that name already exist, here in this code we will keep the existing one and will not enqueue the same request again.




– Talks –


* Work Manager Initialization 
   * Automatic Initialization 
      * Initialize work Manager automatically with default configuration.
      * Content Provider call WorkManager Initializer and it gets manifest merged into our application.
      * The way content provider works they initialize first. Before application onCreate() happens
      * So when onCreate() happen workManger is already initialized and if we call WorkManager.getInstance() it won’t return null


* ON - Demand Initialization
   * To customize configuration of WorkManager
   * Initialize work manager Lazy manner or simply initialize when i want
   * Do initialize on demand first disable automatic work manager initialization.


– Learning – 
* Work Manager Enforces a minimum Interval of 15 minutes for every periodic work request. If you want more frequent we have to use other solutions such as Alarm Manager. 
* We have CoroutineWorker Class which implement doWork as a suspend function
* Whereas we can Worker class which implement doWork as a non suspend function




        
* – Constructor parameters of WORKER Class –
   * Context 
      * WorkManager passes to our worker class
      * Context allow our worker to access application level resources, services or files
   * Work parameters 
      * Contains information about the work such as input data ( which allow you to access any data passed to Workers when WorkRequest is created ), tags, runtime constraints and more.