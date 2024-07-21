import java.util.Scanner

class Payment {
    fun processPayment(totalPayment: Int) {
        val scanner = Scanner(System.`in`)
        var paymentAmount: Int

        println("\nMetode pembayaran: ")
        println("1. Tunai")
        println("2. Debit")
        println("3. Qris")
        print("Pilih metode pembayaran: ")
        val paymentMethod = scanner.nextInt()

        when (paymentMethod) {
            1 -> {
                println("Anda memilih pembayaran tunai.")
                do {
                    print("Masukkan Uang Pembayaran: Rp ")
                    paymentAmount = scanner.nextInt()
                    if (paymentAmount < totalPayment) {
                        println("Maaf, uang Anda tidak mencukupi. Silakan masukkan uang lagi.")
                    }
                } while (paymentAmount < totalPayment)
                val change = paymentAmount - totalPayment
                println("Kembalian Anda: Rp $change")
            }
            2 -> {
                println("Anda memilih pembayaran debit.")
                // No need for additional input or change calculation
                println("Pembayaran debit sebesar Rp $totalPayment berhasil diproses.")
            }
            3 -> {
                println("Anda memilih pembayaran Qris.")
                // No need for additional input or change calculation
                println("Pembayaran Qris sebesar Rp $totalPayment berhasil diproses.")
            }
            else -> {
                println("Metode pembayaran tidak valid.")
                return
            }
        }
    }
}