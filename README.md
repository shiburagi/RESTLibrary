# RESTLibrary
This library I use in my previous project and current project and may got an issue and error. I will keep improve this library until it stable and useful.

![Screenshot](https://github.com/shiburagi/RESTLibrary-BETA-/blob/master/screenshot/device-2016-03-03-172807.png)

## Features
 * send request with / without build in background process, method support (GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE)

Android 2.0+ support

## Download
 * (https://drive.google.com/file/d/0Bw_drx3o3plaZzlLaHZzTDEtYVk/view?usp=sharing) 

## Including In Your Project
you can include it by **download this project** and **import /restlibrary** as **module**.

## Usage

**NOTE:**　Initialize this line of code at the top of onCreate() in activity, or at the top of onCreateView() in fragment

``` java
RESTManager.init(context);
```
or
``` java
RESTManager.init(context, false);
```

to send a request, **using build in background process**
``` java
RESTManager.getInstance().requestRawSync(
        new Path(
                null, // null if doesn't have local/dummy data in app
                "http://jsonplaceholder.typicode.com/posts", // null if the REST API not ready yet
                RESTManager.METHOD_GET //method of the request
        ),
        "{}", //JSON String
        new RESTManager.OnDataRequestListener<StringBuilder>() { // Interface
            /**
             * The process is complete without any error.
             * @param object
             */
            @Override
            public void onSuccess(StringBuilder object) {
            }
            /**
             *  if an error occurred
             * @param e
             */
            @Override
            public void onFailed(Exception e) {
            }
            /**
             * if the device doesn't have internet connection
             */
            @Override
            public void onNoIntenetConnection() {
            }
            /**
             * This method always execute.
             * @param success
             */
            @Override
            public void finish(boolean success) {
            }
        }

);
```
or **by own background process**
``` java
new AsyncTask<Void, Void, Void>() {
    @Override
    protected Void doInBackground(Void... params) {

        try {
            StringBuilder builder =
                    RESTManager.getInstance().retrieveRawData(
                            new Path(
                                    null,
                                    "http://jsonplaceholder.typicode.com/posts",
                                    RESTManager.METHOD_GET
                            ),
                            new StringBuilder(raw)
                    );
           
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}.execute();
```

to set the request timeout milisecond,
``` java
// Load image, decode it to Bitmap and return Bitmap synchronously
RESTManager.getInstance().setTimeout(miliseconds);
```

## License

    Copyright 2011-2015 Sergey Tarasevich

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
