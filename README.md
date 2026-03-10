Modul 4 Refleksi: 

1. Refleksi berdasarkan pertanyaan evaluasi Percival (2017) mengenai alur TDD:

Berdasarkan evaluasi Percival, alur TDD ini sangat berguna. Pendekatan Red-Green-Refactor memaksa saya memikirkan edge cases sejak awal dan memberikan 
rasa aman bahwa logika kode tidak rusak saat melakukan refactoring (misal saat mengubah string menjadi Enum).

Yang perlu diperbaiki ke depannya: Saya harus lebih teliti menyiapkan mock data atau dependensi tes pada fase Red. 
Sebelumnya, asumsi setup tes yang kurang lengkap (seperti memasukkan list product kosong pada Order) sempat menyebabkan 
NullPointerException. Ke depannya, saya akan memastikan struktur objek dipahami secara utuh sebelum mulai menulis unit test.

2. Secara keseluruhan, unit test yang saya buat sudah cukup baik dalam mengikuti prinsip F.I.R.S.T, meskipun ada beberapa pelajaran penting yang saya dapatkan:

-Fast (Cepat): Ya, unit test saya untuk Model, Repository, dan Service tidak bergantung pada database eksternal atau network, sehingga dapat dieksekusi dalam hitungan milidetik.

-Independent (Mandiri): Awalnya ada sedikit kelemahan di sini. Namun, setelah direfaktor, saya menggunakan anotasi @BeforeEach untuk selalu membuat objek Order, Payment, dan 
Product baru sebelum setiap fungsi tes dijalankan. Ini memastikan satu tes tidak akan memengaruhi state (kondisi) tes lainnya.

-Repeatable (Dapat Diulang): Ya, tes saya memberikan hasil yang sama baik saat dijalankan di local environment (IntelliJ/Terminal) maupun nantinya saat dijalankan di CI/CD (pipeline otomatis).

-Self-Validating (Memvalidasi Sendiri): Ya, saya secara ketat menggunakan fungsi assertEquals(), assertNull(), dan assertThrows() dari JUnit 5. Tes akan otomatis memberikan 
status merah/hijau tanpa saya harus melakukan pengecekan log manual (print statement).

-Timely (Tepat Waktu): Ya, sesuai dengan metode TDD, saya membuat kerangka pengujian terlebih dahulu (fase Red) sebelum mengimplementasikan fungsionalitas di PaymentController, PaymentServiceImpl, dan Model Payment (fase Green).

Hal yang perlu saya lakukan ke depannya:
Saya perlu lebih memperhatikan prinsip Independent dan Timely dalam hal menyiapkan Mocking. Ke depannya, saya akan memastikan bahwa dummy data yang saya buat pada tahapan awal pengujian tidak memiliki ketergantungan 
tersembunyi dengan model lain agar tes benar-benar terisolasi dengan baik.


-------------------------------------------------------------------------------------------------------------------
Modul 3 Refleksi:
Implementasi prinsip SOLID: 

1. Single Responsibility Principle (SRP)

Sebelumnya, kelas CarController diimplementasikan di dalam file yang sama dengan ProductController.java (sebagai inner class). 
Hal ini melanggar SRP karena satu file memiliki dua tanggung jawab yang berbeda yaitu menangani rute untuk product dan menangani rute untuk Car. 
Oleh karena itu, saya telah mengubahnya dengan memisahkan CarController ke dalam filenya sendiri (CarController.java). 
Dengan pemisahan ini, ProductController hanya memiliki satu alasan untuk berubah (yaitu jika ada perubahan logika pada produk), dan CarController hanya berurusan dengan logika mobil.

2. Open-Closed Principle (OCP)

Saya telah mengimplementasikan OCP melalui penggunaan interface pada lapisan Service, yaitu ProductService dan CarService. 
Arsitektur ini memungkinkan sistem untuk terbuka terhadap perluasan (open for extension) namun tertutup dari modifikasi (closed for modification). 
contoh: Jika suatu saat nanti saya perlu menambahkan tipe mobil baru (misalnya ElectricCarServiceImpl), 
saya cukup membuat kelas baru yang mengimplementasikan interface CarService tanpa perlu memodifikasi atau merusak kode pada interface itu sendiri ataupun pada CarController.

