import java.util.*
import kotlin.system.exitProcess

class FurnitureStore {
    private var furnitureList = listOf(
        Furniture("Kursi", 200000, 5),
        Furniture("Meja", 350000, 5),
        Furniture("Sofa", 500000, 5),
        Furniture("Kasur", 900000, 5)
    )
    private var orders = mutableListOf<Order>()
    private var totalOrder = 0
    private var customerName = ""

    fun showFurnitureList() {
        println("\n=== Daftar Furniture ===")
        for ((index, furniture) in furnitureList.withIndex()) {
            println("${index + 1}. ${furniture.name} - Rp ${furniture.price}/unit ( Tersedia: ${furniture.available} unit)")
        }
    }

    private fun findFurniture(name: String): Furniture? {
        return furnitureList.find { it.name.equals(name, ignoreCase = true) }
    }

    fun orderFurniture() {
        val scanner = Scanner(System.`in`)
        while (true) {
            print("Masukkan Nama Furniture yang ingin dipesan : ")
            val furnitureName = scanner.nextLine()
            val furniture = findFurniture(furnitureName)

            if (furniture == null) {
                print("Furniture tidak ditemukan.\n")
                continue
            }
            print("Masukkan jumlah unit : ")
            val quantity = scanner.nextInt()

            // Memeriksa ketersediaan furniture
            if (furniture.available < quantity) {
                print("Maaf, unit tersebut tidak tersedia untuk $quantity unit.\n")
                continue
            }

            // Mengurangi ketersediaan furniture
            furniture.available -= quantity

            // Menghitung total harga untuk 1 pemesanan
            val totalPrice = furniture.price * quantity

            // Menyimpan data pemesanan
            orders.add(Order(furnitureName, quantity, totalPrice))
            totalOrder += totalPrice

            // Tampilkan detail pemesanan
            print("\nPemesanan untuk $furnitureName berhasil!\n")
            print("Jika Ingin menambah pemesanan, Silahkan pilih 1 Kembali ke Menu Utama\n")
            manageOrders()
            break
        }
    }

    private fun manageOrders() {
        val scanner = Scanner(System.`in`)

        while (true) {
            print("\n1. Kembali ke Menu Utama | 2. Edit Transaksi | 3. Hapus Transaksi | 4. Pembayaran | 5. Keluar : ")
            when (scanner.nextInt()) {
                1 -> return
                2 -> editTransaction()
                3 -> deleteTransaction()
                4 -> processPayment()
                5 -> {
                    println("\nTerima kasih telah berbelanja di toko kami, $customerName!")
                    exitProcess(0)
                }
                else -> println("Pilihan tidak valid. Masukkan angka 1 - 5.")
            }
        }
    }

    private fun editTransaction() {
        val scanner = Scanner(System.`in`)
        print("Masukkan Nama Furniture yang ingin diedit: ")
        val furnitureName = scanner.nextLine()
        val orderIndex = orders.indexOfFirst { it.furnitureName.equals(furnitureName, ignoreCase = true) }

        if (orderIndex == -1) {
            println("Transaksi tidak ditemukan.\n")
            return
        }

        print("Masukkan jumlah unit baru: ")
        val newQuantity = scanner.nextInt()
        val furniture = findFurniture(furnitureName) ?: return

        // Mengembalikan ketersediaan furniture
        furniture.available += orders[orderIndex].quantity

        // Mengurangi ketersediaan furniture dengan jumlah baru
        if (furniture.available < newQuantity) {
            println("Maaf, unit tersebut tidak tersedia untuk $newQuantity unit.\n")
            return
        }

        furniture.available -= newQuantity
        val newTotalPrice = furniture.price * newQuantity
        orders[orderIndex] = Order(furnitureName, newQuantity, newTotalPrice)
        print("Transaksi berhasil diubah.\n")
    }

    private fun deleteTransaction() {
        val scanner = Scanner(System.`in`)
        print("Masukkan Nama Furniture yang ingin dihapus: ")
        val furnitureName = scanner.nextLine()
        val orderIndex = orders.indexOfFirst { it.furnitureName.equals(furnitureName, ignoreCase = true) }

        if (orderIndex == -1) {
            println("Transaksi tidak ditemukan.\n")
            return
        }

        val furniture = findFurniture(furnitureName) ?: return
        furniture.available += orders[orderIndex].quantity
        orders.removeAt(orderIndex)
        println("Transaksi berhasil dihapus.\n")
    }

    private fun payment(totalPayment: Int) {
        Payment().processPayment(totalPayment)
    }

    private fun processPayment() {
        if (orders.isEmpty()) {
            println("Tidak ada transaksi yang tersedia untuk pembayaran.")
            return
        }
        print("Masukkan Nama Anda : ")
        val scanner = Scanner(System.`in`)
        customerName = scanner.nextLine()
        println("\t\t====================== Nota Pembayaran ======================")
        println("\t\tNama Pemesan: $customerName\n")
        println("\t\tNama Furniture\t\tJumlah Unit\tTotal Harga")

        var totalPayment = 0
        for (order in orders) {
            println("\t\t${order.furnitureName}\t\t${order.quantity}\t\tRp ${order.totalPrice}")
            totalPayment += order.totalPrice
        }

        println("\t\tTotal Semua: Rp $totalPayment")

        if (totalPayment > 500000) {
            val discount = totalPayment * 15 / 100
            totalPayment -= discount
            println("\t\t~ Anda mendapatkan Diskon 15% sebesar Rp $discount ~")
        }
        println("\t\tTotal Pembayaran: Rp $totalPayment")
        println("\t\t==============================================================")
        payment(totalPayment)
    }
}
