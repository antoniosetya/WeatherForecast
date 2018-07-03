<h1 align="center">WeatherForecast</h1>

Dibuat oleh Antonio Setya (13516002)

<br>
<br>

## *Quick Links*

- [API dan *OpenWeather* API](#api-dan-openweather-api)
- [*Package Structure*](#package-structure)
- [*Checklist*](#checklist)
- [*Compiling and Running*](#compiling-and-running)
- [*How to use*](#how-to-use)

<br>

## API dan *OpenWeather* API

API (*Application Programming Interface*) adalah sekumpulan protokol, *subroutine*, dan *tools* untuk membangun sebuah aplikasi. Dalam kata lain, API adalah sekumpulan cara untuk melakukan komunikasi dengan komponen *software* lainnya. API dibuat oleh pengembang *software* untuk memudahkan pengembangan aplikasi dan memudahkan komunikasi antar aplikasi. Dengan adanya API, kita dapat menggunakan sebuah fitur dari yang diperlukan suatu *software* lain  tanpa perlu mengetahui bagaimana *software* tersebut "menghasilkan" fitur tersebut.

API sendiri datang dalam berbagai bentuk. Ada API sebagai *library* atau *framework* dari sebuah bahasa pemrograman. (seperti *C++ Standard Template Library* atau *Java Collections Framework*). Ada pula API untuk *operating system*. API ini memberikan perantara antara aplikasi dan *operating system* dimana aplikasi berjalan. Terakhir, ada *Web API*, yang bekerja melalui jaringan komputer (seperti *OpenWeather* ini).

*OpenWeather* API adalah sebuah *interface* yang menyediakan data cuaca dari berbagai kota di dunia. API ini berada di [api.openweathermap.org](https://openweathermap.org/api). Untuk menggunakan API dari *OpenWeather*, pengguna perlu mendaftarkan diri terlebih dahulu di *website* tersebut dan mendapatkan *API key*.

Untuk mendapatkan data cuaca dari suatu kota, kita lakukan *GET request* ke

```api.openweathermap.org/data/2.5/weather?q={nama_kota}&appid={API_key}```,

dengan {nama_kota} diganti dengan nama kota yang diiginkan, dan {API_key} diganti dengan *API key* yang kita dapatkan saat melakukan registrasi. Melakukan *request* dengan nama kota dapat menimbulkan ambiguitas, karena bisa saja ada dua kota dengan nama yang mirip/sama. Maka dari itu, *OpenWeather* menyarankan untuk melakukan *request* dengan ID dari kota tersebut. *Request* tersebut dapat dilakukan ke 

```api.openweathermap.org/data/2.5/weather?id={id_kota}&appid={API_key}```,

dengan {id_kota} diganti dengan ID dari kota yang diinginkan. ID kota-kota tersebut dapat diunduh [disini](http://bulk.openweathermap.org/sample/).

API dari *OpenWeather* dapat menyediakan data dalam format *JSON* atau *XML*. Dalam program ini, format data yang akan digunakan adalah *JSON*. Diambil dari dokumentasi API, berikut adalah contoh data yang dikirim oleh *OpenWeather*:

```
{"coord":
{"lon":145.77,"lat":-16.92},
"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}],
"base":"cmc stations",
"main":{"temp":293.25,"pressure":1019,"humidity":83,"temp_min":289.82,"temp_max":295.37},
"wind":{"speed":5.1,"deg":150},
"clouds":{"all":75},
"rain":{"3h":3},
"dt":1435658272,
"sys":{"type":1,"id":8166,"message":0.0166,"country":"AU","sunrise":1435610796,"sunset":1435650870},
"id":2172797,
"name":"Cairns",
"cod":200}
```

Beberapa *field* yang muncul dalam contoh diatas bisa saja tidak muncul pada saat kita melakukan suatu *request* untuk suatu kota. Hal tersebut dipengaruhi oleh cuaca dan data yang diperoleh oleh *OpenWeather* akan bervariasi untuk setiap kota.

Untuk mendapatkan data prakiraan cuaca sampai dengan 5 hari kedepan, kita dapat melakukan *GET request* ke
 
```api.openweathermap.org/data/2.5/forecast?id={id_kota}&appid={API_key}```,

dengan contoh *response* dari *API* sebagai berikut :

```
{
  "cod": "200",
  "message": 0.0097,
  "cnt": 2,
  "list": [
    {
      "dt": 1529377200,
      "main": {
        "temp": 305.5,
        "temp_min": 302.371,
        "temp_max": 305.5,
        "pressure": 1024.71,
        "sea_level": 1025.16,
        "grnd_level": 1024.71,
        "humidity": 89,
        "temp_kf": 3.13
      },
      "weather": [
        {
          "id": 801,
          "main": "Clouds",
          "description": "few clouds",
          "icon": "02d"
        }
      ],
      "clouds": {
        "all": 12
      },
      "wind": {
        "speed": 3.57,
        "deg": 109.001
      },
      "sys": {
        "pod": "d"
      },
      "dt_txt": "2018-06-19 03:00:00"
    },
    {
      "dt": 1529388000,
      "main": {
        "temp": 306.11,
        "temp_min": 303.764,
        "temp_max": 306.11,
        "pressure": 1022.99,
        "sea_level": 1023.5,
        "grnd_level": 1022.99,
        "humidity": 83,
        "temp_kf": 2.35
      },
      "weather": [
        {
          "id": 801,
          "main": "Clouds",
          "description": "few clouds",
          "icon": "02d"
        }
      ],
      "clouds": {
        "all": 12
      },
      "wind": {
        "speed": 3.76,
        "deg": 53.0044
      },
      "sys": {
        "pod": "d"
      },
      "dt_txt": "2018-06-19 06:00:00"
    }
  ]
}
```

Untuk lebih lengkapnya mengenai *OpenWeather API*, dapat dilihat [disini](https://openweathermap.org/api).

[Back to top](#weatherforecast)
<br>

## *Package Structure*

__*subject to change*__

Terdapat dua package utama dalam program ini, yang dibungkus dalam package *com.pyra*, yaitu *weatherforecast* dan *weatherforecast.data*. Package "pembungkus" *com.pyra* dibuat untuk mengikuti *Google Java Code Style*.

Dalam package *com.pyra.weatherforecast*, terdapat empat (4) *class* "penting", yaitu : 
- *Main* : program utama dan layar utama (menampilkan layar pencarian kota)
- *WeatherScreen* : menampilkan cuaca sekarang dan prakiraan cuaca kota yang dipilih
- *CitySearcher* : bertugas untuk mencari kota dari data lokal berdasarkan kata kunci/*substring* tertentu dan mengembalikan ID dari kota tersebut. ID tersebut digunakan untuk berkomunikasi dengan *OpenWeather API*
- *WeatherGrabber* : bertugas untuk mengambil data cuaca dari *OpenWeather API* berdasarkan ID dari kota yang diinginkan.   

Dalam package tersebut pula, terdapat satu package lagi, yaitu *data*, yang berisi representasi data yang digunakan dalam program ini. Dalam package ini terdapat *class* *City*, yang merepresentasikan sebuah kota, *Weather* yang merepresentasikan kondisi cuaca di suatu *city*, dan *Forecast* yang merepresentasikan prakiraan cuaca di suatu *city*.

Berikut adalah ilustrasi hierarkis dari *package* yang terdapat dalam program ini.
```
com
 |--- pyra
        |---- weatherforecast
                        |---- Main
                        |---- WeatherScreen
                        |---- CitySearcher
                        |---- WeatherGrabber
                        |---- data
                               |---- City
                               |---- Weather
                               |---- Forecast
```

[Back to top](#weatherforecast)
<br>

## *Checklist*


<table>
  <tr>
    <th><i>Package</i></th>
    <th><i>Component</i></th>
    <th><i>Remarks</i></th>
    <th><i>JUnit test</i></th>
    <th>Keterangan</th>
  </tr>
  <tr>
    <td rowspan="4">com.pyra.weatherforecast</td>
    <td><i>Main</i></td>
    <td><i>In Progress</i></td>
    <td>N/A</td>
    <td><i>Refining UI...</i></td>
  </tr>
  <tr>
    <td><i>WeatherScreen</i></td>
    <td><i>In Progress</i></td>
    <td>N/A</td>
    <td><i>Refining UI...</i></td>
  </tr>
  <tr>
    <td><i>CitySearcher</i></td>
    <td><i>Completed</i></td>
    <td><i>Completed, Passed</i></td>
    <td></td>
  </tr>
  <tr>
    <td><i>WeatherGrabber</i></td>
    <td><i>In Progress</i></td>
    <td><i>N/A, results are fluctuative</i></td>
    <td></td>
  </tr>
  <tr>
    <td rowspan="3">com.pyra.weatherforecast.data</td>
    <td><i>City</i></td>
    <td><i>Completed</i></td>
    <td><i>Completed, Passed</i></td>
    <td></td>
  </tr>
  <tr>
    <td><i>Weather</i></td>
    <td><i>Completed</i></td>
    <td><i>Completed, Passed</i></td>
    <td></td>
  </tr>
  <tr>
    <td><i>Forecast</i></td>
    <td><i>Completed</i></td>
    <td><i>Completed, Passed</i></td>
    <td></td>
  </tr>
  <tr>
    <td><i>Others</i></td>
    <td><i>JDepend result</i></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>

[Back to top](#weatherforecast)
<br>
 
## *Compiling and Running*

__*subject to change*__

### *Dependencies*
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [json-simple](https://code.google.com/archive/p/json-simple/)
- [Eclipse IDE](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/oxygen3a) (*optional*, untuk *compiling*)

[Back to top](#weatherforecast)
<br>

## *How to Use*

TBD

[Back to top](#weatherforecast)
<br>