3. Liskov Substitution Principle (LSP)

Sebelumnya, kode saya melanggar LSP karena CarController melakukan pewarisan (extends) dari ProductController. 
Padahal secara konsep, CarController bukanlah sebuah ProductController, dan fungsi-fungsi rutenya sangat berbeda. 
Jika instance ProductController diganti (disubstitusi) secara paksa oleh CarController, jalannya program (terutama mapping routing) akan menjadi salah. 
Oleh karena itu, saya telah memperbaikinya dengan menghapus  extends ProductController pada CarController, sehingga CarController kini berdiri sendiri sebagai 
entitas Controller yang independen.

4. Interface Segregation Principle (ISP)

Saya telah mengimplementasikan ISP di dalam sistem ini. Alih-alih membuat satu interface raksasa yang menggabungkan seluruh method untuk produk dan mobil , 
saya memisahnya menjadi interface yang lebih kecil dan spesifik yaitu ProductService hanya berisi keterikatan untuk produk, dan CarService khusus untuk mobil. 
Dengan demikian, ProductController tidak dipaksa untuk mengimplementasikan atau bergantung pada method yang sama sekali tidak relevan dengannya seperti updateCar().

5. Dependency Inversion Principle (DIP)

 Sebelumnya, kode saya melanggar DIP karena modul tingkat tinggi (CarController) bergantung langsung pada modul tingkat rendah berupa kelas konkret (CarServiceImpl), 
 dibuktikan dengan deklarasi @Autowired private CarServiceImpl carservice;. Saya telah memperbaiki kode tersebut dengan mengganti referensinya menjadi antarmuka/abstraksinya: 
 @Autowired private CarService carService; dengan cara ini, Controller tidak lagi terikat pada implementasi detail, melainkan bergantung pada abstraksi.


Refleksi: 

1) Dalam project ini, saya telah menerapkan kelima prinsip SOLID:

-Single Responsibility Principle (SRP) 
Saya memisahkan CarController dari ProductController. Sebelumnya, CarController menumpang di dalam file yang sama dan menangani 
dua entitas yang berbeda. Sekarang, ProductController hanya bertanggung jawab atas alur logika Produk, dan CarController khusus untuk Mobil.

-Open-Closed Principle (OCP)
Saya menggunakan antarmuka (interface) seperti CarService. Struktur ini memungkinkan penambahan fungsionalitas baru (misalnya membuat kelas ElectricCarServiceImpl di masa depan) 
tanpa perlu memodifikasi kode antarmuka maupun Controller yang sudah ada.

-Liskov Substitution Principle (LSP) 
Saya menghapus relasi pewarisan (extends ProductController) pada CarController. Sebelumnya, karena CarController mewarisi ProductController, 
hal ini melarang hierarki karena entitas rute mobil bukanlah sebuah produk. Kini CarController berdiri sebagai entitas mandiri.

-Interface Segregation Principle (ISP)
Antarmuka dipisahkan secara spesifik. Operasi produk diatur dalam ProductService dan operasi mobil dalam CarService. 
Hal ini memastikan klien tidak dipaksa untuk bergantung pada method yang tidak mereka gunakan (misalnya ProductController tidak perlu tahu soal fungsi updateCar).

Dependency Inversion Principle (DIP) 
Pada Controller, saya mengubah dependensi dari kelas konkret (implementasi) menjadi abstraksinya. 
Contohnya, @Autowired private CarServiceImpl carservice; diubah menjadi @Autowired private CarService carService;.

2) Explain the advantages of applying SOLID principles to your project with examples.

Menerapkan prinsip SOLID membuat perangkat lunak jauh lebih mudah dipelihara (maintainable), diuji (testable), dan dikembangkan (extensible).

