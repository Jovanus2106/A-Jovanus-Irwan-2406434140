Modul 2 Refleksi:

Selama pengerjaan latihan, beberapa masalah kualitas kode yang perlu saya perbaiki:

-Inkompatibilitas Versi Gradle dan Plugin: Terjadi error getConvention() karena penggunaan Gradle 9.x atau versi Spring Boot yang tidak stabil dengan plugin SonarCloud lama. Strategi saya adalah melakukan downgrade Gradle ke versi stabil (8.10.2) dan memperbarui plugin org.sonarqube ke versi 5.x yang sudah mendukung internal API Gradle terbaru.

-Kesalahan Konfigurasi Analisis Kualitas (SonarCloud): Analisis gagal karena ketidaksesuaian antara sonar.projectKey dan sonar.organization (masalah case-sensitivity dan mismatch nama). Strategi saya adalah menyinkronkan key di file build.gradle.kts agar identik dengan yang terdaftar di dasbor SonarCloud, serta memastikan penggunaan huruf kecil (lowercase) pada nama organisasi.

-Laporan Coverage Unit Test: SonarCloud awalnya tidak bisa membaca hasil cakupan tes. Strategi saya adalah mengonfigurasi plugin Jacoco agar secara eksplisit menghasilkan laporan dalam format XML (xml.required.set(true)) dan mengarahkan properti sonar.coverage.jacoco.xmlReportPaths ke lokasi file tersebut agar data coverage dapat diunggah dan dianalisis.

Implementasi saat ini sudah memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD). Alur CI terpenuhi karena setiap kali ada perubahan kode yang di-push ke repositori GitHub, sistem secara otomatis menjalankan automated testing dan analisis statis menggunakan SonarCloud untuk memastikan tidak ada bug atau penurunan kualitas kode yang masuk ke main branch. Sementara itu, aspek CD terpenuhi melalui integrasi otomatis yang melakukan build image Docker dan melakukan deployment ke platform PaaS setelah semua tahap pengujian dan verifikasi dinyatakan berhasil. Dengan demikian, seluruh siklus dari penulisan kode hingga aplikasi dapat diakses oleh pengguna sudah berjalan secara otomatis tanpa memerlukan intervensi manual yang berulang.

link deploy : minor-raynell-a-jovanus-irwan-2406434140-69fce545.koyeb.app/
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
