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


[Back to top](#weatherforecast)
<br>

## *Package Structure*

__*subject to change*__

Terdapat satu package utama saja dalam program ini, yang dibungkus dalam package *com.pyra*, yaitu *weatherforecast*. Package "pembungkus" *com.pyra* dibuat untuk mengikuti *Google Java Code Style*, dan satu package *weatherforecast* dibuat karena program tidak terlalu besar untuk dipecah-pecah menjadi beberapa package.

Dalam package *com.pyra.weatherforecast*, terdapat empat (4) *class*, yaitu *Main*, *UI*, *CitySearcher*, dan *WeatherGrabber*. Kelas *Main* mengandung program utama, *UI* mengatur tampilan GUI program (menggunakan *Java SWING*), *CitySearcher* bertugas untuk mencari kota dari data lokal berdasarkan kata kunci/*substring* tertentu, dan *WeatherGrabber* bertugas untuk mengambil data cuaca dari *OpenWeather API*.   

[Back to top](#weatherforecast)
<br>

## *Checklist*

__*subject to change*__

<table>
  <tr>
    <th><i>Component</i></th>
    <th><i>Remarks</i></th>
    <th><i>JUnit test</i></th>
    <th>Keterangan</th>
  </tr>
  <tr>
    <td colspan="4">Package : com.pyra.weatherforecast</td>
  </tr>
  <tr>
    <td>Main</td>
    <td></td>
    <td>N/A</td>
    <td></td>
  </tr>
  <tr>
    <td>UI</td>
    <td></td>
    <td>N/A</td>
    <td></td>
  </tr>
  <tr>
    <td><i>CitySearcher</i></td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td><i>WeatherGrabber</i></td>
    <td>*Designing*</td>
    <td></td>
    <td></td>
  </tr>
</table>

[Back to top](#weatherforecast)
<br>
 
## *Compiling and Running*

TBD

[Back to top](#weatherforecast)
<br>

## *How to Use*

TBD

[Back to top](#weatherforecast)
<br>