- Fleksibilitas Tinggi (DIP & OCP)
  Dengan bergantung pada abstraksi (CarService), Controller menjadi kebal terhadap perubahan di level bawah. 
  Jika suatu saat algoritma penyimpanan data diubah (misalnya dari memori lokal ke database sungguhan), 
  saya hanya perlu membuat kelas implementasi baru yang meng-implements CarService tanpa perlu mengutak-atik atau merusak baris kode di CarController.

- Lokalisasi Bug & Keterbacaan (SRP): 
  karena ProductController dan CarController sudah dipisah, file menjadi lebih ringkas. Jika terjadi bug khusus pada halaman Edit Car, 
  saya tahu persis harus mencari di kelas CarController. Pencarian masalah menjadi sangat fokus dan tidak berisiko merusak logika fungsionalitas Produk.

3) Explain the disadvantages of not applying SOLID principles to your project with examples.

- Risiko Kerusakan Tidak Terduga (LSP Violation)
  sebelumnya, CarController melakukan extends pada ProductController. Jika suatu saat developer lain menambahkan logika keamanan (security filter) 
  atau mengubah rute default di ProductController, CarController akan ikut terdampak atau bahkan error karena ia mewarisi sifat yang tidak ia butuhkan secara paksa.

- Keterikatan yang Kuat / Tightly Coupled (DIP Violation)
  Saat CarController bergantung langsung pada CarServiceImpl (kelas konkret), Controller dan Service menjadi saling mengunci. Jika implementasi CarServiceImpl dihapus, diubah namanya, 
  atau diganti parameter konstruktornya, Controller akan langsung error. Hal ini juga membuat kode sangat sulit untuk diuji (Unit Testing), karena kita tidak bisa menyisipkan mock object dengan mudah.

------------------------------------------------------------------------------------------------------------------------------
Modul 2 Refleksi:

Selama pengerjaan latihan, beberapa masalah kualitas kode yang perlu saya perbaiki:

-Inkompatibilitas Versi Gradle dan Plugin: Terjadi error getConvention() karena penggunaan Gradle 9.x atau versi Spring Boot yang tidak stabil dengan plugin SonarCloud lama. Strategi saya adalah melakukan downgrade Gradle ke versi stabil (8.10.2) dan memperbarui plugin org.sonarqube ke versi 5.x yang sudah mendukung internal API Gradle terbaru.

-Kesalahan Konfigurasi Analisis Kualitas (SonarCloud): Analisis gagal karena ketidaksesuaian antara sonar.projectKey dan sonar.organization (masalah case-sensitivity dan mismatch nama). Strategi saya adalah menyinkronkan key di file build.gradle.kts agar identik dengan yang terdaftar di dasbor SonarCloud, serta memastikan penggunaan huruf kecil (lowercase) pada nama organisasi.

-Laporan Coverage Unit Test: SonarCloud awalnya tidak bisa membaca hasil cakupan tes. Strategi saya adalah mengonfigurasi plugin Jacoco agar secara eksplisit menghasilkan laporan dalam format XML (xml.required.set(true)) dan mengarahkan properti sonar.coverage.jacoco.xmlReportPaths ke lokasi file tersebut agar data coverage dapat diunggah dan dianalisis.

Implementasi saat ini sudah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD). Alur CI terpenuhi karena setiap kali ada perubahan kode yang di-push ke repositori GitHub, sistem secara otomatis menjalankan automated testing dan analisis statis menggunakan SonarCloud untuk memastikan tidak ada bug atau penurunan kualitas kode yang masuk ke main branch. Sementara itu, aspek CD terpenuhi melalui integrasi otomatis yang melakukan build image Docker dan melakukan deployment ke platform PaaS setelah semua tahap pengujian dan verifikasi dinyatakan berhasil. Dengan demikian, seluruh siklus dari penulisan kode hingga aplikasi dapat diakses oleh pengguna sudah berjalan secara otomatis tanpa memerlukan intervensi manual yang berulang.

