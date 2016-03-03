# RESTLibrary

## Features
 * send request with / without build in background process, method support (GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE)

Android 2.0+ support

## Downloads
 * **[universal-image-loader-1.9.5.jar](https://github.com/nostra13/Android-Universal-Image-Loader/raw/master/downloads/universal-image-loader-1.9.5.jar)**

## Usage

### Acceptable URIs examples

**NOTE:**　Initialize this line of code at the top of onCreate() in activity, or at the top of onCreateView() in fragment

``` java
RESTManager.init(context);
```
or
``` java
RESTManager.init(context, false);
```

to send a request, **simple**
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
or
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

to send the timeout milisecond
``` java
// Load image, decode it to Bitmap and return Bitmap synchronously
RESTManager.getInstance
```

### Complete
``` java
// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view 
//	which implements ImageAware interface)
imageLoader.displayImage(imageUri, imageView, options, new ImageLoadingListener() {
	@Override
	public void onLoadingStarted(String imageUri, View view) {
		...
	}
	@Override
	public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		...
	}
	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		...
	}
	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		...
	}
}, new ImageLoadingProgressListener() {
	@Override
	public void onProgressUpdate(String imageUri, View view, int current, int total) {
		...
	}
});
```
``` java
// Load image, decode it to Bitmap and return Bitmap to callback
ImageSize targetSize = new ImageSize(80, 50); // result Bitmap will be fit to this size
imageLoader.loadImage(imageUri, targetSize, options, new SimpleImageLoadingListener() {
	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// Do whatever you want with Bitmap
	}
});
```
``` java
// Load image, decode it to Bitmap and return Bitmap synchronously
ImageSize targetSize = new ImageSize(80, 50); // result Bitmap will be fit to this size
Bitmap bmp = imageLoader.loadImageSync(imageUri, targetSize, options);
```

## Load & Display Task Flow
![Task Flow](https://github.com/nostra13/Android-Universal-Image-Loader/raw/master/wiki/UIL_Flow.png)


## Applications using Universal Image Loader
**[MediaHouse, UPnP/DLNA Browser](https://play.google.com/store/apps/details?id=com.dbapp.android.mediahouse)** | **[Prezzi Benzina (AndroidFuel)](https://play.google.com/store/apps/details?id=org.vernazza.androidfuel)** | **[ROM Toolbox Lite](https://play.google.com/store/apps/details?id=com.jrummy.liberty.toolbox)**, [Pro](https://play.google.com/store/apps/details?id=com.jrummy.liberty.toolboxpro) | [Stadium Astro](https://play.google.com/store/apps/details?id=com.astro.stadium.activities) | [Chef Astro](https://play.google.com/store/apps/details?id=com.sencha.test) | [Sporee - Live Soccer Scores](https://play.google.com/store/apps/details?id=com.sporee.android) | **[EyeEm - Photo Filter Camera](https://play.google.com/store/apps/details?id=com.baseapp.eyeem)** | **[Topface - meeting is easy](https://play.google.com/store/apps/details?id=com.topface.topface)** | **[reddit is fun](https://play.google.com/store/apps/details?id=com.andrewshu.android.reddit)** | **[Diaro - personal diary](https://play.google.com/store/apps/details?id=com.pixelcrater.Diaro)** | **[Meetup](https://play.google.com/store/apps/details?id=com.meetup)** | [Vingle - Magazines by Fans](https://play.google.com/store/apps/details?id=com.vingle.android) | [Anime Music Radio](https://play.google.com/store/apps/details?id=com.maxxt.animeradio) | [WidgetLocker Theme Viewer](https://play.google.com/store/apps/details?id=com.companionfree.WLThemeViewer) | [ShortBlogger for Tumblr](https://play.google.com/store/apps/details?id=com.luckydroid.tumblelog) | [SnapDish Food Camera](https://play.google.com/store/apps/details?id=com.vuzz.snapdish) | **[Twitch](https://play.google.com/store/apps/details?id=tv.twitch.android.viewer)** | [TVShow Time, TV show guide](https://play.google.com/store/apps/details?id=com.tozelabs.tvshowtime) | [Planning Center Services](https://play.google.com/store/apps/details?id=com.ministrycentered.PlanningCenter) | **[Lapse It](https://play.google.com/store/apps/details?id=com.ui.LapseIt)** | [My Cloud Player for SoundCloud](https://play.google.com/store/apps/details?id=com.mycloudplayers.mycloudplayer) | **[SoundTracking](https://play.google.com/store/apps/details?id=com.schematiclabs.soundtracking)** | [LoopLR Social Video](https://play.google.com/store/apps/details?id=com.looplr) | [Hír24](https://play.google.com/store/apps/details?id=hu.sanomamedia.hir24) | **[Immobilien Scout24](https://play.google.com/store/apps/details?id=de.is24.android)** | **[Lieferheld - Pizza Pasta Sushi](https://play.google.com/store/apps/details?id=de.lieferheld.android)** | [Loocator: free sex datings](https://play.google.com/store/apps/details?id=com.ivicode.loocator) | [벨팡-개편 이벤트,컬러링,벨소리,무료,최신가요,링투유](https://play.google.com/store/apps/details?id=com.mediahubs.www) | [Streambels AirPlay/DLNA Player](https://play.google.com/store/apps/details?id=com.tuxera.streambels) | [Ship Mate - All Cruise Lines](https://play.google.com/store/apps/details?id=shipmate.carnival) | [Disk & Storage Analyzer](https://play.google.com/store/apps/details?id=com.mobile_infographics_tools.mydrive) | [糗事百科](https://play.google.com/store/apps/details?id=qsbk.app) | [Balance BY](https://play.google.com/store/apps/details?id=com.vladyud.balance) | **[Anti Theft Alarm - Security](https://play.google.com/store/apps/details?id=br.com.verde.alarme)** | **[XiiaLive™ - Internet Radio](https://play.google.com/store/apps/details?id=com.android.DroidLiveLite)** | **[Bandsintown Concerts](https://play.google.com/store/apps/details?id=com.bandsintown)** | **[Save As Web Archive](https://play.google.com/store/apps/details?id=jp.fuukiemonster.webmemo)** | [MCPE STORE -Download MCPE file](https://play.google.com/store/apps/details?id=com.newidea.mcpestore) | **[All-In-One Toolbox (29 Tools)](http://aiotoolbox.com/)** | [Zaim](https://play.google.com/store/apps/details?id=net.zaim.android) | **[Calculator Plus Free](https://play.google.com/store/apps/details?id=com.digitalchemy.calculator.freedecimal)** | [Truedialer by Truecaller](https://play.google.com/store/apps/details?id=com.truecaller.phoneapp) | [DoggCatcher Podcast Player](https://play.google.com/store/apps/details?id=com.snoggdoggler.android.applications.doggcatcher.v1_0) | [PingTools Network Utilities](https://play.google.com/store/apps/details?id=ua.com.streamsoft.pingtools) | [The Traveler](https://play.google.com/store/apps/details?id=edu.bsu.android.apps.traveler) | [minube: travel photo album](https://play.google.com/store/apps/details?id=com.minube.app) | [Wear Store for Wear Apps](https://play.google.com/store/apps/details?id=goko.ws2) | [Cast Store for Chromecast Apps](https://play.google.com/store/apps/details?id=goko.gcs) | [WebMoney Keeper](https://play.google.com/store/apps/details?id=com.webmoney.my)

## Donation
You can support the project and thank the author for his hard work :)

<a href='https://pledgie.com/campaigns/19144'><img alt='Click here to lend your support to: Universal Image Loader for Android and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/19144.png?skin_name=chrome' border='0' ></a> <a href="http://flattr.com/thing/1110177/nostra13Android-Universal-Image-Loader-on-GitHub" target="_blank"><img src="http://api.flattr.com/button/flattr-badge-large.png" alt="Flattr this" title="Flattr this" border="0" /></a>
* **PayPal** - nostra.uil[at]gmail[dot]com

## Alternative libraries

 * [AndroidQuery : ImageLoading](https://code.google.com/p/android-query/wiki/ImageLoading)
 * [DroidParts : ImageFetcher](http://droidparts.org/image_fetcher.html)
 * [Fresco](https://github.com/facebook/fresco)
 * [Glide](https://github.com/bumptech/glide)
 * [Picasso](https://github.com/square/picasso)
 * [UrlImageViewHelper](https://github.com/koush/UrlImageViewHelper)
 * [Volley : ImageLoader](https://android.googlesource.com/platform/frameworks/volley/)

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
