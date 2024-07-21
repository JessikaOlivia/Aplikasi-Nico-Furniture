import java.util.*
import kotlin.system.exitProcess

fun main() {
    val furnitureStore = FurnitureStore()
    val scanner = Scanner(System.`in`)
    println("\nSelamat Datang Di Toko Niko Furniture!")
    while (true) {
        println("\n=== Menu Utama ===")
        println("1. Lihat Daftar Furniture")
        println("2. Pesan Furniture")
        println("3. Keluar")
        print("Pilih opsi: ")
        when (scanner.nextInt()) {
            1 -> furnitureStore.showFurnitureList()
            2 -> furnitureStore.orderFurniture()
            3 -> {
                println("Terima kasih telah berbelanja di toko kami!")
                exitProcess(0)
            }
            else -> println("Pilihan tidak valid. Masukkan angka 1 - 3.")
        }
    }
}