link deploy : 
minor-raynell-a-jovanus-irwan-2406434140-69fce545.koyeb.app/

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Modul 1 Refleksi
Refleksi 1 :
Refleksi Implementasi Clean Code dan Secure Coding
Pada modul ini, saya telah mengimplementasikan dua fitur baru menggunakan Spring Boot, yaitu fitur Create Product dan Edit/Delete Product. Setelah melakukan pengecekan ulang terhadap source code yang telah dibuat, berikut adalah refleksi saya terkait penerapan prinsip clean code dan secure coding practices, serta beberapa hal yang masih dapat diperbaiki.

Clean Code Principles yang saya terapkan
Single Responsibility Principle (SRP) Setiap kelas memiliki tanggung jawab yang jelas. Contohnya, kelas Product hanya merepresentasikan data produk, sedangkan ProductController hanya menangani alur request dan response dari user.

Penamaan Variabel dan Method yang Jelas Penamaan seperti productName, productQuantity, createProductPage, dan deleteProduct sudah cukup deskriptif sehingga memudahkan pembacaan dan pemahaman kode.

Menghindari Hard Code yang Tidak Perlu ID produk di-generate menggunakan UUID, sehingga tidak ada nilai statis atau hard-coded yang berpotensi menimbulkan konflik data.

Secure Coding Practices yang Telah Diterapkan
Mencegah Manipulasi Data dari Client productId tidak diinput langsung oleh user melalui form, melainkan di-generate di server. Hal ini mengurangi risiko manipulasi data oleh client.
2.Pemrosesan Data di Server Side Logika utama seperti pembuatan ID dan validasi data dilakukan di backend, bukan di sisi frontend.

Hal yang Masih Bisa Diperbaiki
Error Handling yang Lebih Baik Saat ini, belum ada penanganan error dan validasi masih tidak ada. Ke depannya, dapat ditambahkan custom error page atau global exception handler agar feedback ke user lebih informatif.

Penggunaan DTO (Data Transfer Object) Saat ini, entity Product masih langsung digunakan untuk binding form. Untuk aplikasi yang lebih besar, akan lebih baik jika menggunakan DTO agar entity tidak terekspos langsung.

Kesimpulan
Secara keseluruhan, implementasi fitur sudah mengikuti prinsip clean code dan secure coding dasar dengan cukup baik. Struktur kode sudah rapi, mudah dibaca, dan relatif aman dari input tidak valid. Namun, masih terdapat beberapa ruang perbaikan agar kode menjadi lebih scalable, maintainable, dan aman untuk pengembangan jangka panjang.


Refleksi 2: 

Evaluasi Unit Testing & Code Coverage
- Setelah mempraktikkan pembuatan Unit Test, saya menyadari bahwa tes yang baik memberikan rasa aman saat kita ingin mengubah kode di masa depan (refactoring).
Berapa banyak test? Jumlahnya harus proporsional dengan kompleksitas logika bisnis, mencakup semua jalur kode (branching), dan memvalidasi skenario sukses maupun gagal.
Code Coverage: Mencapai angka 100% code coverage bukan berarti aplikasi kita sudah sempurna tanpa cacat. Coverage hanya menunjukkan baris mana yang sudah dijalankan saat tes, namun tidak menjamin bahwa logika di dalam baris tersebut sudah benar secara fungsional untuk segala kondisi input.

-Kualitas Kode pada Functional Test
Mengenai pembuatan functional test suite baru yang hanya menyalin kode (copy-paste) dari suite sebelumnya:
Masalah Kualitas: Hal ini menciptakan redundansi kode yang tinggi. Jika terdapat perubahan pada infrastruktur (seperti perubahan port atau base URL), kita harus memperbarui banyak file secara manual yang rentan terhadap kesalahan manusia.
Rekomendasi Perbaikan: Sebaiknya dibuat sebuah class induk (Base Class) yang menampung konfigurasi umum. File-file test lainnya cukup mewarisi class tersebut, sehingga kode lebih ringkas, terorganisir, dan mengikuti prinsip keberlanjutan kode yang baik.
