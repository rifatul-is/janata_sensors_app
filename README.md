# Janata Sensor Monitoring Application

A simple sensor monitoring application with graphs.

Throughout the development of the Android application, the initial phases, notably the design implementation and getting the data from sensors with subsequent storage in the internal SQLite database, proceeded smoothly. However, the introduction of a background service posed a notable challenge. While the service functioned as intended when the app was minimized, its termination from the task manager would instantly kill the app along with its service.

This issue was particularly pronounced on newer Android versions, which had more strict background restriction policies on background processes. Consequently, additional methods had to be implements to make sure the continued operation of the background service even after the app was closed through the task manager.

The solution to this challenge was realized through the implementation of a **_BroadcastReceiver_**. Triggered within the **_onDestroy_** method of the activity, this BroadcastReceiver would restart the service, ensuring its persistence even after the app's termination from the task manager. A verification had also needed to be taken to see if the the service is already running in the background otherwise it would create the service again. This was tricky to determine as being new to services, I haven't thought that cheaking for running services was mandatory.

### Tasks Completed

- Build a basic android app with java that starts getting the values from light sensor, proximity sensor, accelerometer, and gyroscope, and shows the latest value in 4 different cards in the app screen.
- Record the sensor values in the android sqlite db every 5 minutes, and make time series charts of each of the 4 sensors. If you click on the sensor value cards from the home screen, it goes into a screen with the time series chart corresponding to that sensor.
- Run the service in the background, so even if the user closes it from the task manager, the app keeps running in the background, it should be showing a notification to let the user know of all the 4 values.
- Make the whole app look pretty, be creative.
- Upload the solution in a github repo and send the link to us. Add the apk in the repo.
- The challenges that you have faced and the things that you have learned, describe those in the readme file. Also mention the tasks that you completed. make sure to keep it in the repo.
- Send the repo url to careers@janatawifi.com

### Tasks That Remain Incomplete

- Reimplement the apis to get data for proximity, accelerometer in android Native Development Kit, increase the data refresh rate from the default rate, with C++ or C and then use those to get the data and visualize the latest data."

### Final Outcome

Never having worked in the **Background Services, Sensor and Internal Android Database** part of android app development this was a relatively new area. I have now a much better understanding on how apps run on background on both older android devices and also newer android devices that come with more strict background service policies. Building this simple app was actually very interesting experiance and I have learned a lot and would continue to do so even after this.
