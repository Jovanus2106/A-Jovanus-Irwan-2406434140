# A-Jovanus-Irwan-2406434140
AdvancedProgramming

# Refleksi Implementasi Clean Code dan Secure Coding

Pada modul ini, saya telah mengimplementasikan dua fitur baru menggunakan Spring Boot, yaitu fitur Create Product dan Edit/Delete Product. Setelah melakukan pengecekan ulang terhadap source code yang telah dibuat, 
berikut adalah refleksi saya terkait penerapan prinsip clean code dan secure coding practices, serta beberapa hal yang masih dapat diperbaiki.

## Clean Code Principles yang saya terapkan 

1. Single Responsibility Principle (SRP)
   Setiap kelas memiliki tanggung jawab yang jelas. Contohnya, kelas `Product` hanya merepresentasikan data produk, sedangkan `ProductController` hanya menangani alur request dan response dari user.

2. Penamaan Variabel dan Method yang Jelas
   Penamaan seperti `productName`, `productQuantity`, `createProductPage`, dan `deleteProduct` sudah cukup deskriptif sehingga memudahkan pembacaan dan pemahaman kode.

3. Menghindari Hard Code yang Tidak Perlu
   ID produk di-generate menggunakan `UUID`, sehingga tidak ada nilai statis atau hard-coded yang berpotensi menimbulkan konflik data.

## Secure Coding Practices yang Telah Diterapkan

1. Mencegah Manipulasi Data dari Client
   `productId` tidak diinput langsung oleh user melalui form, melainkan di-generate di server. Hal ini mengurangi risiko manipulasi data oleh client.

2.Pemrosesan Data di Server Side
   Logika utama seperti pembuatan ID dan validasi data dilakukan di backend, bukan di sisi frontend.

## Hal yang Masih Bisa Diperbaiki

1. Error Handling yang Lebih Baik
   Saat ini, belum ada penanganan error dan validasi masih tidak ada. Ke depannya, dapat ditambahkan *custom error page* atau *global exception handler* agar feedback ke user lebih informatif.

2. Penggunaan DTO (Data Transfer Object)
   Saat ini, entity `Product` masih langsung digunakan untuk binding form. Untuk aplikasi yang lebih besar, akan lebih baik jika menggunakan DTO agar entity tidak terekspos langsung.

## Kesimpulan

Secara keseluruhan, implementasi fitur sudah mengikuti prinsip clean code dan secure coding dasar dengan cukup baik. Struktur kode sudah rapi, mudah dibaca, dan relatif aman dari input tidak valid. Namun, masih terdapat beberapa ruang perbaikan agar kode menjadi lebih scalable, maintainable, dan aman untuk pengembangan jangka panjang.
