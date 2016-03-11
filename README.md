# RESTLibrary
This library I use in my previous and current android project and may got an issue and error. 
I will keep improve this library until it stable and useful.

![Screenshot](https://github.com/shiburagi/RESTLibrary-BETA-/blob/master/screenshot/device-2016-03-03-172807.png)

## Features
 * send request with / without build in background process, method support (GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE)

Android 2.0+ support

## Download
 * **JAR** : (https://drive.google.com/file/d/0Bw_drx3o3plaMWlLaVBXOXcwZWc/view?usp=sharing)
 * **APK** : (https://drive.google.com/file/d/0Bw_drx3o3plaZzlLaHZzTDEtYVk/view?usp=sharing) 

## Including In Your Project
This library is presented as a `.jar` file which you can include in the `libs/`
folder of your application. You can download the latest version from the
[google drive](https://drive.google.com/file/d/0Bw_drx3o3plaMWlLaVBXOXcwZWc/view?usp=sharing).

**or**,
you can include it by **download this project** and **import /restlibrary** as **module**.

## Usage

**NOTE:**　**Initialize** this line of code at the **top of onCreate() in main activity**, or at the **top of onCreateView() in main** fragment

``` java
// parameter : context
RESTManager.init(context);
```
or
``` java
// parameter : context and isExprimental status
RESTManager.init(context, false);
```

to send a request, 
**using build in background process**
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
             * if the process is complete without any error.
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
                                    null, // null if doesn't have local/dummy data in app
                                    "http://jsonplaceholder.typicode.com/posts", // null if the REST API not ready yet
                                    RESTManager.METHOD_GET //method of the request
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
RESTManager.getInstance().setTimeout(miliseconds);
```

This library include **GSON** in **dependency**, which means you can **send request in object representation(POJO)** rather than a plain string and **retrieve the response in object representation(POJO)**. Below the example how to use it,

``` java
Login login = new Login(username, password);

RESTManager.getInstance().requestSync(
        new Path(
                null, // null if doesn't have local/dummy data in app
                "http://jsonplaceholder.typicode.com/posts", // null if the REST API not ready yet
                RESTManager.METHOD_POST //method of the request
        ),
        login, // POJO 
        Login.class,
        new RESTManager.OnDataRequestListener<Login>() { // Interface
            /**
             * if the process is complete without any error.
             * @param object
             */
            @Override
            public void onSuccess(Login object) {
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
## Contact
For any enquiries, please send an email to tr32010@gmail.com. 

## License

    Copyright 2016-2017 Shiburagi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
